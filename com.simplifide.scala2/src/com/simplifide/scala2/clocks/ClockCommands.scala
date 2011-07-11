/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.clocks

import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.command._
import com.simplifide.scala2.parser._

import scala.collection.mutable.HashMap

object ClockCommands extends CommandSection("clock_head") {

  def it:CommandSection = this
	
  val commands:List[Command] = List(ClockCommand,ResetCommand,EnableCommand)

  override val command:String = {
    val builder = new StringBuilder();
     builder.append("clock_head ${name} {\n")
     val builder2 = new StringBuilder()
     builder2.append(ClockCommand.command)
     builder2.append(ResetCommand.command)
     builder2.append(EnableCommand.command)
     builder.append(StringOps.indentLines(builder2.toString, 1))
     builder.append("}\n")
     builder.toString

  }

  override val commandMap:HashMap[String,CommandParameter] = {
    val map = new HashMap[String,CommandParameter]();
      combineMaps(map,List(ClockCommand.commandMap,ResetCommand.commandMap,EnableCommand.commandMap))
      combineMap(map,HashMap("name" ->  new StringParameter("Name")))
      map
}

  override val description = {
    val builder = new StringBuilder()
    builder.append("Defines a head which can be used for other flops");
    builder.toString

  }


  override val keywords = {
    List("simplifide","clock_head","clock","reset","enable","index","posedge",
         "negedge","async","sync","active_high","active_low")
  }

  override def getParser():BaseParser = new ClockParser()

}

object ClockCommand extends Command("clock") {
  override val command:String = {
    StringOps.createLineSpace(List("clock",StringOps.surroundQuotes("${clkname}"),"${clkedge}"), 20)
  }
  override val description:String = "Create a clock";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("clkname"    -> new StringParameter("Name"),
              "clkedge"    -> new ChoiceParameter("ClockEdge",List("posedge","negedge")))
  }
}

object ResetCommand extends Command("reset") {
  override val command:String = {
    StringOps.createLineSpace(List("reset",StringOps.surroundQuotes("${resetname}"),"${sync}","${activeedge}"), 20)
  }
  override val description:String = "Create a reset";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("resetname"   -> new StringParameter("Name"),
              "sync"        -> new ChoiceParameter("ResetType",List("sync","async")),
              "activeedge"  -> new ChoiceParameter("ResetActive",List("active_low","active_high")))
  }
}

object EnableCommand extends Command("enable") {
  override val command:String = {
    StringOps.createLineSpace(List("enable",StringOps.surroundQuotes("${enablename}")), 20)
  }
  override val description:String = "Create a reset";
  override val commandMap:HashMap[String,CommandParameter] = {
        HashMap("enablename" -> new StringParameter("Name"))
  }
}

