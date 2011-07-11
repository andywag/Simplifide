/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.nodes

import com.simplifide.scala2.clocks.ClockNodes
import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.expression.segments._
import com.simplifide.scala2.core.basic.vars._

import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parser.BaseTokens
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.core.basic.Clocks

class ExpressionNodes {
	
}

object ExpressionNodes extends BaseNode {

  /** Base class which returns an expression */
  class ExpressionNode extends BaseNode {
    def getExpression(info:BaseParserInfo):Expression = null
  }
  /** Node which encompasses both signals and assignments */
  abstract class SegmentTopNode extends ExpressionNode {
    def getAssignment(info:BaseParserInfo):List[Expression] = List[AssignmentExpr]()
    def getSignals(info:BaseParserInfo):List[SignalProto]
  }
  
  
  class Segments(val clk:Option[ClockNodes.Head], val expressions:List[SegmentTopNode]) extends RootNode{
    
    def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
      
      val flop = clk match {
        case Some(x) => x.createFlopHead
        case None    => Clocks.defaultFlop("clock","reset","enable")
      }
      val protos = expressions.flatMap(x => x.getSignals(info))
      val assigns = expressions.flatMap(x => x.getAssignment(info))
      //val protos = signals.getSignals(info)
      //val assigns = assignments.getAssignments(info);
        
      return new ExpressionBodyBase(name,flop,protos,assigns)
     }
     val command:Command = null
  }


  /*
  class Assignments(items:List[Assignment]) extends BaseNode {
    def getAssignments(info:BaseParserInfo):List[AssignmentExpr] = items.map(x => x.getAssignment(info))  
  }*/
    
  class Assignment(val output:Variable,val input:Option[AssignBody]) extends SegmentTopNode {
    override def getAssignment(info:BaseParserInfo):List[Expression] = input match {
      case Some(x) => return List(new AssignmentExpr(output.getVariable(info),x.getExpression(info),this.nodeText))
      case None    => return List(output.getExpression(info))
    }
      
      
    override def getSignals(info:BaseParserInfo):List[SignalProto]        = List[SignalProto]()
  }
  
  class AssignBody(val input:ExprTop) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = {
      return input.getExpression(info)
    }
  }
  
  class ExprTop(val expr:ExpressionNode, val qu:Option[Question]) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = {
       qu match {
         case None    => return expr.getExpression(info)
         case Some(x) => return new QuestionExpr(expr.getExpression(info),x.getTrue(info),x.getFalse(info))
       }
    }
  }
  class Question(val tru:ExpressionNode, val fal:ExpressionNode) extends ExpressionNode {
    def getTrue(info:BaseParserInfo):Expression  = tru.getExpression(info)
    def getFalse(info:BaseParserInfo):Expression = fal.getExpression(info)
  }


  class Expr(val value:Term,val items:List[ExprItem]) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = {
       if (items.length == 0 ) return value.getExpression(info)
       return new Addition(value.getExpression(info),items.map(x => x.getTerm(info)))   
    }
  }
  abstract class ExprItem(val value:ExpressionNode) extends ExpressionNode {
    def getTerm(info:BaseParserInfo):com.simplifide.scala2.expression.segments.Term
  }
  
  
  class Sub(override val value:ExpressionNode) extends ExprItem(value) {
    override def getTerm(info:BaseParserInfo):com.simplifide.scala2.expression.segments.Term = 
      return new com.simplifide.scala2.expression.segments.Term.Sub(value.getExpression(info))
  }
  class Add(override val value:ExpressionNode) extends ExprItem(value) {
    override def getTerm(info:BaseParserInfo):com.simplifide.scala2.expression.segments.Term = 
      return new com.simplifide.scala2.expression.segments.Term.Add(value.getExpression(info))
  }

  class Term(val factor:ExpressionNode,val term:Option[ExpressionNode]) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = {
       term match {
         case None    => return factor.getExpression(info)
         case Some(x) => return new Multiply(factor.getExpression(info),x.getExpression(info))
       }
       
    }
  }

  class TermItem(val value:ExpressionNode) extends ExpressionNode
  class Div(override val value:ExpressionNode)  extends TermItem(value) {
    override def getExpression(info:BaseParserInfo):Expression = return new Factor.Div(value.getExpression(info))
  }
  class Mult(override val value:ExpressionNode) extends TermItem(value) {
    override def getExpression(info:BaseParserInfo):Expression = return new Factor.Mult(value.getExpression(info))
  }

  class FactorNode(val value:ExpressionNode) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = return value.getExpression(info)
  }
   
  class UnaryNode(val typ:Option[String],val value:ExpressionNode) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = {
      val exp = value.getExpression(info)
      typ match {
        case None    => return exp
        case Some(x) => return new Unary(exp)
      }
    }
  }
  
  /** TODO issue with multiple expression */ 
  class Par(val value:List[ExpressionNode],val round:Option[SignalNodes.FunctionTopNode]) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = {
      round match {
        case Some(x) => return x.createFunction(value,info)
        case None    => return new Paren(value(0).getExpression(info))
      }
    }
  }
  
  class Integer(val value:IntNode) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = new ParameterCall.Integer(value.getNumber)
  }
  
  class Float(val value:SimpleNode.FloatTop) extends ExpressionNode {
    override def getExpression(info:BaseParserInfo):Expression = new ParameterCall(value.getFloatValue)
  }
  
  class  Variable(val value:SimpleNode,val index:Option[Index],val func:Option[FuncBody]) extends ExpressionNode {
    
    def getVariable(info:BaseParserInfo):SignalCallTop = 
      return getExpression(info).asInstanceOf[SignalCallTop]
//new SignalCall(value.value,index.map(x => x.getIndex), value.position.line + info.line)
      
    
    override def getExpression(info:BaseParserInfo):Expression = {
      func match {
        case Some(x) => return new FunctionExpression(value,x.expressions.map(y => y.getExpression(info)),
                                                      index.map(x => x.getIndex),value.position.line + info.line)
        case None    => return new SignalCall(value.value,index.map(x => x.getIndex), value.position.line + info.line)
      }
    }
  }
  class Index(val num:Option[IntNode],val value:SimpleNode,val delta:Option[Delta]) extends ExpressionNode {
    def getIndex:SignalIndex = new SignalIndex(value.value,
                                               num.map(x => x.getNumber),
                                               delta.map(x => x.getValue),
                                               delta.map(x => x.getSign))
  }
  class Delta(val value:IntNode,val pos:Boolean) extends ExpressionNode {
    def getValue:Int = value.getNumber
    def getSign:Boolean = pos
    
  }
  
  class FuncBody(val expressions:List[ExpressionNode]) extends ExpressionNode



}
