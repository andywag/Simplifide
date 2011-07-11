/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.flop

import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.basic.operator._
import com.simplifide.scala2.clocks.FlopControl

import scala.collection.mutable.ListBuffer

class SimpleFlop(val name:Option[String],
                 val head:FlopControl,
                 val reset:ListBuffer[SimpleFlop.Segment],
                 val enable:ListBuffer[SimpleFlop.Segment]) extends BaseCodeSegment {

  /** Creates a reset list for this flop. */
  private def createResetList:BaseCodeSegment = {
    val buffer = new ListBufferSegment()
    for (segment <- reset) {
      val assign = segment.in match {
        case Some(x) => new AssignStatement(segment.out,x)
        case None    => new AssignStatement(segment.out,FlopReset)
      }
      buffer.segments.append(assign)
    }
    return buffer;
  }

  /** Creates an enable list for this flop. */
  private def createEnableList:BaseCodeSegment = {
    val buffer = new ListBufferSegment()
    for (segment <- enable) {
      segment.in match {
        case Some(x) => buffer.segments.append(new AssignStatement(segment.out,x))
        case None    =>
      }
    }
    return buffer;
  }

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val flop = new ResetEnableFlop(name,head,createResetList,createEnableList)
    return writer.createCode(flop)
  }

 

}


object SimpleFlop {
  class Segment(val out:BaseCodeSegment,val in:Option[BaseCodeSegment]) {
    def getResetSegment:Segment = new Segment(out,None)
  }
}

