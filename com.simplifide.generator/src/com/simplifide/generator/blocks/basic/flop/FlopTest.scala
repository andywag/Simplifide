/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.flop

import com.simplifide.scala2.top.InterfaceTop

object FlopTest {
     def test(exprstr: String) = {
      val ver = InterfaceTop.createVerilog(null,exprstr)
      val vhdl = InterfaceTop.createVhdl(null,exprstr)
    }

   //def main(args: Array[String]) = test("state alpha(0);")
    def main(args: Array[String]) = {
      println("Hello World")
      val lines = scala.io.Source.fromFile("C:/simplifide_base/eclipse/com.simplifide.generator/src/com/simplifide/scala2/blocks/basic/flop/flop.txt").mkString
     test(lines)

    }
}
