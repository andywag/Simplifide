/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.command

import com.simplifide.scala2.util.StringOps
import scala.collection.JavaConversions

class CommandParameter(val name:String) {
  def getJavaChoices():java.util.List[String] = return null;
  def getDescription():String = name
}

class StringParameter(name:String) extends CommandParameter(name) {
  override def getDescription():String = StringOps.surroundTag(name,"define").toString
}

class IntParameter(name:String) extends CommandParameter(name)

class FloatParameter(name:String) extends CommandParameter(name)

class ChoiceParameter(name:String,val choices:List[String]) extends CommandParameter(name) {

  override def getDescription():String = {
    val builder = new StringBuilder()
    var first = true;
    for (choice <- choices) {
      if (first) first = false;
      else builder.append(" | ")
      builder.append("<type>")
      builder.append(choice)
      builder.append("</type>")
    }
    builder.toString
  }
  override def getJavaChoices():java.util.List[String] = {
    JavaConversions.asJavaList[String](choices)

  }
}

class BooleanParameter(name:String) extends ChoiceParameter(name,List("true","false"))