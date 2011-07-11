package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.command.{StringParameter, Command, CommandParameter}
import com.simplifide.scala2.parser.nodes.{IdentNode, SimpleNode}
import com.simplifide.scala2.parser.BaseParser

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */

/** Set of basic generic types to be used when creating a parser */
class BasicGenericTypes {

}

//object BasicGenericTypes {
//  val INDENT = 20
//  /** Basic Type Consisting of a String */
//  class StringType(override val name:String, val description:String) extends GenericType(name) {
//     /** Return the command String associated with this type */
//    val commandString:String  = StringOps.createLineSpace(List(name,"\"${name}\""), INDENT)
//    /** Returns a HashMap containing the command parameters */
//    val commandMap:HashMap[String,CommandParameter] = {
//      val cmap = new HashMap[String,CommandParameter]()
//      cmap.put(name,new StringParameter(description))
//      cmap
//    }
//    /** List of Commands which compose this type */
//    val commands:List[Command] = List()
//    /** Keywords associated with this command */
//    val keywords:List[String] = List(name)
//    /** Return a parser associated with this type */
//    def createParser(name:String, description:String):BaseGenericParser = new BasicStringParser(name,description)
//  }
//
//  trait BasicStringParser extends BaseParser {
//    def
//    def parse(input:Input):Parser[GenericNode] = name ~ identDef(description) ^^ {case nam ~ id => new BasicStringNode(id)}
//  }
//
//  class BasicStringNode(id:IdentNode) extends GenericNode {
//     override def getValue:String = id.value
//  }
//
//}