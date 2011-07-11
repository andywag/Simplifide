/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression

import com.simplifide.scala2.command._
import com.simplifide.scala2.parser._
import scala.collection.mutable.HashMap

object ExpressionCommands extends CommandSection("dsp"){

	 def it:CommandSection = this
  /** List of Commands internal to this command */
  override val commands:List[Command] = List()
  /** Keywords associated with this command */
  override val keywords:List[String] = List("dsp","fixed","constant","signal","signalr","input","output",
                                            "complex","static","R","U","T","RC","TC","UC","s","u","sign",
                                            "quad_rotate")
  /** Parser associate with this command */
  override def getParser():BaseParser = ExpressionParser
  
  val command:String = "dsp";
  /** Map for describing which types are required for the template */
  val commandMap:HashMap[String,CommandParameter] = new HashMap[String,CommandParameter]();
  /** Description for this command which shows up in the hover */
  val description:String = "New DSP";
  
}
