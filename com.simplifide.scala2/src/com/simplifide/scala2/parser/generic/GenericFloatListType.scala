package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.parser.nodes.SimpleNode
import com.simplifide.scala2.expression.FixedSegment
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.command._

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */



case class GenericFloatListType(override val name:String, override val description:String) extends GenericType(name,description) {
     /** Return the command String associated with this type */
    val commandString:String  = StringOps.createLineSpace(List(name,"[${value1_" + name + "}" + ",${value1_" + name + "}" +  "]"), StringOps.INDENT)
    /** Returns a HashMap containing the command parameters */
    val commandMap:HashMap[String,CommandParameter] = {
      HashMap("value1_" + name  -> new FloatParameter("First Value"),
              "value2_" + name  -> new FloatParameter("Second Value"))
      }
    /** List of Commands which compose this type */
    val commands:List[Command] = List(new GenericCommand(this))
    /** Keywords associated with this command */
    val keywords:List[String] = List(name)
    /** Return a parser associated with this type */
    //def createParser(name:String, description:String):BaseGenericParser = new BasicStringParser(name,description)
  }

object GenericFloatListType {



  trait Parser extends BaseGenericParser with FixedSegment.FixedParser{

    def generic_float_list(name:String,desc:String, typ:GenericType) = name ~ "[" ~ repsep(floatDef,",") ~ "]" ^^
      {case nam ~ "["  ~ fix ~ "]" => new Node(typ,fix)}
  }

  class Node(typ:GenericType,node:List[SimpleNode.FloatTop]) extends GenericNode(typ) {
    override def getValue(info:BaseParserInfo):List[Float] = {
      node.map(x => x.getFloatValue)
  }


}
}