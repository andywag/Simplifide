/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.component

import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.context.ModuleContext
import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.top.ObjectWriter
import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer



abstract class InternalSeg extends BaseCodeSegment {
  
}


 private class InternalComponent(val module:Option[ModuleContext], entity:String) extends BaseCodeSegment {
    override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
      module match {
        case Some(x) => return SegmentReturn.segment(ObjectWriter.createComponent(x))
        case None    => return SegmentReturn.segment(new ComponentErrors.EntityNotFound(entity))
      }
    }
 }

 class ComponentSeg(val entity:String,val library:Option[String]) extends InternalSeg{
    override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
      
      val module:Option[ModuleContext] = writer.context.findModule(library, entity)
      val internal = new InternalComponent(module,entity)
      return internal.createVhdlCode(writer)
    }
  }
 
  class UsedSeg extends InternalSeg {

   private def create(writer:CodeWriter):SegmentReturn = {
     val module = writer.context.getActiveModule.get
     val modules:Buffer[InternalComponent] = module.connections.map(x => new InternalComponent(Some(x.module),""))
     return this.createVhdlList(modules.toList, writer)

  }
   override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
     writer.context.getActiveModule match {
       case Some(x) => return create(writer)
       case None    => return SegmentReturn.segment(new ComponentErrors.EnclosingModuleNotFound())
     }
     
    }
  }
  
  
  class ComponentsSeg(context:CurrentContext,name:String, val seg:List[InternalSeg]) extends CommandCodeSegment(name) {
  override val command:Command = ComponentCommands


  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
    return this.createVhdlList(this.seg, writer)  
  }
}

object ComponentSegment {


 

 

}
