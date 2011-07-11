/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.flop

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.command._
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.top.ParserContext
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.basic.flop._
import com.simplifide.scala2.top._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.{SimpleSegment, BaseCodeSegment}
import com.simplifide.scala2.parser.nodes._

object FlopNodes {

  class Top(val clk:ClockHead,val statements:List[Statement]) extends RootNode {
    val command:Command = FlopCommands
    override def createSegment(contxt:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
      val flops = statements.map(x => x.getFlop(clk.getFlopControl))
      return new FlopSegment(name,flops)
    }
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      clk.createContext(context, offset)
      statements.foreach(x => x.createContext(context, offset))
    }
  }
  class ClockHead(val signal:SimpleNode) extends CommandNode {
    val command:Command = FlopCommands.ClockHead
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      signal.createContext(context, offset)
    }
    def getFlopControl:FlopControl = {
      ScalaContext.getObjectType[FlopControl](signal.value) match {
        case (Some(x)) => return x
        case None      => return Clocks.defaultFlop("clock","reset","enable")
      }
    }

  }

  class Statement(val items:List[Item]) extends BaseNode {
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      items.foreach(x => x.createContext(context, offset))
    }
    def getFlop(control:FlopControl):FlopIndividual = {
      return new FlopIndividual("",control,getResetSegments,getInputSegments)
    }

    def getResetSegments:List[SimpleFlop.Segment] = items.map(x => x.getResetSegment)
    def getInputSegments:List[SimpleFlop.Segment] = items.map(x => x.getInputSegment)
  }

  class Item(val out:SimpleNode.StringNode,val body:FlopNodes.Body) extends BaseNode {
    val command:Command = FlopCommands.Statement
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      out.createContext(context, offset)
      body.createContext(context, offset)
    }
    def getResetSegment:SimpleFlop.Segment = {
        val seg = body.getReset.flatMap[BaseCodeSegment](x => Some(new SimpleSegment(x)))
        new SimpleFlop.Segment(new SimpleSegment(out.value),seg)
    }
    def getInputSegment:SimpleFlop.Segment = {
        new SimpleFlop.Segment(new SimpleSegment(out.value),Some(new SimpleSegment(body.in.value)))
    }
  }

  class Body(val in:SimpleNode.StringNode) extends BaseNode {
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      in.createContext(context, offset)
    }
    def getInput:String = return in.value
    def getReset:Option[String] = return None
  }
  
  class Reset(override val in:SimpleNode.StringNode,val res:SimpleNode.StringNode) extends Body(in) {
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      res.createContext(context, offset)
    }
    override def getReset:Option[String] = return Some(res.value)
  }

  class Input(override val in:SimpleNode.StringNode) extends Body(in) {

  }

}
