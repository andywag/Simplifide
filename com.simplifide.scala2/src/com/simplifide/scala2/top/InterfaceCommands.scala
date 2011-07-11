/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top

import scala.collection.mutable.ListBuffer

//import com.simplifide.scala2.blocks.lut.LutCommands
import com.simplifide.scala2.command._

import com.simplifide.scala2.parser.BaseParser
import com.simplifide.scala2.expression.ExpressionCommands
/** This class is the main class used by eclipse to get
 *  the commands which are used in the tool. The class is
 *  required for Java as it doesn't support objects
 */
class InterfaceCommands {}

object InterfaceCommands {
	
   def it = this

   val commands:ListBuffer[CommandSection] = new ListBuffer[CommandSection]
                                   
   def addCommands(commands1:java.util.List[CommandSection]) {
	   for (i <- 0 until commands1.size()) {
	  	   commands.append(commands1.get(i));
	   }
	   
   }
   
  /** Return a list of registered commands */
  def getCommands():List[CommandSection] = {
    return commands.toList
    /*
	   List(ClockCommands,
         StateMachineCommand, 
         FlopCommands, 
         ComponentCommands,
         InstanceCommands,
         ExpressionCommands,
         FftCommand)
    */
  }

 

  def getJavaCommands():java.util.List[Command] = {
    scala.collection.JavaConversions.asJavaList(getCommands)
  } 



   /** Returns the keywords defined in the commands. This is
    *  mainly used in coloring for the editor
    **/
  def getJavaKeyWords():java.util.List[String] = {
    val lis = new java.util.ArrayList[String]()
    for (command <- getCommands) {
      val keys =  scala.collection.JavaConversions.asJavaList(command.keywords)
      lis.addAll(keys)
    }
    return lis
  }

 

}
