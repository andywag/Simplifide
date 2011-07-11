package com.simplifide.generator.blocks.equalizer.filter

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.{BaseCodeSegment, SegmentReturn, CodeWriter}
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.vars.{SignalNew,OpType}
import com.simplifide.scala2.core.module.{Instance, ModuleSegment}
import com.simplifide.generator.blocks.equalizer.Shifter
import com.simplifide.generator.blocks.OpticalConstants
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexVectorArray}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/21/11
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */

class ParallelFilterGain(override val name:String,
                         val location:String,
                         override val clk:FlopControl,
                         val signalIn:ComplexVectorGain,
                         val signalOut:ComplexVectorGain,
                         val params:ParallelFilter.Params) extends ModuleSegment(name,clk)    {

  val signalInternal = {
    val sig   = ComplexVectorArray.newSignal("signal_internal",OpType.Signal,params.intFixed,signalIn.signal.len)
    val gain  = signalIn.gain
    new ComplexVectorGain(sig,gain)
  }

  /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
        buffer.append(signalIn.signal)
        buffer.append(signalIn.gain)
        buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(signalOut.signal, signalOut.gain)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(signalInternal.signal)


  val filter = {
     val filtName = name + "_mod"
     val signals   = new ParallelFilter.Signals(clk,signalIn.signal,signalInternal.signal)
     new ParallelFilter(filtName,location,params,signals)

  }


  override def getFileList:List[String] = filter.getFileList ::: List(name,name+"_shift")


  override def createCode(writer:CodeWriter):SegmentReturn = {
     val segments = new ListBuffer[BaseCodeSegment]()
     // Create the Filter Module


     segments.append(filter.createInstance(location,writer))

      val shifter = new Shifter(name+"_shift",clk,signalInternal,signalOut,OpticalConstants.GAINSHIFT,1)
      segments.append(shifter.createInstance(location,writer))



      return SegmentReturn.combineReturns(writer,segments)
  }

}