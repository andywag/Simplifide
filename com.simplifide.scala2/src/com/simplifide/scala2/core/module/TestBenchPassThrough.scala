package com.simplifide.scala2.core.module

import com.simplifide.scala2.clocks.FlopControl
import collection.mutable.ListBuffer
import collection.immutable.HashMap
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.basic.operator.BinaryOperator
import com.simplifide.scala2.core.basic.test.Initial
import com.simplifide.scala2.core.basic.flop.{TopFlop, ResetEnableFlop, SimpleFlopList}
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core._

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/24/11
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */


class TestBenchPassThrough(override val name:String, val module:ModuleSegment) extends ModuleSegment(name,module.clk) {


        /** Main Index for the simulation */
    //val count:SignalNew = SignalNew.newSignal(TestBench.COUNTNAME,OpType.Reg,FixedTypeMain.unsigned(countWidth,0))

    override val inputs:List[SignalNew] = module.inputs
    override val outputs:List[SignalNew] = {
      val statements = module.getStorageMap.getExtraAssignStatements.map(x => x.output.asInstanceOf[SignalNew])
      module.outputs ::: statements
    }
    /** Returns a list of internal signals for this module */
    override val signals:List[SignalNew] = List()

    override def createCode(writer:CodeWriter):SegmentReturn = {
      val segments = new ListBuffer[BaseCodeSegment]()
      // Create the Reset and Index Control
      segments.append(new Comment.SingleLine("Create the extra signal assignments"))
      segments.appendAll(module.getStorageMap.getExtraAssignStatements)
      segments.append(new Comment.SingleLine("Create the module DUT instantiation"))
      segments.append(Instance.simple(new Module.Segment(this.module.name,"",this.module)))

      return SegmentReturn.combineReturns(writer,segments)
    }

  }

object TestBenchPassThrough {



}

