/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.instance

import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.context.ModuleContext
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentError
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.top.ObjectWriter

class InstanceSegment(val instances:List[InstanceSegment.InstanceDef]) extends CommandCodeSegment("instances") {
  override val command:Command = InstanceCommands
  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
    return this.createVhdlList(this.instances, writer)
  }

}

object InstanceSegment {


  class InstanceDef(val inst:Instance,val connections:List[Connection]) extends BaseCodeSegment{
     override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
       val map = new java.util.HashMap[String,String]()
       connections.foreach(x => x.addConnect(map))
       val module = inst.getModule(writer.context)
       module match {
        case Some(x) => return SegmentReturn.segment(ObjectWriter.createInstance(x,map))
        case None    => return SegmentReturn.segment(new SegmentError("InstanceModule " + inst.entity + " Not Defined"))
      }
    }
  }
  class Instance(val entity:String,val library:Option[String]) {
    def getModule(context:CurrentContext):Option[ModuleContext] = {
      context.findModule(library, entity)
    }

    //val module:Option[ModuleContext] = context.findModule(library, entity)
  }
  class Connection(val src:String, val dest:String) {
    def addConnect(map:java.util.HashMap[String,String]) {
      map.put(src, dest);
    }
  }
}
