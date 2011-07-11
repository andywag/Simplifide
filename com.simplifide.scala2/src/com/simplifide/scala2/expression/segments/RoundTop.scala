/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.basic.fixed.MultiplySegment
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError

abstract class RoundTop(val value:Expression, val fixed:FixedCall) extends Expression{
    def createAddition(statements:List[StatementSegment],context:ExpressionContext):RoundTop.AdditionReturn = {
      val fixR:FixedCall.Return = fixed.getFixedType(context)
      fixR.fixed match {
        case Some(x) => {
          val st = createAddition(statements,x)  
          return new RoundTop.AdditionReturn(Some(st),fixR.error)
        }
        case None    => return new RoundTop.AdditionReturn(None,fixR.error)
      }
    }
    
    def createMultiplication(first:StatementSegment,second:StatementSegment,context:ExpressionContext):RoundTop.MultiplyReturn = {
       val fixR:FixedCall.Return = fixed.getFixedType(context)
      fixR.fixed match {
        case Some(x) => {
          val st = createMultiplication(first,second,x)  
          return new RoundTop.MultiplyReturn(Some(st),fixR.error)
        }
        case None    => return new RoundTop.MultiplyReturn(None,fixR.error)
      }
    } 
    
    def createAddition(statements:List[StatementSegment],fix:FixedType):AdditionSegment
    def createMultiplication(first:StatementSegment,second:StatementSegment,fix:FixedType):MultiplySegment
  
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    val value1:ExpressionReturn = value.convertExpression(context)
    // Passes the error through with or without the addition term
    if (value1.statement == None) return value1
    val segs = List[StatementSegment](value.convertExpression(context).statement.get)
    val ret:RoundTop.AdditionReturn = createAddition(segs,context)
    ret.add match {
      case Some(x) => return new ExpressionReturn(None,ret.add,ret.errors)
      case None    => return new ExpressionReturn(None,None,ret.errors)
    }
    
  }
}

object RoundTop {
  
  class Round(override val value:Expression, override val fixed:FixedCall) extends RoundTop(value,fixed) {
    def createAddition(statements:List[StatementSegment],fix:FixedType):AdditionSegment      
    = new AdditionSegment.Round(statements,fix)
    def createMultiplication(first:StatementSegment,second:StatementSegment,fix:FixedType):MultiplySegment
    = new MultiplySegment.Round(first,second,fix)

  }
  class Trunc(override val value:Expression, override val fixed:FixedCall) extends RoundTop(value,fixed) {
    def createAddition(statements:List[StatementSegment],fix:FixedType):AdditionSegment       = 
      new AdditionSegment.TruncFixed(statements,fix)
    def createMultiplication(first:StatementSegment,second:StatementSegment,fix:FixedType):MultiplySegment =
      new MultiplySegment.TruncFixed(first,second,fix)
  }
  
  class Clip(override val value:Expression, override val fixed:FixedCall) extends RoundTop(value,fixed) {
    def createAddition(statements:List[StatementSegment],fix:FixedType):AdditionSegment       =
      new AdditionSegment.RoundClip(statements,fix)
    def createMultiplication(first:StatementSegment,second:StatementSegment,fix:FixedType):MultiplySegment = 
      new MultiplySegment.RoundClip(first,second,fix)
  }
  class RoundClip(override val value:Expression, override val fixed:FixedCall) extends RoundTop(value,fixed) {
    def createAddition(statements:List[StatementSegment],fix:FixedType):AdditionSegment       = 
      new AdditionSegment.RoundClip(statements,fix)
    def createMultiplication(first:StatementSegment,second:StatementSegment,fix:FixedType):MultiplySegment = 
      new MultiplySegment.RoundClip(first,second,fix)
  }
  class TruncClip(override val value:Expression, override val fixed:FixedCall) extends RoundTop(value,fixed) {
    def createAddition(statements:List[StatementSegment],fix:FixedType):AdditionSegment       = 
      new AdditionSegment.TruncClip(statements,fix)
    def createMultiplication(first:StatementSegment,second:StatementSegment,fix:FixedType):MultiplySegment = 
      new MultiplySegment.TruncClip(first,second,fix)
  }
  
  class AdditionReturn(val add:Option[AdditionSegment],val errors:List[InterfaceError])
  class MultiplyReturn(val add:Option[MultiplySegment],val errors:List[InterfaceError])
  
}