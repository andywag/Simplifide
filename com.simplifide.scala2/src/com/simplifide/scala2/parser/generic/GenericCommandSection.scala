package com.simplifide.scala2.parser.generic

import collection.mutable.LinkedHashMap
import com.simplifide.scala2.parser.BaseParser
import collection.mutable.HashMap
import com.simplifide.scala2.command.{Command, CommandParameter, CommandSection}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class which defines a generic command section. This describes the set of parsers and commands associated with
 * this structure
 */
class GenericCommandSection(override val name:String,
                            val doc:String,
                            val typeMap:LinkedHashMap[String,GenericType],
                            val creator:GenericCommandSegment.Creator) extends CommandSection(name) {

  // Command Class Overrides

  /** Creates the composite command by combining the subcommands into a single block */
  override val command:String = {
    val builder = new StringBuilder
    val commands:List[Command] = typeMap.values.flatMap(x => x.commands).toList
    combineCommands(name,commands)
  }
  /** Returns the composite command map by combining the subcommand s into a single map */
  override val commandMap:HashMap[String,CommandParameter] = {
    val newMap = new HashMap[String,CommandParameter]()
    for ((key,value) <- typeMap) {
      for ((key1,value1) <- value.commandMap) {
        newMap.put(key1,value1)
      }
    }
    newMap
  }
  override val description = doc

  // End Command Class Overrides

  // Command Section Overrides

    /** Create a generic parser associated with this type map */
  override def getParser():BaseParser = {
    val parser = new GenericParser(this,typeMap)
    parser.initialize
    return parser
  }
  override val commands:List[Command] = typeMap.values.flatMap(x => x.commands).toList
  override val keywords:List[String]  = typeMap.values.flatMap(x => x.keywords).toList

  // End Command Section Overrides

}