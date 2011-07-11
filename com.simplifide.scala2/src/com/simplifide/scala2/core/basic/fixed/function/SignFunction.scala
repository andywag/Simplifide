/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed.function

import com.simplifide.scala2.core.basic.fixed.FixedSelect
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.operator.Select
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.basic.vars.Signing
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError

class SignFunction(in1:BaseSignal) extends TopFunction{

/** Get the number of terms for this expression */
  override def getNumberOfTerms:Int = 1
  /** Return the term associated with the nth point */
  override def getTerm(index:StatementSegment.TermIndex):StatementSegment = in1.getTerm(index)
  
  override def convertTerm(index:StatementSegment.TermIndex):StatementSegment = 
    new SignFunction(in1.convertTerm(index).asInstanceOf[BaseSignal])
  /** Create the segment associated with this code */
  override def createShareSegment(states:List[StatementSegment]):StatementSegment=
    new SignFunction(states(0).asInstanceOf[BaseSignal])	
	
  override def getFixedType:FixedType = new FixedTypeMain(Signing.UnSigned,1,0)
  /** Creates the code for this segment */
  override def createCode(writer:CodeWriter):SegmentReturn = {
    val inv  = in1.getFixedType.frac - in1.getFixedType.width
    val main = new FixedTypeMain(Signing.UnSigned,1,inv)
    val sel = new FixedSelect(in1,main)
    return writer.createCode(sel)
  }
  /** Creates the code for this segment */
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    val inv  = in1.getFixedType.frac - in1.getFixedType.width
    val main = new FixedTypeMain(Signing.UnSigned,1,inv+1)
    val sel = new FixedSelect(in1,main)
    return writer.createCode(sel,output)
  }
  
  def createCItem(writer:CodeWriter):SegmentReturn = {
    val fix = in1.getFixedType;
    val sret = in1.createCode(writer)
    
    val con = math.pow(2.0,fix.width-1)
    
    if (fix.signed.isSigned) return new SegmentReturn(sret.code + " > 0",sret.errors)
    else return new SegmentReturn(sret.code + " > " + con,sret.errors)
  }
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn = 
      return createCItem(writer)
  
   override def createFixedCode(writer:CodeWriter):SegmentReturn = 
     return createCItem(writer)
  
  override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = 
    return createCItem(writer)
  
  override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = 
     return createCItem(writer)
  
}

object SignFunction {
  def handleDecode(expressions:List[ExpressionReturn],line:Int):ExpressionReturn = {
    if (expressions.size == 0 || expressions.size > 1) {
      return ExpressionReturn.error(new InterfaceError.Error(line,"Sign Requires 1 input"))
    }
      
    expressions(0).statement match {
      case None    => return new ExpressionReturn(None,None,expressions(0).errors)
      case Some(x) => {
          if (x.isInstanceOf[BaseSignal]) return new ExpressionReturn(None,Some(new SignFunction(x.asInstanceOf[BaseSignal])),expressions(0).errors)
          else return new ExpressionReturn(None,None,List(new InterfaceError.Error(line,"Input must be signal")))
        }
    }
  }
  
}
