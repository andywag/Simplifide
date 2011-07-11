package com.simplifide.scala2.parser.generic

import com.simplifide.scala2.parser.BaseParser
import collection.mutable.LinkedHashMap

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */

/** Basic Parser Class used */
abstract class BaseGenericParser extends BaseParser {

//  var command:GenericCommandSection
//  var types:LinkedHashMap[String,GenericType]
//  /** Parse the input stream */
//  def parseRule:Parser[GenericNode]
//
//  /** Parse the input based on a string */
//  def genericParse(str:String):ParseResult[GenericNode] = {
//    val tokens = tokenize(str)
//    phrase(parseRule)(tokens)
//  }
//
//  def newParse(baseParser:BaseGenericParser, str:String):ParseResult[GenericNode] = {
//      val result = baseParser.genericParse(str)
//      return result
//  }
//
//  /*def resultMatch(str:String) = {
//    val result = genericParse(str)
//    result match {
//      case Success(out, in1) => {
//            internal = in1
//            results.put(key,out)
//      }
//      case _ => return lastNoSuccess
//    }
//  }*/
//
//  def parse_input(in:Input, map:LinkedHashMap[String,GenericNode]):ParseResult[GenericNode.Root] = {
//      var internal = in   // Internal
//      var results = new LinkedHashMap[String,GenericNode]() // Ordered Map of Output Results
//      // Parse each set of the input with the parsers from the list
//      for ((key,value) <- types) {
//        // Parse the Input Data
//        val baseParser:BaseGenericParser = value.createParser(key,"")
//        val result = newParse(baseParser,internal.toString)
//        result match {
//          case Success(out, in1) => {
//            internal = in1
//            results.put(key,out)
//          }
//          case _ => return lastNoSuccess
//        }
//      }
//      return new Success(new GenericNode.Root(command,results),null)
//
//    }
//
//


}