/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.basic.StatementSegment

/**Method which is a part select for the input block returns in[slice]
 * @param in Input Signal
 * @param slice Part Select
 */
class SliceOp(in:BaseCodeSegment,slice:BaseCodeSegment) extends StatementSegment.Simple{

  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder()
    builder.append(in.createVerilogCode(writer))
    builder.append("[")
    builder.append(writer.createCode(slice))
    builder.append("]")
    SegmentReturn.segment(builder.toString)
  }
  override def createVhdlCode(writer:CodeWriter):SegmentReturn     = {
    val builder = new StringBuilder()
    builder.append(in.createVhdlCode(writer))
    builder.append("(")
    builder.append(slice.createVhdlCode(writer))
    builder.append(")")
    SegmentReturn.segment(builder.toString)
  }

}
