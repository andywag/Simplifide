package com.simplifide.generator.blocks.equalizer.filter

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.module.{Instance, Module}
import collection.immutable.HashMap
import com.simplifide.scala2.core.basic.{Comment, StatementSegment}
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.complex.{ComplexMultiply, ComplexVectorArray, ComplexSignal}
import com.simplifide.generator.blocks.fft.ParallelFft

class ParallelEqualizer(name:String,
					              location:String,
					              val ifft:ParallelFft.ModuleBase,
                        val filter:ParallelEqualizerFilter.ModuleBase,
                        val fft:ParallelFft.ModuleBase) extends StatementSegment {

  def getClockControl:FlopControl = ifft.getClockControl

  /** Create the Inputs -- Ties directly to the ifft */
  def createInput:ComplexVectorArray = ifft.fft.createInput
  /** Create the Taps   -- Ties directly to the filter taps */
  def createTaps:ComplexVectorArray = filter.fft.createTaps
  /** Create the Outputs -- Tied Directly from the FFT Output */
  def createOutput:ComplexVectorArray = fft.fft.createOutput
  /** Create the Filter Input */
  def createFilterInput = filter.fft.createInput.copyWithType(OpType.Signal)
  /** Create the Filter Output */
  def createFilterOuput = filter.fft.createOutput.copyWithType(OpType.Signal)

 




  override def createCode(writer:CodeWriter):SegmentReturn = {
      val instances = List(new Instance(ifft.name,ifft,HashMap()),
              			   new Instance(filter.name,filter,HashMap()),             
              			   new Instance(fft.name,fft,HashMap()))

      val ret = instances.map(x => writer.createCode(x))
      return SegmentReturn.combineReturns(ret,List())


	  return SegmentReturn.segment("")
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }

}

object ParallelEqualizer {


   case class ModuleBase(override val name:String,
                         override val location:String,
                         val fft:ParallelEqualizer) extends Module(name,location) {

     override def getInputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(fft.getClockControl.getAllSignals(OpType.ModuleInput))
        buffer.append(fft.createInput)
        buffer.append(fft.createTaps)
        return buffer.toList
      }

      override def getOutputs:List[SignalNew]         = List(fft.createOutput)

      override def getSignals:List[SignalNew]         = List(fft.createFilterInput,fft.createFilterOuput)

      override def getSegments:List[StatementSegment] = List(fft)


    }



   }