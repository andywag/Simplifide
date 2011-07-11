package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parameters.{CoreParameterHolder,CoreParameter}
import com.simplifide.scala2.command._
import com.simplifide.scala2.parser.nodes.{ChoiceNode, IntNode, SimpleNode, IdentNode}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */



case class GenericChoiceType(override val name:String,
                             override val description:String,
                             val ch:List[String]) extends GenericType(name,description) {
     /** Return the command String associated with this type */
    val commandString:String  = StringOps.createLineSpace(List(name,"${" + name + "}"), StringOps.INDENT)
    /** Returns a HashMap containing the command parameters */
    val commandMap:HashMap[String,CommandParameter] = {
      val cmap = new HashMap[String,CommandParameter]()
      cmap.put(name,new ChoiceParameter(description,ch))
      cmap
    }
    /** List of Commands which compose this type */
    val commands:List[Command] = List(new GenericCommand(this))
    /** Keywords associated with this command */
    val keywords:List[String] = ch ::: List(name)
    /** Return a parser associated with this type */
    //def createParser(name:String, description:String):BaseGenericParser = new BasicStringParser(name,description)
  }

object GenericChoiceType {



  trait Parser extends BaseGenericParser {
    def generic_choice(name:String,desc:String,ch:List[String],typ:GenericType ) = name ~ choiceDef(ch,desc) ^^
      {case nam ~ id => new Node(typ,id)}
  }

  class Node(typ:GenericType,node:ChoiceNode) extends GenericNode(typ) {
     def getValue(info:BaseParserInfo):String = {
        return node.value
      }
  }




}