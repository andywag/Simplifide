package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.core.basic.fixed.AdditionStatement
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.{ListBuffer, HashMap}
import com.simplifide.generator.blocks.equalizer.filter.ParallelFilter
import com.simplifide.scala2.core.basic.flop.{SimpleFlopList,SimpleFlop}
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexSignal, ComplexConstant, ComplexVectorArray}
import com.simplifide.scala2.core.module.{ModuleSegment, Module, Instance}
import com.simplifide.scala2.core.basic.{SimpleMux, Statement, Comment, StatementSegment}
import com.simplifide.scala2.core.basic.operator.Select
import com.simplifide.generator.blocks.equalizer.Shifter
import com.simplifide.generator.blocks.OpticalConstants

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/10/11
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */

/** Segment which defines the chromatic dispersion filter stage
  * This is the final addition which combines the data at the output of the
  * cd filter segment. This block is a top level wrapper module which contains blocks
  * for normalizing the input/output segments to the same level
  */

class CdStageAdditionTop(override val name:String,
                      val location:String,
                      override val clk:FlopControl,
                      val signalIn:ComplexVectorGain,
                      val signalOut:ComplexVectorGain,
                      val param:CdStageAdditionTop.Params) extends ModuleSegment(name,clk) {


  val currentInternal = ComplexVectorArray.newSignal("current_internal",OpType.Signal,param.intWidth,signalIn.signal.len)
  val delayInternal   = ComplexVectorArray.newSignal("delay_internal",OpType.Signal,param.intWidth,signalIn.signal.len)
  val gainnternal     = SignalNew.newSignal("gain_internal",OpType.Signal,signalIn.gain.getFixedType)


  val adderOutput     = ComplexVectorGain.newSignal("adder_output",
                                                    OpType.Signal,
                                                    param.intWidth,
                                                    signalIn.signal.len,
                                                    signalIn.gain.getFixedType)



  /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
        buffer.append(signalIn.signal)
        buffer.append(signalIn.gain)
        buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(signalOut.signal,signalOut.gain)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(currentInternal, delayInternal, gainnternal, adderOutput.signal, adderOutput.gain)

  val scaleModule = {
    new CdStageAdditionScale(name+"_scale",location,clk,signalIn,currentInternal,delayInternal,adderOutput.gain)
  }

  val adderModule = {
    new CdStageAddition(name+"_add",location,clk,currentInternal,delayInternal,adderOutput.signal)
  }

  val shifterModule = {
    new Shifter(name+"_shift",clk, adderOutput, signalOut, OpticalConstants.GAINWIDTH,0)
  }

  override def getFileList:List[String] = scaleModule.getFileList ::: adderModule.getFileList ::: shifterModule.getFileList ::: List(name)



  override def createCode(writer:CodeWriter):SegmentReturn = {

    val segments = new ListBuffer[BaseCodeSegment]()
    // Create the Scaling Module
    segments.append(scaleModule.createInstance(location,writer))
    // Create the Addition Portion of the Module
    segments.append(adderModule.createInstance(location,writer))
    // Create the final stage of shifting
    segments.append(shifterModule.createInstance(location,writer))

	  return SegmentReturn.combineReturns(writer,segments)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }


}

object CdStageAdditionTop {
  class Params(val intWidth:FixedType)
}