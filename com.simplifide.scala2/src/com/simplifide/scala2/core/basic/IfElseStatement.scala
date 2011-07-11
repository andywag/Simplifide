/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.core.BaseCodeSegment
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.context.CurrentContext


class IfElseStatement extends BaseCodeSegment{
  val statements = new ListBuffer[IfElseClause]();

  def addClause(condition:Option[BaseCodeSegment],body:BaseCodeSegment) {
    var clause:IfElseClause = null
    if (statements.length == 0) clause = new IfElseFirst(condition,body)
    else if (condition == None) clause = new IfElseLast(body)
    else                        clause = new IfElseClause(condition,body)
    statements += clause;
  }

  override def createVerilogCode(writer:CodeWriter):SegmentReturn =  {
    val build = new StringBuilder();
    for (statement <- statements) {
      build.append(statement.createVerilogCode(writer));
    }
    return SegmentReturn.segment(build.toString)
  }

   override def createVhdlCode(writer:CodeWriter):SegmentReturn =  {
    val build = new StringBuilder();
    for (statement <- statements) {
      build.append(statement.createVhdlCode(writer));
    }
    build.append("end if;\n");
    return SegmentReturn.segment(build.toString)
  }

}

class IfElseClause(condition:Option[BaseCodeSegment],body:BaseCodeSegment) extends BaseCodeSegment {
 override def createVerilogCode(writer:CodeWriter):SegmentReturn =  {
    val build = new StringBuilder();
    build.append("else if (");
    build.append(condition.get.createVerilogCode(writer).code);
    build.append(") begin\n")
    build.append(StringOps.indentLines(body.createVerilogCode(writer).code, 1))
    build.append("end\n");
    return SegmentReturn.segment(build.toString)
  }
   override def createVhdlCode(writer:CodeWriter):SegmentReturn ={
    val build = new StringBuilder();
    build.append("elsif (");
    build.append(condition.get.createVhdlCode(writer).code);
    build.append(") then\n")
    build.append(StringOps.indentLines(body.createVhdlCode(writer).code, 1))
    //build.append("end if;");
    return SegmentReturn.segment(build.toString)
  }
}

class IfElseFirst(condition:Option[BaseCodeSegment],body:BaseCodeSegment) extends IfElseClause(condition,body) {
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn =  {
    val build = new StringBuilder();
    build.append("if (");
    build.append(condition.get.createVerilogCode(writer).code);
    build.append(") begin\n")
    build.append(StringOps.indentLines(body.createVerilogCode(writer).code, 1))
    build.append("end\n");
    return SegmentReturn.segment(build.toString)
  }

  override def createVhdlCode(writer:CodeWriter):SegmentReturn =  {
    val build = new StringBuilder();
    build.append("if (");
    build.append(condition.get.createVhdlCode(writer).code);
    build.append(") then\n")
    build.append(StringOps.indentLines(body.createVhdlCode(writer).code, 1))
    return SegmentReturn.segment(build.toString)
  }
}

class IfElseLast(body:BaseCodeSegment) extends IfElseClause(None,body) {
   override def createVerilogCode(writer:CodeWriter):SegmentReturn =  {
    val build = new StringBuilder();
    build.append("else begin\n")
    build.append(StringOps.indentLines(body.createVerilogCode(writer).code, 1))
    build.append("end\n");
    return SegmentReturn.segment(build.toString)
  }
   override def createVhdlCode(writer:CodeWriter):SegmentReturn =  {
    val build = new StringBuilder();
    build.append("else\n")
    build.append(StringOps.indentLines(body.createVhdlCode(writer).code, 1))
    return SegmentReturn.segment(build.toString)
  }
}

