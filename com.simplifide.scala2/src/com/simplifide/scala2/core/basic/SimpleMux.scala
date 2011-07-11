/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.context.CurrentContext
import vars.BaseSignal


/** Simple Question Statement */
class SimpleMux(val condition:BaseCodeSegment,val tr:BaseCodeSegment,val fa:BaseCodeSegment) extends StatementSegment.Simple{

  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder()
    builder.append("(")
    builder.append(condition.createVerilogCode(writer))
    builder.append(")")
    builder.append(" ? ")
    builder.append(tr.createVerilogCode(writer))
    builder.append(" : ")
    builder.append(fa.createVerilogCode(writer))
    
    return SegmentReturn.segment(builder.toString)
  }
  
}

object SimpleMux {

  def newMux(condition:BaseCodeSegment,tr:BaseCodeSegment,fa:BaseCodeSegment) =new SimpleMux(condition,tr,fa)

  def statement(output:BaseSignal,condition:BaseCodeSegment,tr:BaseCodeSegment, fa:BaseCodeSegment):StatementSegment = {
    val mux = newMux(condition,tr,fa)
    val stat = new SimpleStatement(output,mux)
    return stat
  }
}
