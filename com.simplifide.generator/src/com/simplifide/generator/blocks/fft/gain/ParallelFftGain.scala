/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft.gain

import com.simplifide.scala2.core.basic.SimpleStatement
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import collection.immutable.HashMap
import com.simplifide.scala2.core.basic.{Comment, StatementSegment}
import collection.mutable.{LinkedHashMap, ListBuffer}
import com.simplifide.scala2.core.module._
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexVectorArray, ComplexSignal}
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.generator.blocks.fft.Fft

/**                                                        goo
 * Parallel FFT which has no sharing and butterflies done with fixed operations
 */
class ParallelFftGain(override val name:String,
                    val location:String,
                    val signal:Fft.Signals,
                    val params:Fft.Param) extends ModuleSegment(name,signal.clk) {

  override val trace:Boolean = false
  // Module Segment Overrides
  val inputs:List[SignalNew] = {
     val buffer = new ListBuffer[SignalNew]
     buffer.appendAll(signal.clk.getAllSignals(OpType.ModuleInput))
     buffer.append(this.createInput.signal)
     buffer.append(this.createInput.gain)
     buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(createOutput.signal,createOutput.gain)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = createSignals.flatMap(x => x.getAllSignals)

  override def getStimulusMap:Stimulus.Map = {
    val linkMap = new LinkedHashMap[BaseSignal,Stimulus]()
    clk.getResetSignal(OpType.Signal)  match {case Some(x) => linkMap.put(x,new Stimulus.Constant(0,x)); case _ => }
    clk.getEnableSignal(OpType.Signal) match {case Some(x) => linkMap.put(x,new Stimulus.Constant(1,x)); case _ => }
    linkMap.put(createInput.gain,new Stimulus.Constant(0,createInput.gain))
    linkMap.put(createInput.signal,new Stimulus.File("datain",createInput.signal))
    new Stimulus.Map(linkMap,this)
  }
  override def getStorageMap:Storage.Map = {
    val linkMap = new LinkedHashMap[BaseSignal,Storage]()
    linkMap.put(createInput.signal,new Storage.File("dataino",createInput.signal,true))
    linkMap.put(createOutput.gain,new Storage.File("gain",createOutput.gain,false))
    linkMap.put(createOutput.signal,new Storage.File("dataout",createOutput.signal,true))
    new Storage.Map(linkMap)
  }
    // End Module Segment Overrides

  /** Create the Input to this FFT which is a vector of complex signals */
  def createInput:ComplexVectorGain = {
    val sig = new ComplexVectorArray(signal.inputName,OpType.ModuleInput,this.params.inFixed,params.length)
    val gain = SignalNew.newSignal(signal.inputName + "_gain",OpType.ModuleInput,FixedTypeMain.signed(ParallelFftGain.GAINWIDTH,0))
    return new ComplexVectorGain(sig,gain)
  }

  /** Create the Input to the FFT which is a vector of complex signals */
  def createOutput:ComplexVectorGain = {
    val sig = new ComplexVectorArray(signal.outputName,OpType.ModuleOutput,params.outFixed,params.length)
    val gain = SignalNew.newSignal(signal.outputName + "_gain",OpType.ModuleOutput,FixedTypeMain.signed(ParallelFftGain.GAINWIDTH,0))
    return new ComplexVectorGain(sig,gain)
  }
  /** Create the Internal Signals for the Butterfly. Not Needed. Save for the Upper Stage */
  private def createSignals:List[ComplexVectorGain] = {
    val sigs = new ListBuffer[ComplexVectorGain]()
    val dep = params.depth
    for (i <- 0 to params.depth) {
      val sig = getSignal(i,OpType.Signal)
      sigs.append(sig)
    }
    return sigs.toList
  }

  def getSignal(index:Int,typ:OpType):ComplexVectorGain = {
     val siz = params.stageInternal.size
     // Calculate the Width of the Internal Signals
     val stage = if (index == 0) params.inFixed
                 else if (index == params.depth) params.outFixed
                 else if (index < siz) params.stageInternal(index-1)
                 else params.stageInternal(siz-1 )

     val vec = ComplexVectorArray.newSignal(name + "_internal_" + index,typ,stage,params.length)
     val gain = SignalNew.newSignal(name + "_internal_gain_" + index,typ,FixedTypeMain.signed(ParallelFftGain.GAINWIDTH,0))
     val cgain = new ComplexVectorGain(vec,gain)
     return cgain
  }

  def getInternalSignal(index:Int):ComplexVectorGain = {
     val vec = ComplexVectorArray.newSignal("internal",OpType.Signal,params.butterflyInternal(index),params.length)
     val gain = SignalNew.newSignal("internal_gain",OpType.Signal,FixedTypeMain.signed(ParallelFftGain.GAINWIDTH,0))
     val cgain = new ComplexVectorGain(vec,gain)
     return cgain
  }

  /** Returns the input to the butterfly stage */
  def getInput(index:Int):ComplexVectorGain =
    return getSignal(index,OpType.ModuleInput)

  /** Returns the input to the butterfly stage */
  def getOutput(index:Int):ComplexVectorGain =
    return getSignal(index+1,OpType.ModuleRegOutput)

   override def getFileList:List[String] = {
     val files = new ListBuffer[String]
     for (i <- 0 until params.depth) {
        files.append(name + "_stage_" + i)
        files.append(name + "_stage_" + i + "_int")
        files.append(name + "_stage_" + i + "_shift")
     }
     files.append(name)
     return files.toList
   }


  override def createCode(writer:CodeWriter):SegmentReturn = {
    val returns = new ListBuffer[SegmentReturn]()

    //val arr2vec = new ComplexVectorArray.ArrayToComplex(createInput.signal, getSignal(0,OpType.Signal))
    //val vec2arr = new ComplexVectorArray.ComplexToArrayBitReverse(createOutput.signal,getSignal(params.depth,OpType.Signal))

    //returns.append(SegmentReturn.segment(debugCSD))
    //returns.append(writer.createCode(new Comment.SingleLine("Convert the input to the individual complex signals")))
    //returns.append(writer.createCode(arr2vec))
    //returns.append(writer.createCode(new Comment.SingleLine("Convert the complex outputs to a single signal")))
    //returns.append(writer.createCode(vec2arr))

    returns.append(writer.createCode(new Comment.SingleLine("Attach the inputs ")))
    returns.append(writer.createCode(new SimpleStatement(getInput(0).signal,createInput.signal)))
    returns.append(writer.createCode(new SimpleStatement(getInput(0).gain,createInput.gain)))

    returns.append(writer.createCode(new Comment.SingleLine("Create the bit reversed outputs ")))
    returns.append(writer.createCode(new ComplexVectorArray.BitReverse(this.createOutput.signal,getOutput(params.depth-1).signal)))
    returns.append(writer.createCode(new SimpleStatement(createOutput.gain,getOutput(params.depth-1).gain)))


    for (i <- 0 until params.depth) {
      val nam   =  name + "_stage_" + i
      val stage =  new ParallelButterflyStageGain(nam,
        this.location,
        signal.clk,
        getInput(i),
        getInternalSignal(i),
        getOutput(i),
        params,
        i)
      val mod = new Module.Segment(nam,location,stage)
      val ins   =  new Instance(nam,mod,new HashMap())
      writer.createCode(mod)
      returns.append(writer.createCode(ins))
    }
    return SegmentReturn.combineReturns(returns.toList, List())
  }


}

object ParallelFftGain {

  val GAINWIDTH = 5;

}

