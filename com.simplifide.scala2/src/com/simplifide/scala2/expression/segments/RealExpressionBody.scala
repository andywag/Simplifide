/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.command._
import com.simplifide.scala2.core.basic.flop.SimpleFlop
import com.simplifide.scala2.core.basic.flop.SimpleFlopList
import com.simplifide.scala2.core.ccode.FunctionBase
import com.simplifide.scala2.expression.ExpressionCommands
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError
import scala.collection.immutable.LinearSeq
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Comment
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.basic.Clocks
import com.simplifide.scala2.core.ccode.FunctionBase
import com.simplifide.scala2.clocks.FlopControl

class RealExpressionBody(val name:String,
                     val flop:FlopControl,
                     val signals:List[BaseSignal],
                     val assigns:List[StatementSegment]) extends BaseCodeSegment{
  
  override def createHeaderCode(writer:CodeWriter):SegmentReturn      = {
    val builder = new StringBuilder
    val fixed = new FunctionBase.HeaderHead(name + "_fixed",signals)
    val float = new FunctionBase.HeaderHead(name + "_float",signals)
    builder.append(fixed.createHeaderCode(writer))
    builder.append(float.createHeaderCode(writer))
    return SegmentReturn.segment(builder.toString)
  }
  
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn       = {
    val con = new ExpressionContext(signals)
    val flop = createFlop;
    val flop_com = new Comment.SingleLine("Signal Delay")
    val tot:List[BaseCodeSegment] = assigns ::: List(flop_com,flop)
    val fixed = new FunctionBase(name + "_float", signals, tot)
    return fixed.createFloatCode(writer)
  }
  
  override def createFixedCode(writer:CodeWriter):SegmentReturn       = {
    val con = new ExpressionContext(signals)
    val flop = createFlop;
    val flop_com = new Comment.SingleLine("Signal Delay")
    val tot:List[BaseCodeSegment] = assigns ::: List(flop_com,flop)
    val fixed = new FunctionBase(name + "_fixed", signals, tot)
    return fixed.createFixedCode(writer)
  }
  
  
  private def createFlop:SimpleFlopList = {
      val ena:List[SimpleFlop.Segment] = signals.flatMap(x => x.getFlopSegments)
      val res:List[SimpleFlop.Segment] = ena.map(x => x.getResetSegment)
      new SimpleFlopList(Some("test"),flop,res,ena)
  }
  
 
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    val tot:List[BaseCodeSegment] = List(new Comment.SingleLine("Signal Declarations"))
    val defs:List[SignalDeclarationNew] = signals.flatMap(x => x.getSignalDeclaration)   
    val flopC:List[BaseCodeSegment] = List(new Comment.SingleLine("Delay Lines"))
    val flop = createFlop
    val tot1:List[BaseCodeSegment] = tot ::: defs ::: assigns ::: flopC ::: List(flop);
    
    return this.createList(tot1, writer,List())
    
  }
                     

}
