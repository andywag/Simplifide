/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.command

import com.simplifide.scala2.parser.BaseParser
import com.simplifide.scala2.util.StringOps

/**This is the highest level interface which defines this command to the eclipse interface */
abstract class CommandSection(name:String) extends Command(name) {
  /** List of Commands internal to this command */
  val commands:List[Command]
  /** Keywords associated with this command */
  val keywords:List[String]
  /** Parser associate with this command */
  def getParser():BaseParser

  def getJavaKeywords:java.util.List[String] = {
    scala.collection.JavaConversions.asJavaList(keywords)
  }
  def getCommandList:java.util.List[Command] = {
    scala.collection.JavaConversions.asJavaList(commands)
  }

  def combineCommands(name:String,in:List[Command]):String =  {
    val builder = new StringBuilder()
    builder.append(name)
    builder.append(" ${block}")
    builder.append(" { \n")
    val builder2 = new StringBuilder
    in.foreach(x => builder2.append(x.command))
    builder.append(StringOps.indentLines(builder2.toString, 1))
    builder.append("}\n")
    builder.toString
  }

  def combineCommands(name:String,in:Command*):String =  {
    val builder = new StringBuilder()
    builder.append(name)
    builder.append(" { \n")
    val builder2 = new StringBuilder
    in.foreach(x => builder2.append(x.command))
    builder.append(StringOps.indentLines(builder2.toString, 1))
    builder.append("}\n")
    builder.toString
  }



}
