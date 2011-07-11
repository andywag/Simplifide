package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.complex._
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.module._
import com.simplifide.generator.blocks.equalizer.rotation.ParallelCordicRotation
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.basic.operator.{BinaryOperator, UnaryOperator, Concat, Select}
import com.simplifide.scala2.core.basic.{StatementSegment, SimpleMux, SimpleStatement, Comment}
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
class CdConjugate(override val name:String,
                    val location:String,
                    override val clk:FlopControl,
                    val conjugate:SignalNew,
                    val signalInGain:ComplexVectorGain,
                    val signalOutGain:ComplexVectorGain) extends ModuleSegment(name,clk) {


  override val trace:Boolean = false

  val signalIn  = signalInGain.signal
  val signalOut = signalOutGain.signal


  val internalIn   = signalIn.createInternalSignal(OpType.Signal)
  val internalOut  = signalOut.createInternalSignal(OpType.Signal)
  val internalGain = signalInGain.gain.copyAsSignalNew("internal_gain",Some(OpType.Reg),None,None)

  val conjSignal   = ComplexVectorArray.newSignal("conjugate",OpType.Signal,signalIn.getRealFixedType,signalIn.len)
  val conjReg   = ComplexVectorArray.newSignal("conjugate_reg",OpType.Signal,signalIn.getRealFixedType,signalIn.len)

   /** Returns a list of inputs for this module */
    val inputs:List[SignalNew]  = {
      val buffer = new ListBuffer[SignalNew]
      buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
      buffer.append(signalInGain.signal)
      buffer.append(signalInGain.gain)
      buffer.append(conjugate)
      buffer.toList

    }
    /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(signalOutGain.signal, signalOutGain.gain)
  
  val signals:List[SignalNew] = List(internalIn,internalOut, conjSignal, conjReg, internalGain)


    def createNegateStatment(signalIn:ComplexVectorArray,index:Int):StatementSegment = {
      val signal = signalIn.getImagSlice(index)    // Get the input signal associated with this negate operation
      val neg = new UnaryOperator.Negative(signal) //
      val wid = signalIn.getRealFixedType.width;
      val maxNeg = Constant.newIntConstant(math.pow(2.0,wid-1).toInt,wid)
      val maxPos = Constant.newIntConstant(math.pow(2.0,wid-1).toInt-1,wid)
      new SimpleMux(conjugate,new SimpleMux(new BinaryOperator.EQ(signal,maxNeg),maxPos,neg),signalIn.getImagSlice(index))
    }

    override def createCode(writer:CodeWriter):SegmentReturn = {
      val segments = new ListBuffer[BaseCodeSegment]()
      segments.append(new Comment.SingleLine("Convert the signals into individual signals"))
      segments.append(new ComplexVectorArray.ArrayToComplex(signalIn,internalIn))
      segments.append(new ComplexVectorArray.ComplexToArray(signalOut,internalOut))
      segments.append(new Comment.SingleLine("Conjugate the input signal"))
      for (i <- 0 until signalIn.len) {
        segments.append(new SimpleStatement(conjSignal.getRealSlice(i),signalIn.getRealSlice(i)))
        segments.append(new SimpleStatement(conjSignal.getImagSlice(i),createNegateStatment(signalIn,i)))
      }
      segments.append(SimpleFlopList.newFlop(clk,List(conjReg,internalGain),List(conjSignal,signalInGain.gain)))
      segments.append(new SimpleStatement(signalOut,conjReg))
      segments.append(new SimpleStatement(signalOutGain.gain,internalGain))

      return SegmentReturn.combineReturns(writer,segments)
  }




}
