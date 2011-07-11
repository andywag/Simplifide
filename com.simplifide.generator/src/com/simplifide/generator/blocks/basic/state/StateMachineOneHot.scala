/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.state

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.flop._
import com.simplifide.scala2.core.basic.operator.SliceOp
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.context.CurrentContext



class StateMachineOneHot(name:String,
                         flop:FlopControl,
                         currentState:Option[BaseCodeSegment],
                         nextState:Option[BaseCodeSegment],
                         transitions: ListBuffer[Transition]) extends StateMachine(name,flop,currentState,nextState,transitions){



  private def createStateFlop(init:String):BaseCodeSegment = {

    val resetMap = new ListBuffer[SimpleFlop.Segment]();
    val enableMap = new ListBuffer[SimpleFlop.Segment]();

    new SimpleFlop.Segment(getCurrentState,None)
    resetMap +=  new SimpleFlop.Segment(getCurrentState,None)
    resetMap +=  new SimpleFlop.Segment(new SliceOp(getCurrentState,new NumberSegment(0)),Some(new IdentSegment(init)))
    enableMap += new SimpleFlop.Segment(getCurrentState, Some(getNextState))
    
    val nflop = new SimpleFlop(Some(name + "_flop"),this.flop, resetMap, enableMap)
    return nflop
  }

  def createVerilogBody:BaseCodeSegment = {
    val case1 = new CaseStatement(new IdentSegment("1'b1"));
    for (transition <- transitions) {
      transition.createOneHotTransition(case1, this)
    }
    val init = new AssignBlockingStatement(this.getNextState,FlopReset)
    val lis  = new ListSegment( List(init,case1))
    val alw = new AlwaysStar(Some(name),lis,new ListBuffer[BaseCodeSegment]())
    return alw
  }

  def createVhdlBody:BaseCodeSegment = {
    //val case1 = new CaseStatement(new IdentSegment("1'b1"));
    val segments = new ListBufferSegment()
    segments.segments.append(new AssignStatement(this.getNextState,FlopReset))
    for (transition <- transitions) {
      segments.segments.append(transition.createVhdlOneHotTransition(this))
    }
    val alw = new AlwaysStar(Some(name),segments,new ListBuffer[BaseCodeSegment]())
    return alw
  }

  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder()
    builder.append(createVerilogBody.createVerilogCode(writer).code)
    builder.append(createStateFlop("1").createVerilogCode(writer).code)
    return SegmentReturn.segment(builder.toString())
  }

  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder()
    builder.append(createVhdlBody.createVhdlCode(writer).code)
    builder.append(createStateFlop("'1'").createVhdlCode(writer).code)
    return  SegmentReturn.segment(builder.toString())
  }

}
