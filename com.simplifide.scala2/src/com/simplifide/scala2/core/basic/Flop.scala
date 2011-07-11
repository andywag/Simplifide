/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.BaseCodeSegment

import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.basic.operator._
import scala.collection.mutable.HashMap
import com.simplifide.scala2.context.CurrentContext

import com.simplifide.scala2.clocks.FlopControl




class Flop(val name:Option[String],head:FlopControl, map:HashMap[BaseCodeSegment,FlopOutput]) extends BaseCodeSegment {

  private def createObject(writer:CodeWriter, segment:BaseCodeSegment):SegmentReturn = {
     val alw = new AlwaysSensitivity(name,segment,head.createSensitivityList())
     return writer.createCode(alw)
  }

  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    val segment = createVerilogSegment()
    return createObject(writer,segment)
  }

  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
    val segment = createVhdlSegment()
    return createObject(writer,segment)
  }

  
  private def getSegmentList(creator:(BaseCodeSegment,FlopOutput)=>BaseCodeSegment):BaseCodeSegment = {
    val buffer = new ListBufferSegment()
    for ((key, value) <- map) {
      val assign = creator(key,value);
      buffer.segments.append(assign)
    }
    return buffer;
  }



  private def getResetList():BaseCodeSegment = {
    val creator = (x:BaseCodeSegment,y:FlopOutput) =>
    new AssignStatement(x,y.reset match {case Some(s) => s; case _ => FlopReset})
    return getSegmentList(creator)
  }

  private def getAssignList():BaseCodeSegment = {
    val creator = (x:BaseCodeSegment,y:FlopOutput)=>new AssignStatement(x,y.out)
    return getSegmentList(creator)
  }
  
  
  private def createVerilogSegment():BaseCodeSegment = {
   
    val reset:Option[Reset] = head.getReset();

     if (reset == None) { // No Reset Clause
       val enable:Option[BaseCodeSegment] = head.getEnable();
       if (enable == None) { // No Enable As Well
         return this.getAssignList()
       }
       else {
         val ifelse = new IfElseStatement()
         ifelse.addClause(enable, getAssignList())
         return ifelse;
       }
    }
    else { // Creating Flop With Reset
      val ifelse = new IfElseStatement()
      val cond:Option[BaseCodeSegment] = {if (reset.get.activeLow) Some(new UnaryNot(reset.get)); else reset}
      ifelse.addClause(cond, getResetList()) // Adding Reset Clause
      ifelse.addClause(head.getEnable(), getAssignList())
      return ifelse
    }
   

  }

   private def createVhdlSegment():BaseCodeSegment = {

    def createResetStatement(reset:Reset,ifelse:IfElseStatement) {
       val cond:BaseCodeSegment = {
          if (reset.activeLow)  new OperatorEquals(reset,new QuoteSegment("0"));
          else                  new OperatorEquals(reset,new QuoteSegment("1"));
       }
       ifelse.addClause(Some(cond), getResetList) // Adding Reset Clause
    }

    def createClockEnableStatement(clock:BaseCodeSegment,enable:Option[BaseCodeSegment],ifelse:IfElseStatement) {
     
      val clkTick = new TickOperator(clock,"event")
      val clkOne  = new OperatorEquals(clock,new QuoteSegment("1"))
      val cond:BaseCodeSegment = {
        if (enable == None)  new OperatorAnd(clkTick,clkOne)
        else new OperatorAnd(clkTick,clkOne,enable.get)
      }
      
      ifelse.addClause(Some(cond), getAssignList())
    }

    val clock:Clock           = head.getClock;
    val reset:Option[Reset]   = head.getReset();
    val enable:Option[Enable] = head.getEnable(); // Creating Enable Clause
    val ifelse = new IfElseStatement();

    if (reset == None) { // Creating Flop With Reset
     createClockEnableStatement(clock,enable,ifelse)
    }
    else { // No Reset Clause
      createResetStatement(reset.get,ifelse) // Create the Reset Clause
      createClockEnableStatement(clock,enable,ifelse)
      
    }
    return ifelse
  }
  

}

object Flop {

  def simpleFlop(head:FlopControl,out:BaseCodeSegment,in:BaseCodeSegment):Flop = {
    val map = new HashMap[BaseCodeSegment,FlopOutput]();
    map.put(out, new FlopOutput(in,None));
    val flop = new Flop(None,head,map);
    return flop;
  }
}

class FlopOutput(val out:BaseCodeSegment,val reset:Option[BaseCodeSegment])

object FlopReset extends BaseCodeSegment {
  override def createVhdlCode(writer:CodeWriter):SegmentReturn    = SegmentReturn.segment("(others => '0')")
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment("0")
}
