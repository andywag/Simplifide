/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression

import com.simplifide.scala2.parser._
import com.simplifide.scala2.clocks.ClockNodes
import com.simplifide.scala2.expression.nodes._
import com.simplifide.scala2.expression.FixedSegment.FixedParser

object ExpressionParser extends BaseParser with FixedParser with SignalParser{

  lexical.reserved   ++= List("signal","signalr","constant","input","output","fixed","complex",
		  			"R","U","T","RC","UC","TC","s","u","c","clock_head")

  val commandSection = ExpressionCommands
  val commandString:String = "dsp"

   def clk_head_statement    = addPosition("clock_head" ~ identDef("ClockHead") ~ ";" ^^
     {case "clock_head" ~ ident ~ ";"  => new ClockNodes.Head(ident)})
   
  /*
  def fixed_opt_sign = choiceDef(List("s","u","c")) ~ "," ^^ {case ch ~ "," => ch}
  
  def fixed_def = opt(fixed_opt_sign) ~ anyDef ~ "," ~ anyDef ^^ 
    {case ch ~ wid ~ "," ~ frac => SignalNodes.FixedDef(ch,wid,frac)}
  
  def fixed_param = identDef ^^ {case id => SignalNodes.FixedParam(id)} 
  
  def fixed  = "<" ~ (fixed_def|fixed_param) ~ ">" ^^ 
    {case "<" ~ fix ~ ">" => fix}
  */ 
  
  /*
  def vector_reg = addPosition("[" ~ numberDef ~"]" ^^ {case "[" ~ num ~"]" => SignalNodes.VectorNode(num)})
  def vector     = addPosition("(" ~ numberDef ~")" ^^ {case "(" ~ num ~")" => SignalNodes.VectorNode(num)})
   
  def assign_func  = addPosition(stringDef ^^ {case st => SignalNodes.StringAssign(st)})
  def assign_float = addPosition(floatDef  ^^ {case fl => SignalNodes.FloatAssign(fl)})
  def assign = addPosition("=" ~ (assign_func | assign_float)  ^^ 
        {case "=" ~ an => SignalNodes.SignalAssign(an)})
  
  def sig_assign = identDef ~ rep(vector) ~ opt(vector_reg) ~ opt(assign) ^^ {case id ~ vec ~ reg ~ ass => 
      SignalNodes.SingleSignalNode(id,vec,reg,ass)} 
  
  def complex = "complex" ^^ {case "complex" => SignalNodes.ComplexNode()}
  
  def signal =  choiceDef(SignalNodes.signalTypes) ~ opt(complex) ~ fixed ~ repsep(sig_assign,",")  ~ ";"^^
   {case ch ~ com ~ fix ~ sigs ~ ";" => SignalNodes.SignalNode(ch,com,fix,sigs)}

   

  def round_fixed = addPosition(choiceDef(SignalNodes.roundOptions) ~ fixed ^^ 
      {case ch ~ fix => SignalNodes.RoundOption(ch,fix)})
   
  def func = addPosition(identDef ^^ {case id => SignalNodes.FunctionNode(id)})
  */
  
  def delta = addPosition( ("-"|"+")  ~ numberDef ^^ 
        {
          case "-" ~ index => new ExpressionNodes.Delta(index,false)
          case "+" ~ index => new ExpressionNodes.Delta(index,true)
        })
  
  def inte    = addPosition(numberDef ^^ {case flo => new ExpressionNodes.Integer(flo)})

  def const    = addPosition(floatDef ^^ {case flo => new ExpressionNodes.Float(flo)})
        
  
   def func_body:Parser[ExpressionNodes.FuncBody] = addPosition("(" ~ rep1sep(ques,",") ~ ")" ^^ 
		  {case "(" ~ exp ~ ")" => new ExpressionNodes.FuncBody(exp)})
  
   def index =  addPosition("[" ~ opt(numberDef) ~ identDef ~ opt(delta) ~ "]" ^^
         {case "[" ~ num ~ id ~ del ~ "]" => new ExpressionNodes.Index(num,id,del)})
         
   
         
   def variable = addPosition(identDef  ~ opt(func_body) ~ opt(index) ^^ 
      {case id ~ fu ~ ind => new ExpressionNodes.Variable(id,ind,fu)})

   def par:Parser[ExpressionNodes.ExpressionNode]     = addPosition(opt(func | round_fixed) ~ "(" ~ rep1sep(ques,",") ~ ")" ^^ 
         {case rnd ~ "(" ~ expr1 ~ ")" => new ExpressionNodes.Par(expr1,rnd)})
   
   def factor  = addPosition((variable | par | const | inte) ^^ {case vari => new ExpressionNodes.FactorNode(vari) })
   def unary   = addPosition( opt("-") ~ factor ^^ {case n ~ fact => new ExpressionNodes.UnaryNode(n,fact)})
   def mult    = addPosition("*" ~ unary ^^ {case "*" ~ factor1 => new ExpressionNodes.Mult(factor1)})
   def div     = addPosition("/" ~ unary ^^ {case "*" ~ factor1 => new ExpressionNodes.Div(factor1)})
   def term    = addPosition(unary ~ opt(mult | div) ^^ {case factor1 ~ item => new ExpressionNodes.Term(factor1,item)})
   def add     = addPosition("+" ~ term ^^ {case "+" ~ term => new ExpressionNodes.Add(term)})
   def sub     = addPosition("-" ~ term ^^ {case "-" ~ term => new ExpressionNodes.Sub(term)})
   
   def expr    = addPosition(term ~ rep(add | sub)  ^^ {case term ~ add => new ExpressionNodes.Expr(term,add)})
   def quop    = addPosition("?" ~ expr ~ ":" ~expr ^^ {case "?" ~ tr ~ ":" ~fa => new ExpressionNodes.Question(tr,fa)})
   def ques:Parser[ExpressionNodes.ExprTop]    = addPosition(expr ~ opt(quop) ^^ { case ex ~ q => new ExpressionNodes.ExprTop(ex,q)})
  
   def assignbody = addPositionText("=" ~ ques  ^^ {case "=" ~ exp  => new ExpressionNodes.AssignBody(exp)})
   def assignment = addPositionText(variable ~ opt(assignbody) ~ ";" ^^ {case vari ~ exp ~ ";" => new ExpressionNodes.Assignment(vari,exp)})
  
   def segments = addPosition(opt(clk_head_statement) ~ rep(signal | assignment) ^^ 
        {case clk ~ sig  => new ExpressionNodes.Segments(clk,sig)})


   /** Standard Parsing Function */
   def parse(s:String):ParseResult[ExpressionNodes.Segments] = {
        val tokens = new lexical.Scanner(s)
        phrase(segments)(tokens)
    }

    def test(s:String) {
      parse(s) match {
        case Success(tree, _) => tree.createSegment(null,"aa",new BaseParserInfo(0))
        case e: NoSuccess => println(e.toString)
      }
    }

}
