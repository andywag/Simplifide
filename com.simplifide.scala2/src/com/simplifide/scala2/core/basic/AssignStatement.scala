/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn

import com.simplifide.scala2.context.CurrentContext


class AssignStatement(output:BaseCodeSegment,input:BaseCodeSegment) extends BaseCodeSegment{

  val operator = " <= "
  
  def createRealCode(writer:CodeWriter,op:String):SegmentReturn = {
    val build = new StringBuilder();
    build.append(writer.createSimpleCode(output))
    build.append(op)
    build.append(writer.createSimpleCode(input))
    build.append(";\n")
    return SegmentReturn.segment(build.toString);
  }
  
  override def createCode(writer:CodeWriter):SegmentReturn = {
    return createRealCode(writer, operator)
  }
  
  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
     return createRealCode(writer," <= ")
  }

 

}

class AssignBlockingStatement(output:BaseCodeSegment,input:BaseCodeSegment) extends AssignStatement(output,input){
  override val operator = " = "

}

