/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser.nodes

import com.simplifide.scala2.top.ParserContext
import com.simplifide.scala2.command.Command

/** The base level node which contains all of the generic operations associated with being a return type from a parser */
class BaseNode {

  var position:NodePosition = NoNodePosition;
  var nodeText:Option[String] = None
  /** Getter and Setter for Position of Node */
  def setNodePosition(posit:NodePosition) {position = posit}
  def getPosition:NodePosition = {return position}
  /** Returns the length of this node -- only useful when creating the node */
  def getLength:Int = 0
  /** Defines if there is a description used for text hovers */
  def containsDescription:Boolean = false
  /** Returns the description of this node */
  def getDescription:String = return ""

  /** Determines whether the node contains the given offset */
  def containsPosition(offset:Int):Boolean = {
    return position.containsOffset(offset)
  }

  /** Method which creates the context associated with this node. Used by the editor for normal tasks */
  def createContext(context:ParserContext,offset:Int) = {
    if (containsPosition(offset)) context.nodes.add(this)
  }
  
  override def toString = "BaseNode" + this.getPosition 

}

object BaseNode {



  def createOptionContext(context:ParserContext,offset:Int, node:Option[BaseNode]) = {
    if (node != None && node.get.containsPosition(offset)) node.get.createContext(context, offset)
  }
}





