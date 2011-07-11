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

class ParallelEqualizerFilter(name:String,location:String,
                              val signal:ParallelEqualizerFilter.Signals,
                              params:ParallelEqualizerFilter.Params) extends StatementSegment {


  /** Create the Inputs */
  def createInput:ComplexVectorArray =
    new ComplexVectorArray(signal.inputName,OpType.ModuleInput,this.params.inFixed,params.length)

  /** Create the Taps  */
  def createTaps:ComplexVectorArray =
    new ComplexVectorArray(signal.tapName,OpType.ModuleInput,this.params.tapFixed,params.length)

  /** Create the Outputs */
  def createOutput:ComplexVectorArray =
    new ComplexVectorArray(signal.outputName,OpType.ModuleOutput,params.outFixed,params.length)

 




  override def createCode(writer:CodeWriter):SegmentReturn = {
      val in1 = new ComplexSignal(new SignalNew("in1",OpType.ModuleInput,params.tapFixed,VectorType.NoVector))
      val in2 = new ComplexSignal(new SignalNew("in2",OpType.ModuleInput,params.inFixed,VectorType.NoVector))
      val out = new ComplexSignal(new SignalNew("out",OpType.ModuleRegOutput,params.outFixed,VectorType.NoVector))

      val iname = name + "_mul"
      val mult = new ComplexMultiply(iname,signal.clk,in1,in2,out,false,params.intFixed)
      val mult_mod = new ComplexMultiply.ModuleBase(name + "_mult",location,mult)
      writer.createCode(mult_mod)

      val inV  = createInput
      val outV = createOutput
      val tapV = createTaps

      val instances = new ListBuffer[Instance]()
      for (i <- 0 until params.length) {

        val map = HashMap(mult_mod.getRealIn0Name -> writer.createCode(inV.getRealSlice(i)).code,
                          mult_mod.getImagIn0Name -> writer.createCode(inV.getImagSlice(i)).code,
                          mult_mod.getRealIn1Name -> writer.createCode(tapV.getRealSlice(i)).code,
                          mult_mod.getImagIn1Name -> writer.createCode(tapV.getImagSlice(i)).code,
                          mult_mod.getRealOutName -> writer.createCode(outV.getRealSlice(i)).code,
                          mult_mod.getImagOutName -> writer.createCode(outV.getImagSlice(i)).code)
        val ins = new Instance(iname + i,mult_mod,map)
        instances.append(ins)
      }
      return SegmentReturn.combineReturns(instances.toList.map(x => writer.createCode(x)),List())

  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }

}

object ParallelEqualizerFilter {

   case class Signals(clk:FlopControl,
                     inputName:String,
                     tapName:String,
                     outputName:String)

   case class Params(length:Int,
		   			         inFixed:FixedType,
                     tapFixed:FixedType,
                     outFixed:FixedType,
                     intFixed:FixedType)

   case class ModuleBase(override val name:String,
                         override val location:String,
                         val fft:ParallelEqualizerFilter) extends Module(name,location) {

     override def getInputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(fft.signal.clk.getAllSignals(OpType.ModuleInput))
        buffer.append(fft.createInput)
        buffer.append(fft.createTaps)
        return buffer.toList
      }

      override def getOutputs:List[SignalNew]         = List(fft.createOutput)

      override def getSignals:List[SignalNew]         = List()

      override def getSegments:List[StatementSegment] = List(fft)


    }



   }