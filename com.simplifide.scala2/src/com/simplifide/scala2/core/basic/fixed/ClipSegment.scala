/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.core.basic.vars.FixedType

import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.SimpleMux
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.operator._


class ClipSegment(val input:BaseSignal,val fixed:FixedType) extends StatementSegment{
  
    override def getSignal = input
    def getInternalFixed(output:BaseSignal):FixedType = output.getFixedType;
    
   override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn       = {
     return input.createCode(writer)
  }
  
  override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn       = {
    
    val signal = writer.createCode(input)
    val in     = fixed.width - fixed.frac -1
    //val level  = math.pow(2.0, in)
    val builder = new StringBuilder
    builder.append("clip")
    builder.append("(")
    builder.append(signal.code)
    builder.append(",")
    builder.append(in.toString)
    builder.append(",")
    builder.append(fixed.width)
    builder.append(")")
    return SegmentReturn.segment(builder.toString)
  }
  
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      val inFix  = input.getFixedType
      val outFix = output.getFixedType
      
      val diff = (inFix.width - inFix.frac) - (outFix.width - outFix.frac) 
      val bot  = input.getFixedType.width - diff - 1
    
      val width = input.getFixedType.width
      val top = new Select(input,Some(input.getFixedType.width-1),None)
      var neg = new BinaryOperator.And(top,new UnaryOperator.Not(new Select(input,Some(width-2),Some(width-2))))
      var pos = new BinaryOperator.And(new UnaryOperator.Not(top),new Select(input,Some(width-2),Some(width-2)))

      for (i <- bot until width-2) {
        neg = new BinaryOperator.And(neg,new UnaryOperator.Not(new Select(input,Some(i),Some(i))))
        pos = new BinaryOperator.And(pos,new UnaryOperator.Not(new Select(input,Some(i),Some(i))))
      }
      //val sec = new Select(input,Some(input.getFixedType.width-2),Some(bot))
      
      //val neg = new BinaryOperator.And(top,new UnaryOperator.NotAnd(sec))
      //val pos = new BinaryOperator.And(new UnaryOperator.Not(top),sec)

      // Truncate the signal
      val signal = new Select(input,Some(output.getFixedType.width-1),Some(0))
      val mux  = new SimpleMux(pos,new Constant.Max(outFix),signal)
      val mux2 = new SimpleMux(neg,new Constant.Min(outFix),mux)
      return writer.createCode(mux2)
    
    }
     /*
      override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      val inFix  = input.getFixedType
      val outFix = output.getFixedType

      val diff = (inFix.width - inFix.frac) - (outFix.width - outFix.frac)
      val bot  = input.getFixedType.width - diff - 1

      val top = new Select(input,Some(input.getFixedType.width-1),None)

      val sec = new Select(input,Some(input.getFixedType.width-2),Some(bot))

      val neg = new BinaryOperator.And(top,new UnaryOperator.NotAnd(sec))
      val pos = new BinaryOperator.And(new UnaryOperator.Not(top),sec)

      // Truncate the signal
      val signal = new Select(input,Some(output.getFixedType.width-1),Some(0))
      val mux  = new SimpleMux(pos,new Constant.Max(outFix),signal)
      val mux2 = new SimpleMux(neg,new Constant.Min(outFix),mux)
      return writer.createCode(mux2)

    }  */
}
