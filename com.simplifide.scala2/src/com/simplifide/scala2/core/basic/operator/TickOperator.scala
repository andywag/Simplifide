/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn

class TickOperator(in:BaseCodeSegment,op:String) extends BaseCodeSegment{

  override def createCode(writer:CodeWriter):SegmentReturn = {
    SegmentReturn.segment(writer.createCode(in).code + "'" + op);
  }
  
  
}
