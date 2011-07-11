package com.simplifide.scala2.parameters

import com.simplifide.scala2.expression.FixedSegment
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.parser.nodes.{SimpleNode, BaseNode,IdentNode,RootNode, IntNode}
import com.simplifide.scala2.command.{StringParameter, Command, CommandParameter, IntParameter,CommandSection, CommandCodeSegment}
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.parser.{BaseParserInfo, BaseParser}
import collection.mutable.{ListBuffer, HashMap}
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.context.CurrentContext


/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 1/31/11
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */

class ParameterSegment {}

object ParameterSegment  {
  val TITLE = "parameters"
  val KEYWORDS = List(TITLE)
  // Parser
  object Parser extends BaseParser with FixedSegment.FixedParser {
	  val commandSection = Commands
      val commandString:String = TITLE
	  
      def parameter = identDef ~ (identDef | stringDef | numberDef | fixed)     ^^
        {case nam ~ value => new Nodes.Parameter(nam,value)}

      def parameters = rep(parameter) ^^ {case lis => new Nodes.Parameters(lis)}
      
      def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(parameters)(tokens)
    }
  }
  // Nodes
  object Nodes {
    class Parameters(params:List[Parameter]) extends RootNode {
      val command:Command = Commands
    	def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
        val coreParams = params.map(x => x.getParam(info))
        coreParams.foreach(x => CoreParameterHolder.parameters.put(x.name,x))
        return Segment
      }

    }

    class Parameter(nam:IdentNode,value:BaseNode) extends BaseNode {
      def getParam(info:BaseParserInfo):CoreParameter = {
         value match {
          case IdentNode(v,_)             => return new CoreParameter.Ident(nam.value,value.asInstanceOf[IdentNode].value)
          case SimpleNode.StringNode(v,_)            => return new CoreParameter.Strin(nam.value,value.asInstanceOf[SimpleNode.StringNode].value)
          case IntNode(v,_)               => return new CoreParameter.Integer(nam.value,value.asInstanceOf[IntNode].getNumber)
          case FixedSegment.Nodes.FixedParam(v) => {
        	  // This shouldn't occur
        	  return new CoreParameter.Error(nam.value)
          }
          case FixedSegment.Nodes.FixedDef(_,_,_) => {
        	  val nfix:FixedType = value.asInstanceOf[FixedSegment.Nodes.FixedDef].getFixedType
        	  return new CoreParameter.Fixed(nam.value,nfix)
          }
         }
      }

    }
  }
  // Segment
  object Segment extends CommandCodeSegment(TITLE){
    override val command:Command = Commands
    override val commands:ListBuffer[Command] = new ListBuffer[Command]();
    override val matchingC:Boolean = false

     override def createCode(writer:CodeWriter):SegmentReturn = {
        return SegmentReturn.segment("")

     }

  }

  // Command
  object Commands extends CommandSection(TITLE) {
      override val description:String = "Parameter Declaration"
     /** List of Commands internal to this command */
     override val commands:List[Command] = List()
     /** Keywords associated with this command */
     override val keywords:List[String] = KEYWORDS
     /** Parser associate with this command */
     override def getParser():BaseParser = Parser

     override val commandMap = HashMap[String,CommandParameter]()

     override val command = TITLE
  }
}