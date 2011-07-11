package com.simplifide.generator.blocks.basic.state

import scala.util.parsing.combinator.syntactical._



import com.simplifide.scala2.parser._
import com.simplifide.scala2.parser.nodes._

object StateParser extends BaseParser {
	lexical.delimiters ++= List("(",")",";","->",",","{","}",":")
	lexical.reserved   ++= List("state","transition","state_machine","one_hot","case","current_state","next_state","clock_head","true","false")
		

  override def commandString():String = "state_machine"

  val commandSection = StateMachineCommand

  def transition_case_state  = addPosition("case" ~ "(" ~ stringDef("Case Condition") ~ ")" ^^
    { case "case" ~ "(" ~ condition ~ ")"  => new TransitionCaseNode(condition) })

  def state_hot  = "(" ~ numberDef ~ ")" ^^ 
    {case "(" ~ num ~ ")" => new StateHotNode(num)}

  def state_def  (name:String) = identDef(name) ~ opt(state_hot) ^^
    {case id ~ hot => new StateDefNode(id,hot)}

  def transition_state_other = addPosition("->" ~ state_def("Next State") ~  ":" ~ opt(transition_case_state) ~ opt(stringDef("Transition Condition")) ^^
    {case "->" ~ next_state ~ ":" ~  cas ~ condition => new TransitionOtherNode(next_state, condition, cas )})

  def transition_state_first = addPosition(state_def("Starting State") ~ rep(transition_state_other) ^^
    {case current_state ~ next_state => new TransitionFirstNode(current_state,next_state)})

  def transition_statement   = addPosition("transition" ~ rep(transition_state_first) ^^
      {case "transition" ~ states  => new TransitionNode(states)})

  def one_hot_statement     = addPosition("one_hot" ~ booleanDef("EnableOneHot") ^^
    {case "one_hot" ~ ident  => new OneHotNode(ident)})

  def clk_head_statement    = addPosition("clock_head" ~ identDef("ClockHead") ^^
     {case "clock_head" ~ ident  => new ClockHeadNode(ident)})

  def current_state = addPosition("current_state" ~ stringDef("Current State") ^^
     {case "current_state" ~ ident  => new CurrentStateNode(ident)})

  def next_state = addPosition("next_state" ~ stringDef("Next State") ^^
     {case "next_state" ~ ident  => new NextStateNode(ident)})

  def state_machine_expressions =  addPosition(opt(one_hot_statement) ~ opt(clk_head_statement)~opt(current_state)~opt(next_state)~transition_statement ^^
    {case one~clk~current~next~elements => new StateExpressionNode(one,clk,current,next,elements)})

  def state_machine = addPosition( state_machine_expressions ^^
         { case expressions  => new StateMachineNode(expressions) })

    override def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(state_machine)(tokens)
    }


    def apply(s:String):RootNode = {
        parse(s) match {
            case Success(tree, _) => tree
            case e: NoSuccess =>
                   throw new IllegalArgumentException("Bad syntax: "+s)
        }
    }

    

    /*
    //Simplify testing
    def test(exprstr: String) = {
        parse(exprstr) match {
            case Success(tree, _) =>
                println("Tree: "+tree)
                val machine = tree.createSegment("testst")
                println(machine.createVerilog)
                println(machine.createVhdl)
               
            case e: NoSuccess => Console.err.println(e)
        }
    }

   //def main(args: Array[String]) = test("state alpha(0);")
    def main(args: Array[String]) = {
      val lines = scala.io.Source.fromFile("C:/simplifide_base/eclipse/com.simplifide.generator/src/com/simplifide/scala2/parser/basic.txt").mkString
      test(lines)
    }*/
 	
}