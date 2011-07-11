package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.complex.{ComplexConstant, ComplexVectorArray}
import collection.immutable.HashMap
import com.simplifide.scala2.core.complex._
import com.simplifide.scala2.command.{Command, CommandCodeSegment}
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.generator.blocks.equalizer.Shifter
import com.simplifide.generator.blocks.equalizer.filter.{ParallelFilterGain, ParallelFilter}
import com.simplifide.generator.blocks.gain.ParallelCSDMultipliciationGain
import collection.mutable.{LinkedHashMap, ListBuffer}
import com.simplifide.scala2.core.module._
import com.simplifide.generator.blocks.equalizer.rotation.ParallelCordicRotation
import com.simplifide.scala2.core.basic.{SimpleMux, SimpleStatement, StatementSegment, Comment}
import com.simplifide.scala2.core.basic.flop.SimpleFlopList
import com.simplifide.scala2.core.basic.operator.{Concat, Select}
import com.simplifide.scala2.core.basic.vars.FixedTypeMain

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
class CdFilterStageTop(override val name:String,
                    val location:String,
                    override val clk:FlopControl,
                    val signalIn:ComplexVectorGain,
                    val signalOut:ComplexVectorGain,
                    val filterStageProject:Project.Basic[CdFilterStage],
                    val cordicProject:Project.Basic[ParallelCordicRotation],
                    val numStages:Int,
                    val zeroTones:Int) extends ModuleSegment(name,clk) {


  override val trace:Boolean = false

    val stageOutput = {
      val sigs = new ListBuffer[ComplexVectorGain]
      for (i <- 0 until numStages) {
        val gain = ComplexVectorGain.newSignal("stageOutput_" + i,OpType.Signal,signalIn.fixed,signalIn.len,signalIn.gain.fixed)
        sigs.append(gain)
      }
      sigs.toList
    }

    val stageInput = {
      val sigs = new ListBuffer[ComplexVectorGain]
      for (i <- 0 until numStages) {
        val gain = ComplexVectorGain.newSignal("stageInput_" + i,OpType.Signal,signalIn.fixed,signalIn.len,signalIn.gain.fixed)
        sigs.append(gain)
      }
      sigs.toList
    }



    val bypass = SignalNew.newSignal("bypass_stage",OpType.ModuleInput,FixedTypeMain.unsigned(numStages,0))


    /** Returns a list of inputs for this module */
    val inputs:List[SignalNew]  = {
      val buffer = new ListBuffer[SignalNew]
      buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
      buffer.append(signalIn.signal)
      buffer.append(signalIn.gain)
      buffer.append(cordicProject.moduleSegment.vectorAngleIn)
      buffer.append(bypass)
      buffer.toList

    }
    /** Returns a list of outputs for this module */
    val outputs:List[SignalNew] = List(signalOut.signal, signalOut.gain)
    /** Returns a list of internal signals for this module */
    val signals:List[SignalNew] = stageOutput.map(x => x.signal) ::: stageOutput.map(x => x.gain) ::: stageInput.map(x => x.signal) ::: stageInput.map(x => x.gain)

    override val projects = List(filterStageProject, cordicProject)




    private def zeroInputVector(output:ComplexVectorGain, inputVectorGain:ComplexVectorGain):List[SimpleStatement] ={
      val inputVector = inputVectorGain.signal
      val halfGap    = 2*zeroTones*inputVector.getRealFixedType.width // 2 for gap, 2 for complex
      val top        = Select.newSelect(inputVector,inputVector.fixed.width-1,inputVector.fixed.width/2 + halfGap)
      val mid        = Constant.newIntConstant(value = 0, width = 2*halfGap)
      val bot        = Select.newSelect(inputVector,inputVector.fixed.width/2-1 - halfGap,0)
      val cat        = Concat.newConcat(List(top,mid,bot))

      val state1     = new SimpleStatement(output.signal,cat)
      val state2     = new SimpleStatement(output.gain,inputVectorGain.gain)

      return List(state1,state2)

    }


    override def createCode(writer:CodeWriter):SegmentReturn = {
      val segments = new ListBuffer[BaseCodeSegment]()
      segments.append(new Comment.SingleLine("Attach the Input to the stage input "))
      /*segments.append(new SimpleStatement(stageInput(0).signal,signalIn.signal))
      segments.append(new SimpleStatement(stageInput(0).gain,signalIn.gain))
      for (i <- 1 until numStages) {
        segments.append(new SimpleStatement(stageInput(i).signal,stageOutput(i-1).signal))
        segments.append(new SimpleStatement(stageInput(i).gain,stageOutput(i-1).gain))
      }
      segments.append(new SimpleStatement(signalOut.signal,stageOutput(numStages-1).signal))
      segments.append(new SimpleStatement(signalOut.gain  ,stageOutput(numStages-1).gain))
      */
      segments.appendAll(zeroInputVector(stageInput(0),signalIn))
      for (i <- 1 until numStages) {
        segments.appendAll(zeroInputVector(stageInput(i),stageOutput(i-1)))
      }
      segments.appendAll(zeroInputVector(signalOut,stageOutput(numStages-1)))




      val filterStage = filterStageProject.moduleSegment
      for (i <- 0 until numStages-1) {
        val map = Map(filterStage.bypass.name           -> bypass.simpleSlice(i),
                      filterStage.signalIn.signal.name  -> stageInput(i).signal.name,
                      filterStage.signalIn.gain.name    -> stageInput(i).gain.name,
                      filterStage.signalOut.signal.name -> stageOutput(i).signal.name,
                      filterStage.signalOut.gain.name   -> stageOutput(i).gain.name)
        segments.append(new Comment.SingleLine("CD Filter Stage " + i))
        segments.append(filterStage.createConnection("stage_" + i,map))
      }
      val cordic = cordicProject.moduleSegment
      segments.append(new Comment.SingleLine("Final Cordic Stage"))
      val map = Map(cordic.vectorIn.signal.name  ->  stageInput(numStages-1).signal.name,
                    cordic.vectorIn.gain.name    ->  stageInput(numStages-1).gain.name,
                    cordic.vectorOut.signal.name ->  stageOutput(numStages-1).signal.name,
                    cordic.vectorOut.gain.name   ->  stageOutput(numStages-1).gain.name)
      segments.append(cordic.createConnection("cordic",map))




      return SegmentReturn.combineReturns(writer,segments)
  }




}
