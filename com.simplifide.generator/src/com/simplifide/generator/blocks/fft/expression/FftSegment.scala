/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft.expression

import com.simplifide.generator.blocks.fft.Fft
import com.simplifide.generator.blocks.fft.ParallelFft
import com.simplifide.scala2.command._
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.module.Instance
import collection.immutable.HashMap
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.parameters.CoreParameter
import com.simplifide.scala2.parameters.CoreParameterHolder


class FftSegment(blockName:String,name:String,location:String,param:Fft.Param,signal:Fft.Signals) extends CommandCodeSegment("parallel_fft") {
  /** Template used for creating the state machine */
  override val command:Command = FftCommand
  override val commands:ListBuffer[Command] = new ListBuffer[Command]();
  override val matchingC:Boolean = false
  

  
  override def createCode(writer:CodeWriter):SegmentReturn = {
    val fft = new ParallelFft(name,location,signal,param)
    val mod = new ParallelFft.ModuleBase(name,location,fft)
    writer.createCode(mod)
    //val instance = new Instance(name,model,new HashMap())
    //return writer.createCode(instance)
    val obj = new CoreParameter.Object(blockName,mod)
    CoreParameterHolder.parameters.put(blockName,obj)

    return SegmentReturn.segment("")
  }
}
