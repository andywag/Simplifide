/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.component

import com.simplifide.scala2.command._
import com.simplifide.scala2.util.StringOps
import scala.collection.mutable.HashMap


object ComponentCommands extends CommandSection("component") {

	 def it:CommandSection = this
	
   override val description:String = "Component Declaration"

  override val commands:List[Command] = List(Component)

  override val keywords:List[String] = List("components","component","used")
  /** Parser associate with this command */
  override def getParser() = ComponentParser

  override val commandMap = Component.commandMap

  override val command = {
     val builder = new StringBuilder();
     builder.append("components ${name} {\n")
     val builder2 = new StringBuilder()
     builder2.append(Component.command)
     builder.append(StringOps.indentLines(builder2.toString, 1))
     builder.append("}\n")
     builder.toString
  }



  object Component extends Command("components") {
   override val command:String = {
      val builder = new StringBuilder()
      builder.append(StringOps.createLineSpace(List("component","${entity}","${library}"), 20))
      builder.toString
    }
    override val description:String = "Assign list for register";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("library" -> new StringParameter("Library"))
      HashMap("entity" -> new StringParameter("Library"))
    }
  }
}


