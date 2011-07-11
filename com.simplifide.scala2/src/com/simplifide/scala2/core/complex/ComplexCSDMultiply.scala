/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.complex

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.fixed.AdderTree
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.core.basic.vars.FixedType
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.{Comment, StatementSegment}

class ComplexCSDMultiply(name:String,
                         clk:FlopControl,
                         csd:ComplexConstant, 
                         signal:ComplexSignal,
                         output:ComplexSignal,
                         internal:FixedType,
                         delays:Int,
                         flop:Boolean) extends StatementSegment{

  def this(name:String,
           clk:FlopControl,
           csd:ComplexConstant,
           signal:ComplexSignal,
           output:ComplexSignal,
           internal:FixedType,
           delays:Int) {
      this(name,clk,csd,signal,output,internal,delays,true)
  }

  def createCSDComment(constant:Constant):String = {
    val builder = new StringBuilder
    val csdR:List[Constant.CSD] = constant.createCSD
    builder.append(constant.value)
    builder.append("[")
    for (csd <- csdR) {
      builder.append(csd.debugString);
      builder.append(" ");
    }
    builder.append("]")

    return builder.toString
  }

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val segments = new ListBuffer[SegmentReturn]()

    val realCSD:Constant = csd.getRealConstant
    val imagCSD:Constant = csd.getImagConstant
    val imagNegCSD:Constant = csd.getNegativeImagConstant

    segments.append(writer.createCode(new Comment.SingleLine("Complex Multiplication [" + realCSD.debugCSDString + "+ j" + imagCSD.debugCSDString + "]")))
    segments.append(writer.createCode(new Comment.SingleLine(output.debugString + signal.debugString + internal.getDescription)))
    segments.append(writer.createCode(new Comment.SingleLine(realCSD.debugCSDString + " " + imagNegCSD.debugCSDString)))

    val realAdd  = List(new AdderTree.Value(realCSD,signal.getReal),new AdderTree.Value(imagNegCSD,signal.getImag))
    val realMult = new AdderTree(name+"_real",clk,realAdd,internal,delays,flop)
    segments.append(writer.createCode(new Comment.SingleLine("Real Multiplication")))
    segments.append(writer.createCode(realMult,output.getReal))

    segments.append(writer.createCode(new Comment.SingleLine(realCSD.debugCSDString + " " + imagCSD.debugCSDString)))
    val imagAdd  = List(new AdderTree.Value(realCSD,signal.getImag),new AdderTree.Value(imagCSD,signal.getReal))
    val imagMult = new AdderTree(name+"_imag",clk,imagAdd,internal,delays,flop)
    segments.append(writer.createCode(new Comment.SingleLine("Imag Multiplication")))
    segments.append(writer.createCode(imagMult,output.getImag))

    return SegmentReturn.combineReturns(segments.toList, List())
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCode(writer)
  }
  
}
