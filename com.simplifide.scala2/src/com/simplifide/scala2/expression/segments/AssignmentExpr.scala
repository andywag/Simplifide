/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.top.InterfaceError

class AssignmentExpr(val output:SignalCallTop, val input:Expression, val text:Option[String]) extends Expression {
  
  private def trimText(text:String):String = {
    val strings = text.split("\n")
    if (strings.length == 0) return text
    var out:String = ""
    for (string <- strings) {
      val tr = string.trim
      if (!tr.startsWith("//") && !tr.startsWith("--")) {
    	  if (tr.endsWith(";")) out = string
      }
    }
    return out
  }
  
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    // Create the output.
    val out:Option[BaseSignal] = output.getSignal(context)
    out match { // If output not assigned return an error
      case Some(x) => {
          val value1 = input.convertExpression(context)
          value1.statement match { 
            case None    => return value1 // Input Error Case
            case Some(y) => { // Check to see if the code exists
                val state = text match {
                  case None    => new Statement(x,y)
                  case Some(z) => new Statement.WithCode(x,y,Some(trimText(z)))
                }
                return new ExpressionReturn(None,Some(state),value1.errors)
            }
          }
      }
      case None    => {
          val err = new InterfaceError.Error(output.line,"Signal " + output.name + " Not Found")
          ExpressionReturn.error(err)
      }
    }
    
  }
  
  
}
