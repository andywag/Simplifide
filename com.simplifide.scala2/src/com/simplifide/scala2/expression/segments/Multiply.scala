/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments


import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.basic.fixed.MultiplySegment
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.fixed._

class Multiply(val first:Expression,val second:Expression) extends Expression{

  
  
  def createSegment(first:StatementSegment,second:StatementSegment):StatementSegment = 
     new MultiplySegment.Trunc(first,second)
   
 
  
    // Normal Call to create the expresion
  override def convertExpression(context:ExpressionContext):ExpressionReturn =  {
    return convertExpression(context,None)
  }
 
  // Called when creating a round
  //override def convertExpression(context:ExpressionContext,round:RoundTop):ExpressionReturn =  {
  //  return convertExpression(context,Some(round))
  //}
  
   def convertExpression(context:ExpressionContext, round:Option[RoundTop]):ExpressionReturn = {
    // Convert all of the expressions 
    val firstRes1:ExpressionReturn =  first.convertExpression(context)
    val secondRes1:ExpressionReturn = second.convertExpression(context)
    //val allSegments:List[ExpressionReturn]  = List(firstRes1) ::: segs
    // Create a list of errors for the total list of segments
    val err:List[InterfaceError] = firstRes1.errors ::: secondRes1.errors
    val rerr:List[InterfaceError] = err.filter(x => x.isError)
    // Return the list of errors if there is an actual error rather than warning or info
    if (rerr.length > 0) return new ExpressionReturn(None,None,err)
    
    // Create the list of statements
    val firstRes:StatementSegment = new MultiplyFactor.Empty(firstRes1.statement.get)
    val secondRes:StatementSegment = secondRes1.statement.get
    //val totRes:List[StatementSegment] = List(firstRes) ::: otherRes
    
    round match {
      case None    => return new ExpressionReturn(None,Some(createSegment(firstRes,secondRes)),err)
      case Some(x) => {
          val add:RoundTop.MultiplyReturn =  x.createMultiplication(firstRes,secondRes,context)
          add.add match {
            case None    => return new ExpressionReturn(None,None,err ::: add.errors)
            case Some(y) => return new ExpressionReturn(None,Some(y),err ::: add.errors)
          }
      }
    }
    
   
    
  }

  
}



