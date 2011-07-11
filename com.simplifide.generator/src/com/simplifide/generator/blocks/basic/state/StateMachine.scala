package com.simplifide.generator.blocks.basic.state



import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.command._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.IdentSegment
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.AlwaysProcess
import com.simplifide.scala2.core.basic.AlwaysStar
import com.simplifide.scala2.core.basic.CaseStatement
import com.simplifide.scala2.core.BasicSegments
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.context.CurrentContext

/**
 * Created by IntelliJ IDEA.
 * User: andy 
 * Date: Aug 24, 2010
 * Time: 11:28:07 PM
 * To change this template use File | Settings | File Templates.
 */

class StateMachine(name:String,val flop:FlopControl,val currentState:Option[BaseCodeSegment],
                   val nextState:Option[BaseCodeSegment],
                   transitions: ListBuffer[Transition]) extends CommandCodeSegment(name)  {
  
  override val command = StateMachineCommand
 
  def getCurrentState:BaseCodeSegment = {
    currentState match {case Some(x) => return x
      case None => return BasicSegments.ident("state")}
  }

  def getNextState:BaseCodeSegment = {
     nextState match {case Some(x) => return x
      case None => return BasicSegments.ident("next_state")}
  }



  private def createCaseBody(writer:CodeWriter):AlwaysProcess = {
    val case1 = new CaseStatement(new IdentSegment(getCurrentState.createCode(writer).code));
    for (transition <- transitions) {
      transition.createTransition(case1, this)
    }
    val buffer = new ListBuffer[BaseCodeSegment]();
    buffer.append(getCurrentState)
    val alw = new AlwaysStar(Some(name + "_body"),case1,buffer)
    return alw
  }

  protected def createStateFlopReset:Option[BaseCodeSegment] = Some(transitions.head.state)

  protected def createStateFlop():BaseCodeSegment = {

    val in = new FlopOutput(getNextState,createStateFlopReset)
    val map = new HashMap[BaseCodeSegment,FlopOutput]
    map.put(getCurrentState,in)
    val nflop = new Flop(Some(name + "_flop"),this.flop, map)
    return nflop
  }

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val alw = this.createCaseBody(writer);
    val flop = createStateFlop()
    val builder = new StringBuilder()
    builder.append(writer.createCode(alw))
    builder.append(writer.createCode(flop))
    return SegmentReturn.segment(builder.toString())
  }
  
  /*
  override def createVerilog():String = {
    return createCode(BaseCodeSegment.verilogWriter(_))
  }

  override def createVhdl():String = {
    return createCode(BaseCodeSegment.vhdlWriter(_))
  }
  */


}

class State(val name:String,val value:Option[Int]) extends BaseCodeSegment {

  override def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment(name)
  

}


