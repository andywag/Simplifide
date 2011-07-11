package com.simplifide.scala2.parser.generic

import util.parsing.combinator.Parsers
import com.simplifide.scala2.parser.nodes.{RootNode, CommandNode, BaseNode}
import com.simplifide.scala2.parser.{BaseParserInfo, BaseParser}
import com.simplifide.scala2.context.CurrentContext
import collection.mutable.{HashMap, LinkedHashMap}
import com.simplifide.scala2.command.{CommandParameter, CommandCodeSegment, Command}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/22/11
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */


/**A class whose purpose is to create a general structure of parser which can be combined into larger parsers
 * which are simply created using list of smaller parts
 */
abstract class GenericType(val name:String, val description:String)   {

  /** Return the command String associated with this type */
  val commandString:String
  /** Returns a HashMap containing the command parameters */
  val commandMap:HashMap[String,CommandParameter]
  /** List of Commands which compose this type */
  val commands:List[Command]
  /** Keywords associated with this command */
  val keywords:List[String]
  /** Return a parser associated with this type */
  //def createParser(name:String,desc:String):BaseGenericParser

}

object GenericType {



  /** Root Node which is returned from this parser and used to create the segment */
  abstract class Root(val results:LinkedHashMap[String,GenericNode]) extends RootNode {
    def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment  = {
      // Map the Results to a set of Values
      val newMap = new LinkedHashMap[String,Any]()
      for ((key,value) <- results) {
        newMap.put(key,value.getValue(info))
      }
      return null
    }
  }

  abstract class Segment(val name:String, val resultMap:LinkedHashMap[String,Any]) extends CommandCodeSegment(name) {

  }

  abstract class TypeCommand(override val name:String) extends Command(name) {

  }

}
