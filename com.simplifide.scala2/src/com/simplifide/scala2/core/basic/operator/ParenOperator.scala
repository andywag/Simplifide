package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/9/11
 * Time: 6:22 AM
 * To change this template use File | Settings | File Templates.
 */

class ParenOperator(val in:StatementSegment) extends StatementSegment.Simple {

  override def createCode(writer:CodeWriter):SegmentReturn  = {
    val builder = new StringBuilder
    builder.append("(")
    builder.append(writer.createCode(in))
    builder.append(")")

    return SegmentReturn.segment(builder.toString)
  }
}