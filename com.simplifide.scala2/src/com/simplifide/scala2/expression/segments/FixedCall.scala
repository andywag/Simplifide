/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError
import com.simplifide.scala2.core.basic.vars._

abstract class FixedCall extends Expression {
  
  def getFixedType(context:ExpressionContext):FixedCall.Return;
  def getFixedBasic:Option[FixedType] = None
  
 
}

object FixedCall {
  
  class Param(val name:String, val line:Int) extends FixedCall  {
    def getSignal(context:ExpressionContext):Option[BaseSignal] = {
      val sig = context.getSignal(name)    
      return sig
    }
  
    override def convertExpression(context:ExpressionContext):ExpressionReturn = {
      getSignal(context) match {
      case Some(x) => ExpressionReturn.segment(x)
      case None    => {
          val error:InterfaceError = new InterfaceError.Error(line,"Parameter " + name + " not found")
          return ExpressionReturn.error(error)
        }
      }
    }
    
    override def getFixedType(context:ExpressionContext):Return = {
      getSignal(context) match {
      case Some(x) =>  {
          if (x.isInstanceOf[FixedType]) return new Return(Some(x.asInstanceOf[FixedType]),List())
          return new Return(None,List(new InterfaceError.Error(line,"Parameter " + name + " must be a fixed type")))
      }
      case None    => {
          val error:InterfaceError = new InterfaceError.Error(line,"Parameter " + name + " not found")
          return new Return(None,List(error))
        }
      }
    }
    
  }
  
  class Fixed(val fixed:FixedType) extends FixedCall {
    override def getFixedType(context:ExpressionContext):Return = new Return(Some(fixed),List())
    override def getFixedBasic:Option[FixedType] = Some(fixed)
  }
  
  class Return(val fixed:Option[FixedType], val error:List[InterfaceError])
  
}


