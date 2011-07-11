/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser

import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._
import scala.util.parsing.combinator.token.StdTokens

trait BaseTokens extends StdTokens {
  class CurlyToken(val chars:String) extends Token
  class CurlyNode(val value:String) extends BaseNode

  class TickToken(val chars:String) extends Token
  class TickNode(value1:String, description1:Option[String]) extends SimpleNode(value1,description1)
  
  class FloatToken(val chars:String) extends Token
  case class FloatNode(override val value:String, override val description:Option[String]) extends SimpleNode.FloatTop(value,description)

  
}