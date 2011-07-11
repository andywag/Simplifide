/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.lut

import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._


class LutNodes extends RootNode{

  val command:Command = LutCommands
  def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
    return null;
 }

}

object LutNodes {


  class Signal(val node:SimpleNode.StringNode) extends CommandNode {
    val command:Command = LutCommands.Signal
  }

  class File(val node:SimpleNode.StringNode) extends CommandNode {
    val command:Command = LutCommands.File
  }

  class Function(val node:SimpleNode.StringNode) extends CommandNode {
    val command:Command = LutCommands.Function
  }

}
