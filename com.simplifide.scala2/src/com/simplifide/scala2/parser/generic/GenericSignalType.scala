package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.parser.nodes.{SimpleNode,IdentNode}
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parameters.{CoreParameter,CoreParameterHolder}
import com.simplifide.scala2.command.{IntParameter, Command, StringParameter, CommandParameter}
import com.simplifide.scala2.core.basic.vars.{SignalNew,OpType}
import com.simplifide.scala2.expression.FixedSegment

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */



case class GenericSignalType(override val name:String, override val description:String) extends GenericType(name,description) {
     /** Return the command String associated with this type */
    val commandString:String  = StringOps.createLineSpace(List(name,"<${" + name + "_w},${" + name + "_f}>","  ${" + name + "_nam}" ), 20)
    /** Returns a HashMap containing the command parameters */
    val commandMap:HashMap[String,CommandParameter] = {
      HashMap(name + "_w" ->  new IntParameter("Width"),
              name + "_f" ->  new IntParameter("Fraction"),
              name + "_nam" ->  new StringParameter("Name"))
    }
    /** List of Commands which compose this type */
    val commands:List[Command] = List(new GenericCommand(this))
    /** Keywords associated with this command */
    val keywords:List[String] = List(name)
    /** Return a parser associated with this type */
    //def createParser(name:String, description:String):BaseGenericParser = new BasicStringParser(name,description)
  }

object GenericSignalType {



  trait Parser extends BaseGenericParser with FixedSegment.FixedParser{
    def generic_signal(name:String,desc:String, typ:GenericType) = name ~ fixed ~ identDef(desc) ^^
      {case nam ~ fi ~ id=> new Node(typ,id,fi)}
  }

  class Node(typ:GenericType,node:IdentNode,fix:FixedSegment.Nodes.Fixed) extends GenericNode(typ) {
     def getValue(info:BaseParserInfo):SignalNew = {
        val signalName = node.value
        val signalFix  = fix.getFixed(info).getFixedBasic.get
        return SignalNew.newSignal(signalName,OpType.Signal,signalFix)
      }
  }




}