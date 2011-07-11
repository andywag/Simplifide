/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars.BaseConstant
import com.simplifide.scala2.expression.ExpressionReturn

class ParameterCall(val value:Float) extends Expression{

  override def convertExpression(context:ExpressionContext):ExpressionReturn = 
    new ExpressionReturn(None,Some(new BaseConstant.BaseFloat(value)),List())
  
  
}

object ParameterCall {
  
    class Integer(val value:Int) extends Expression {
       override def convertExpression(context:ExpressionContext):ExpressionReturn = 
        new ExpressionReturn(None,Some(new BaseConstant.BaseFloat(value)),List())
    }
}
