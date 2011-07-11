/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.fixed.ParenSegment
import com.simplifide.scala2.expression.ExpressionReturn

class Paren(val value:Expression) extends Expression {
  
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    val value1:ExpressionReturn = value.convertExpression(context)
    // Passes the error through with or without the addition term
    value1.statement match {
      case Some(x) => ExpressionReturn.segment(new ParenSegment(x))
      case None    => return value1
    }
  }
  
}


