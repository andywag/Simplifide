package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.complex._
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.module._
import com.simplifide.generator.blocks.equalizer.rotation.ParallelCordicRotation
import com.simplifide.scala2.core.basic.{SimpleStatement, Comment}
import com.simplifide.generator.blocks.fft.gain.ParallelFftGain
import collection.mutable.{LinkedHashMap, ListBuffer}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/10/11
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class which creates one stage of the CD Filter
 */
class CdTop(override val name:String,
                    val location:String,
                    override val clk:FlopControl,
                    val signalIn:ComplexVectorGain,
                    val signalOut:ComplexVectorGain,
                    val fftProject:Project.Basic[ParallelFftGain],
                    val ifftProject:Project.Basic[ParallelFftGain],
                    val filterStageProject:Project.Basic[CdFilterStage],
                    val cordicProject:Project.Basic[ParallelCordicRotation],
                    val numStages:Int,
                    val zeroTones:Int) extends ModuleSegment(name,clk) {


  override val trace:Boolean = true

    /** Signal which controls the inverse cd operation */
    val inverseCd = SignalNew.newSignal("inverse_cd",OpType.Input,FixedTypeMain.unsigned(1,0))
    /** val bypass signal which defines the bypass opeartion for the block */
    val bypass = SignalNew.newSignal("bypass_stage",OpType.ModuleInput,FixedTypeMain.unsigned(numStages,0))
    /** Input to the overlap block which is created by conjugating the input if necessary */
    val overlapAddIn = ComplexVectorGain.newSignal("overlap_add_in",OpType.Signal,signalIn.fixed,signalIn.len)
    /** Input to the fft block */
    val fftIn = ComplexVectorGain.newSignal("fft_in",OpType.Signal,signalIn.fixed,2*signalIn.len)
    /** Fft output which goes into the filter */
    val fftOut = fftIn.createSignal("fft_out",signalIn.fixed)
    val stageOut = fftIn.createSignal("stage_out",signalIn.fixed)
    val ifftOut = fftIn.createSignal("ifft_out",signalIn.fixed)
    /** Output of the overlap block */
    val overlapAddOut = ComplexVectorGain.newSignal("overlap_add_out",OpType.Signal,signalIn.fixed,signalIn.len)


    val stageModule = new CdFilterStageTop(name+"_filt",
                                           location,
                                           clk,
                                           fftOut,
                                           stageOut,
                                           filterStageProject,
                                           cordicProject,
                                           numStages,
                                           zeroTones)



    /** Returns a list of inputs for this module */
    val inputs:List[SignalNew]  = {
      val buffer = new ListBuffer[SignalNew]
      buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
      buffer.append(bypass)
      buffer.append(inverseCd)
      buffer.append(signalIn.signal)
      buffer.append(signalIn.gain)
      buffer.append(cordicProject.moduleSegment.vectorAngleIn)
      buffer.toList

    }
    /** Returns a list of outputs for this module */
    val outputs:List[SignalNew] = List(signalOut.signal, signalOut.gain)
    /** Returns a list of internal signals for this module */
    val signals:List[SignalNew] = List(fftIn.signal, fftIn.gain, fftOut.signal, fftOut.gain, stageOut.signal, stageOut.gain,
                                       ifftOut.signal, ifftOut.gain, overlapAddIn.signal, overlapAddIn.gain, overlapAddOut.signal,
                                       overlapAddOut.gain)

    val cdInConjugate   = new CdConjugate(name + "_inconj",location,clk,inverseCd,signalIn,overlapAddIn)
    val cdOverlap       = new CdOverlapBlock.Add(name+"_add",location,clk,overlapAddIn,fftIn)
    val cdUnOverlap     = new CdOverlapBlock.Remove(name+"_remove",location,clk,ifftOut,overlapAddOut)
    val cdOutConjugate  = new CdConjugate(name + "_outconj",location,clk,inverseCd,overlapAddOut,signalOut)


    override def getFileList:List[String] = cdOverlap.getFileList ::: cdUnOverlap.getFileList :::
      List(stageModule.name, name) ::: cdInConjugate.getFileList ::: cdOutConjugate.getFileList


    override val projects = List(fftProject, ifftProject, filterStageProject, cordicProject)

    override def getStimulusMap:Stimulus.Map = {
      val linkMap = new LinkedHashMap[BaseSignal,Stimulus]()
      clk.getResetSignal(OpType.Signal)  match {case Some(x) => linkMap.put(x,new Stimulus.Constant(0,x)); case _ => }
      clk.getEnableSignal(OpType.Signal) match {case Some(x) => linkMap.put(x,new Stimulus.Constant(1,x)); case _ => }
      //linkMap.put(bypass, new Stimulus.Constant(0,bypass))
      linkMap.put(inverseCd,new Stimulus.File("inverse_cd",inverseCd))
      linkMap.put(bypass,new Stimulus.File("bypass",bypass))
      linkMap.put(signalIn.gain,new Stimulus.Constant(0,signalIn.gain))
      linkMap.put(signalIn.signal,new Stimulus.File("datain",signalIn.signal))
      linkMap.put(cordicProject.moduleSegment.vectorAngleIn  ,new Stimulus.File("anglein",cordicProject.moduleSegment.vectorAngleIn))

      new Stimulus.Map(linkMap,this)
    }
   override def getStorageMap:Storage.Map = {
    val linkMap = new LinkedHashMap[BaseSignal,Storage]()
    linkMap.put(signalIn.signal,new Storage.File("dataino",signalIn.signal,true))
    linkMap.put(signalOut.signal,new Storage.File("dataout",signalOut.signal,true))
    linkMap.put(signalOut.gain,new Storage.File("dataoutgain",signalOut.gain,false))
    linkMap.put(fftIn.signal,new Storage.File("fftin" ,fftIn.signal,true,"cd_top."))
    linkMap.put(fftIn.gain,new Storage.File("fftingain" ,fftIn.gain,false,"cd_top."))
    linkMap.put(fftOut.signal,new Storage.File("fftout" ,fftOut.signal,true,"cd_top."))
    linkMap.put(fftOut.gain,new Storage.File("fftoutgain"   ,fftOut.gain,false,"cd_top."))
    // Output the individual cordic stage outputs
    for (i <- 0 until numStages-1) {
      val sig = stageModule.stageOutput(i).signal
      val gain = stageModule.stageOutput(i).gain
      linkMap.put(sig,new Storage.File("st" + i    ,sig,  true,"cd_top.cd_top_filt."))
      linkMap.put(gain,new Storage.File("stgain" + i  ,gain, false,"cd_top.cd_top_filt."))
    }

    linkMap.put(stageOut.signal,new Storage.File("stageout" ,stageOut.signal,true,"cd_top."))
    linkMap.put(stageOut.gain,new Storage.File("stagegain"   ,stageOut.gain,false,"cd_top."))
    linkMap.put(ifftOut.signal,new Storage.File("ifftout" ,ifftOut.signal,true,"cd_top."))
    linkMap.put(ifftOut.gain,new Storage.File("ifftgain"   ,ifftOut.gain,false,"cd_top."))

    new Storage.Map(linkMap)
  }



    override def createCode(writer:CodeWriter):SegmentReturn = {
      // Attach the FFT
      val segments = new ListBuffer[BaseCodeSegment]()
      // Create the Input Conjugate Block
      segments.append(new Comment.SingleLine("Input Conjugation Block"))
      segments.append(cdInConjugate.createInstance(location,writer))
      // Create the Input Overlap
      segments.append(new Comment.SingleLine("Overlap Creation"))
      segments.append(cdOverlap.createInstance(location,writer))
      // Create the FFT Block
      segments.append(new Comment.SingleLine("FFT Instantiation"))
      val fftMap =  Map(fftProject.moduleSegment.createInput.signal.name    -> fftIn.signal.name,
                           fftProject.moduleSegment.createInput.gain.name      -> fftIn.gain.name,
                           fftProject.moduleSegment.createOutput.signal.name   -> fftOut.signal.name,
                           fftProject.moduleSegment.createOutput.gain.name     -> fftOut.gain.name)
      segments.append(fftProject.moduleSegment.createConnection(fftProject.moduleSegment.name,fftMap))
      // Create the Filter
      segments.append(new Comment.SingleLine("Filter Module Instantiation"))
      segments.append(stageModule.createInstance(location,writer))
      // Create the IFFT
      val ifftMap =  Map(ifftProject.moduleSegment.createInput.signal.name    -> stageOut.signal.name,
                            ifftProject.moduleSegment.createInput.gain.name      -> stageOut.gain.name,
                            ifftProject.moduleSegment.createOutput.signal.name   -> ifftOut.signal.name,
                            ifftProject.moduleSegment.createOutput.gain.name     -> ifftOut.gain.name)
      val ifftMapPass =  Map(ifftProject.moduleSegment.createInput.signal.name    -> fftOut.signal.name,
                             ifftProject.moduleSegment.createInput.gain.name      -> fftOut.gain.name,
                             ifftProject.moduleSegment.createOutput.signal.name   -> ifftOut.signal.name,
                             ifftProject.moduleSegment.createOutput.gain.name     -> ifftOut.gain.name)
      segments.append(new Comment.SingleLine("IFFT Instantiation"))
      segments.append(ifftProject.moduleSegment.createConnection(ifftProject.moduleSegment.name,ifftMap))
      // Remove the overlap from the signal
      segments.append(cdUnOverlap.createInstance(location,writer))
      // Create the Input Conjugate Block
      segments.append(new Comment.SingleLine("Output Conjugation Block"))
      segments.append(cdOutConjugate.createInstance(location,writer))

      return SegmentReturn.combineReturns(writer,segments)
  }




}
