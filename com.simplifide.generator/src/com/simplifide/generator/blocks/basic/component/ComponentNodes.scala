/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.component

import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.top.ParserContext

object ComponentNodes {

 class ComponentsNode(val components:List[ComponentTopNode]) extends RootNode {
    val command:Command = ComponentCommands
    override def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
      new ComponentsSeg(context,name,components.map(x => x.createComponent))
    }
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      components.foreach(x => x.createContext(context, offset))
    }
  }

 abstract class ComponentTopNode extends CommandNode {
   val command:Command = ComponentCommands.Component
   def createComponent:InternalSeg;
 }

 class UsedNode extends ComponentTopNode {
 def createComponent:InternalSeg = {
      return new UsedSeg()
    }
  }

  class ComponentNode(val entityNode:IdentNode, val libraryNode:Option[IdentNode]) extends ComponentTopNode {
    def createComponent:InternalSeg = {
      libraryNode match {
        case Some(x) => new ComponentSeg(entityNode.value,Some(x.value))
        case None    => new ComponentSeg(entityNode.value,None)
      }
      return new ComponentSeg(entityNode.value,libraryNode.map(x => x.value))
    }
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      BaseNode.createOptionContext(context, offset, libraryNode)
      entityNode.createContext(context, offset)
    }

  }


}
