package com.simplifide.generator.blocks.gain

import com.simplifide.generator.blocks.OpticalConstants
import com.simplifide.generator.blocks.equalizer.Shifter
import com.simplifide.scala2.core.complex.ParallelComplexCSDMultiply
import com.simplifide.scala2.core.basic.fixed.AdditionStatement
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.{ListBuffer, HashMap}
import com.simplifide.scala2.core.basic.flop.{SimpleFlopList,SimpleFlop}
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexSignal, ComplexConstant, ComplexVectorArray}
import com.simplifide.scala2.core.module.{ModuleSegment, Module, Instance}
import com.simplifide.scala2.core.basic.{SimpleMux, Statement, Comment, StatementSegment}
import com.simplifide.scala2.core.basic.operator.{UnaryOperator, BarrelShifter, Select}



/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/10/11
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */

/** Segment which defines the chromatic dispersion filter stage
  * This is the final addition which combines the data at the output of the
  * cd filter segment
  */

class ParallelCSDMultipliciationGain(override val name:String,
                      val location:String,
                      override val clk:FlopControl,
                      val signalIn:ComplexVectorGain,
                      val signalOut:ComplexVectorGain,
                      val taps:List[ComplexConstant],
                      val internalWidth:FixedType,
                      val params:ParallelComplexCSDMultiply.Params) extends ModuleSegment(name,clk) {


  val signalInternal = {
    val sig   = ComplexVectorArray.newSignal("signal_internal",OpType.Signal,internalWidth,signalIn.signal.len)
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

  override def getFileList:List[String] = List(name, name+"_mod", name+"_shift")


    override def createCode(writer:CodeWriter):SegmentReturn = {

      val segments = new ListBuffer[BaseCodeSegment]()
      val signals = new ParallelComplexCSDMultiply.Signals(clk,signalIn.signal,signalInternal.signal,taps)
      // Create the CSD Module
      val csdModName = name+"_mod"
      val csd    = new ParallelComplexCSDMultiply(csdModName,params,signals)
      val csdMod = new ParallelComplexCSDMultiply.ModuleBase(csdModName,location,csd)
      writer.createCode(csdMod)
      segments.append(new Instance(csdModName,csdMod,Map[String,String]()))
      // Create the Shifter
      val shifter = new Shifter(name+"_shift",clk,signalInternal,signalOut,OpticalConstants.GAINSHIFT,params.delays)
      segments.append(shifter.createInstance(location,writer))



	    return SegmentReturn.combineReturns(writer,segments)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }


}

object ParallelCSDMultipliciationGain {




}