package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.command.{IntParameter, Command, StringParameter, CommandParameter}
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parameters.{CoreParameterHolder,CoreParameter}
import com.simplifide.scala2.parser.nodes.{SimpleNode, IntNode, IdentNode}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */



case class GenericFloatType(override val name:String, override val description:String) extends GenericType(name,description) {
     /** Return the command String associated with this type */
    val commandString:String  = StringOps.createLineSpace(List(name,"${" + name + "}"), StringOps.INDENT)
    /** Returns a HashMap containing the command parameters */
    val commandMap:HashMap[String,CommandParameter] = {
      val cmap = new HashMap[String,CommandParameter]()
      cmap.put(name,new IntParameter(description))
      cmap
    }
    /** List of Commands which compose this type */
    val commands:List[Command] = List(new GenericCommand(this))
    /** Keywords associated with this command */
    val keywords:List[String] = List(name)
    /** Return a parser associated with this type */
    //def createParser(name:String, description:String):BaseGenericParser = new BasicStringParser(name,description)
  }

object GenericFloatType {



  trait Parser extends BaseGenericParser {
    def generic_float(name:String,desc:String, typ:GenericType) = name ~ (floatDef | identDef(desc)) ^^ {case nam ~ id => new Node(typ,id)}
  }

  class Node(typ:GenericType,node:SimpleNode) extends GenericNode(typ) {
     def getValue(info:BaseParserInfo):Float = {
        node match {
          case SimpleNode.FloatTop(_,_)   => return node.asInstanceOf[SimpleNode.FloatTop].getFloatValue
          case IdentNode(v,_) => {
            val par = CoreParameterHolder.parameters.get(v)
            par match {
              case None => return -1
              case Some(x) => if (x.isInstanceOf[CoreParameter.Float]) return x.asInstanceOf[CoreParameter.Float].value
            }
            return 0
          }
        }
      }
  }




}