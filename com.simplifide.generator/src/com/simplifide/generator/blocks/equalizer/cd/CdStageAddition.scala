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
import com.simplifide.scala2.core.basic.operator.Select

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

class CdStageAddition(override val name:String,
                      val location:String,
                      override val clk:FlopControl,
                      val signalIn0:ComplexVectorArray,
                      val signalIn1:ComplexVectorArray,
                      val signalOut:ComplexVectorArray) extends ModuleSegment(name,clk) {



  val internalIn0  = signalIn0.createInternalSignal(OpType.Signal)
  val internalIn1  = signalIn1.createInternalSignal(OpType.Signal)

  val internalOut = signalOut.createInternalSignal(OpType.Signal)


  /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
        buffer.append(signalIn0)
        buffer.append(signalIn1)
        buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(signalOut)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(internalIn0,internalIn1,internalOut)




    override def createCode(writer:CodeWriter):SegmentReturn = {

    val segments = new ListBuffer[BaseCodeSegment]()

    segments.append(new Comment.SingleLine("Convert the signals into individual signals"))
    segments.append(new ComplexVectorArray.ArrayToComplex(signalIn0,internalIn0))
    segments.append(new ComplexVectorArray.ArrayToComplex(signalIn1,internalIn1))

    segments.append(new ComplexVectorArray.ComplexToArray(signalOut,internalOut))

    // Create the Initial Delay Stage

    val inp = internalIn0
    val regp = internalIn1
    val outp = internalOut
    val intWidth = internalIn0.getRealFixedType
    // Create the First Adder Stages
    // Consists of [1 j -1 -1] and [1 -j -1 j] for the input and delayed values
    for (i <- 0 until inp.getNumber/4) {
      // First Segment (1,1)
      val j = 4*i
      val adderR0:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(j).getReal,
          inp.getComplexSlice(j).getReal,
          regp.getComplexSlice(j).getReal,false,false,intWidth)
      val adderI0:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(j).getImag,
        inp.getComplexSlice(j).getImag,
        regp.getComplexSlice(j).getImag,false,false,intWidth)
      // Second Segment (j,-j)
      val k = 4*i + 1
      val adderR1:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(k).getReal,
        inp.getComplexSlice(k).getImag,
        regp.getComplexSlice(k).getImag,false,true,intWidth)
      val adderI1:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(k).getImag,
        inp.getComplexSlice(k).getReal,
        regp.getComplexSlice(k).getReal,true,false,intWidth)
      // Third Segment (1,-1)
      val m = 4*i + 2
      val adderR2:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(m).getReal,
        inp.getComplexSlice(m).getReal,
        regp.getComplexSlice(m).getReal,true,true,intWidth)
      val adderI2:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(m).getImag,
        inp.getComplexSlice(m).getImag,
        regp.getComplexSlice(m).getImag,true,true,intWidth)
      // Forth Segment (1,-1)
      val n = 4*i + 3
      val adderR3:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(n).getReal,
        inp.getComplexSlice(n).getImag,
        regp.getComplexSlice(n).getImag,true,false,intWidth)
      val adderI3:Statement = AdditionStatement.createRoundClip(outp.getComplexSlice(n).getImag,
        inp.getComplexSlice(n).getReal,
        regp.getComplexSlice(n).getReal,false,true,intWidth)
      segments.appendAll(List(adderR0,adderI0,adderR1,adderI1,adderR2,adderI2,adderR3,adderI3))

    }

	  return SegmentReturn.combineReturns(writer,segments)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }


}

