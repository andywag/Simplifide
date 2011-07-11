package com.simplifide.generator.general

import com.simplifide.scala2.parser.BaseParser
import com.simplifide.scala2.parser.nodes.{BaseNode, SimpleNode}
import com.simplifide.scala2.command.{Command, CommandParameter, IntParameter}
import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 1/28/11
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */

trait NameLocationParser extends BaseParser {
    lexical.delimiters ++= List()
    lexical.reserved   ++= List("name","location")

    def name             = "name"            ~ stringDef ^^ {case "name"     ~ num => NameLocationParser.NameNode(num)}
    def location         = "location"        ~ stringDef ^^ {case "location" ~ num => NameLocationParser.LocationNode(num)}

    def name_location = name ~ location ^^ {case nam ~ loc => NameLocationParser.TopNode(nam,loc)}
}

object NameLocationParser {

  case class TopNode(nam:NameNode,loc:LocationNode) extends BaseNode {
    def getName:String     = nam.getName
    def getLocation:String = loc.getLocation

  }

  case class NameNode(node:SimpleNode.StringNode)  extends BaseNode {
    def getName:String = node.value
  }
  case class LocationNode(node:SimpleNode.StringNode)  extends BaseNode {
    def getLocation:String = node.value
  }

  /** Name of the Top Level Module */
  object NameCommand extends Command("name") {
    override val command:String = {
      StringOps.createLineSpace(List("name","\"${name}\""), 20)
    }
    override val description:String = "Name of the Top Level Module";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("name" -> new IntParameter("Name of the Module"))
    }
  }
  /** Location of the top level module */
  object LocationCommand extends Command("location") {
    override val command:String = {
      StringOps.createLineSpace(List("location","\"${location}\""), 20)
    }
    override val description:String = "Location of the Modules in this block";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("name" -> new IntParameter("Name of the Module"))
    }
  }

}