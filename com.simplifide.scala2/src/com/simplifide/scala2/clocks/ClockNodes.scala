/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.clocks

import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.basic._

import com.simplifide.scala2.command._
import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._

import com.simplifide.scala2.top.ParserContext
import com.simplifide.scala2.top.ScalaContext 


class ClockNodes {}

class ClockNode(name:SimpleNode.StringNode,edge:ChoiceNode) extends CommandNode {

  override val command = ClockCommand
  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      name.createContext(context, offset)
      edge.createContext(context, offset)
   }

  def createClock():Clock = {
    if (edge.value == "posedge") return new Clock(name.value,true);
    else return new Clock(name.value,false)
  }
}

class ResetNode(name:SimpleNode.StringNode,sync:ChoiceNode, active:ChoiceNode) extends CommandNode {
  
  override val command = ResetCommand
  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      name.createContext(context, offset)
      sync.createContext(context, offset)
      active.createContext(context, offset)
   }

  def createReset():Reset = {
    val ed =  { if (sync.value == "sync") false else true}
    val act = { if (active.value == "active_low") true else false}
    return new Reset(name.value,ed,act)
  }
}

class EnableNode(name:SimpleNode.StringNode) extends CommandNode {

  override val command = EnableCommand
  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      name.createContext(context, offset)
   }

  def createEnable():Enable = {
    return new Enable(name.value)
  }
}

class ClockHeadNode(val clk:ClockNode,val res:Option[ResetNode],val ena:Option[EnableNode],val ind:Option[ClockNodes.Index]) extends RootNode{

  override val command = ClockCommands

    override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      clk.createContext(context, offset)
      BaseNode.createOptionContext(context, offset, res)
      BaseNode.createOptionContext(context, offset, ena)
   }
  override def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
    val clock = clk.createClock
    val reset =  if (res == None) None else Some(res.get.createReset)
    val enable = if (ena == None) None else Some(ena.get.createEnable)
    return new FlopControl(name,clock,
                           res.map(x => x.createReset),
                           ena.map(x => x.createEnable),
                           ind.map(x => x.createIndex))
  }
}

object ClockNodes {
	
	class Head(signal:SimpleNode) extends BaseNode {
		//val command:Command = ClockHeadCommand
  
		def createFlopHead():FlopControl = {
			val name = signal.value
			ScalaContext.getObjectType[FlopControl](name) match {
            	case Some(x) => return x
            	case _       =>
			}
			Clocks.defaultFlop("clock","reset","enable")
		}
}
	
  class Index(name:SimpleNode.StringNode,index:IntNode) extends CommandNode {

  override val command = EnableCommand
  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      name.createContext(context, offset)
   }

  def createIndex():Clocks.Index = {
    return new Clocks.Index(name.value,index.value.toInt)
  }
}
}