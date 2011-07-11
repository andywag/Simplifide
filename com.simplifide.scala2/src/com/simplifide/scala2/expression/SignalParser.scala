/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression

import com.simplifide.scala2.expression.nodes.SignalNodes
import com.simplifide.scala2.parser.BaseParser

trait SignalParser extends BaseParser with FixedSegment.FixedParser{
  def vector_reg = addPosition("[" ~ numberDef ~"]" ^^ {case "[" ~ num ~"]" => new SignalNodes.VectorNode(num)})
  def vector     = addPosition("(" ~ numberDef ~")" ^^ {case "(" ~ num ~")" => new SignalNodes.VectorNode(num)})
   
  def assign_func  = addPosition(stringDef ^^ {case st => new SignalNodes.StringAssign(st)})
  def assign_float = addPosition(floatDef  ^^ {case fl => new SignalNodes.FloatAssign(fl)})
  def assign = addPosition("=" ~ (assign_func | assign_float)  ^^ 
        {case "=" ~ an => new SignalNodes.SignalAssign(an)})
  
  def sig_assign = identDef ~ rep(vector) ~ opt(vector_reg) ~ opt(assign) ^^ {case id ~ vec ~ reg ~ ass => 
      new SignalNodes.SingleSignalNode(id,vec,reg,ass)}
  
  def complex = "complex" ^^ {case "complex" => new SignalNodes.ComplexNode()}
  
  def signal =  choiceDef(SignalNodes.signalTypes) ~ opt(complex) ~ fixed ~ repsep(sig_assign,",")  ~ ";"^^
   {case ch ~ com ~ fix ~ sigs ~ ";" => new SignalNodes.SignalNode(ch,com,fix,sigs)}

   

  def round_fixed = addPosition(choiceDef(SignalNodes.roundOptions) ~ fixed ^^ 
      {case ch ~ fix => new SignalNodes.RoundOption(ch,fix)})
   
  def func = addPosition(identDef ^^ {case id => new SignalNodes.FunctionNode(id)})
}
