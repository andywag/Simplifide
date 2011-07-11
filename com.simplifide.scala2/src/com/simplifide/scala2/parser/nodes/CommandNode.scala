/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser.nodes

import com.simplifide.scala2.command._

/** Node which has a command associated with it*/
abstract class CommandNode extends BaseNode{
  /** Command this node is attached to */
  val command:Command;
  /** Convenience class to get the command */
  def getCommand():Command = return command;

  override def containsDescription:Boolean = true
  override def getDescription:String = {
	  if (command != null) command.getDetailedDescription
	  else ""
  }
  

}
