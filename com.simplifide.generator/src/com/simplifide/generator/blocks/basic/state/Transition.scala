/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.state

import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._


import com.simplifide.scala2.core.basic.operator._

import scala.collection.mutable.ListBuffer

abstract class Transition(val state:State, val transitions:ListBuffer[TransState]) {

    def createVhdlOneHotTransition(machine:StateMachine):BaseCodeSegment = {
      val ifelse = new IfElseStatement()
      val cond = new OperatorEquals(new SliceOp(machine.getCurrentState,state),new IdentSegment("'1'"))
      ifelse.addClause(Some(cond), this.createCondition(machine,vhdl_hot_assign))
      return ifelse
  }

    def createOneHotTransition(state1:CaseStatement, machine:StateMachine) {
      state1.addCondition(Some(new SliceOp(machine.getCurrentState,state)),this.createCondition(machine,one_hot_assign))
    }

    def createTransition(state1:CaseStatement, machine:StateMachine) {
        state1.addCondition(Some(state),this.createCondition(machine,assign))
    }

    def createCondition(machine:StateMachine,
                        generator:(BaseCodeSegment,BaseCodeSegment)=>AssignStatement):BaseCodeSegment

    val assign = (state:BaseCodeSegment,in:BaseCodeSegment) => new AssignBlockingStatement(state,in)
    val one_hot_assign = (state:BaseCodeSegment,in:BaseCodeSegment) => new AssignBlockingStatement(new SliceOp(state,in),new IdentSegment("1'b1"))
    val vhdl_hot_assign = (state:BaseCodeSegment,in:BaseCodeSegment) => new AssignStatement(new SliceOp(state,in),new IdentSegment("'1'"))


}

class IfTransition(override val state:State,override val transitions:ListBuffer[TransState]) extends Transition(state,transitions) {
  override def createCondition(machine:StateMachine,generator:(BaseCodeSegment,BaseCodeSegment)=>AssignStatement):BaseCodeSegment =  {
    val cond = new IfElseStatement()

    if (transitions.length == 1 && transitions.head.cond == None) { // Special case with no condition
      return generator(machine.getNextState, transitions.head.state)
    } 

    var default = false
    for (transition <- transitions) {
      val seg = transition.cond match {case Some(x) => Some(new IdentSegment(x)); case _ => None;}
      if (transition.cond == None) default = true
      cond.addClause(seg,generator(machine.getNextState, transition.state))
    }
    if (!default) { // Handles the default clause if it doesn't exist
      cond.addClause(None, generator(machine.getNextState,state))
    }

    return cond;
  }
}

class CaseTransition(override val state:State,val caseCond:BaseCodeSegment,override val transitions:ListBuffer[TransState]) extends Transition(state,transitions) {
override def createCondition(machine:StateMachine,
                             generator:(BaseCodeSegment,BaseCodeSegment)=>AssignStatement):BaseCodeSegment =  {
     val cond = new CaseStatement(caseCond)
     for (transition <- transitions) {
       val seg = transition.cond match {case Some(x) => Some(new IdentSegment(x)); case _ => None }
       cond.addCondition(seg,generator(machine.getNextState, transition.state))
      }
      return cond;
  }
}

class TransState(val state:State,val cond:Option[String])
