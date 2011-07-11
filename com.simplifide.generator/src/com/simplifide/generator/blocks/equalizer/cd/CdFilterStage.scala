package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.complex.{ComplexConstant, ComplexVectorArray}
import collection.immutable.HashMap
import com.simplifide.scala2.core.complex._
import com.simplifide.scala2.command.{Command, CommandCodeSegment}
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.{SimpleMux,SimpleStatement, StatementSegment}
import com.simplifide.scala2.core.basic.Comment
import com.simplifide.generator.blocks.equalizer.Shifter
import com.simplifide.generator.blocks.equalizer.filter.{ParallelFilterGain, ParallelFilter}
import com.simplifide.generator.blocks.gain.ParallelCSDMultipliciationGain
import collection.mutable.{LinkedHashMap, ListBuffer}
import com.simplifide.scala2.core.module._
import com.simplifide.scala2.core.basic.flop.SimpleFlopList

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
class CdFilterStage(override val name:String,
                    val location:String,
                    override val clk:FlopControl,
                    val signalIn:ComplexVectorGain,
                    val signalOut:ComplexVectorGain,
                    val params:CdFilterStage.Params) extends ModuleSegment(name,clk) {


  override val trace:Boolean = true


   val csdOutput      = signalIn.createSignal("csd_output",signalIn.fixed)
   val filterOutput   = signalIn.createSignal("filter_out",signalIn.fixed)
   val adderOutput   = signalIn.createSignal("adder_out",signalIn.fixed)
   val lastOut        = signalIn.createSignal("last_out",signalIn.fixed)
   val lastReg        = signalIn.createSignal("last_reg",signalIn.fixed,OpType.Reg)
   val bypass         = SignalNew.newSignal("bypass", OpType.ModuleInput)

    /** Returns a list of inputs for this module */
    val inputs:List[SignalNew]  = {
      val buffer = new ListBuffer[SignalNew]
      buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
      buffer.append(signalIn.signal)
      buffer.append(signalIn.gain)
      buffer.append(bypass)
      buffer.toList
    }
    /** Returns a list of outputs for this module */
    val outputs:List[SignalNew] = List(signalOut.signal, signalOut.gain)
    /** Returns a list of internal signals for this module */
    val signals:List[SignalNew] = List(csdOutput.signal, csdOutput.gain,filterOutput.signal, filterOutput.gain,
                                       adderOutput.signal, adderOutput.gain, lastOut.signal, lastOut.gain,
                                       lastReg.signal, lastReg.gain)

    /** Module which contains the initial CSD Multiplication */
    val csdModule = {
      val csdName = name + "_csd"
      val par = new ParallelComplexCSDMultiply.Params(params.csdInternalWidth, params.csdDelay, false)
      new ParallelCSDMultipliciationGain(csdName,location,clk,signalIn,csdOutput,getCsdTaps,par.internal,par)
    }

    val filterModule = {
      val filtName = name + "_filter"
      val filtParam = new ParallelFilter.Params(params.filterTaps,
                                                params.filterDepth,
                                                params.filterInternalWidth)
     new ParallelFilterGain(filtName,location,clk,csdOutput,filterOutput,filtParam)
    }
    val adderModule = {
      val adderName  = name + "_adder"
      val adderParam = new CdStageAdditionTop.Params(params.adderWidth)
      new CdStageAdditionTop(adderName,location,clk,filterOutput,adderOutput,adderParam)
    }


    override def getFileList:List[String] = csdModule.getFileList ::: filterModule.getFileList ::: adderModule.getFileList ::: List(name)

    override def getStimulusMap:Stimulus.Map = {
      val linkMap = new LinkedHashMap[BaseSignal,Stimulus]()
      clk.getResetSignal(OpType.Signal)  match {case Some(x) => linkMap.put(x,new Stimulus.Constant(0,x)); case _ => }
      clk.getEnableSignal(OpType.Signal) match {case Some(x) => linkMap.put(x,new Stimulus.Constant(1,x)); case _ => }
      linkMap.put(bypass, new Stimulus.Constant(0,bypass))
      linkMap.put(signalIn.gain,new Stimulus.Constant(0,signalIn.gain))
      linkMap.put(signalIn.signal,new Stimulus.File("datain",signalIn.signal))
      new Stimulus.Map(linkMap,this)
    }

   override def getStorageMap:Storage.Map = {
    val linkMap = new LinkedHashMap[BaseSignal,Storage]()
    linkMap.put(signalIn.signal,new Storage.File("dataino",signalIn.signal,true))
    linkMap.put(signalIn.gain,new Storage.File("datainogain",signalIn.gain,false))
    linkMap.put(signalOut.signal,new Storage.File("dataout",signalOut.signal,true))
    linkMap.put(signalOut.gain,new Storage.File("datagain",signalOut.gain,false))
    linkMap.put(csdOutput.signal,new Storage.File("csdout" ,csdOutput.signal,true,"filter_stage."))
    linkMap.put(csdOutput.gain,new Storage.File("csdgain"   ,csdOutput.gain,false,"filter_stage."))
    linkMap.put(filterOutput.signal,new Storage.File("filterout" ,filterOutput.signal,true,"filter_stage."))
    linkMap.put(filterOutput.gain,new Storage.File("filtergain"   ,filterOutput.gain,false,"filter_stage."))


    new Storage.Map(linkMap)
  }


    private def getCsdTaps:List[ComplexConstant] = {
      def createTap(ggain:Double,ind:Double, index:Int):ComplexConstant = {
        val gain = ggain*ind*ind;
        val rterm:Float = math.cos(gain).toFloat
        val iterm:Float = math.sin(gain).toFloat
        return ComplexConstant.newComplex(params.csdTapWidth,rterm,iterm)
      }
      val consts = new ListBuffer[ComplexConstant]()
      for (i <- 0 until signalIn.signal.len/2) {
    	val c = 2.0*i.toFloat/signalIn.signal.len.toFloat
        consts.append(createTap(params.cdGain,c,i))
      }
      for (i <- 0 until signalIn.signal.len/2) {
    	val c = 2.0*(signalIn.signal.len/2-i).toFloat/signalIn.signal.len.toFloat
        consts.append(createTap(params.cdGain,c,i))
      }
      return consts.toList

    }

    override def createCode(writer:CodeWriter):SegmentReturn = {
      val segments = new ListBuffer[BaseCodeSegment]()
      // Bypass Segment
      segments.append(new Comment.SingleLine("Potentially Bypass the Block"))
      segments.append(SimpleMux.statement(lastOut.signal,bypass,signalIn.signal,adderOutput.signal))
      segments.append(SimpleMux.statement(lastOut.gain,bypass,signalIn.gain,adderOutput.gain))
      segments.append(SimpleFlopList.newFlop(clk,List(lastReg.signal,lastReg.gain),List(lastOut.signal,lastOut.gain)))
      segments.append(new SimpleStatement(signalOut.signal,lastReg.signal))
      segments.append(new SimpleStatement(signalOut.gain,lastReg.gain))


      // Create the Initial CSD Multiplication Stage
      segments.append(csdModule.createInstance(location,writer))
      // Create the Parallel Filter Operation
      segments.append(filterModule.createInstance(location,writer))
      // Create the Final Butterfly Addition Stage
      segments.append(adderModule.createInstance(location,writer))

      return SegmentReturn.combineReturns(writer,segments)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }


}

object CdFilterStage {
  class Params(val cdGain:Double,
               val csdWidth:FixedType,
               val csdTapWidth:FixedType,
               val csdInternalWidth:FixedType,
               val csdDelay:Int,
               val filterTaps:List[BaseConstant],
               val filterDepth:Int,
               val filterInWidth:FixedType,
               val filterOutWidth:FixedType,
               val filterInternalWidth:FixedType,
               val adderWidth:FixedType)



}