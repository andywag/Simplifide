/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top.parser

import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser.nodes.{BaseNode, SimpleNode, IdentNode}
import com.simplifide.scala2.parser.ParserReturnValue
import com.simplifide.scala2.top.{ParserContext, InterfaceError,ScalaContext}
import com.simplifide.scala2.parameters.{CoreParameter, CoreParameterHolder}

/**Class which contains the nodes related to parsing the top level input stream. This consists of
 *
 * Items which define a list of commands for the input code
 * Item which defines an individual section of the code
 */
class TotalParserNodes {}


object TotalParserNodes {

  /**Node which contains the information associated with a section of the input stream
   * @param keyword The node where the name of this command is stored
   * @param name The name of this node
   * @param segment The text inside this command
   */
class Item(val keyword:IdentNode,val name:IdentNode,val segment:SimpleNode.StringNode) extends BaseNode {

  def parse(context:CurrentContext):ParserReturnValue = {
    TotalParserTop.getParser(keyword.value) match {
      case Some(x) => { // Sub-parser existing case
          val value:ParserReturnValue = x.createSegment(context,segment.value, name.value,name.position.line-1)
          if (value.isError) { // With Error Condition offset the error and return the value
            val err:InterfaceError = value.getError.get
            //val nerr:InterfaceError = err.newErrorwithOffset(name.position.line-1)
            return new ParserReturnValue.Error(err)
          } 
          else {

            ScalaContext.objects.put(name.value, value.getSegment.get)
            //CoreParameterHolder.parameters.put(name.value, new CoreParameter.Object(name.value,value.getSegment.get))
          }
          return value
      }
      case None => { // No existing parser found
          return new ParserReturnValue.Error(new InterfaceError.Error(name.position.line-1,"Command \"" + keyword.value + "\" Not Found\n"))
      }
    }

  }
  override def createContext(context:ParserContext, offset:Int)  {
    super.createContext(context, offset)
    if (segment.containsPosition(offset)) {
       val parser = TotalParserTop.getParser(keyword.value)
      parser match {
        case Some(x) => {
            context.commandSection_=(x.commandSection)
            val off = offset - segment.getPosition.start
            x.createContext(segment.value, off, context)
        }
        case _ =>
      }
    }
  }
  override def toString = "ParserNode" + this.getPosition + keyword.value
}


/**Node which contains a list of commands used to parse the input stream from the rtl.*/

class Items(val commands:List[Item]) extends BaseNode {

  /** Parses the data inside this node */
  def parse(context:CurrentContext):List[ParserReturnValue] = {
    commands.map(x => x.parse(context))
  }

  override def createContext(context:ParserContext, offset:Int)  {
    super.createContext(context, offset)
      for (command <- commands) {
        if ((offset > command.getPosition.start) && (offset <= command.getPosition.end)) {
          command.createContext(context, offset)
        }
    }
  }
  override def toString = "ParserListNode" + this.getPosition + "\n   " + commands

}
}
