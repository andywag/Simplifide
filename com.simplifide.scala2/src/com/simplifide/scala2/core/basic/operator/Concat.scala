package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/10/11
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */

class Concat(val states:List[StatementSegment]) extends StatementSegment.Simple {

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder
    builder.append("{")
    var first = true
    for (state <- states) {
      if (!first) builder.append(",")
      builder.append(writer.createCode(state))
      first = false
    }
    builder.append("}")

    return SegmentReturn.segment(builder.toString)
  }
}

object Concat {
  def newConcat(states:List[StatementSegment]):Concat = new Concat(states)
}