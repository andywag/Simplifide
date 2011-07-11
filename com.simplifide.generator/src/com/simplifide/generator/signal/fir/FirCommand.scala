/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.signal.fir

import com.simplifide.scala2.command._
import com.simplifide.scala2.parser._

import scala.collection.mutable.HashMap

object FirCommand extends CommandSection("fir") {

  override def getParser:BaseParser = null

  override val command:String = {
    val builder = new StringBuilder();

    builder.toString
  };
  override val description:String = "FIR filter";
  override val commandMap:HashMap[String,CommandParameter] = {
    HashMap()
  }
  override val keywords = {
    List()
  }

  override val commands = {
    List()
  }

}
