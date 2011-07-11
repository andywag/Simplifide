package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.core.basic.fixed.AdditionStatement
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.{ListBuffer, HashMap}
import com.simplifide.generator.blocks.equalizer.filter.ParallelFilter
import com.simplifide.scala2.core.basic.flop.{SimpleFlopList,SimpleFlop}
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexSignal, ComplexConstant, ComplexVectorArray}
import com.simplifide.scala2.core.module.{ModuleSegment, Module, Instance}
import com.simplifide.scala2.core.basic.{SimpleMux, Statement, Comment, StatementSegment}
import com.simplifide.scala2.core.basic.operator.{UnaryOperator, BarrelShifter, Select}
import com.simplifide.generator.blocks.OpticalConstants

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/10/11
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */

/** Segment which defines the chromatic dispersion filter stage
  * This is the final addition which combines the data at the output of the
  * cd filter segment
  */

class CdStageAdditionScale(override val name:String,
                      val location:String,
                      override val clk:FlopControl,
                      val signalIn:ComplexVectorGain,
                      val delayOut:ComplexVectorArray,
                      val currentOut:ComplexVectorArray,
                      val gainOut:SignalNew) extends ModuleSegment(name,clk) {


  val registerVector:ComplexVectorGain =
    ComplexVectorGain.newSignal("delay",OpType.Reg,signalIn.signal.getRealFixedType,signalIn.signal.len,signalIn.gain.getRealFixedType)


  val gainDiff = SignalNew.newSignal("gain_diff",OpType.Signal,FixedTypeMain.signed(signalIn.gain.getFixedType))
  val absgainDiff = SignalNew.newSignal("abs_gain_diff",OpType.Signal,FixedTypeMain.signed(signalIn.gain.getFixedType))

  val strongVector     = ComplexVectorArray.newSignal("strong_signal",OpType.Signal,signalIn.signal.getRealFixedType,signalIn.signal.len)
  val weakVector       = ComplexVectorArray.newSignal("weak_signal",OpType.Signal,signalIn.signal.getRealFixedType,signalIn.signal.len)

  val strongSignal     = strongVector.createInternalSignal(OpType.Signal)
  val weakSignal       = weakVector.createInternalSignal(OpType.Signal)

  val strongOutVector  = ComplexVectorArray.newSignal("strong_out",OpType.Signal,delayOut.getRealFixedType,signalIn.signal.len)
  val weakOutVector    = ComplexVectorArray.newSignal("weak_out",OpType.Signal,delayOut.getRealFixedType,signalIn.signal.len)

  val strongOutSignal  = strongOutVector.createInternalSignal(OpType.Signal)
  val weakOutSignal    = weakOutVector.createInternalSignal(OpType.Signal)


  /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
        buffer.append(signalIn.signal)
        buffer.append(signalIn.gain)
        buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(delayOut,currentOut,gainOut)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(registerVector.signal, registerVector.gain, gainDiff, absgainDiff, strongVector, weakVector, strongSignal,
                                     weakSignal, strongOutVector, weakOutVector, strongOutSignal, weakOutSignal)


  override def getFileList = List(name + "_w_sh",name + "_s_sh",name)

    override def createCode(writer:CodeWriter):SegmentReturn = {

      val segments = new ListBuffer[BaseCodeSegment]()
    // Initial Input Delay
    segments.append(new Comment.SingleLine("Delay the input data by 1 sample"))
    segments.append(SimpleFlopList.newFlop(clk,List(registerVector.signal,registerVector.gain),List(signalIn.signal, signalIn.gain)))
    // Calculate Gain Difference
    segments.append(new Comment.SingleLine("Calculate the gain differences between the signal and delayed signal"))
    segments.append(AdditionStatement.createTrunc(gainDiff,registerVector.gain,signalIn.gain,false,true, gainDiff.getRealFixedType))
    segments.append(SimpleMux.statement(absgainDiff,Select.sign(gainDiff),new UnaryOperator.Negative(gainDiff),gainDiff))

    // Split the Weak and Strong Signals
    segments.append(new Comment.SingleLine("Split the signal into strong and weak signal"))
    segments.append(SimpleMux.statement(strongVector,Select.sign(gainDiff),signalIn.signal,registerVector.signal))
    segments.append(SimpleMux.statement(weakVector,  Select.sign(gainDiff),registerVector.signal,signalIn.signal))
    segments.append(SimpleMux.statement(gainOut,Select.sign(gainDiff),signalIn.gain,registerVector.gain))
    // Convert the Strong and Weak signal to internal values
    segments.append(new Comment.SingleLine("Convert the signals into individual signals"))
    segments.append(new ComplexVectorArray.ArrayToComplex(strongVector,strongSignal))
    segments.append(new ComplexVectorArray.ArrayToComplex(weakVector,weakSignal))
    segments.append(new Comment.SingleLine("Convert the outputs"))
    segments.append(new ComplexVectorArray.ComplexToArray(strongOutVector,strongOutSignal))
    segments.append(new ComplexVectorArray.ComplexToArray(weakOutVector,weakOutSignal))
    // Select the outputs
    segments.append(new Comment.SingleLine("Convert the signals to the appropriate versions"))
    segments.append(SimpleMux.statement(currentOut,Select.sign(gainDiff),weakOutVector,strongOutVector))
    segments.append(SimpleMux.statement(delayOut,Select.sign(gainDiff),strongOutVector,weakOutVector))


    // Create the Barrel Shifter Module
    val weakShift   = new BarrelShifter(name + "_w_sh",clk,weakSignal.getReal,weakOutSignal.getReal,absgainDiff,OpticalConstants.ADDERSHIFT)
    val weakMod     = weakShift.createModule(location,writer)
    val strongShift = new BarrelShifter(name + "_s_sh",clk,strongSignal.getReal,strongOutSignal.getReal,absgainDiff,0)
    val strongMod   = strongShift.createModule(location,writer)


    for (i <- 0 until signalIn.signal.len) {
      val weakRmap =  Map(weakShift.signalIn.getName   ->  weakSignal.getComplexSlice(i).getReal.getName,
                                 weakShift.signalOut.getName  -> weakOutSignal.getComplexSlice(i).getReal.getName)
      segments.append(new Instance("shift_w_r_" + i,weakMod,weakRmap))
      val weakImap =  Map(weakShift.signalIn.getName   ->  weakSignal.getComplexSlice(i).getImag.getName,
                                 weakShift.signalOut.getName  -> weakOutSignal.getComplexSlice(i).getImag.getName)
      segments.append(new Instance("shift_w_i_" + i,weakMod,weakImap))
      val strongkRmap =  Map(strongShift.signalIn.getName ->  strongSignal.getComplexSlice(i).getReal.getName,
                                 strongShift.signalOut.getName   -> strongOutSignal.getComplexSlice(i).getReal.getName)
      segments.append(new Instance("shift_s_r_" + i,strongMod,strongkRmap))
      val strongImap =  Map(strongShift.signalIn.getName ->  strongSignal.getComplexSlice(i).getImag.getName,
                                 strongShift.signalOut.getName  -> strongOutSignal.getComplexSlice(i).getImag.getName)
      segments.append(new Instance("shift_s_i_" + i,strongMod,strongImap))
    }




	  return SegmentReturn.combineReturns(writer,segments)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }


}

object CdStageAdditionScale {
  val RANGE = 5;

  class Params(val intWidth:FixedType)



}