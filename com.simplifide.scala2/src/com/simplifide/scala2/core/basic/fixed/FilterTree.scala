package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.clocks.FlopControl
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.module.Module
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/8/11
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */

class FilterTree (val name:String,
                  val param:FilterTree.Param,
                  val signal:FilterTree.Signal)  extends StatementSegment.Simple  {

  /** Creates the set of signals associated with the first section of the filter addition */
  val createFirstStage:SignalNew =
	  new SignalNew(name+"_st1",OpType.Signal,param.internal,new VectorType(List(param.taps.size),None))
  /** Returns the internal signals associated with this block */
  def getInternalSignals:List[SignalNew] = List(createFirstStage)



  override def createCode(writer:CodeWriter):SegmentReturn = {
    // Adder Creation
    def createAdder(index:Int):AdditionSegment = {
      val center = math.floor(signal.input.size.toFloat/2.0).toInt
      val first  = new AdditionTerm.Empty(signal.input(center - index))
      val second = new AdditionTerm.AddTerm(signal.input(center + index))
      if (index == 0)  return new AdditionSegment.RoundClip(List(first),param.internal)
      else             return new AdditionSegment.RoundClip(List(first,second),param.internal)

    }
    val segments = new ListBuffer[BaseCodeSegment]()

    // Signal Declarations
    val sigs = this.createFirstStage

    // Create the First Adder Stage and create the adder tree nodes
    val tap_values = new ListBuffer[AdderTree.Value]()
    val len = param.taps.size
    for (i <- 0 until len) {
      if (param.taps(i) != 0) {  // Only create the adder if the tap != 0
        // Create the First Adder
        val add = new Statement(sigs.getSlice(i),createAdder(i))
        segments.append(add)
        // Create the adder tree tap values
        val tr = new AdderTree.Value(param.taps(len-i-1),sigs.getSlice(i))
        tap_values.append(tr)
      }
    }
    // Adder Tree
    val tree = new AdderTree(name,signal.clk,tap_values.toList,param.internal,param.depth)
    val treeSeg = new AdderTree.Normal(tree,signal.output)
    segments.append(treeSeg)
    return SegmentReturn.combineReturns(writer,segments)
  }

}

object FilterTree {

   class Param(val taps:List[BaseConstant],
               val internal:FixedType,
               val depth:Int)

   class Signal(val clk:FlopControl,
		   			    val input:List[SignalNew],
                val output:SignalNew)


   class ModuleBase(override val name:String,
                         override val location:String,
                         val filter:FilterTree) extends Module(name,location) {

     override def getInputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(filter.signal.clk.getAllSignals(OpType.ModuleInput))
        buffer.appendAll(filter.signal.input)
        return buffer.toList
      }

      override def getOutputs:List[SignalNew]         = List(filter.signal.output)

      override def getSignals:List[SignalNew]         = List(filter.createFirstStage)

      override def getSegments:List[StatementSegment] = List(filter)


    }



   }