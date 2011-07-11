/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top

import com.simplifide.scala2.command.CommandSection
import com.simplifide.scala2.parser.nodes.BaseNode

class ParserContext {
  val nodes = new java.util.ArrayList[BaseNode]()
  var commandSection:CommandSection = null;
  
  def getCommandSection:CommandSection = commandSection;
  
  def getNodes:java.util.ArrayList[BaseNode] = return nodes
  
}
