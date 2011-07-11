/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.instance

import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.parser.BaseParserInfo
import com.simplifide.scala2.parser.nodes._
import com.simplifide.scala2.top.ParserContext

object InstanceNodes {

  
 class Instances(val components:List[InstanceDef]) extends RootNode {
    val command:Command = InstanceCommands
    
    override def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = 
      new InstanceSegment(components.map(x => x.createInstance))
    
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      components.foreach(x => x.createContext(context, offset))
    }
  }
  


 class InstanceDef(val inst:InstanceNodes.Instance, val con:List[InstanceNodes.Connect]) extends BaseNode {

    def createInstance:InstanceSegment.InstanceDef = {
      new InstanceSegment.InstanceDef(inst.createInstance,con.map(x => x.createConnection))
    }
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      inst.createContext(context, offset)
      con.foreach(x => x.createContext(context, offset))
    }
 }

 class Instance(val entityNode:IdentNode, val libraryNode:Option[IdentNode]) extends CommandNode {
   val command:Command = InstanceCommands.Instance
   def createInstance:InstanceSegment.Instance = new InstanceSegment.Instance(entityNode.value,libraryNode.map(x => x.value))
   
    override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      entityNode.createContext(context, offset)
      BaseNode.createOptionContext(context, offset, libraryNode)
    }

  }

 class Connect(val src:SimpleNode.StringNode, val dest:SimpleNode.StringNode) extends CommandNode {
   val command:Command = InstanceCommands.Connect
   override def createContext(context:ParserContext,offset:Int)  {
      super.createContext(context, offset);
      src.createContext(context, offset)
      dest.createContext(context, offset)
    }
   def createConnection:InstanceSegment.Connection = new InstanceSegment.Connection(src.value,dest.value)
 }


}
