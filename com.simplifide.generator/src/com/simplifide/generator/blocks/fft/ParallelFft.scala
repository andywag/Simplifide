/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.VectorType
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.module.{Instance, Module}
import collection.immutable.HashMap
import com.simplifide.scala2.core.complex.{ComplexVectorArray, ComplexSignal}
import com.simplifide.scala2.core.basic.{Comment, StatementSegment}
import com.simplifide.scala2.core.basic.vars.Constant

class ParallelFft(val name:String,
                  val location:String,
                  val signal:Fft.Signals,
                  val params:Fft.Param) extends Fft {


  /** Create the Input to this FFT which is a vector of complex signals */
  def createInput:ComplexVectorArray =
    new ComplexVectorArray(signal.inputName,OpType.ModuleInput,this.params.inFixed,params.length)

  /** Create the Input to the FFT which is a vector of complex signals */
  def createOutput:ComplexVectorArray =
    new ComplexVectorArray(signal.outputName,OpType.ModuleOutput,params.outFixed,params.length)

  /** Create the Internal Signals for the Butterfly. Not Needed. Save for the Upper Stage */
  def createSignals:List[ComplexSignal] = {
    val sigs = new ListBuffer[ComplexSignal]()
    val dep = params.depth
    for (i <- 0 to params.depth) {
      val sig = getSignal(i,OpType.Signal)
      sigs.append(sig)
    }
    return sigs.toList
  }

  def getSignal(index:Int,typ:OpType):ComplexSignal = {
     val siz = params.stageInternal.size
     // Calculate the Width of the Internal Signals
     val stage = if (index == 0) params.inFixed
                 else if (index == params.depth) params.outFixed
                 else if (index < siz) params.stageInternal(index-1)
                 else params.stageInternal(siz-1 )

     val proto = new SignalNew(name + "_internal_" + index,typ,stage,new VectorType(List(params.length),None))
     val sig   = new ComplexSignal(proto)
     return sig
  }

  /** Returns the input to the butterfly stage */
  def getInput(index:Int):ComplexSignal =
    return getSignal(index,OpType.ModuleInput)

  /** Returns the input to the butterfly stage */
  def getOutput(index:Int):ComplexSignal =
    return getSignal(index+1,OpType.ModuleOutput)

  /*
  def debugCSD:String = {
    val builder = new StringBuilder
	  for (i <- -256 until 256) {
      val csds = Constant.createCSD(i):List[Constant.CSD]
      builder.append("//")
      builder.append(i)
      builder.append("---")
      builder.append("(")
      for (csd <- csds) {
        builder.append(csd.debugString)
        builder.append(",")
      }
      builder.append(")\n")
	  }
    return builder.toString
  } */

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val returns = new ListBuffer[SegmentReturn]()

    val arr2vec = new ComplexVectorArray.ArrayToComplex(createInput, getSignal(0,OpType.Signal))
    val vec2arr = new ComplexVectorArray.ComplexToArrayBitReverse(createOutput,getSignal(params.depth,OpType.Signal))

    //returns.append(SegmentReturn.segment(debugCSD))
    returns.append(writer.createCode(new Comment.SingleLine("Convert the input to the individual complex signals")))
    returns.append(writer.createCode(arr2vec))
    returns.append(writer.createCode(new Comment.SingleLine("Convert the complex outputs to a single signal")))
    returns.append(writer.createCode(vec2arr))

    for (i <- 0 until params.depth) {
      val nam   =  name + "_stage_" + i
      val stage =  new ParallelButterflyStage(name,signal.clk,getInput(i),getOutput(i),params,i,true)
      val mod   =  new ParallelButterflyStage.ModuleBase(nam,location,stage)
      val ins   =  new Instance(nam,mod,new HashMap())
      writer.createCode(mod)
      returns.append(writer.createCode(ins))
    }
    return SegmentReturn.combineReturns(returns.toList, List())
  }
          
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }        

}

object ParallelFft {
   case class ModuleBase(override val name:String,
                         override val location:String, 
                         val fft:ParallelFft) extends Module(name,location) {

     def getClockControl:FlopControl = fft.signal.clk
     override def getInputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(fft.signal.clk.getAllSignals(OpType.ModuleInput))
        buffer.append(this.fft.createInput)
        return buffer.toList
      }
      
      override def getOutputs:List[SignalNew]         = List(this.fft.createOutput)
     
      override def getSignals:List[SignalNew]         = fft.createSignals
      
      override def getSegments:List[StatementSegment] = List(fft)
    
    
    }
     
    
  
   }

