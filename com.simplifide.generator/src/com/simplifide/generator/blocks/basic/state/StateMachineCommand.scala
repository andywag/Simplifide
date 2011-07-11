/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.state

import com.simplifide.scala2.clocks._
import com.simplifide.scala2.command._
import com.simplifide.scala2.parser._
import com.simplifide.scala2.util.StringOps
import scala.collection.mutable.HashMap

object StateMachineCommand extends CommandSection("state_machine"){

  def it:CommandSection = this
	
  override val command:String = {
     val builder = new StringBuilder();
     builder.append("state_machine ${name} {\n")
     val builder2 = new StringBuilder()
     builder2.append(OneHotCommand.command)
     builder2.append(ClockHeadCommand.command)
     builder2.append(CurrentStateCommand.command)
     builder2.append(NextStateCommand.command)
     builder2.append(TransitionFirstCommand.command)
     builder.append(StringOps.indentLines(builder2.toString, 1))
     builder.append("}\n")
     builder.toString
  }

  override val commandMap:HashMap[String,CommandParameter] = {
    val map = new HashMap[String,CommandParameter]();
    combineMaps(map,List(OneHotCommand.commandMap,ClockHeadCommand.commandMap,
                         CurrentStateCommand.commandMap, NextStateCommand.commandMap,TransitionFirstCommand.commandMap))
      combineMap(map,HashMap("name" ->  new StringParameter("Name")))
    map
}

  override val description = {
    val builder = new StringBuilder()
    builder.append("Creates a state machine");
    builder.toString
  }

  override val keywords = {
    List("simplifide","end_simplifide","state_machine","state","current_state","next_state","transition","case","clock","reset","enable","posedge",
         "negedge","async","sync","active_high","active_low","true","false","one_hot")
  }

  override val commands = {
    List(ClockHeadCommand, CurrentStateCommand, NextStateCommand)
  }

  override def getParser:BaseParser = StateParser




}

object OneHotCommand extends Command("one_hot") {
  override val command:String = {
    StringOps.createLineSpace(List("one_hot","${one}"), 20)
  }
  override val description:String = "Attach the clock head to this state machine";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("one" -> new BooleanParameter("EnableOneHot"))
  }
}

object ClockHeadCommand extends Command("clock_head") {
  override val command:String = {
    StringOps.createLineSpace(List("clk_head","${clk_head}"), 20)
  }
  override val description:String = "Attach the clock head to this state machine";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("clk_head" -> new StringParameter("StateName"))
  }
}

object CurrentStateCommand extends Command("current_state") {
  override val command:String = {
    StringOps.createLineSpace(List("current_state",StringOps.surroundQuotes("${state}")), 20)
  }
  override val description:String = "The name of the current state";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("state" -> new StringParameter("StateName"))
  }
}

object NextStateCommand extends Command("next_state") {
  override val command:String = {
    StringOps.createLineSpace(List("next_state",StringOps.surroundQuotes("${next_state}")), 20)
  }
  override val description:String = "The name of the next state";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("next_state" -> new StringParameter("NextStateName"))
  }
}

object TransitionFirstCommand extends Command("transition") {
  override val command:String = {
    StringOps.createLineSpace(List("transition","${current} -> ${next} :","${caseopt}","\"${condition}\""), 20)
  }
  override val description:String = "Create the first transition statement";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("current"   -> new StringParameter("CurrentState"),
              "next"      -> new StringParameter("NextState"),
              "caseopt"   -> new StringParameter("CaseCondition"),
              "condition" -> new StringParameter("TransitionCondition"))
  }
}

object TransitionCaseCommand extends Command("case") {
  override val command:String = "case(\"${case_cond}\")"
  override val description:String = "Create the case operator";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("case_cond" -> new StringParameter("CaseCondition"))
  }
}

