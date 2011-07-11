/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.state

import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.command._
import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.top._
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.clocks.FlopControl
class StateNodes {

}

class StateMachineNode(val children:StateExpressionNode) extends RootNode {

  override val command:Command = StateMachineCommand
  override def createContext(context:ParserContext,offset:Int) {
    super.createContext(context, offset)
    children.createContext(context, offset)
  }

  override def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = createStateMachine(name)

  def createStateMachine(name:String):StateMachine ={
    if (children.isOneHot) {
      return new StateMachineOneHot(name,
                            children.createFlopHead(),
                            children.createCurrentState(),
                            children.createNextState(),
                            children.trans.createTransitions())

     }
     else {
       return new StateMachine(name,
                            children.createFlopHead(),
                            children.createCurrentState(),
                            children.createNextState(),
                            children.trans.createTransitions())
     }
    }
    
}

class StateExpressionNode(val onehot:Option[OneHotNode],val clkhead:Option[StateSignalNode], current:Option[StateSignalNode],
                          next:Option[StateSignalNode], val trans:TransitionNode) extends BaseNode {

   override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      BaseNode.createOptionContext(context, offset, onehot)
      BaseNode.createOptionContext(context, offset, clkhead)
      BaseNode.createOptionContext(context, offset, current)
      BaseNode.createOptionContext(context, offset, next)
      trans.createContext(context, offset)
   }

  def isOneHot:Boolean = {
    onehot match {
      case Some(x) => x.signal.isTrue
      case _       => return false
    }
  }

  def createCurrentState():Option[BaseCodeSegment] = current match {
    case Some(x) => Some(BasicSegments.ident(x.signal.value))
    case None => None
  }
  
  def createNextState():Option[BaseCodeSegment] = next match {
    case Some(x) => Some(BasicSegments.ident(x.signal.value))
    case None => None
  }

  def createFlopHead():FlopControl = {
    clkhead match {
      case (Some(x)) => {
          val name = x.signal.value;
          ScalaContext.getObjectType[FlopControl](name) match {
            case Some(x) => return x
            case _       =>
          }
      }
      case _      =>
    }
    Clocks.defaultFlop("clock","reset","enable")
  }


}

abstract class StateSignalNode(val signal:SimpleNode) extends CommandNode {
  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      signal.createContext(context, offset)

   }
}

class OneHotNode(val signal:BooleanNode) extends BaseNode {
    override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      signal.createContext(context, offset)
   }
}

class ClockHeadNode(signal:SimpleNode) extends StateSignalNode(signal) {
  val command:Command = ClockHeadCommand
  
   def createFlopHead():FlopControl = {
     val name = signal.value
     ScalaContext.getObjectType[FlopControl](name) match {
            case Some(x) => return x
            case _       =>
    }
    Clocks.defaultFlop("clock","reset","enable")
  }
}

class CurrentStateNode(signal:SimpleNode) extends StateSignalNode(signal) {
  val command:Command = CurrentStateCommand
}

class NextStateNode(signal:SimpleNode) extends StateSignalNode(signal){
  val command:Command = NextStateCommand
}

class TransitionNode(children:List[TransitionFirstNode]) extends BaseNode {

  

   override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      children.foreach(x => x.createContext(context, offset))
   }

  def createTransitions():ListBuffer[Transition] = {
    val buf:ListBuffer[Transition] = new ListBuffer[Transition]();
    for (child <- children) {
      buf.append(child.createTransition())
    }
    return buf
  }
  
}

class StateHotNode(val number:IntNode) extends BaseNode {
    override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      number.createContext(context, offset)
   }
}
class StateDefNode(val ident:IdentNode,val number:Option[StateHotNode]) extends BaseNode {

  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      ident.createContext(context, offset)
   }

  def getNumber:Option[Int] = number match {
    case Some(x) => return Some(x.number.getNumber)
    case _       => None
  }
  def createState:State = {
    return new State(ident.value,getNumber)
  }
}

class TransitionFirstNode(val currentState:StateDefNode, nextState:List[TransitionOtherNode]) extends BaseNode {

  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      currentState.createContext(context, offset)
      nextState.foreach(x => x.createContext(context, offset))
   }

  def getState():State = {return currentState.createState}

  def createTransition():Transition = {
    def getTransition(states:ListBuffer[TransState]):Transition = {
      if (nextState.head.isCase) return new CaseTransition(getState(),nextState.head.getCase,states)
      else return new IfTransition(getState(),states)
    }

    var cond:ListBuffer[TransState] = new ListBuffer[TransState]();
     for (node <- nextState) {
        cond.append(node.createTransition())
     }
     return getTransition(cond)

  }



}

class TransitionOtherNode(nextState:StateDefNode,condition:Option[SimpleNode.StringNode],cas:Option[TransitionCaseNode]) extends BaseNode {

  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      nextState.createContext(context, offset)
      BaseNode.createOptionContext(context, offset, condition)
      BaseNode.createOptionContext(context, offset, cas)
   }

  def getCase():BaseCodeSegment = {
    if (cas != None) return cas.get.getCaseCondition()
    return null;
  }
  def isCase():Boolean = {
    if (cas == None) return false
    return true;
  }
  def createTransition():TransState = {
    condition match {
      case Some(x) => return new TransState(nextState.createState,Some(x.value))
      case _       => return new TransState(nextState.createState,None)
    }
    
  }
}

class TransitionCaseNode(val condition:SimpleNode.StringNode) extends BaseNode {

  override def createContext(context:ParserContext,offset:Int) {
      super.createContext(context, offset)
      condition.createContext(context, offset)
   }

  def getCaseCondition():BaseCodeSegment = {return BasicSegments.ident(condition.value)}
}
