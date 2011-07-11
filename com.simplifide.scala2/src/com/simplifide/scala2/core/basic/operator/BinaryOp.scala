/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.context.CurrentContext

abstract sealed class BinaryOp(inputs:BaseCodeSegment*) extends BaseCodeSegment {

  val verilogOperator:String
  val vhdlOperator:String
  
  def createCode(writer:CodeWriter, operator:String):SegmentReturn  = {
    val builder = new StringBuilder()
    builder.append(StringOps.repeatAfterFirst(inputs.toBuffer,operator,writer))
    SegmentReturn.segment(builder.toString())
  }

   override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
     return createCode(writer,verilogOperator)
   }

   override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
     return createCode(writer,vhdlOperator)
   }

}


class OperatorEquals(inputs:BaseCodeSegment*) extends BinaryOp(inputs:_*) {
    override val verilogOperator:String =  "=="
    override val vhdlOperator:String    = "="
}

class OperatorAnd(inputs:BaseCodeSegment*) extends BinaryOp(inputs:_*) {
    override val verilogOperator:String = " && "
    override val vhdlOperator:String    = " and "
}