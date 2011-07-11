package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.core.basic.vars.BaseSignal

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/14/11
 * Time: 8:23 AM
 * To change this template use File | Settings | File Templates.
 */

abstract class BinaryOperator(in1:StatementSegment,in2:StatementSegment) extends StatementSegment {

  val operator:String
  override def createCode(writer:CodeWriter):SegmentReturn  = {
    val in1R = writer.createCode(in1)
    val in2R = writer.createCode(in2)
    return SegmentReturn.segment(in1R.code + operator + in2R.code)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn  = {
    return createCode(writer)
  }

}

object BinaryOperator  {

  class And( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " & "
  }

  class LogicalAnd( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " && "
  }

  class GT( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " > "
  }
  class GTE( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " >= "
  }
  class LT( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " < "
  }
  class LTE( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " <= "
  }
  class EQ( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " == "
  }
  class NEQ( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " != "
  }

  class Plus( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " + "
  }

  class Minus( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " - "
  }

  class Or( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " | "
  }

  class Xor( val in1:StatementSegment, val in2:StatementSegment) extends BinaryOperator(in1,in2) {
      override val operator = " ^ "
  }



}