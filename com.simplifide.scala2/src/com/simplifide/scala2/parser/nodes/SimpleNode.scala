/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser.nodes

// TODO Need to enclose the classes inside the main object SimpleNode

/**Set of Nodes which contain basic types like Integer, Boolean, ...
 */
class SimpleNode(val value:String, val description:Option[String]) extends BaseNode{
  override def containsDescription:Boolean = return true
  override def getDescription:String = {
    if (description != None) {
      return "<key>" + description.get + "</key>" + " : " + value
    }
    return ""
  }
  override def getLength:Int = {return value.length}
 
}

object SimpleNode {
    case class FloatTop(override val value:String,
                   override val description:Option[String]) extends SimpleNode(value,description) {
             def getFloatValue:scala.Float = value.toFloat;
    }

  case class StringNode(override val value:String, override val description:Option[String]) extends SimpleNode(value,description) {
    override def getLength:Int = return value.length + 2
  }

}

case class IntNode(override val value:String, override val description:Option[String]) extends SimpleNode(value,description) {
  def getNumber:Int = return value.toInt
}

class BooleanNode(override val value:String,override val description:Option[String]) extends SimpleNode(value,description) {
  def isTrue:Boolean = {
    if (value == "true") return true;
    return false;
  }
}
case class IdentNode(override val value:String,override val description:Option[String]) extends SimpleNode(value,description)

class ChoiceNode(val choices:List[String],override val value:String, override val description:Option[String]) extends SimpleNode(value,description) {
  override def getDescription:String = {
    val builder = new StringBuilder()
    if (description != None) {
    	builder.append("<key>")
    	builder.append(description.get)
    	builder.append(" : ")
    	builder.append("</key>")
    }
    	
    builder.append(value)
    builder.append(" : ")
    var first = true;
    for (choice <- choices) {
      if (first) first = false;
      else builder.append(" | ")
      builder.append("<type>")
      builder.append(choice)
      builder.append("</type>")
    }

    return builder.toString
  }
}