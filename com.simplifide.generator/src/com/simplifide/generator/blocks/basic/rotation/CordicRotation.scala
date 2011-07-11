package com.simplifide.generator.blocks.basic.rotation

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.complex.ComplexSignal
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.{SegmentReturn, CodeWriter}
import com.simplifide.scala2.core.basic.fixed.function.QuadRotateFunction
import com.simplifide.scala2.core.basic.vars.{SignalNew, BaseSignal, FixedType ,OpType, VectorType}
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.flop.SimpleFlopList
import com.simplifide.scala2.core.basic.Comment
import com.simplifide.scala2.core.module.Module

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/13/11
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */

class CordicRotation(val name:String,
                     val param:CordicRotation.Params,
                     val signals:CordicRotation.Signals) extends StatementSegment {

  override def createCode(writer:CodeWriter):SegmentReturn = {

    val returns = new ListBuffer[SegmentReturn]()

    // Create the Internal Signals
    val quadOut   = new ComplexSignal(new SignalNew("quad_out",OpType.Signal,signals.input.getFixedType,VectorType.NoVector))
    val quadAngle = new SignalNew("quad_angle",OpType.Signal,signals.angle.getFixedType,VectorType.NoVector)
    val quadOutR   = new ComplexSignal(new SignalNew("quad_out_r",OpType.Signal,signals.input.getFixedType,VectorType.NoVector))
    val quadAngleR = new SignalNew("quad_angle_R",OpType.Signal,signals.angle.getFixedType,VectorType.NoVector)

    val stAngle = SignalNew.newSignal("st_angle",OpType.Signal,param.angleWidth,param.stages)
    val stValue = ComplexSignal.newComplex("st_value",OpType.Signal,param.internalWidth,param.stages)
    val stAngleR = SignalNew.newSignal("str_angle",OpType.Signal,param.angleWidth,param.delays)
    val stValueR = ComplexSignal.newComplex("str_value",OpType.Signal,param.internalWidth,param.delays)

    val internalSignals = List(quadOut,quadAngle,quadOutR,quadAngleR,stValue,stValueR,stAngle,stAngleR)
    val decs = internalSignals.flatMap(x => x.getSignalDeclaration)
    returns.appendAll(decs.map(x => writer.createCode(x)))

    // Create the Initial Quadrant Rotation

    val quadComment = new Comment.SingleLine("Quadrant Rotation")
    val quad = new QuadRotateFunction(signals.input.getReal,signals.input.getImag,signals.angle,
                                      quadOut.getReal,quadOut.getImag,quadAngle)
    val quadFlop = SimpleFlopList.newFlop(signals.clk,List(quadOutR.getReal,quadOutR.getImag,quadAngleR),List(quadOut.getReal,quadOut.getImag,quadAngle))

    returns.append(writer.createCode(quadComment))
    returns.append(writer.createCode(quad))
    returns.append(writer.createCode(quadFlop))

    // Create the First Cordic Stage
    val stage0 = new CordicStage("stage_" + 0,quadOutR,quadAngleR,stValue.getComplexSlice(0),stAngle.getSlice(0),param.internalWidth,param.angleWidth,0)
    val stage0Flop  = SimpleFlopList.newFlop(signals.clk, List(stValueR.getComplexSlice(0).getReal,stValueR.getComplexSlice(0).getImag,stAngle.getSlice(0)),
      List(stValue.getComplexSlice(0).getReal,stValue.getComplexSlice(0).getImag,stAngleR.getSlice(0)) )

    returns.append(writer.createCode(stage0))
    returns.append(writer.createCode(stage0Flop))

    // Create the Other Cordic Stages
    for (i <- 1 until param.stages) {
       val stage = new CordicStage("stage_" + i,stValueR.getComplexSlice(i-1),stAngleR.getSlice(i-1),stValue.getComplexSlice(i),stAngle.getSlice(i),param.internalWidth,param.angleWidth,0)
       val stageFlop  = SimpleFlopList.newFlop(signals.clk, List(stValueR.getComplexSlice(i).getReal,stValueR.getComplexSlice(i).getImag,stAngle.getSlice(i)),
          List(stValue.getComplexSlice(i).getReal,stValue.getComplexSlice(i).getImag,stAngleR.getSlice(i)) )
       returns.append(writer.createCode(stage))
       returns.append(writer.createCode(stageFlop))
    }

    return SegmentReturn.combineReturns(returns.toList)

  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }


}




object CordicRotation {
  /** Parameters for Cordic Rotation */
  class Params(val stages:Int,
               val delays:Int,
               val internalWidth:FixedType,
               val angleWidth:FixedType) {

  }
  /** Signals For Cordic Rotation */
  class Signals(val clk:FlopControl,
                val input:ComplexSignal,
                val angle:SignalNew,
                val output:ComplexSignal) {

  }


   class ModuleBase(override val name:String,
                         override val location:String,
                                  val cordic:CordicRotation) extends Module(name,location) {

     override def getInputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(cordic.signals.clk.getAllSignals(OpType.ModuleInput))
        buffer.append(cordic.signals.input.getReal)
        buffer.append(cordic.signals.input.getImag)
        buffer.append(cordic.signals.angle)
        return buffer.toList
      }

      override def getOutputs:List[SignalNew]         = List(cordic.signals.output.getReal,cordic.signals.output.getImag)

      override def getSignals:List[SignalNew]         = List()

      override def getSegments:List[StatementSegment] = List(cordic)



}


}