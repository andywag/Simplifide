/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn

/* @deprecated  */
class UnaryOp(in:BaseCodeSegment, operator:String) extends BaseCodeSegment{

  override def createCode(writer:CodeWriter):SegmentReturn = {
    SegmentReturn.segment(operator + writer.createCode(in));
  }
}

  class UnaryNot(in:BaseCodeSegment) extends UnaryOp(in,"!") {

  }

object UnaryOp {
  class Not(in:BaseCodeSegment) extends UnaryOp(in,"~")
}
