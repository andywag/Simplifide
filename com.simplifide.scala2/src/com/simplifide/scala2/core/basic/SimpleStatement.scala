/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import vars.{SignalTrait, BaseSignal, FixedType}

class SimpleStatement(val output:StatementSegment,val input:StatementSegment) extends StatementSegment{
  
  val standardOperator = " = "
  val verilogOperator  = " = "
  

  /** Attaches the output to be used to create the code */
  override def createCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder();

    // Create the code for the input segment
    val inputSeg = writer.createCode(input.getRealSegment)

    // Create the rest of the statement
    builder.append(writer.createCode(this.createPrefix()))
    builder.append(writer.createCode(output).code)
    
    if (writer.isVerilog) builder.append(verilogOperator) 
    else builder.append(standardOperator)
    
    builder.append(inputSeg.code)
    builder.append(";\n")
    return SegmentReturn.segment(builder.toString)
  }
  /** Returns the prefix which is used at the beginning of the assignment statement */
  def createPrefix():BaseCodeSegment = new SimpleStatement.Prefix()
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    val builder = new StringBuilder();

    // Create the code for the input segment
    val inputSeg = writer.createCode(input.getRealSegment, output)

    // Create the rest of the statement
    builder.append(writer.createCode(this.createPrefix()))
    builder.append(writer.createCode(output).code)
    
    if (writer.isVerilog) builder.append(verilogOperator) 
    else builder.append(standardOperator)
    
    builder.append(inputSeg.code)
    builder.append(";\n")
    return SegmentReturn.segment(builder.toString)
  }
}

object SimpleStatement {
  class Internal(override val output:BaseSignal,override val input:StatementSegment) extends Statement(output,input) {
      override def createPrefix(output:BaseSignal):BaseCodeSegment = new Statement.PrefixInternal(output)
  }

  class Prefix() extends BaseCodeSegment {
    override def createVerilogCode(writer:CodeWriter):SegmentReturn     = SegmentReturn.segment("assign ");
    override def createFloatCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("");
    override def createFixedCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("");
 
  }

  
}
