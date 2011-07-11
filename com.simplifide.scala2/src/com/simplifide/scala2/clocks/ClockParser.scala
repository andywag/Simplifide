/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.clocks


import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._

class ClockParser extends BaseParser{
    lexical.reserved   ++= List("clock","reset","enable","signal","index","posedge","negedge","active_high","active_low","sync","async")

    val commandSection = ClockCommands
    def commandString():String = "clock_head"

    def signal_clock = addPosition("clock" ~ stringDef("Clock Name") ~ choiceDef(List("posedge","negedge"),"Edge of the Clock") ^^
      { case "clock" ~ name ~ edge => new ClockNode(name,edge)})

    def signal_reset =  addPosition("reset" ~ stringDef("Reset Name") ~ choiceDef(List("sync","async"),"Type of reset") ~ choiceDef(List("active_high","active_low"),"Level which the reset is active") ^^
      { case "reset" ~ name ~ sync ~ active => new ResetNode(name,sync,active)})

    def signal_enable = addPosition("enable" ~ stringDef("Enable Name") ^^
     { case "enable" ~ name   => new EnableNode(name)})

    def signal_index = addPosition("index" ~ stringDef("Index Name") ~ numberDef("Period") ^^
     { case "index" ~ name ~ index   => new ClockNodes.Index(name,index)})

    def signal_signal =  addPosition("signal" ~ stringDef("Signal Name") ^^
     { case "signal" ~ name   => new EnableNode(name)})

    def clock_header = addPosition( signal_clock ~ opt(signal_reset) ~ opt(signal_enable) ~ opt(signal_index) ^^
      {case  clk ~ res ~ ena ~ ind => new ClockHeadNode(clk,res,ena,ind)})



     
   


       def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(clock_header)(tokens)
    }
    
}

