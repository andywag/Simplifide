/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.complex

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.fixed.AdderTree
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.{Comment, StatementSegment}
import com.simplifide.scala2.core.module.Module
import com.simplifide.scala2.core.basic.vars._

class ParallelComplexCSDMultiply(val name:String,
                                 val param:ParallelComplexCSDMultiply.Params,
                                 val signal:ParallelComplexCSDMultiply.Signals) extends StatementSegment{


  def createInternalInput:ComplexSignal =
	  ComplexSignal.newComplex("internal_input",OpType.Signal, signal.input.getRealFixedType,signal.input.len)

  def createInternalOutput:ComplexSignal =
	  ComplexSignal.newComplex("internal_output",OpType.Signal, signal.output.getRealFixedType,signal.output.len)

  def getInternalSignals:List[SignalNew] = List(createInternalInput,createInternalOutput)

  private def createComplexComment(mag:Boolean):StatementSegment = {
    val builder = new StringBuilder()
    builder.append("Taps [")
    if (mag)  signal.taps.map(x => builder.append(" " + x.magPhaseString))
    else      signal.taps.map(x => builder.append(" " + x.complexString))
    builder.append("]")
    new Comment.SingleLine(builder.toString)

  }

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val returns = new ListBuffer[SegmentReturn]()

    returns.append(writer.createCode(createComplexComment(false)))
    returns.append(writer.createCode(createComplexComment(true)))
    returns.append(writer.createCode(new Comment.SingleLine("Convert Vector Siganls to Individual Complex Signals")))
    val arr2vec = new ComplexVectorArray.ArrayToComplex(signal.input, createInternalInput)
    val vec2arr = new ComplexVectorArray.ComplexToArray(signal.output,createInternalOutput)
    returns.append(writer.createCode(arr2vec))
    returns.append(writer.createCode(vec2arr))

    for (i <- 0 until signal.input.len) {
      val comment = new Comment.SingleLine("Multiplier " + i)
      val mult = new ComplexCSDMultiply(name+ "_" + i,
                                        signal.clk,
                                        signal.taps(i),
                                        createInternalInput.getSlice(i).asInstanceOf[ComplexSignal],
                                        createInternalOutput.getSlice(i).asInstanceOf[ComplexSignal],
                                        param.internal,
                                        param.delays,
                                        param.flop)
      returns.append(writer.createCode(comment))
      returns.append(writer.createCode(mult))
    }

    return SegmentReturn.combineReturns(returns.toList, List())
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCode(writer)
  }
  
}

object ParallelComplexCSDMultiply {

  /** Parameters for the Complex Multiply */
  class Params(val internal:FixedType,           // Internal Width for the Multiplication
               val delays:Int,
               val flop:Boolean)                   // Number of delays for the CSD Multiply

  /** Signals for the Complex Multiply */
  class Signals(val clk:FlopControl,              // Flop Control
                val input:ComplexVectorArray,     // Input Signal
                val output:ComplexVectorArray,    // Output Signal
                val taps:List[ComplexConstant])   // List of Taps

  class ModuleBase(override val name:String,
                           override val location:String,
                           val multiply:ParallelComplexCSDMultiply) extends Module(name,location) {

       override def getInputs:List[SignalNew] = {
          val buffer = new ListBuffer[SignalNew]
          buffer.appendAll(multiply.signal.clk.getAllSignals(OpType.ModuleInput))
          buffer.append(multiply.signal.input)
          return buffer.toList
        }

        override def getOutputs:List[SignalNew]         = List(multiply.signal.output)

        override def getSignals:List[SignalNew]         = multiply.getInternalSignals

        override def getSegments:List[StatementSegment] = List(multiply)


      }

}