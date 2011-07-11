/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core._
import com.simplifide.scala2.top.InterfaceError

abstract class SignalCallTop(val name:String, val line:Int) extends Expression {
  def getSignal(context:ExpressionContext):Option[BaseSignal]
  
}

class SignalCall(override val name:String,val index:Option[SignalIndex],override val line:Int) extends SignalCallTop(name,line) {
  
  def getSignal(context:ExpressionContext):Option[BaseSignal] = {
    val sig = context.getSignal(name)    
    if (sig == None) return None
    index match {
      case None    => return sig
      case Some(x) => return new Some(sig.get.getDelta(x))
      
    }
  }
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    getSignal(context) match {
      case Some(x) => ExpressionReturn.segment(x)
      case None    => {
          val error:InterfaceError = new InterfaceError.Error(line,"Signal " + name + " Not Found")
          return ExpressionReturn.error(error)
      }
    }
  }
    
}

class SignalIndex(val name:String,val scale:Option[Int], val offset:Option[Int], val sign:Option[Boolean]) {
  def getOffset:Option[Int] = {
    offset match {
      case None => return None
      case Some(x) => {
          sign match {
             case None    => return Some(x)
             case Some(y) => if (y) return Some(x) else return Some(-x) 
          }
      }
    }
  }
  
}

