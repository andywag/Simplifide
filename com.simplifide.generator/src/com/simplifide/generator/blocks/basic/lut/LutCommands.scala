/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.lut

import com.simplifide.scala2.util._
import com.simplifide.scala2.command._
import com.simplifide.scala2.parser._
import scala.collection.mutable.HashMap


object LutCommands extends CommandSection("look_up_table"){

  val command:String = "look_up_table";
  val description:String = "Look up table";
  val commands:List[Command] = List(Signal,Function,File)
  val commandMap = new HashMap[String,CommandParameter]()
  val keywords:List[String] = List("signal","function","file")
  
  def getParser():BaseParser = LutParser

  object Signal extends Command("signal") {
    override val command:String = {
      StringOps.createLineSpace(List("signal",StringOps.surroundQuotes("${signal}")), 20)
    }
    override val description:String = "Name of the Signal";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("signal"    -> new StringParameter("Signal"))
    }
  }

  object Function extends Command("function") {
    override val command:String = {
      StringOps.createLineSpace(List("function",StringOps.surroundQuotes("${function}")), 20)
    }
    override val description:String = "Define the function which is included in the LUT";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("function"    -> new StringParameter("Function"))
    }
  }

  object File extends Command("file") {
    override val command:String = {
      StringOps.createLineSpace(List("file",StringOps.surroundQuotes("${file}")), 20)
    }
    override val description:String = "Location of the file containing the values";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("file"    -> new StringParameter("File"))
    }
  }
  

}
