/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression


import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._
import scala.util.parsing.combinator._

object TextExpression {

  def test(expr: String) = {
      ExpressionParser.test(expr)
     
  }

   //def main(args: Array[String]) = test("state alpha(0);")
    def main(args: Array[String]) = {
      println("Hello World")
      val lines = scala.io.Source.fromFile("C:/simplifide_base/eclipse/com.simplifide.scala2/src/com/simplifide/scala2/blocks/signal/expr/expression.txt").mkString
      test(lines)

    }
}
