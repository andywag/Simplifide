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


class TestBench(override val name:String, val module:ModuleSegment) extends ModuleSegment(name,module.clk) {

    /** Returns a list with the clock signal removed */
    def filterClockSignal(inSignals:List[SignalNew]):List[SignalNew] = {
      val buffer = new ListBuffer[SignalNew]()
      for (signal <- inSignals) {
        val clkName = module.clk.getClock.name
        val sigName = signal.name
        if (!clkName.equalsIgnoreCase(sigName)) buffer.append(signal)
      }
      buffer.toList
    }

    val countWidth = {

      math.ceil(math.log(module.simLength)/math.log(2.0)).toInt
    }
        /** Main Index for the simulation */
    val count:SignalNew = SignalNew.newSignal(TestBench.COUNTNAME,OpType.Reg,FixedTypeMain.unsigned(countWidth,0))

    override val inputs:List[SignalNew] = List(module.clk.getClockSignal(OpType.ModuleInput))
    override val outputs:List[SignalNew] = List()
    /** Returns a list of internal signals for this module */
    override val signals:List[SignalNew] = {
      val sigs = new ListBuffer[SignalNew]()
      sigs.append(count)
      sigs.appendAll(module.inputs.map(x => x.copy(x.name,Some(OpType.Reg),None,None).asInstanceOf[SignalNew]))
      sigs.appendAll(module.outputs.map(x => x.copy(x.name,Some(OpType.Signal),None,None).asInstanceOf[SignalNew]))
      sigs.appendAll(module.getStimulusMap.getInitialSignals)
      sigs.appendAll(module.getStorageMap.getInitialSignals)
      filterClockSignal(sigs.toList)
      //sigs.toList
    }


    override def createCode(writer:CodeWriter):SegmentReturn = {
      val segments = new ListBuffer[BaseCodeSegment]()
      // Create the Reset and Index Control
      segments.append(new TestBench.BaseSegment(this))
      val stimMap = module.getStimulusMap.linkedMap
      stimMap.put(count,new Stimulus.Constant(0,count))       // Append Count to the Stimulas Map
      // Append the Inital Segment to the test bench
      segments.append(new Comment.SingleLine("Initialize the variables"))
      segments.append(new Initial.TestBench(new Stimulus.Map(stimMap,module),module.getStorageMap))
      // Create the Data Loading Operation
      segments.append(new Comment.SingleLine("Create the segment to load the data into the testbench"))
      segments.append(new TestBench.DataLoader(this))
      // Create the Data Storing Operation
      segments.append(new Comment.SingleLine("Create the segment to store the results to a file for post processing"))
      segments.append(new TestBench.DataStorage(this))
      // Create the Module Instantiation
      segments.append(new Comment.SingleLine("Create the module DUT instantiation"))
      segments.append(Instance.simple(new Module.Segment(this.module.name,"",this.module)))


      return SegmentReturn.combineReturns(writer,segments)
    }

  }

object TestBench {

  val COUNTNAME  = "count"

  val COUNTDELTA = 3


  class BaseSegment(val bench:TestBench) extends StatementSegment {

    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = createCode(writer)
    override def createCode(writer:CodeWriter):SegmentReturn = {
      val segments = new ListBuffer[BaseCodeSegment]()

      val nclk:FlopControl = new FlopControl("",bench.clk.getClock,None,None,None)
      // Create the Count Generation Block
      val cplus = new BinaryOperator.Plus(bench.count,Constant.newConstant(1,bench.count.getFixedType))
      val countFlop = SimpleFlopList.newFlop(nclk,List(bench.count),List(cplus))
      segments.append(new Comment.SingleLine("Create the time index for the simluation"))
      segments.append(countFlop)
      // Create the Reset
      val resetStatment = new IfElseStatement()
      val resetStart = new BinaryOperator.GTE(bench.count,Constant.newConstant(COUNTDELTA,bench.count.getFixedType))
      resetStatment.addClause(Some(resetStart),new AssignStatement(bench.clk.getResetSignal(OpType.Signal).get,Constant.newConstant(1,FixedTypeMain.unsigned(1,0))))
      val resetFlop = new ResetEnableFlop(None,nclk,null,resetStatment)
      segments.append(new Comment.SingleLine("Create the reset for the testbench"))
      segments.append(resetFlop)
      // Create the Finish Flop which ends the simulation after a period of time
      segments.append(new Comment.SingleLine("Create the finish statement for the testbench"))
      segments.append(new FinishFlop(bench))

      return SegmentReturn.combineReturns(writer,segments)
    }
  }

  class FinishFlop(val bench:TestBench) extends StatementSegment.Simple {
     override def createCode(writer:CodeWriter):SegmentReturn = {
        val ifelse = new IfElseStatement
        ifelse.addClause(Some(new BinaryOperator.GT(bench.count,bench.count.createConstant(bench.simLength))), new IdentSegment("$finish;"))
        val flop = new TopFlop(None,bench.clk,ifelse)
        return writer.createCode(flop)
     }
  }

  class DataLoader(val bench:TestBench) extends StatementSegment.Simple {
       override def createCode(writer:CodeWriter):SegmentReturn = {
          val op = new BinaryOperator.GT(bench.count,bench.count.createConstant(COUNTDELTA));
          val rop = bench.clk.getEnableSignal(OpType.Signal) match {
            case Some(x) => new BinaryOperator.LogicalAnd(x,op)
            case None    => op
          }
          val flopControl = new FlopControl("",bench.clk.clock,None,Some(new Clocks.EnableSegment(rop)),None)
          val flop = SimpleFlopList.newFlop(flopControl,bench.module.getStimulusMap.getStimulusFlopStatements(bench.countWidth))
          return writer.createCode(flop)
       }
  }

  class DataStorage(val bench:TestBench) extends StatementSegment.Simple {
       override def createCode(writer:CodeWriter):SegmentReturn = {
          val flopControl = new FlopControl("",bench.clk.clock,None,None,None)
          val fdisplay    = bench.module.getStorageMap.getStorageFlopSegments
          val segments    = new ListSegment(fdisplay)
          val ifelse = new IfElseStatement
          ifelse.addClause(bench.clk.getResetSignal(OpType.Signal),segments)
          val flop = new TopFlop(None,flopControl,ifelse)
          return writer.createCode(flop)
       }
  }


}

