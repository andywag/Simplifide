/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.util
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import java.lang.StringBuilder
import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer

class StringOps {}


object StringOps {

  val INDENT = 20

  def writeSpaces(value:String,indent:Int):String = {
    val build:StringBuilder = new StringBuilder();
    for (i <- 0 to indent) build.append(' ');
    build.append(value)
    return build.toString
  }
  
  def writeIndent(value:String,indent:Int):String = {
    val build:StringBuilder = new StringBuilder();
    for (i <- 0 to 3*indent) build.append(' ');
    build.append(value)
    return build.toString
  }

   def indentLines(value:String,indent:Int):String = {
    val build:StringBuilder = new StringBuilder();
    val lines = value.split("\n")
    for (line <- lines) {
      build.append(writeIndent(line, indent))
      build.append("\n")
    }
    return build.toString
  }

  def indentCode(writer:CodeWriter, item:BaseCodeSegment):String = {
    val value = writer.createSimpleCode(item)
    val build:StringBuilder = new StringBuilder();
    val lines = value.split("\n")
    for (line <- lines) {
      build.append(writeIndent(line, 1))
      build.append("\n")
    }
    return build.toString
  }

  def repeatAfterFirst(list:Buffer[BaseCodeSegment],prefix:String, writer:CodeWriter) : String = {
    val builder = new StringBuilder();
    var first = true;

    for (item <- list) {
      if (first) {
        builder.append(writer.createSimpleCode(item))
        first = false
      }
      else {
        builder.append(prefix)
        builder.append(writer.createSimpleCode(item))
      }
    }
    return builder.toString
  }

  def surroundQuotes(in1:String) = {
    val builder = new StringBuilder()
    builder.append("\"")
    builder.append(in1)
    builder.append("\"")
    builder.toString
  }

  def surroundTag(in:String,tag:String) = {
	  val builder = new StringBuilder()
	  builder.append("<")
	  builder.append(tag)
	  builder.append(">")
	  builder.append(in)
	  builder.append("</")
	  builder.append(tag)
	  builder.append(">")
  }
  
  def createLineSpace(in:List[String],space:Int):String = {
    val builder = new StringBuilder()
    var pos = 0
    for (value <- in) {
      while (builder.length < pos) {
        builder.append(' ')
      }
      builder.append(value)
      pos += space
    }
    builder.append("\n")
    builder.toString
  }
  
  

}
