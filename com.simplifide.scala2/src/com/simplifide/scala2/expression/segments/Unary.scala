/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.fixed.ParenSegment
import com.simplifide.scala2.core.basic.statement.UnarySegment
import com.simplifide.scala2.expression.ExpressionReturn

class Unary(val value:Expression) extends Expression {
  
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    val value1:ExpressionReturn = value.convertExpression(context)
    value1.statement match {
      case Some(x) => return new ExpressionReturn(None,Some(new UnarySegment.Negative(x)),value1.errors)
      case None    => return new ExpressionReturn(None,None,value1.errors)
    }
  }
  
}





