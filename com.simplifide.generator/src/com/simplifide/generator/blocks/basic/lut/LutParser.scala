/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.lut

import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._

object LutParser extends BaseParser{

  lexical.reserved   ++= List("function","file","signal")

  val commandSection = LutCommands

  def signal_dec   = addPosition("signal"    ~ stringDef ^^ {case "signal"   ~ command => new LutNodes.Signal(command)})
  def function_dec = addPosition("function"  ~ stringDef ^^ {case "function" ~ command => new LutNodes.Function(command)})
  def file_dec     = addPosition("file"      ~ stringDef ^^ {case "file"     ~ command => new LutNodes.File(command)})
  
  def look_up_table = signal_dec ~ (file_dec | function_dec) ^^ {case sig ~ func => new LutNodes()}

  def commandString():String = "look_up_table"

  def parse(s:String):ParseResult[RootNode] = {
    val tokens = new lexical.Scanner(s)
    phrase(look_up_table)(tokens)
  }
}
