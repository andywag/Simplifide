/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.statement

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.BaseCodeSegment
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.util.StringOps


/** Case Statement not including the always/process head */
class CaseStatement2(condition:StatementSegment) extends StatementSegment{

  override def getSignal:BaseSignal = null;
  
  val conditions:ListBuffer[CaseCondition2] = new ListBuffer[CaseCondition2]();

  def addCondition(cond:Option[StatementSegment],result:StatementSegment) {
    val condition = new CaseCondition2(cond,result);
    conditions += condition;
  }

  

  def createVerilogCodeInternal(writer:CodeWriter, signal:Option[BaseSignal]):SegmentReturn =  {
    def createVerilogHead(writer:CodeWriter, signal:Option[BaseSignal]):String = {
      val build2:StringBuilder = new StringBuilder();
      build2.append("case(");
      build2.append(writer.createCode(condition,signal).code)
      build2.append(")");
      return build2.toString
    }

    def createVerilogBody(writer:CodeWriter, signal:Option[BaseSignal]):String = {
      val build = new StringBuilder();
      for (condition <- conditions) {
        build.append(writer.createCode(condition,signal).code)
      }
      return StringOps.indentLines(build.toString,1)
     }
      
    val build = new StringBuilder();
    build.append(createVerilogHead(writer,signal));
    build.append('\n')
    build.append(createVerilogBody(writer,signal))
    build.append("endcase\n")
    SegmentReturn.segment(build.toString)
  }
  
 



  def createVhdlCodeInternal(writer:CodeWriter,output:Option[BaseSignal]):SegmentReturn = {

    def createVhdlBody(output:Option[BaseSignal]):String = {
      val build = new StringBuilder();
      for (condition <- conditions) {
        build.append(writer.createCode(condition, output).code )
      }
      return StringOps.indentLines(build.toString,1)
    }

    val build = new StringBuilder();
      build.append("case ");
      
      build.append(writer.createCode(condition, output).code)
      build.append(" is\n")
      build.append(createVhdlBody(output))
      build.append("end case;\n")
      return SegmentReturn.segment(build.toString)
  }

 def createCCodeInternal(writer:CodeWriter,output:Option[BaseSignal]):SegmentReturn = {

    def createBody(output:Option[BaseSignal]):String = {
      val build = new StringBuilder();
      for (condition <- conditions) {
        build.append(writer.createCode(condition, output).code )
      }
      return StringOps.indentLines(build.toString,1)
    }

    val build = new StringBuilder();
      build.append("switch(");
      build.append(writer.createCode(condition, output).code)
      build.append(") {\n")
      build.append(createBody(output))
      build.append("}\n")
      return SegmentReturn.segment(build.toString)
  }
  
 override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return new SegmentReturn("",List())
  }
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    return createVerilogCodeInternal(writer,None)
  }
  override def createVerilogCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createVerilogCodeInternal(writer,Some(output))
  }
  
  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
    return createVhdlCodeInternal(writer,None)
  }
  override def createVhdlCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createVhdlCodeInternal(writer,Some(output))
  }
  
  override def createFixedCode(writer:CodeWriter):SegmentReturn = {
    return createCCodeInternal(writer,None)
  }
  override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCCodeInternal(writer,Some(output))
  }
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn = {
    return createCCodeInternal(writer,None)
  }
  override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCCodeInternal(writer,Some(output))
  }
  
  
}

class CaseCondition2(val cond:Option[StatementSegment],result:StatementSegment) extends StatementSegment {


  def createVerilogCodeInternal(writer:CodeWriter,signal:Option[BaseSignal]):SegmentReturn = {
    val build = new StringBuilder(16);
    val condS = cond match {
      case Some(x) => writer.createCode(x,signal).code
      case _ => "default"
    }
    val resS  = writer.createCode(result,signal).code
    
    build.append(condS)
    build.append(" : ")
    
    val sp = resS.split("\n")
    if (sp.length == 1) {
      build.append(resS)
    }
    else {
      val res2 = StringOps.indentLines(resS, 1)
      build.append('\n')
      build.append(res2)
    }

    return SegmentReturn.segment(build.toString)
  }

 def createVhdlCodeInternal(writer:CodeWriter,signal:Option[BaseSignal]):SegmentReturn = {
    val build = new StringBuilder();
    val condS = cond match {
      case Some(x) => writer.createCode(x,signal).code
      case _ => "others"
    }
    val resS  = writer.createCode(result,signal).code

    build.append("when ")
    build.append(condS)
    build.append(" => ")

    val sp = resS.split("\n")
    if (sp.length == 1) {
      build.append(resS)
    }
    else {
      val res2 = StringOps.indentLines(resS, 1)
      build.append('\n')
      build.append(res2)
    }

    return SegmentReturn.segment(build.toString)
  }
  
  def createCCodeInternal(writer:CodeWriter,signal:Option[BaseSignal]):SegmentReturn = {
    val build = new StringBuilder();
    // Create the Condition String
    val condS = cond match {
      case Some(x) => "case " + writer.createCode(x,signal).code + " : "
      case _ => "default :"
    }
    // Create the result String
    val resS  = writer.createCode(result,signal).code + " break;\n"
    // Append the Condition String
    build.append(condS)
    
    val sp = resS.split("\n")
    if (sp.length == 1) {
      build.append(resS)
    }
    else {
      val res2 = StringOps.indentLines(resS, 1)
      build.append('\n')
      build.append(res2)
    }

    return SegmentReturn.segment(build.toString)
  }
  
  
  /** Creates the code for this segment 
      TODO Should be error added
   */
   override def createCode(writer:CodeWriter):SegmentReturn = {
    new SegmentReturn("Testing",List())
  }
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    new SegmentReturn("Testing",List())
  }
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    return createVerilogCodeInternal(writer,None)
  }
  override def createVerilogCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createVerilogCodeInternal(writer,Some(output))
  }
  
  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
    return createVhdlCodeInternal(writer,None)
  }
  override def createVhdlCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createVhdlCodeInternal(writer,Some(output))
  }
  
  override def createFixedCode(writer:CodeWriter):SegmentReturn = {
    return createCCodeInternal(writer,None)
  }
  override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCCodeInternal(writer,Some(output))
  }
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn = {
    return createCCodeInternal(writer,None)
  }
  override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCCodeInternal(writer,Some(output))
  }

}
