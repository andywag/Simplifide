/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.basic.vars.BaseSignal

class Comment(val text:String) extends StatementSegment{
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
       writer.createCode(this)
     }
}

object Comment {
  class SingleLine(override val text:String) extends Comment(text) {
     
    
    
     def createItem(prefix:String):SegmentReturn = {
       return SegmentReturn.segment(prefix + " " + text + "\n")
     }
     
     override def createFloatCode(writer:CodeWriter):SegmentReturn = return createItem("//")
     override def createFixedCode(writer:CodeWriter):SegmentReturn = return createItem("//")
     override def createVerilogCode(writer:CodeWriter):SegmentReturn = return createItem("//")
     override def createVhdlCode(writer:CodeWriter):SegmentReturn     = return createItem("--")
  }
}
