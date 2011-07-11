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
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.basic.Clocks
import com.simplifide.scala2.core.ccode.FunctionBase
import com.simplifide.scala2.clocks.FlopControl


/** @deprecated --- use ExpressionBodyBase instead*/
class ExpressionBody(val name:String,val flop:FlopControl,val protos:List[SignalProto],val assignments:List[AssignmentExpr]) extends CommandCodeSegment("segments"){

  var signals:List[BaseSignal] = List()
  
  override val command:Command = ExpressionCommands;
  override val commands:ListBuffer[Command] = new ListBuffer[Command]();
  override val matchingC:Boolean = true
  
  def createSegments:List[BaseCodeSegment] = {
    List[BaseCodeSegment]()
  }
  
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
    val assigns:List[StatementSegment] = assignments.map(x => x.convertExpression(con).statement.get)
    val flop = createFlop;
    val flop_com = new Comment.SingleLine("Signal Delay")
    val tot:List[BaseCodeSegment] = assigns ::: List(flop_com,flop)
    val fixed = new FunctionBase(name + "_float", signals, tot)
    return fixed.createFloatCode(writer)
  }
  
  override def createFixedCode(writer:CodeWriter):SegmentReturn       = {
    val con = new ExpressionContext(signals)
    val assigns:List[StatementSegment] = assignments.map(x => x.convertExpression(con).statement.get)
    val flop = createFlop;
    val flop_com = new Comment.SingleLine("Signal Delay")
    val tot:List[BaseCodeSegment] = assigns ::: List(flop_com,flop)
    val fixed = new FunctionBase(name + "_fixed", signals, tot)
    return fixed.createFixedCode(writer)
  }
  
  
  private def createFlop:SimpleFlopList = {
      val ena:List[SimpleFlop.Segment] = signals.flatMap(x => x.getFlopSegments)
      val res:List[SimpleFlop.Segment] = ena.map(x => x.getResetSegment)
      new SimpleFlopList(Some("test"),Clocks.defaultFlop("clk","reset","enable"),res,ena)
  }
  
 
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {

    // Create the context which is used to create the assignments
    
    // First pass to convert the prototype signals
    val sigs:List[BaseSignal] = protos.flatMap(x => x.createFixed)
    val con2 = new ExpressionContext(sigs)
    val sigs2:List[SignalProto.Return] = protos.map(x => x.createSignal(con2))
    val errors:List[InterfaceError]    = sigs2.flatMap(x => x.errors)
    if (errors.length > 0) return new SegmentReturn("",errors)
    
    signals = sigs2.flatMap(x => x.signal)
    val con = new ExpressionContext(signals)
    
    // Convert the Expression Statements to actual assignments
    val ret:List[ExpressionReturn] = assignments.map(x => x.convertExpression(con))
    // Check for errors in the expression return. If so return only this
    val fret:List[InterfaceError] = ret.flatMap(x => x.errors)
    // If there are any catastrophic errors return the list
    val eret = fret.filter(x => x.isError)
    if (eret.length > 0) {
      return new SegmentReturn("",fret)
    }
    
    // The automatic get should be ok give the catastrophic errors have been removed
    // Just in case the other segments are removed
    val rassigns = ret.filter(x => x.statement != None)
    val assigns:List[StatementSegment] = rassigns.map(x => x.statement.get)
    
    val tot:List[BaseCodeSegment] = List(new Comment.SingleLine("Signal Declarations"))
    val defs:List[SignalDeclarationNew] = signals.flatMap(x => x.getSignalDeclaration)   
    val flopC:List[BaseCodeSegment] = List(new Comment.SingleLine("Delay Lines"))
    val flop = createFlop
    val tot1:List[BaseCodeSegment] = tot ::: defs ::: assigns ::: flopC ::: List(flop);
    
    return this.createList(tot1, writer,fret)
    
  }
  
  
}

