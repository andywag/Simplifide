package com.simplifide.generator.blocks.equalizer.rotation

import com.simplifide.scala2.core.module.ModuleSegment
import com.simplifide.scala2.clocks.FlopControl
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.Comment
import com.simplifide.scala2.core.basic.vars.{VectorArray, OpType, FixedType, SignalNew}
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexSignal, ComplexVectorArray}
import com.simplifide.generator.blocks.basic.rotation.{CordicFirstStage, CordicStage}
import com.simplifide.scala2.core.basic.flop.SimpleFlopList

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/9/11
 * Time: 7:13 PM
 * To change this template use File | Settings | File Templates.
 */

class ParallelCordicStage(override val name:String,
                          override val clk:FlopControl,
                          val vectorIn:ComplexVectorArray,
                          val vectorAngleIn:VectorArray,
                          val vectorOut:ComplexVectorArray,
                          val vectorAngleOut:VectorArray,
                          val internal:FixedType,
                          val angleInternal:FixedType,
                          val stageNumber:Int) extends ModuleSegment(name,clk) {

  val inputAngle  = SignalNew.newSignal("internal_input_angle",OpType.Signal,vectorAngleIn.getRealFixedType,vectorAngleIn.len)
  val outputAngle  = SignalNew.newSignal("internal_output_angle",if (stageNumber == 0) OpType.Reg else OpType.Signal,vectorAngleOut.getRealFixedType,vectorAngleOut.len)
  val inputSignal = ComplexSignal.newComplex("internal_input",OpType.Signal,vectorIn.getRealFixedType,vectorIn.len)
  val outputSignal = ComplexSignal.newComplex("internal_output",if (stageNumber == 0) OpType.Reg else OpType.Signal,vectorOut.getRealFixedType,vectorOut.len)

  val vectorAngleInternal = new VectorArray("internal_angle_out",OpType.Signal,vectorAngleOut.getRealFixedType,vectorAngleOut.len)

    /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
    val buffer = new ListBuffer[SignalNew]
     buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
     buffer.append(vectorIn)
     buffer.append(vectorAngleIn)
     buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(vectorOut,vectorAngleOut.copyWithType(OpType.ModuleRegOutput))
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(inputAngle,outputAngle,inputSignal,outputSignal, vectorAngleInternal)

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val segments = new ListBuffer[BaseCodeSegment]()
    //returns.append(SegmentReturn.segment(debugCSD))
    segments.append(new Comment.SingleLine("Convert the input to the individual complex signals"))
    segments.append(new ComplexVectorArray.ArrayToComplex(vectorIn, inputSignal))
    segments.append(new Comment.SingleLine("Convert the complex outputs to a single signal"))
    segments.append(new ComplexVectorArray.ComplexToArray(vectorOut, outputSignal))
    segments.append(new Comment.SingleLine("Convert the input angle to the individual single"))
    segments.append(new VectorArray.ArrayToSignal(vectorAngleIn, inputAngle))
    segments.append(new Comment.SingleLine("Convert the output angle to the individual single"))
    segments.append(new VectorArray.SignalToArray(vectorAngleInternal, outputAngle))

    for (i <- 0 until vectorIn.len) {
      segments.append(new Comment.SingleLine("Perform the Rotation for the " + i + "th input"))
      val nam = name + "_stage_" + i

      val cordic = if (stageNumber > 0) {
          new CordicStage(nam,
                          inputSignal.getSlice(i).asInstanceOf[ComplexSignal],
                          inputAngle.getSlice(i),
                          outputSignal.getSlice(i).asInstanceOf[ComplexSignal],
                          outputAngle.getSlice(i),
                          internal,
                          angleInternal,
                          stageNumber)
      }
      else {
          new CordicFirstStage(nam,
                          inputSignal.getSlice(i).asInstanceOf[ComplexSignal],
                          inputAngle.getSlice(i),
                          outputSignal.getSlice(i).asInstanceOf[ComplexSignal],
                          outputAngle.getSlice(i))
      }
      segments.append(cordic)
    }
    // Create the output flop for the angle
    segments.append(SimpleFlopList.newFlop(clk,List(vectorAngleOut),List(vectorAngleInternal)))


    return SegmentReturn.combineReturns(writer,segments)
  }

}