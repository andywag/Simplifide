/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.flop

import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.basic.operator._
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.context.CurrentContext
/**
 * Flop which contains the structure of the flop but has a unique reset and enable
 * statement
 */
class ResetEnableFlop(val name:Option[String],
					  val head:FlopControl,
					  val res:BaseCodeSegment,
					  val ena:BaseCodeSegment) extends StatementSegment.Simple {

  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {

      val body = head.getReset match {
        case Some(x) => {  // If a reset exists create an initial segment
            val ifelse = new IfElseStatement()
            val cond = if (x.activeLow) new UnaryNot(x) else x;
            ifelse.addClause(Some(cond), res)       // Reset Clause
            ifelse.addClause(head.getEnable(),ena)  // Enable Clause
            ifelse
        }
        case None => {     // If there isn't a reset create the enable statement
          head.getEnable match {
            case Some(x) => {
               val ifelse = new IfElseStatement()
               ifelse.addClause(Some(x),ena)
               ifelse
            }
            case None    => ena
          }

        }
      }

      val fl = new TopFlop(name,head,body)
      fl.createVerilogCode(writer)
  }


   private def createEnable:BaseCodeSegment = {
      val clkTick = new TickOperator(head.clock,"event")
      val clkOne  = new OperatorEquals(head.clock,new QuoteSegment("1"))
      val cond:BaseCodeSegment = {
      head.enable match {
          case Some(x) => new OperatorAnd(clkTick,clkOne,x)
          case None    => new OperatorAnd(clkTick,clkOne)
          }
      }
      return cond
   }

  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {

      val ifelse = new IfElseStatement()
      head.getReset match {
        case Some(x) => {
            val cond = new OperatorEquals(x,new IdentSegment(if (x.activeLow) "'0'" else "'1'"));
            ifelse.addClause(Some(cond), res)
        }
        case None =>
      }
      ifelse.addClause(Some(createEnable),ena)
      val fl = new TopFlop(name,head,ifelse)
      fl.createVhdlCode(writer)

  }

}
