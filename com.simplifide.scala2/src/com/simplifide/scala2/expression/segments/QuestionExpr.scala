/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.fixed.QuestionSegment
import com.simplifide.scala2.expression.ExpressionReturn

class QuestionExpr(val condition:Expression, val tr:Expression, val fa:Expression) extends Expression {
  
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    val cond:ExpressionReturn =  condition.convertExpression(context)
    val tru:ExpressionReturn  =  tr.convertExpression(context)
    val fal:ExpressionReturn  =  fa.convertExpression(context)
    
    val errors = cond.errors ::: tru.errors ::: fal.errors
    
    cond.statement match {
      case None    => return new ExpressionReturn(None,None,errors)
      case Some(x) => {
          tru.statement match {
            case None    => return new ExpressionReturn(None,None,errors)
            case Some(y) => {
                fal.statement match {
                  case None => return new ExpressionReturn(None,None,errors)
                  case Some(z) => return new ExpressionReturn(None,Some(new QuestionSegment(x,y,z)),errors)
                }
            }
          }
      }
    }
    
  }
}
