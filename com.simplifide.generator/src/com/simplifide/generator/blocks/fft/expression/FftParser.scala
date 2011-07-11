/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft.expression

import com.simplifide.generator.general.GeneralSegment
import com.simplifide.generator.blocks.basic.state.ClockHeadNode
import com.simplifide.scala2.expression.FixedSegment
import com.simplifide.scala2.expression.SignalParser
import com.simplifide.scala2.expression.nodes.SignalNodes
import com.simplifide.scala2.parser.BaseParser
import com.simplifide.scala2.parser.nodes.RootNode


object FftParser extends BaseParser with FixedSegment.FixedParser with SignalParser with GeneralSegment.Parser{

  val NAME_INPUT  = "input"
  val NAME_OUTPUT = "output"

	lexical.reserved   ++= List("clock_head","length","radix","input_fixed","output_fixed","twiddle_fixed","butterfly_fixed","internal_fixed",
                                "name","location","order","dit","dif",NAME_INPUT,NAME_OUTPUT,"fft_type","fft","ifft")

  lexical.delimiters ++= List("(",")",";",",")
  
  val commandSection = FftCommand
  val commandString:String = FftCommand.TITLE
  
  
  def clk    = addPosition("clock_head" ~ identDef("ClockHead")  ^^
     {case "clock_head" ~ ident   => new ClockHeadNode(ident)})
  def name             = "name"            ~ stringDef ^^ {case "name"     ~ num => new FftNodes.Name(num)}
  def location         = "location"        ~ stringDef ^^ {case "location" ~ num => new FftNodes.Location(num)}
  def fft_type         = "fft_type"        ~ choiceDef(List("fft","ifft")) ^^
    {case "fft_type" ~ ch => new FftNodes.FftType(ch)}
  def dit_dif          = "order"           ~ choiceDef(List("dit","dif")) ^^
    {case "order" ~ ch => new FftNodes.Order(ch)}

  //def length           = "length"          ~ numberDef ^^ {case "length" ~ num => FftNodes.Length(num)}
  def in_fixed            = "input_fixed"     ~ fixed ^^ {case "input_fixed"  ~ num => new FftNodes.InFixed(num)}
  def out_fixed           = "output_fixed"    ~ fixed ^^ {case "output_fixed" ~ num => new FftNodes.OutFixed(num)}
  def angleFixed       = "twiddle_fixed"     ~ fixed     ^^ {case "twiddle_fixed" ~ num => new FftNodes.Angle(num)}
  def butterflyFixed   = "butterfly_fixed" ~ "("            ~ repsep(fixed,",") ~ ")" ^^ 
    {case "butterfly_fixed" ~ "(" ~ fix ~ ")" => new FftNodes.Butterfly(fix)}
  def internalFixed    = "internal_fixed"  ~ "("            ~ repsep(fixed,",") ~ ")" ^^ 
    {case "internal_fixed" ~ "(" ~ fix ~ ")" => new FftNodes.Internal(fix)}

  def input            = "input"     ~ stringDef  ^^ {case "input" ~  nam => new FftNodes.Input(nam)}
  def output           = "output"    ~ stringDef  ^^ {case "output"~  nam => new FftNodes.Output(nam)}
  
  def total = clk ~ name ~ location ~ fft_type ~ dit_dif~ int_definition("length") ~ in_fixed ~ out_fixed ~ angleFixed ~ butterflyFixed ~ internalFixed ~  name_definition(NAME_INPUT)  ~ name_definition(NAME_OUTPUT) ^^
    {case cl ~ nam ~ loc ~ typ ~ ord~ len ~ inf ~ outf ~ ang ~ but ~ int ~  inp ~ out => new FftNodes.Total(cl,nam,loc,typ,ord,len,inf,outf,ang,but,int,inp,out)}//,but,int,nam,loc,inp,out)}
  
   def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(total)(tokens)
  }
}

