package com.simplifide.generator.blocks.equalizer.filter

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.simplifide.scala2.clocks.FlopControl
import scala.collection.mutable.ListBuffer
import collection.mutable.HashMap
import com.simplifide.scala2.core.basic.{Comment, StatementSegment}
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.complex.{ComplexMultiply, ComplexVectorArray, ComplexSignal}
import com.simplifide.scala2.core.basic.fixed.FilterTree
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.module.{ModuleSegment, Instance, Module}

class ParallelFilter(override val name:String,
                     val location:String,
                     val params:ParallelFilter.Params,
                     val signal:ParallelFilter.Signals) extends ModuleSegment(name,signal.clk) {

    /** Create the Internal Input Signal for this filter */
  private val createInternalInput:ComplexSignal =
	  ComplexSignal.newComplex("internal_input", OpType.Signal,signal.input.getRealFixedType,signal.input.len)
  /** Create the Internal Output Signal for this Filter */
  private val createInternalOutput:ComplexSignal =
	  ComplexSignal.newComplex("internal_output", OpType.Signal,signal.output.getRealFixedType,signal.output.len)


  val signalInput = SignalNew.newSignal("filter_input",OpType.ModuleInput,signal.input.getRealFixedType,params.taps.size*2-1)
  val signalOutput = SignalNew.newSignal("filter_output",OpType.ModuleOutput,signal.output.getRealFixedType)

    /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
        buffer.append(signal.input)
        buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(signal.output)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(createInternalInput, createInternalOutput)

  val filterModule = {

      val signals = new ListBuffer[SignalNew]()
      for (i <- 0 until signalInput.getNumber) {
        signals.append(signalInput.getSlice(i))
      }
      // Create the Filter Tree
      val filterParams  = new FilterTree.Param(params.taps,params.intFixed,params.depth)
      val filterSigs    = new FilterTree.Signal(signal.clk,signals.toList,signalOutput)

      new FilterTree(name + "_filt",filterParams,filterSigs)
      //val model = new FilterTree.ModuleBase(name + "_filt",location,tree)
      //return model
  }


   override def getFileList:List[String] = List(filterModule.name,name)







    protected def createRealMap(index:Int):HashMap[String,String] = {

      def getTapNumber(index:Int) = (index + createInternalOutput.getNumber) % createInternalOutput.getNumber

      val center = params.taps.size-1 // Center of the filter
      // Scroll through the number of taps to get teh signals for this filter */
      val map = new HashMap[String,String]()               // Create a Map Containing the Connections
      val real_out = createInternalOutput.getComplexSlice(index).getReal            // Get

      map.put(signalOutput.getName,real_out.getName)

      val real_signals = new ListBuffer[SignalNew]()

      for (j <- -center to center) {  // Goes through length of the filter
        val sig = createInternalInput.getComplexSlice(getTapNumber(index+j))           // Real Signal
        map.put(signalInput.getSlice(j + center).getName,sig.getReal.getName)

      }
      // Create the Filter Tree
      return map
  }

      protected def createImagMap(index:Int):HashMap[String,String] = {

      def getTapNumber(index:Int) = (index + createInternalOutput.getNumber) % createInternalOutput.getNumber

      val center = params.taps.size-1 // Center of the filter
      // Scroll through the number of taps to get teh signals for this filter */
      val real_map = new HashMap[String,String]()               // Create a Map Containing the Connections
      val real_out = createInternalOutput.getComplexSlice(index).getImag            // Get

      real_map.put(signalOutput.getName,real_out.getName)

      val real_signals = new ListBuffer[SignalNew]()

      for (j <- -center to center) {  // Goes through length of the filter
        val sig = createInternalInput.getComplexSlice(getTapNumber(index+j))           // Real Signal
        real_map.put(signalInput.getSlice(j + center).getName,sig.getImag.getName)

      }
      // Create the Filter Tree
      return real_map
  }




  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = createCode(writer)

  override def createCode(writer:CodeWriter):SegmentReturn = {

    val segments = new ListBuffer[BaseCodeSegment]()
    // Conver the input/output from a vector to a list of complex signals
    segments.append(new Comment.SingleLine("Convert Vector Siganls to Individual Complex Signals"))
    segments.append(new ComplexVectorArray.ArrayToComplex(signal.input, createInternalInput))
    segments.append(new ComplexVectorArray.ComplexToArray(signal.output,createInternalOutput))


    // Create the first real filter
    val filter = new FilterTree.ModuleBase(filterModule.name,location, filterModule)
    writer.createCode(filter)

      
      for (i <- 0 until signal.input.len ) {
          val rins = new Instance(name + "_filt_re" + i,filter,createRealMap(i).toMap)
          segments.append(rins)

          val iins = new Instance(name + "_filt_im" + i,filter,createImagMap(i).toMap)
          segments.append(iins)
      }
      return SegmentReturn.combineReturns(writer,segments)

  }

 

}

object ParallelFilter {

   case class Signals(clk:FlopControl,
                      input:ComplexVectorArray,
                      output:ComplexVectorArray)

   case class Params(taps:List[BaseConstant],
                     depth:Int,
                     intFixed:FixedType)



   }