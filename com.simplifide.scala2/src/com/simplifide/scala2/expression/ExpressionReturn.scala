/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.top.InterfaceError

class ExpressionReturn(val code:Option[String], val statement:Option[StatementSegment], val errors:List[InterfaceError]) {}


object ExpressionReturn {
  def segment(code:String,statement:StatementSegment) = new ExpressionReturn(Some(code),Some(statement),List())
  def segment(statement:StatementSegment) = new ExpressionReturn(None,Some(statement),List())
  //def error(error:String):ExpressionReturn = new ExpressionReturn(None,None,List(error))
  def error(error:InterfaceError) = new ExpressionReturn(None,None,List(error))
  
}