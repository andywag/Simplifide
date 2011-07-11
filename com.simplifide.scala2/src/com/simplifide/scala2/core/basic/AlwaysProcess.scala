/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.util.StringOps
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.context.CurrentContext


class AlwaysProcess(val name:Option[String],body:BaseCodeSegment) extends BaseCodeSegment{

}


class AlwaysSensitivity(name:Option[String],body:BaseCodeSegment, senseList:ListBuffer[BaseCodeSegment]) extends AlwaysProcess(name,body) {

  def createVerilogSenseList(writer:CodeWriter):String = {
        val build = new StringBuilder()
        build.append("always @(");
        build.append(StringOps.repeatAfterFirst(senseList, " or ", writer))
        build.append(") ")
        return build.toString
    }
 
  

  def createVhdlSenseList(writer:CodeWriter):String = {
    val build = new StringBuilder()
        if (name != None) build.append(name.get + " : ")
        build.append("process (");
        build.append(StringOps.repeatAfterFirst(senseList, ",", writer))
        build.append(") begin\n")
        return build.toString
  }

  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
      val build = new StringBuilder();
      build.append(createVerilogSenseList(writer))
      build.append("begin\n")
      build.append(StringOps.indentCode(writer, body))
      build.append("end\n")
      return SegmentReturn.segment(build.toString());
  }
  
  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
      val build = new StringBuilder()
      build.append(createVhdlSenseList(writer))
      build.append(StringOps.indentCode(writer, body))
      build.append("end process;\n")
      SegmentReturn.segment(build.toString())
  }
  override def createFloatCode(writer:CodeWriter):SegmentReturn       = {
	  writer.createCode(body)
  }
  override def createFixedCode(writer:CodeWriter):SegmentReturn       = {
	  writer.createCode(body)
  }

}

class AlwaysStar(name:Option[String],body:BaseCodeSegment, senseList:ListBuffer[BaseCodeSegment]) extends AlwaysSensitivity(name,body,senseList) {
  override def createVerilogSenseList(writer:CodeWriter):String = {
     return "always @* "
  }
  override def createVhdlSenseList(writer:CodeWriter):String = {
    val builder = new StringBuilder()
    if (name != None) builder.append(name.get + " : ")
    builder.append("process(all) begin\n")
    builder.toString
  }
}

object AlwaysStar {

  def newAlways(body:BaseCodeSegment):AlwaysStar = new AlwaysStar(None,body,new ListBuffer[BaseCodeSegment]())

}
