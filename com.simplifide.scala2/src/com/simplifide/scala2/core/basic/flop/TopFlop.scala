/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.flop

import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.basic.operator._
import com.simplifide.scala2.clocks.FlopControl

/**
 *   Flop which contains a flop head and body
 */
class TopFlop(val name:Option[String],head:FlopControl,body:BaseCodeSegment) extends BaseCodeSegment {
  override def createCode(writer:CodeWriter):SegmentReturn = {
     val alw = new AlwaysSensitivity(name,body,head.createSensitivityList())
     return writer.createCode(alw)
  }
  
}
