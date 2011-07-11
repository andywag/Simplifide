/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.flop

import com.simplifide.scala2.command._
import com.simplifide.scala2.util.StringOps
import scala.collection.mutable.HashMap

object FlopCommands extends CommandSection("flop") {

  def it:CommandSection = this
	
  override val description:String = "Flop description"

  override val commands:List[Command] = List(ClockHead,Statement)
 
  override val keywords:List[String] = List("flop","clk_head","body")
  /** Parser associate with this command */
  override def getParser() = FlopParser

  override val commandMap = combineMaps(ClockHead.commandMap,Statement.commandMap)

  override val command = combineCommands("flop",ClockHead,Statement)

  object ClockHead extends Command("clock_head") {
    override val command:String = {
      StringOps.createLineSpace(List("clock_head","${clk_head}"), 20)
    }
  override val description:String = "Attach the clock head to this state machine";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("clk_head" -> new StringParameter("ClockHead"))
  }
}

  object Statement extends Command("body") {
    override val command:String = {
      val builder = new StringBuilder()
      builder.append(StringOps.createLineSpace(List("body","\"${output1}\" <- \"${input1}\""), 20))
      builder.append(StringOps.createLineSpace(List("","\"${output2}\" <- \"(${reset2}\",\"${input2}\")"), 20))
      builder.toString
    }
  override val description:String = "Assign list for register";
  override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("clk_head" -> new StringParameter("ClockHead"))
    }
  }



}
