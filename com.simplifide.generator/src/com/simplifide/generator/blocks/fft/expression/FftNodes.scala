/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft.expression

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.command._
import com.simplifide.generator.blocks.basic.state.ClockHeadNode
import com.simplifide.generator.blocks.fft.Fft
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.expression.FixedSegment
import com.simplifide.generator.general.GeneralSegment

class FftNodes {}

object FftNodes {
  
  val FFT_TYPES = List("fft","ifft")	
	
  class Top extends BaseNode
  
  class Total(val clk:ClockHeadNode,
              val nam:Name,
              val loc:Location,
              val typ:FftType,
              val ord:Order,
              val len:GeneralSegment.Nodes.IntDefinition,
              val inw:InFixed,
              val outw:OutFixed,
              val ang:Angle,
              val but:Butterfly,
              val int:Internal,
              val inp:GeneralSegment.Nodes.NameDefinition,
              val op:GeneralSegment.Nodes.NameDefinition) extends RootNode {
  
    val command:Command = FftCommand
    override def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
      
      val param = new Fft.Param(len.getValue,
                                2,
                                typ.isFFT,
                                ord.isDit,
                                inw.getFixed(info),
                                outw.getFixed(info),
                                ang.getFixed(info),
                                but.getFixed(info),
                                int.getFixed(info))

      val signal = new Fft.Signals(clk.createFlopHead,
                                   inp.getValue,
                                   op.getValue)
      return new FftSegment(name,nam.getName,loc.getLocation,param,signal)
    }
    
    
  } 

  class FftType(val node:ChoiceNode) extends Top {
	  def isFFT:Boolean = node.value match {
	 	  case "fft" => true
	 	  case _     => false
	  }
  }

  class Order(val node : ChoiceNode) extends Top {
    def isDit:Boolean = {
      node.value match {
        case "dit" => true
	 	    case _     => false
      }
    }
  }

  //case class Length(node:IntNode) extends Top {
  //  def getFftLength:Int = node.getNumber
  //}
  
  class Angle(val node:FixedSegment.Nodes.Fixed)  extends Top {
    def getFixed(info:BaseParserInfo):FixedType = node.getFixed(info).getFixedBasic.get
  }
  class Butterfly(val node:List[FixedSegment.Nodes.Fixed])  extends Top {
    def getFixed(info:BaseParserInfo):List[FixedType] = node.map(x => x.getFixed(info).getFixedBasic.get)
  }
  class Internal(val node:List[FixedSegment.Nodes.Fixed])  extends Top {
    def getFixed(info:BaseParserInfo):List[FixedType] = node.map(x => x.getFixed(info).getFixedBasic.get)
  }
  class Name(val node:SimpleNode.StringNode)  extends Top {
    def getName:String = node.value
  }
  class Location(val node:SimpleNode.StringNode)  extends Top {
    def getLocation:String = node.value
  }
  class InFixed(val node:FixedSegment.Nodes.Fixed)  extends Top {
    //def getName:String = nam.value
    def getFixed(info:BaseParserInfo):FixedType = node.getFixed(info).getFixedBasic.get
    //def getOpType:OpType = OpType.ModuleInput
    //def getSignal(length:Int,info:BaseParserInfo):ComplexSignal = {
    //  val proto = new SignalNew(getName,getOpType,getFixed(info),new VectorType(List(length),None))
    //  return new ComplexSignal(proto)
    //}
  }
  class OutFixed(override val node:FixedSegment.Nodes.Fixed) extends InFixed(node) {
  }

  class Input(val node:SimpleNode.StringNode)  extends Top {
    def getName:String = node.value
  }
  class Output(val node:SimpleNode.StringNode)  extends Top {
    def getName:String = node.value
  }

  /*
  case class OutFixed(nam:StringNode,node:FixedNodes.Fixed)  extends Top {
    def getName:String = nam.value
    def getFixed(info:BaseParserInfo):FixedCall = node.getFixed(info)
  }*/

}
