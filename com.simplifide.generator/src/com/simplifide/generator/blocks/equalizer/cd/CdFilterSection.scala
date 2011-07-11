package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.core.complex.ComplexVectorArray
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.vars.{SignalNew, BaseSignal,OpType}
import collection.mutable.ListBuffer
import collection.immutable.HashMap
import com.simplifide.scala2.core.module.{Instance, Module}
import com.simplifide.scala2.core.basic.{Statement, StatementSegment}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/15/11
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 *
 */
class CdFilterSection(val name:String,
                      val location:String,
                      val params:CdFilterSection.Params,
                      val signals:CdFilterSection.Signals) extends StatementSegment {

   def getInternalSignals:List[ComplexVectorArray] = {
       val sigs = new ListBuffer[ComplexVectorArray]()
       for (i <- 0 until params.stages) {
         val sig = new ComplexVectorArray("internal_" + i,OpType.Signal,signals.input.getFixedType,signals.input.len)
         sigs.append(sig)
       }
       return sigs.toList
   }

   override def createCode(writer:CodeWriter):SegmentReturn = {
      val segments = new ListBuffer[SegmentReturn]()
      // Create the Module
      val filter_name = name + "_filter"
      val internal_signals = getInternalSignals
      val sig = new CdFilterSection.Signals(signals.clk,internal_signals(0),internal_signals(1))
      val filt = new CdFilterSection(filter_name,location,params,sig)
      val filt_mod = new CdFilterSection.ModuleBase(filter_name,location,filt)
      writer.createCode(filt_mod)

      val in_statement  = new Statement(internal_signals(0),signals.input)
      val out_statement = new Statement(signals.output,internal_signals(params.stages-1))
      segments.append(writer.createCode(in_statement))
      segments.append(writer.createCode(out_statement))
      // Create the Module Instances
      for (i <- 0 until params.stages) {
        val map = HashMap(internal_signals(0).getName -> internal_signals(i).getName,
                          internal_signals(1).getName -> internal_signals(i+1).getName)
        val ins = new Instance(filter_name,filt_mod,map)
        segments.append(writer.createCode(ins))

      }
      return SegmentReturn.combineReturns(segments.toList)
   }

   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = createCode(writer)


}

object CdFilterSection {


  class Params( val stages:Int,
                     val extensionParam:CdFilterStage.Params)

  class Signals(val clk:FlopControl,
                     val input:ComplexVectorArray,
                     val output:ComplexVectorArray)

  class ModuleBase(override val name:String,
                         override val location:String,
                         val filter:CdFilterSection) extends Module(name,location) {

     override def getInputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(filter.signals.clk.getAllSignals(OpType.ModuleInput))
        buffer.append(filter.signals.input)
        return buffer.toList
      }

      override def getOutputs:List[SignalNew]         = List(filter.signals.output)

      override def getSignals:List[SignalNew]         = filter.getInternalSignals

      override def getSegments:List[StatementSegment] = List(filter)


    }

}