/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.command._
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.expression.ExpressionCommands
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.top.InterfaceError
import com.simplifide.scala2.core.CodeWriter

import scala.collection.mutable.ListBuffer

class ExpressionBodyBase(val name:String,
                         val flop:FlopControl,
                         val protos:List[SignalProto],
                         val assignments:List[Expression]) extends CommandCodeSegment("segments") {
  
  
  override val command:Command = ExpressionCommands;
  override val commands:ListBuffer[Command] = new ListBuffer[Command]();
  override val matchingC:Boolean = true
  
  def createSegments:List[BaseCodeSegment] = {
    List[BaseCodeSegment]()
  }
  
 def convertAssignments(assigns:List[StatementSegment]):List[AssignmentGroup]= {
    // Convert the statement segments to statements
    val ass1 = assigns.filter(x => x.isInstanceOf[Statement])
    val ass  = ass1.map(x => x.asInstanceOf[Statement])
    
    val groups = new ListBuffer[AssignmentGroup]
    // Split the segments into sets of shared assignments
    var remain:List[Statement] = ass;
    while (remain.length > 0) {
      val states:List[Statement] = remain.filter(x => x.output.sameBaseSignal(remain.head.output))
      groups.append(new AssignmentGroup(this.flop,states))
      remain = remain.filter(x => (!x.output.sameBaseSignal(remain.head.output)))
    }
    return groups.toList
    
  }
  
  override def createCode(writer:CodeWriter):SegmentReturn = {
    // First pass to convert the prototype signals to actual signals
    // Returns an error if this task fails
    val sigs:List[BaseSignal] = protos.flatMap(x => x.createFixed)
    val con2 = new ExpressionContext(sigs)
    val sigs2:List[SignalProto.Return] = protos.map(x => x.createSignal(con2))
    val errors:List[InterfaceError]    = sigs2.flatMap(x => x.errors)
    if (errors.length > 0) return new SegmentReturn("",errors)
    
    val signals:List[BaseSignal] = sigs2.flatMap(x => x.signal)
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
    val groups = convertAssignments(assigns)
    
    val real = new RealExpressionBody(name,flop,signals,groups)
    return writer.createCode(real)
    
  } 
                         

}
