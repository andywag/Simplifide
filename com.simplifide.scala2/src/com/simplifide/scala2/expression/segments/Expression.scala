/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core._
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError

abstract class Expression{
  
  /** Converts the expression to a code segment or returns an error */
  def convertExpression(context:ExpressionContext):ExpressionReturn = null
  /** Converts the expression to a code segment assuming a possible round */
  //def convertExpression(context:ExpressionContext,round:RoundTop):ExpressionReturn = convertExpression(context)
  
}

object Expression {
  
  def combineExpressionErrors(returns:List[ExpressionReturn]):List[InterfaceError] = {
    returns.flatMap(x => x.errors)
  }
  
  
}