/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.nodes

import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.expression.segments._
import com.simplifide.scala2.expression.FixedSegment

import com.simplifide.scala2.core.complex.ComplexSignal
import com.simplifide.scala2.expression.nodes.ExpressionNodes.ExpressionNode
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.core.basic.vars._

class SignalNodes {
	
}

object SignalNodes {

  val roundOptions = List("R","round",
                          "T","truncate",
                          "U","unbiased_round",
                          "RC","round_clip",
                          "TC","truncate_clip",
                          "UC","unbiased_round_clip")
 
  val signalTypes = List("signal","signalr","constant","input","output","fixed")
                          
  class SignalTopNode extends BaseNode

  /*
  class Signals(val signals:List[SignalNode]) extends SignalTopNode {
    def getSignals(info:BaseParserInfo):List[SignalProto] = signals.flatMap(x => x.getSignals(info))
  }*/
 
  abstract class FunctionTopNode extends BaseNode {
    def createFunction(values:List[ExpressionNode],info:BaseParserInfo):Expression = null
  }
  
  class FunctionNode(node:SimpleNode) extends FunctionTopNode {
    override def createFunction(values:List[ExpressionNode],info:BaseParserInfo):Expression = {
      val expressions = values.map(x => x.getExpression(info))
      return new FunctionExpression(node,expressions,None,info.line)
    }
  }
  
  // TODO Need error if more than 1 issue
  class RoundOption(choice:ChoiceNode, fixed:FixedSegment.Nodes.Fixed) extends FunctionTopNode{
    override def createFunction(values:List[ExpressionNode],info:BaseParserInfo):Expression = {
      val first = values(0).getExpression(info)
      choice.value match {
        case "R"              => return new RoundTop.Round(first,fixed.getFixed(info))
        case "round"          => return new RoundTop.Round(first,fixed.getFixed(info))
        case "T"              => return new RoundTop.Trunc(first,fixed.getFixed(info))
        case "truncate"       => return new RoundTop.Trunc(first,fixed.getFixed(info))
        case "C"              => return new RoundTop.Clip(first,fixed.getFixed(info))
        case "clip"           => return new RoundTop.Clip(first,fixed.getFixed(info))
        case "RC"             => return new RoundTop.RoundClip(first,fixed.getFixed(info))
        case "round_clip"     => return new RoundTop.RoundClip(first,fixed.getFixed(info))
        case "TC"             => return new RoundTop.TruncClip(first,fixed.getFixed(info))
        case "truncate_clip"  => return new RoundTop.TruncClip(first,fixed.getFixed(info))
        case _     => return new RoundTop.Trunc(first,fixed.getFixed(info))
      }
    }
   
  }

 

  class SignalNode(typ:ChoiceNode,
                        com:Option[ComplexNode],
                        fixed:FixedSegment.Nodes.Fixed,
                        single:List[SingleSignalNode]) extends ExpressionNodes.SegmentTopNode {

    override def getSignals(info:BaseParserInfo):List[SignalProto] = {
      val fix = fixed.getFixed(info)
      com match {
        
        case None => return single.map(x => x.getSignal(fix,typ.value,info))
        case _    =>  return single.map(x => new SignalProto.Complex(x.getSignal(fix,typ.value,info)))
      }
      
    }
  }
  
  class SingleSignalNode(name:IdentNode,vec:List[VectorNode],
                              reg:Option[VectorNode],assign:Option[SignalAssign]) {
    def getSignal(fixed:FixedCall, typ:String, info:BaseParserInfo):SignalProto = {
      val nreg:Option[Int]  = reg.map(x => x.getNumber)
      val number:List[Int] = vec.map(x => x.getNumber) 
      typ match {
        case "signal"    => return new SignalProto.SignalP(name.value,number,nreg,fixed)
        case "signalr"    => return new SignalProto.SignalrP(name.value,number,nreg,fixed)
        case "constant"  => return new SignalProto.ConstantP(name.value,number,nreg,fixed,assign.get.getInitial)
        case "input"     => return new SignalProto.InputP(name.value,number,nreg,fixed)
        case "output"    => return new SignalProto.OutputP(name.value,number,nreg,fixed)
        case "fixed"     => return new SignalProto.FixedP(name.value,number,nreg,fixed)  
      }
    }
    
  }
  
  class ComplexNode extends SignalTopNode
  
  class VectorNode(number:IntNode) extends SignalTopNode{
    def getNumber:Int = number.value.toInt
  }
  
 

 

  class SignalAssign(val value:SignalAssignNode) extends SignalTopNode {
    def getInitial:ConstantValue = value.getValue
  }
  
  abstract class SignalAssignNode extends BaseNode {
    def getValue:ConstantValue
  }
  class FloatAssign(val value:SimpleNode) extends SignalAssignNode {
    override def getValue:ConstantValue = new ConstantValue.FloatValue(value.value.toFloat)
  }
  
  class StringAssign(val value:SimpleNode.StringNode) extends SignalAssignNode {
    override def getValue:ConstantValue = new ConstantValue.StringValue(value.value)
  }
  
}
