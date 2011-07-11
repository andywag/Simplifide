/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.instance 

import com.simplifide.scala2.command.CommandSection
import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._

object InstanceParser extends BaseParser{

  lexical.delimiters ++= List(";")
  lexical.reserved   ++= List("instance","connect")

  val commandSection:CommandSection = InstanceCommands
  def commandString():String = "instances"

  def connect      = addPosition("connect" ~ stringDef("Source") ~ stringDef("Destination") ~ ";" ^^
  {case "connect" ~ src ~ dest ~ ";" => new InstanceNodes.Connect(src,dest)})

  def instance =  addPosition("instance" ~ identDef("Module Name") ~ opt(identDef("Library Name")) ~ ";" ^^
  {case "instance" ~ mod ~lib ~ ";" => new InstanceNodes.Instance(mod,lib)})

  def instance_def = addPosition(instance ~ rep(connect) ^^
  {case ins ~ con => new InstanceNodes.InstanceDef(ins,con)})

  def instances = rep(instance_def) ^^ {case ins => new InstanceNodes.Instances(ins)}

   def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(instances)(tokens)
    }
}
