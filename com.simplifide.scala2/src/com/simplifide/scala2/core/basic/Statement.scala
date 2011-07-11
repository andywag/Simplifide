/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import vars.{SignalTrait, BaseSignal, FixedType}
import com.simplifide.scala2.core.{IdentSegment, BaseCodeSegment, CodeWriter, SegmentReturn}

class Statement(val output:BaseSignal,val input:StatementSegment) extends StatementSegment{
  
  val standardOperator = " = "
  val verilogOperator  = " = "
  
  def getComment:Option[Comment] = None
  override def getSignal = output
  
  /** Attaches the output to be used to create the code */
  override def createCode(writer:CodeWriter):SegmentReturn = {
    return createCode(writer, output)
  }
  /** Returns the prefix which is used at the beginning of the assignment statement */
  def createPrefix(output:BaseSignal):BaseCodeSegment = new Statement.Prefix(output)
  
  override def createCode(writer:CodeWriter,output1:BaseSignal):SegmentReturn = {
    val builder = new StringBuilder();
    
    // Append the comment if it exists
    getComment.foreach(x => builder.append(writer.createCode(x).code)) 
    
    // Create the code for the input segment
    val inputSeg = writer.createCode(input.getRealSegment, output1)
    
    // Create the code for the extra segments attached to the input segment
    inputSeg.extraStatements.foreach(x => builder.append(writer.createCode(x).code));
    
    // Create the rest of the statement
    builder.append(writer.createCode(this.createPrefix(output)))
    builder.append(writer.createCode(output1).code)
    
    if (writer.isVerilog) builder.append(verilogOperator) 
    else builder.append(standardOperator)
    
    builder.append(inputSeg.code)
    builder.append(";\n")
    return SegmentReturn.segment(builder.toString)
  }
}

object Statement {

  class Simple(override val output:BaseSignal,override val input:StatementSegment) extends Statement(output,input) {
     override def createPrefix(output:BaseSignal):BaseCodeSegment = new IdentSegment("")
  }

  class Internal(override val output:BaseSignal,override val input:StatementSegment) extends Statement(output,input) {
      override def createPrefix(output:BaseSignal):BaseCodeSegment = new Statement.PrefixInternal(output)
  }
  
  class WithCode(override val output:BaseSignal,override val input:StatementSegment, val code:Option[String]) extends Statement(output,input) {
    override def getComment:Option[Comment] = {
      code match {
        case None    => return None
        case Some(x) => return Some(new Comment.SingleLine(x))
      }
    }
  }
  
  class Reg(override val output:BaseSignal,override val input:StatementSegment) extends Statement(output,input) {
    override val verilogOperator = " <= "
    override def createPrefix(output:BaseSignal):BaseCodeSegment = new Statement.RegPrefix(output)
    
  }
  
  class RegPrefix(val output:BaseSignal) extends BaseCodeSegment {
    override def createVerilogCode(writer:CodeWriter):SegmentReturn     = SegmentReturn.segment("");
    override def createFloatCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("");
    override def createFixedCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("");
 
  }
  
  class Prefix(val output:BaseSignal) extends BaseCodeSegment {
    override def createVerilogCode(writer:CodeWriter):SegmentReturn     = SegmentReturn.segment("assign ");
    override def createFloatCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("");
    override def createFixedCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("");
 
  }
  
  class PrefixInternal(val output:BaseSignal) extends BaseCodeSegment {
    override def createVerilogCode(writer:CodeWriter):SegmentReturn     = SegmentReturn.segment(output.getFixedType.getWireDeclaration);
    override def createFloatCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("float ");
    override def createFixedCode(writer:CodeWriter):SegmentReturn       = SegmentReturn.segment("float ");
 
  }
  
}
