package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.parser.nodes.SimpleNode
import com.simplifide.scala2.command.{IntParameter, Command, StringParameter, CommandParameter}
import com.simplifide.scala2.expression.FixedSegment
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.parser.BaseParserInfo

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */



case class GenericFixedType(override val name:String, override val description:String) extends GenericType(name,description) {
     /** Return the command String associated with this type */
    val commandString:String  = StringOps.createLineSpace(List(name,"<${width_" + name + "},${frac_" + name + "}>"), StringOps.INDENT)
    /** Returns a HashMap containing the command parameters */
    val commandMap:HashMap[String,CommandParameter] = {
      HashMap("width_" + name -> new IntParameter("Total Width"),
              "frac_" + name  -> new IntParameter("Number of Fractional Bits"))
      }
    /** List of Commands which compose this type */
    val commands:List[Command] = List(new GenericCommand(this))
    /** Keywords associated with this command */
    val keywords:List[String] = List(name)
    /** Return a parser associated with this type */
    //def createParser(name:String, description:String):BaseGenericParser = new BasicStringParser(name,description)
  }

object GenericFixedType {



  trait Parser extends BaseGenericParser with FixedSegment.FixedParser{


    def generic_fixed(name:String,desc:String, typ:GenericType) = name ~ fixed ^^ {case nam ~ fix => new Node(typ,fix)}
  }

  class Node(typ:GenericType,node:FixedSegment.Nodes.Fixed) extends GenericNode(typ) {
    override def getValue(info:BaseParserInfo):FixedType = {
      node.getFixed(info).getFixedBasic.get
    }

  }




}