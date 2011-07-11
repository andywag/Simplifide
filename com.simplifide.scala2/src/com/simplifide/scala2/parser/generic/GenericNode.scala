package com.simplifide.scala2.parser.generic

import com.simplifide.scala2.parser.nodes.{RootNode, CommandNode}
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.command.{Command, CommandCodeSegment}
import collection.mutable.LinkedHashMap

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */

/** Generic Node associated with the Generic Parser */
abstract class GenericNode(typ:GenericType) extends CommandNode {
  override val command:Command = new GenericCommand(typ)
  /** Returns the value associated with this node */
  def getValue(info:BaseParserInfo):Any

}

object GenericNode {

  case class Root(val commandSection:GenericCommandSection,
                  val typeMap:LinkedHashMap[String,GenericNode]) extends RootNode {

    /** Returns the command section associated with this parser */
    val command:Command = commandSection
    def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
      val newMap = new LinkedHashMap[String,Any]()
      for ((key,value) <- typeMap) {
        newMap.put(key,value.getValue(info))
      }
      return new GenericCommandSegment(commandSection.name,name,command,newMap,commandSection.creator)
    }
  }

}