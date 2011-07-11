/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments


import com.simplifide.scala2.core.basic.fixed.AdditionTerm
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.core.basic.StatementSegment

abstract class Term(val value:Expression) extends Expression {
  
  /** Select between the add or the subtraction term */
  def createTerm(seg:StatementSegment):StatementSegment;
  
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    val value1:ExpressionReturn = value.convertExpression(context)
    // Passes the error through with or without the addition term
    value1.statement match { 
      case Some(x) => new ExpressionReturn(value1.code,Some(createTerm(x)),value1.errors)
      case None    => value1
    }
  }
}

object Term {
   class Add(override val value:Expression) extends Term(value) {
     override def createTerm(seg:StatementSegment):StatementSegment = 
       new AdditionTerm.AddTerm(seg)
     
    /*
    override def convertExpression(context:ExpressionContext):ExpressionReturn = {
        val value1 = AdditionTerm.AddTerm(value.convertExpression(context).statement.get)
        return ExpressionReturn.segment(value1)
    }*/
      
   }
   class Sub(override val value:Expression) extends Term(value) {
     override def createTerm(seg:StatementSegment):StatementSegment = 
        new AdditionTerm.SubTerm(seg)
       /*
     override def convertExpression(context:ExpressionContext):ExpressionReturn = {
       
   
      val value1 = AdditionTerm.SubTerm(value.convertExpression(context).statement.get)
        return ExpressionReturn.segment(value1)
    }*/
   }
}