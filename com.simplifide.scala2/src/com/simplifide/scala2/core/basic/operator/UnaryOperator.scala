package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/14/11
 * Time: 8:23 AM
 * To change this template use File | Settings | File Templates.
 */

abstract class UnaryOperator(in:StatementSegment) extends StatementSegment {

  val operator:String
  override def createCode(writer:CodeWriter):SegmentReturn  = {
    val in1R = writer.createCode(in)
    return SegmentReturn.segment(operator + in1R.code)
  }
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn  = {
    val in1R = writer.createCode(in)
    return SegmentReturn.segment(operator + in1R.code)
  }


}

object UnaryOperator  {

  class Negative(val in:StatementSegment) extends UnaryOperator(in) {
      override val operator = "-"
  }

  class Not(val in:StatementSegment) extends UnaryOperator(in) {
      override val operator = "~"
  }

  class And(val in:StatementSegment) extends UnaryOperator(in) {
      override val operator = "~"
  }

  class NotAnd(val in:StatementSegment) extends UnaryOperator(in) {
      override val operator = "~&"
  }

  class Or(val in:StatementSegment) extends UnaryOperator(in) {
      override val operator = "|"
  }



}