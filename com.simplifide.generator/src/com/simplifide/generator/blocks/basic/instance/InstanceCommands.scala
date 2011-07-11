/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.instance

import com.simplifide.scala2.command._
import com.simplifide.scala2.util.StringOps
import scala.collection.mutable.HashMap

object InstanceCommands extends CommandSection("instances") {

	 def it:CommandSection = this
	
   override val description:String = "Intance Declaration"

  override val commands:List[Command] = List(Instance,Connect)

  override val keywords:List[String] = List("instances","instance","connect")
  /** Parser associate with this command */
  override def getParser = InstanceParser

  override val commandMap = Instance.commandMap

  override val command = {
     val builder = new StringBuilder();
     builder.append("instances ${name} {\n")
     val builder2 = new StringBuilder()
     builder2.append(Instance.command)
     builder.append(StringOps.indentLines(builder2.toString, 1))
     builder.append("}\n")
     builder.toString
  }



  object Instance extends Command("instance") {
   override val command:String = {
      val builder = new StringBuilder()
      builder.append(StringOps.createLineSpace(List("instance","${entity}","${library}"), 20))
      builder.toString
    }
    override val description:String = "Define a New Instantiation";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("entity" -> new StringParameter("Entity"))
      HashMap("library" -> new StringParameter("Library"))
    }
  }

   object Connect extends Command("connect") {
    override val description:String = "Create a non-default connection using regular expression";
    override val command:String = {
      val builder = new StringBuilder()
      builder.append(StringOps.createLineSpace(List("instance","${src}","${dest}"), 20))
      builder.toString
    }
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("src" -> new StringParameter("Source"))
      HashMap("dest" -> new StringParameter("Destination"))
    }
  }

}


