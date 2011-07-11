package com.simplifide.scala2.top.parser

/*
  * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.top._




/**
 * Parser which handles the top level parsing of commands by splitting the
 * input text into smaller sections with the format
 *
 * title name {
 *   Commands
 * }
 */
class TotalParser {}


object TotalParser extends TopParser {

   /** Methods unique to this parser for handling the text inside curly brackets { text } */
  private def curly: Parser[String] = elem("curly", _.isInstanceOf[CurlyToken]) ^^ (_.chars)
  private def curlyDef      = addTokenPosition(curly      ^^ {case num => SimpleNode.StringNode(num,None)})


  private def command = addPosition(identDef("Keyword") ~ identDef("CommandName") ~ curlyDef ^^
    { case word1 ~ word2 ~ curl  => new TotalParserNodes.Item(word1,word2,curl) })

  private def commands = addPosition(rep1(command) ^^ {case lis => new TotalParserNodes.Items(lis)})

  /** Standard Parsing Function. Returns the results of this parsing operation.
   *  This splits the data into multiple sections of commands which are stored in the nodes
   *  TotalParserNodes.Items
   **/
   def parse(s:String):ParseResult[TotalParserNodes.Items] = {
        val tokens = new lexical.Scanner(s)
        phrase(commands)(tokens)
    }


  /** Parse the commands. This module is called when verilog and vhdl are created. First it splits out the commands
   *  and then calls the parser based on this command
   */
  def parseCommand(context:CurrentContext,com:String):List[ParserReturnValue] = {
    parse(com) match {
        case Success(tree:TotalParserNodes.Items, _) =>
          return tree.parse(context)
        case e: NoSuccess =>  {
            return List(new ParserReturnValue.Error(new InterfaceError.Error(e.next.pos.line,e.toString)))
        }
     }
  }

  /** Returns the parse context associated with this position. 
   *  Called based on hovers, hyperlinks, ...
   */
  def getParserContext(com:String, offset:Int):ParserContext = {
    val context = new ParserContext();
    parse(com) match {
        case Success(tree, _) => tree.createContext(context, offset)
        case NoSuccess(mess,_) =>
      }
      return context
  }


}




