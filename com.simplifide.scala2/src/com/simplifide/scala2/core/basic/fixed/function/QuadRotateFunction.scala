/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed.function

import com.simplifide.scala2.core.basic.vars.Signing
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.basic.fixed.FixedSelect
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Comment
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.operator.Select
import com.simplifide.scala2.core.basic.statement.StatementMux
import com.simplifide.scala2.core.basic.statement.UnarySegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError
import scala.collection.mutable.ListBuffer


class QuadRotateFunction(angleOut:BaseSignal, xrOut:BaseSignal,xiOut:BaseSignal,
                                  angleIn:BaseSignal, xr:BaseSignal, xi:BaseSignal) extends TopFunctionStatement(angleOut,angleIn) {
  override def getFixedType:FixedType = xr.getFixedType
  
  override def createCode(writer:CodeWriter):SegmentReturn = {
    val main = new FixedTypeMain(Signing.UnSigned,2,0)
    val sel = new FixedSelect(angleIn,main)
    
    val comR = new Comment("Real Quadrant Mux")
    val muxR = new StatementMux(sel,xrOut,List(xr,new UnarySegment.Negative(xi),new UnarySegment.Negative(xr),xi))
    val comI = new Comment("Imag Quadrant Mux")
    val muxI = new StatementMux(sel,xiOut,List(xi,xr,new UnarySegment.Negative(xi),new UnarySegment.Negative(xr)))
    val comA = new Comment("Reduce the angle to the first quadrant")
    val mres  =  new FixedTypeMain(Signing.UnSigned,angleOut.getFixedType.width-1,angleOut.getFixedType.width-1)
    val usel = new FixedSelect(angleIn,mres)
    
    val ang  = new Statement(angleOut,usel)
    return this.createList(List(comR,muxR,comI,muxI,comA,ang), writer, List())
  }
  /** Creates the code for this segment */
  override def createCode(writer:CodeWriter, output:BaseSignal):SegmentReturn = {
    return createCode(writer)
  }
}

object QuadRotateFunction {
  
  
  
  def handleDecode(expressions:List[ExpressionReturn],line:Int):ExpressionReturn = {
    if (expressions.size != 6 ) {
      return ExpressionReturn.error(new InterfaceError.Error(line,"Quad_rotate Requires 6 inputs"))
    }
    val errors = new ListBuffer[InterfaceError]()
    // Combine the errors
    for (expression <- expressions) {
      errors.appendAll(expression.errors)
    }
    // Return if there is no statement or the inputs aren't BaseSignals
     for (expression <- expressions) {
       if (expression.statement == None) return new ExpressionReturn(None,None,errors.toList)
       if (!expression.statement.get.isInstanceOf[BaseSignal]) return new ExpressionReturn(None,None,List(new InterfaceError.Error(line,"Input must be signal")))
     }
     
     val angO = expressions(0).statement.get.asInstanceOf[BaseSignal];
     val reO  = expressions(1).statement.get.asInstanceOf[BaseSignal];
     val imO  = expressions(2).statement.get.asInstanceOf[BaseSignal];
     val angI = expressions(3).statement.get.asInstanceOf[BaseSignal];
     val reI  = expressions(4).statement.get.asInstanceOf[BaseSignal];
     val imI  = expressions(5).statement.get.asInstanceOf[BaseSignal];
    
     val fun = new QuadRotateFunction(angO,reO,imO,angI,reI,imI)
     return new ExpressionReturn(None,Some(fun),errors.toList)
  }
  
}