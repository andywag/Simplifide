/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.flop

import com.simplifide.scala2.clocks._
import com.simplifide.scala2.command._
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.basic.flop._
import com.simplifide.scala2.core._


class FlopSegment(name:String, flops:List[FlopIndividual]) extends CommandCodeSegment(name){
  override val command:Command = FlopCommands

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder()
    flops.foreach(x => builder.append(writer.createSimpleCode(x)))
    return SegmentReturn.segment(builder.toString)
  }
}

class FlopIndividual(name:String, control:FlopControl,
                     res:List[SimpleFlop.Segment],
                     in:List[SimpleFlop.Segment]) extends BaseCodeSegment {

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val flop = new SimpleFlopList(Some(name),control,res,in)
    return writer.createCode(flop)
  }

 


}
