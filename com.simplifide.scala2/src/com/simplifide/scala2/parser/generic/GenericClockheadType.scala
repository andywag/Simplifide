package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.command.{Command, StringParameter, CommandParameter}
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.parser.nodes.{SimpleNode,IdentNode}
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parameters.{CoreParameter,CoreParameterHolder}
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.top.ScalaContext
import com.simplifide.scala2.core.basic.Clocks

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */



case class GenericClockheadType() extends GenericType(GenericClockheadType.CLOCKHEAD,GenericClockheadType.DESCRIPTION) {
     /** Return the command String associated with this type */
    val commandString:String  = StringOps.createLineSpace(List(GenericClockheadType.CLOCKHEAD,"${clk}"), StringOps.INDENT)
    /** Returns a HashMap containing the command parameters */
    val commandMap:HashMap[String,CommandParameter] = {
      val cmap = new HashMap[String,CommandParameter]()
      cmap.put("clk",new StringParameter(description))
      cmap
    }
    /** List of Commands which compose this type */
    val commands:List[Command] = List(new GenericCommand(this))
    /** Keywords associated with this command */
    val keywords:List[String] = List(name)
    /** Return a parser associated with this type */
    //def createParser(name:String, description:String):BaseGenericParser = new BasicStringParser(name,description)
  }

object GenericClockheadType {

  val CLOCKHEAD   = "clock_head"
  val DESCRIPTION = "Clock definition for this block"

  trait Parser extends BaseGenericParser {
    def generic_clock_head(typ:GenericType) = CLOCKHEAD ~ identDef(DESCRIPTION) ^^ {case nam ~ id => new Node(typ,id)}
  }

  class Node(typ:GenericType,node:IdentNode) extends GenericNode(typ) {
    def getValue(info:BaseParserInfo):FlopControl = {
      val name = node.value
      ScalaContext.getObjectType[FlopControl](name) match {
            case Some(x) => return x
            case _       =>
      }
      Clocks.defaultFlop("clock","reset","enable")
    }


  }




}