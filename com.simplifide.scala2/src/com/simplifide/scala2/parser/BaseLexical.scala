/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser

import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.input.CharArrayReader.EofCh

class BaseLexical extends StdLexical with BaseTokens{

  override def token: Parser[Token] =
    ( curly
    | tick
    | identChar ~ rep( identChar | digit )              ^^ { case first ~ rest => processIdent(first :: rest mkString "") }
    | float
    | digit ~ rep( digit )                              ^^ { case first ~ rest => NumericLit(first :: rest mkString "") }
    | '\'' ~ rep( chrExcept('\'', '\n', EofCh) ) ~ '\'' ^^ { case '\'' ~ chars ~ '\'' => StringLit(chars mkString "") }
    | '\"' ~ rep( chrExcept('\"', '\n', EofCh) ) ~ '\"' ^^ { case '\"' ~ chars ~ '\"' => StringLit(chars mkString "") }
    | EofCh                                             ^^^ EOF
    | '\'' ~> failure("unclosed string literal")
    | '\"' ~> failure("unclosed string literal")
    | delim
    | failure("illegal character")
    )
  

  //def num:Parser[Token]    = digit ~ rep( digit )  ^^ { case first ~ rest => NumericLit(first :: rest mkString "") }
  def float:Parser[Token] = opt('-') ~ digit ~ rep( digit ) ~'.'~ digit ~ rep( digit ) ^^ 
  { case n ~ a ~ b ~ '.' ~ c ~ d => n match  {
	  case None     => new FloatToken( (a :: b.mkString("") :: '.' :: c :: d).mkString(""))
	  case Some(x) => new FloatToken( (x :: a :: b.mkString("") :: '.' :: c :: d).mkString(""))
	  }
  }

  
  def curly:Parser[Token] = '{' ~ rep(chrExcept(EofCh,'}')) ~ '}' ^^ { case '{' ~ chars ~'}' =>  new CurlyToken(chars.mkString("")) }
  def tick:Parser[Token]  = '`' ~ rep(chrExcept(EofCh,'`')) ~ '`' ^^ { case '`' ~ chars ~'`' =>  new TickToken(chars.mkString("")) }
  
  
}
