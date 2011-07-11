/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.flop

import com.simplifide.scala2.parser.BaseParser
import com.simplifide.scala2.parser.nodes.RootNode

object FlopParser extends BaseParser{
  lexical.reserved   ++= List("flop","clock_head","body")
  lexical.delimiters ++= List("<-","(",")",";",",")

  val commandSection = FlopCommands
  def commandString():String = "flop"

   def body_reset = addPosition("(" ~ stringDef("Reset") ~ "," ~stringDef("Input") ~ ")" ^^
    {case "(" ~ reset ~ "," ~ input  ~ ")" => new FlopNodes.Reset(input,reset)})

   def body_input = addPosition(stringDef("Input") ^^ {case in =>  new FlopNodes.Input(in)})

   def body_item = addPosition(stringDef("Output") ~ "<-" ~ (body_input | body_reset) ~ opt(";")  ^^
   {case out ~ "<-" ~ body ~ s => new FlopNodes.Item(out,body)})

   def body_statement    = addPosition("body" ~ rep(body_item) ^^
     {case "body" ~ items  => new FlopNodes.Statement(items)})

  def clk_head_statement    = addPosition("clock_head" ~ identDef("ClockHead") ^^
     {case "clock_head" ~ ident  => new FlopNodes.ClockHead(ident)})

  def flop_statement = addPosition(clk_head_statement ~ rep(body_statement) ^^
    {case clk ~ statements  => new FlopNodes.Top(clk,statements)})

  def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(flop_statement)(tokens)
  }
}
