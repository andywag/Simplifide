/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.component

import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._

object ComponentParser extends BaseParser{

  lexical.delimiters ++= List(";")
  lexical.reserved   ++= List("component","used")

  val commandSection = ComponentCommands
  def commandString():String = "components"

  def used      = "used" ^^ {case "used" => new ComponentNodes.UsedNode()}
  def component =  addPosition("component" ~ identDef("Module Name") ~ opt(identDef("Library Name")) ~ ";" ^^
    {case "component" ~ mod ~lib ~ ";" => new ComponentNodes.ComponentNode(mod,lib)})
   
  def components = addPosition(rep(component | used) ^^ {case lis => new ComponentNodes.ComponentsNode(lis)})

   def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(components)(tokens)
    }
}
