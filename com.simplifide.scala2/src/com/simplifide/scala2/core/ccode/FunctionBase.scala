/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.ccode

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.vars.SignalDeclarationNew


class FunctionBase(val name:String, val signals:List[BaseSignal],val assigns:List[BaseCodeSegment]) extends BaseCodeSegment{
  
  override def createHeaderCode(writer:CodeWriter):SegmentReturn      = {
    val head_fixed = new FunctionBase.Head(name + "fixed",signals)
    val head_float = new FunctionBase.Head(name + "float",signals)
    val builder = new StringBuilder
    builder.append(writer.createCode(head_fixed).code)
    builder.append(writer.createCode(head_float).code)
    return SegmentReturn.segment(builder.toString)
  }
  
   def createItem(writer:CodeWriter):SegmentReturn = {
    val head  = new FunctionBase.Head(name,signals)  
    val state = new FunctionBase.Declaration(signals)
    val body  = new FunctionBase.Body(assigns)
    val builder = new StringBuilder
    builder.append(writer.createCode(head).code)
    builder.append(writer.createCode(state).code)
    builder.append(writer.createCode(body).code)
    builder.append("}\n\n")
      
    return SegmentReturn.segment(builder.toString)
  } 
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn       = {
    return createItem(writer)
  }
  
  override def createFixedCode(writer:CodeWriter):SegmentReturn       = {
    return createItem(writer)
  }
  
  
 
}

object FunctionBase extends BaseCodeSegment {
  class Head(val name:String, val signals:List[BaseSignal]) extends BaseCodeSegment{
  
    val postfix = " { "
    private def createIO(writer:CodeWriter):String = {
      val builder = new StringBuilder
      val decs = signals.flatMap(x => x.getIODeclaration)
      var first = true
      for (dec <- decs) {
        if (!first) builder.append(",") else first = false
        builder.append(writer.createCode(dec))
      }
      return builder.toString
    }
    
    override def createCode(writer:CodeWriter):SegmentReturn = {
      val builder = new StringBuilder
      builder.append("void ")
      builder.append(name)
      builder.append("(")
      builder.append(createIO(writer))
      builder.append(")")
      builder.append(postfix)
      builder.append("\n\n")
      return SegmentReturn.segment(builder.toString)
    }
  }
  
  class HeaderHead(override val name:String, override val signals:List[BaseSignal]) extends Head(name,signals) {
    override val postfix = ";"
  }
  
  
  class Declaration(val signals:List[BaseSignal]) extends BaseCodeSegment {
    
    override def createCode(writer:CodeWriter):SegmentReturn = {
      val builder = new StringBuilder
      val decs:List[SignalDeclarationNew] = signals.flatMap(x => x.getSignalDeclaration)
      decs.foreach(x => builder.append(writer.createCode(x).code))
      return SegmentReturn.segment(builder.toString)
    }
  }
  
  class Body(val assigns:List[BaseCodeSegment] ) extends BaseCodeSegment{
    override def createCode(writer:CodeWriter):SegmentReturn = {
      val builder = new StringBuilder
      assigns.foreach(x => builder.append(writer.createCode(x).code))
      return SegmentReturn.segment(builder.toString)
    }
  }
  
}
