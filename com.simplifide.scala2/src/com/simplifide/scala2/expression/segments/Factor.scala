/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.fixed.AdditionTerm
import com.simplifide.scala2.core.basic.fixed.MultiplyFactor
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.core.basic.StatementSegment


class Factor(val value:Expression) extends Expression {
  
  def createFactor(seg:StatementSegment):StatementSegment = null
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
        val value1:ExpressionReturn = value.convertExpression(context)
        // Passes the error through with or without the addition term
        value1.statement match { 
        case Some(x) => new ExpressionReturn(value1.code,Some(createFactor(x)),value1.errors)
        case None    => value1
    }  
  }
}

object Factor {

  class Mult(override val value:Expression) extends Factor(value) {
      override def createFactor(seg:StatementSegment):StatementSegment = return new MultiplyFactor.MultTerm(seg)
        
      }
    
    
   class Div(override val value:Expression)  extends Factor(value) {
      override def createFactor(seg:StatementSegment):StatementSegment = return new MultiplyFactor.MultTerm(seg)
    }
 }
