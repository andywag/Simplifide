package com.simplifide.scala2.parser.generic

import collection.immutable.HashMap
import com.simplifide.scala2.parser.nodes.{BaseNode, RootNode}
import collection.mutable.{ListBuffer, LinkedHashMap}
import util.parsing.input.Reader
import com.simplifide.scala2.parser.{BaseParser}
import com.simplifide.scala2.command.CommandSection

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/22/11
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class which contains a parser based on a list of defined types. This allows
 * a simple generation of parsers for a give set of parameters
 */
class GenericParser(val command:GenericCommandSection,
                    val types:LinkedHashMap[String,GenericType]) extends BaseGenericParser
                                                                 with GenericChoiceType.Parser
                                                                 with GenericClockheadType.Parser
                                                                 with GenericFixedListType.Parser
                                                                 with GenericFixedType.Parser
                                                                 with GenericIdentType.Parser
                                                                 with GenericIntType.Parser
                                                                 with GenericSignalType.Parser
                                                                 with GenericStringType.Parser
                                                                 with GenericFloatType.Parser
                                                                 with GenericFloatListType.Parser
                                                                 with GenericObjectType.Parser {

 val commandSection:CommandSection = command;
 def commandString():String = command.name

  /** Append the keywords to the generic parser when it is created. */
  def initialize {
    types.values.foreach(x => lexical.reserved ++= x.keywords)
  }

  /** Factory Method used when parsing the data */
  def applyParse(name:String,typ:GenericType,in:Input):ParseResult[GenericNode] = {
    typ match {
      case GenericChoiceType(na,de,ch)  => return generic_choice(name,de,ch,typ)(in)
      case GenericClockheadType()       => return generic_clock_head(typ)(in)
      case GenericFixedListType(na,de)  => return generic_fixed_list(name,de,typ)(in)
      case GenericFixedType(na,de)      => return generic_fixed(name,de,typ)(in)
      case GenericIdentType(na,de)      => return generic_ident(name,de,typ)(in)
      case GenericIntType(na,de)        => return generic_int(name,de,typ)(in)
      case GenericSignalType(na,de)     => return generic_signal(name,de,typ)(in)
      case GenericStringType(na,de)     => return generic_string(name,de,typ)(in)
      case GenericFloatType(na,de)      => return generic_float(name,de,typ)(in)
      case GenericFloatListType(na,de)  => return generic_float_list(name,de,typ)(in)
      case GenericObjectType(na,de)     => return generic_object(name,de,typ)(in)
    }
  }

  /* Parses the input by running a parser over each of the individual parsers */

  def parse_input(in:Input):ParseResult[GenericNode.Root] = {

    var internal = in   // Internal
    var results = new LinkedHashMap[String,GenericNode]() // Ordered Map of Output Results
    // Parse each set of the input with the parsers from the list
    for ((key,value) <- types) {
      // Parse the Input Data
      val result:ParseResult[GenericNode] = applyParse(key,value,internal)
      result match {
        case s @ Success(out, in1) => {
          internal = in1
          results.put(key,out)
        }
        case _ => return lastNoSuccess
      }
    }
    return new Success(new GenericNode.Root(command,results),null)


  }


  /** Parse the Input Data */

  override def parse(s:String):ParseResult[RootNode] = {
      parse_input(new lexical.Scanner(s))
  }

}

object GenericParser {

}