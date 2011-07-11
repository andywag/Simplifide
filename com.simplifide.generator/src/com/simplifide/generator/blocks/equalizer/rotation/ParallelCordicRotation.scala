package com.simplifide.generator.blocks.equalizer.rotation

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.generator.blocks.basic.rotation.CordicRotation
import collection.immutable.HashMap
import com.simplifide.scala2.core.basic.vars.{FixedType, SignalNew, BaseSignal, VectorArray, OpType}
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexVectorArray, ComplexSignal}
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.{SimpleStatement, Comment, StatementSegment}
import com.simplifide.generator.blocks.equalizer.Shifter
import collection.mutable.{LinkedHashMap, ListBuffer}
import com.simplifide.scala2.core.module._

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/14/11
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */

class ParallelCordicRotation(override val name:String,
                             val location:String,
                             override val clk:FlopControl,
                             val vectorIn:ComplexVectorGain,
                             val vectorAngleIn:VectorArray,
                             val vectorOut:ComplexVectorGain,
                             val internalWidth:List[FixedType],
                             val stageWidth:List[FixedType],
                             val internalAngleWidth:List[FixedType],
                             val stageNumber:Int,
                             val ranges:Int) extends ModuleSegment(name,clk) {


  override val trace:Boolean = true

  override def getFileList:List[String] = {
    val files = new ListBuffer[String]

    for (i <- 0 until stageNumber-1) {
       files.append(name + "_stage_" + i)
       files.append(name + "_shift_" + i)
    }
    files.append(name)
    return files.toList
  }

  override def getStimulusMap:Stimulus.Map = {
    val linkMap = new LinkedHashMap[BaseSignal,Stimulus]()
    clk.getResetSignal(OpType.Signal)  match {case Some(x) => linkMap.put(x,new Stimulus.Constant(0,x)); case _ => }
    clk.getEnableSignal(OpType.Signal) match {case Some(x) => linkMap.put(x,new Stimulus.Constant(1,x)); case _ => }
    linkMap.put(vectorIn.gain,new Stimulus.Constant(0,vectorIn.gain))
    linkMap.put(vectorIn.signal,new Stimulus.File("datain",vectorIn.signal))
    linkMap.put(vectorAngleIn  ,new Stimulus.File("anglein",vectorAngleIn))
    new Stimulus.Map(linkMap,this)
  }
  override def getStorageMap:Storage.Map = {
    val linkMap = new LinkedHashMap[BaseSignal,Storage]()
    linkMap.put(vectorIn.signal,new Storage.File("dataino",vectorIn.signal,true))
    linkMap.put(vectorAngleIn,new Storage.File("angleino",vectorAngleIn,true))
    linkMap.put(vectorOut.gain,new Storage.File("gain",vectorOut.gain,false))
    linkMap.put(vectorOut.signal,new Storage.File("dataout",vectorOut.signal,true))
    new Storage.Map(linkMap)
  }


  val angles:List[VectorArray] = {
    val buffer = new ListBuffer[VectorArray]()
    buffer.append(new VectorArray("angle_0",OpType.Signal,vectorAngleIn.getRealFixedType,vectorIn.signal.len))
    for (i <- 1 until stageNumber) {
      buffer.append(new VectorArray("angle_" + i,OpType.Signal,internalAngleWidth(i-1),vectorIn.signal.len))
    }
    buffer.toList
  }
  val stages:List[ComplexVectorGain] = {
    val buffer = new ListBuffer[ComplexVectorGain]()
      val sig  = new ComplexVectorArray("cordic_0",OpType.Signal,vectorIn.signal.getRealFixedType,vectorIn.signal.len)
      val gain = vectorIn.gain.copyAsSignalNew("cordic_0_gain",Some(OpType.Signal),None,None)
      buffer.append(new ComplexVectorGain(sig,gain))
    for (i <- 1 until stageNumber-1) {
      val isig  = new ComplexVectorArray("cordic_" + i,OpType.Signal,stageWidth(i-1),vectorIn.signal.len)
      val igain = vectorIn.gain.copyAsSignalNew("cordic_" + i + "_gain",Some(OpType.Signal),None,None)
      buffer.append(new ComplexVectorGain(isig,igain))
    }
      val lsig = new ComplexVectorArray("cordic_" + (stageNumber-1),OpType.Signal,vectorOut.signal.getRealFixedType,vectorOut.signal.len)
      val lgain = vectorIn.gain.copyAsSignalNew("cordic_" + (stageNumber-1).toString + "_gain"  ,Some(OpType.Signal),None,None)
      buffer.append(new ComplexVectorGain(lsig,lgain))
    buffer.toList
  }
  val stages_int:List[ComplexVectorGain] = {
    val buffer = new ListBuffer[ComplexVectorGain]()
      val sig  = new ComplexVectorArray("cordic_internal_0",OpType.Signal,vectorIn.signal.getRealFixedType,vectorIn.signal.len)
      val gain = vectorIn.gain.copyAsSignalNew("cordic_0_gain",Some(OpType.Signal),None,None)
      buffer.append(new ComplexVectorGain(sig,gain))
    for (i <- 1 until stageNumber) {
      val isig  = new ComplexVectorArray("cordic_internal_" + i,OpType.Signal,internalWidth(i-1),vectorIn.signal.len)
      val igain = vectorIn.gain.copyAsSignalNew("cordic_" + i + "_gain",Some(OpType.Signal),None,None)
      buffer.append(new ComplexVectorGain(isig,igain))
    }
    //  val lsig = new ComplexVectorArray("cordic_internal_" + (stageNumber-1),OpType.Signal,vectorOut.signal.getRealFixedType,vectorOut.signal.len)
    //  val lgain = vectorIn.gain.copyAsSignalNew("cordic_internal_" +(stageNumber-1).toString + "_gain" ,Some(OpType.Signal),None,None)
    //  buffer.append(new ComplexVectorGain(lsig,lgain))
    buffer.toList
  }

  val inputs:List[SignalNew] = {
    val buffer = new ListBuffer[SignalNew]
     buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
     buffer.append(vectorIn.signal)
     buffer.append(vectorIn.gain)
     buffer.append(vectorAngleIn)
     buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(vectorOut.signal,vectorOut.gain)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = stages.map(x => x.signal) ::: stages.map(x => x.gain) ::: stages_int.map(x => x.signal) :::  angles

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val segments = new ListBuffer[BaseCodeSegment]()
    // Attach the inputs and the outputs
    
    segments.append(new Comment.SingleLine("Connect the input to the internal version"))
    segments.append(new SimpleStatement(stages(0).signal,vectorIn.signal))
    segments.append(new SimpleStatement(stages(0).gain,vectorIn.gain))
    segments.append(new Comment.SingleLine("Connect the input angle to the internal version"))
    segments.append(new SimpleStatement(angles(0),vectorAngleIn))
    segments.append(new Comment.SingleLine("Connect the output to the internal version"))
    segments.append(new SimpleStatement(vectorOut.signal,stages(stageNumber-1).signal))
    segments.append(new SimpleStatement(vectorOut.gain,stages(stageNumber-1).gain))

    segments.append(new Comment.SingleLine("First Two Cordic Stages"))


    for (i <- 0 until stageNumber - 1) {
      // Cordic Stage
      segments.append(new Comment.SingleLine("Cordic Stage " + i))
      val nam  = name + "_stage_" + i
      val cord = new ParallelCordicStage(nam,clk,stages(i).signal,angles(i),stages_int(i).signal,angles(i+1),internalWidth(i),internalAngleWidth(i),i)
      val mod  = new Module.Segment(nam,location,cord)
      val ins  = new Instance(nam,mod,new HashMap())
      writer.createCode(mod)
      segments.append(ins)
      // Shift Stage
      segments.append(new Comment.SingleLine("Cordic Rotation Stage " + i))
      val cordName = name + "_shift_" + i
      val shift = new Shifter(nam,clk,stages_int(i),stages(i+1),ranges,0)
      val cordMod = new Module.Segment(cordName,location,shift)
      val cordIns = new Instance(cordName,cordMod,new HashMap())
      writer.createCode(cordMod)
      segments.append(cordIns)

    }

    return SegmentReturn.combineReturns(writer,segments)

  }


}
