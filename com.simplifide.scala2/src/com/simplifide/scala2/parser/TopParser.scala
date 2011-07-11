/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser

import nodes._
import scala.util.parsing.combinator.syntactical.StandardTokenParsers



/** Top level parser which all parsers are based on. This includes the total parser
 *  and the base parsers for all of the subprojects
 */
class TopParser extends StandardTokenParsers with BaseTokens{

  override val lexical = new BaseLexical

  /** A parser which matches an identifier */
  
  def float: Parser[String] =
    elem("float", _.isInstanceOf[FloatToken]) ^^ (_.chars)

  def floatDef      = addTokenPosition(float      ^^ {case num => new FloatNode(num,None)})
  
  
  def tick: Parser[String] =
    elem("tick", _.isInstanceOf[TickToken]) ^^ (_.chars)

  def tickDef      = addTokenPosition(tick      ^^ {case num => new TickNode(num,None)})
  /** Create the position for each node */
  def addPosition[T <: BaseNode](p: => Parser[T]): Parser[T] = Parser { in =>
    p(in) match {
      case Success(t, in1) => {
          val start = in.offset
    	  val stop  = in1.rest.offset
          
          t.setNodePosition(new NodePosition(start,stop,in1.pos));
          Success(t,in1)
          }
      case ns: NoSuccess => {
    	  ns
      }
    }
  }
  def addPositionText[T <: BaseNode](p: => Parser[T]): Parser[T] = Parser { in =>
    p(in) match {
      case Success(t, in1) => {
          val start = in.offset
    	  val stop  = in1.rest.offset
          val seq = in.source;
          val str = seq.subSequence(start,stop-1).toString()
          t.setNodePosition(new NodePosition(start,stop,in1.pos));
          t.nodeText = Some(str.trim)
          Success(t,in1)
          }
      case ns: NoSuccess => {
    	  ns
      }
    }
  }
  
  
  
   def addTokenPosition[T <: BaseNode](p: => Parser[T]): Parser[T] = Parser { in =>
    p(in) match {
      case Success(t, in1) => {
    	  val stop  = in.rest.offset
          val tok = in.first
          val start = stop - t.getLength
          t.setNodePosition(new NodePosition(start,stop,in1.pos));
          Success(t,in1)
          }
      case ns: NoSuccess => {
    	  ns
      }
    }
  }


 def choices(lis:List[String]) = {
    def errorMessage(lis:List[String]):String =  {
      var first = true
      val builder = new StringBuilder();
      for (item <- lis) {
        if (first) first = false
        else builder.append(" or ")
        builder.append(item)
      }
      return builder.toString
    }
    def check(el:Elem):Boolean = {
      val str:String = el.toString.replace("'", "").replace("`", "")
      if (lis.contains(str)) return true
      return false
    }
    elem(errorMessage(lis),check) ^^ (_.chars)
    
  }


 def booleanDef(desc:String) = addTokenPosition(choices(List("true","false")) ^^ {case num => new BooleanNode(num,Some(desc))})
 def choiceDef(lis:List[String])     = addTokenPosition(choices(lis) ^^ {case num => new ChoiceNode(lis,num,None)})
 def numberDef     = addTokenPosition(numericLit ^^ {case num => new IntNode(num,None)})
 def stringDef     = addTokenPosition(stringLit  ^^ {case num => new SimpleNode.StringNode(num,None)})
 def identDef      = addTokenPosition(ident      ^^ {case num => new IdentNode(num,None)})

 def choiceDef(lis:List[String],desc:String)     = addTokenPosition(choices(lis) ^^ {case num => new ChoiceNode(lis,num,Some(desc))})
 def numberDef(desc:String)     = addTokenPosition(numericLit ^^ {case num => new IntNode(num,Some(desc))})
 def stringDef(desc:String)     = addTokenPosition( (stringLit | tick)  ^^ {case num => new SimpleNode.StringNode(num,Some(desc))})
 def identDef(desc:String)      = addTokenPosition(ident      ^^ {case num => new IdentNode(num,Some(desc))})

 def anyDef = identDef | numberDef

 def arg = ident | stringLit | numericLit
 def arg_list = "(" ~ repsep(arg,",") ~")"

}
