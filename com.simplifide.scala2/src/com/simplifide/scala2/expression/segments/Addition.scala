/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.basic.fixed.AdditionTerm
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError
import scala.collection.mutable.ListBuffer

class Addition(val first:Expression,val terms:List[Term]) extends Expression {

 
  def createSegment(statements:List[StatementSegment]):StatementSegment = 
     new AdditionSegment.Trunc(statements)
  
  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    // Convert all of the expressions 
    val firstRes1:ExpressionReturn =  first.convertExpression(context)
    val segs:List[ExpressionReturn] = terms.map(x => x.convertExpression(context))
    val allSegments:List[ExpressionReturn]  = List(firstRes1) ::: segs
    // Create a list of errors for the total list of segments
    val err:List[InterfaceError] = allSegments.flatMap(x => x.errors)
    val rerr:List[InterfaceError] = err.filter(x => x.isError)
    // Return the list of errors if there is an actual error rather than warning or info
    if (rerr.length > 0) return new ExpressionReturn(None,None,err)
    
    // Create the list of statements
    val firstRes:StatementSegment = new AdditionTerm.Empty(firstRes1.statement.get)
    val otherRes:List[StatementSegment] = segs.map(x => x.statement.get)
    val totRes:List[StatementSegment] = List(firstRes) ::: otherRes
    
    return new ExpressionReturn(None,Some(createSegment(totRes)),err)
   
    
  }


}

