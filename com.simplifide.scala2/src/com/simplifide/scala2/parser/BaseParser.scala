package com.simplifide.scala2.parser

import com.simplifide.scala2.top.ParserContext
import scala.util.parsing.combinator.syntactical._
import com.simplifide.scala2.command.CommandSection
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.top.InterfaceError
import util.parsing.input.Reader


/**Main class which handles the parsing operations. The operations are as follows
 *
 *   1. The input string is parsed using parse which returns a ParseResult containing a RootNode
 *   2. The parser result contains a root node which can be used to create a segment using createSegment
 */
abstract class BaseParser extends TopParser{


  override val lexical = new BaseLexical
  lexical.delimiters ++= List("=","+","-","*","/","[","]","(",")",";","<",">",",","?",":",",")

  val commandSection:CommandSection;

 def commandString():String
 /** Function which creates the parser result */
 def parse(in:String):ParseResult[RootNode]
 /** Create the segment */
 def createSegment(context:CurrentContext,command:String, name:String,line:Int):ParserReturnValue = {
       val root = parse(command)
       root match {
            case Success(tree:RootNode, _) =>
                val machine = tree.createSegment(context,name,new BaseParserInfo(line))
                return new ParserReturnValue.Segment(tree,machine)
            case e: NoSuccess =>
              return new ParserReturnValue.Error(new InterfaceError.Error(e.next.pos.line + line,e.toString));
        }
        return null
    }
  /** Create Context */
  def createContext(command:String,offset:Int,context:ParserContext) = {
    val root = parse(command)
    root match {
       case Success(tree, _) => tree.createContext(context,offset)
       case _ =>
    }
  }


  def tokenize(s:String):this.Input = {
      new lexical.Scanner(s)
  }
                                                                    1
 

 def not2(s1:String):Parser[String] = elem("whatever", (!_.equals(s1))) ^^ (_.chars)

 def string_or_ident = stringLit | ident

 

}