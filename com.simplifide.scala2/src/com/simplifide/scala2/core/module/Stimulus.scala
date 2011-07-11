package com.simplifide.scala2.core.module

import collection.mutable.LinkedHashMap
import com.simplifide.scala2.core.{IdentSegment, BaseCodeSegment}
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.{FixedTypeMain, OpType, BaseSignal, SignalNew}
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.basic.operator.{BinaryOperator, SliceOp}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/24/11
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */


/** Class containing descriptors associated with stimulus files */
abstract class Stimulus {
  /** Creates the input used for the initial statement */
  def createInitial:(Option[BaseSignal],StatementSegment)
  /** Returns a list of signals created by the Initial Block */
  def getInitialSignals(simLength:Int):List[SignalNew] = List()
  /** Returns a list of signals */
  def getStimulusFlopStatement(width:Int):Option[(SignalNew,StatementSegment)] = None
}


object Stimulus {


  /** Stimulus class which consists of a simple constant with the same fixed type as the signal input */
  class Constant(val value:Int, val signal:SignalNew)  extends Stimulus {
    def createInitial:(Option[BaseSignal],StatementSegment) = (Some(signal),signal.createConstant(value))
  }
  /** Stimulus class which consists of reading the data from a file */
  class File(val location:String, val signal:SignalNew) extends Stimulus {

    override def getStimulusFlopStatement(width:Int):Option[(SignalNew,StatementSegment)] = {
      val count = SignalNew.newSignal(TestBench.COUNTNAME,OpType.Signal,FixedTypeMain.unsigned(width,0))
      val seg    = new BinaryOperator.Minus(count,count.createConstant(TestBench.COUNTDELTA))
      Some((signal,new SliceOp(createPointerSignal(0),seg)))
    }

    override def getInitialSignals(simLength:Int):List[SignalNew] = List(createPointerSignal(simLength))
    /** Signal which is used to store the data which is read from a file */
    private def createPointerSignal(len:Int) = new SignalNew.Array(signal.name + "_ptr", OpType.Reg, signal.getFixedType,len)

    def createInitial:(Option[BaseSignal],StatementSegment) = {
      val builder = new StringBuilder
      builder.append("$readmemb(")
      builder.append("\"")
      builder.append("data/")
      builder.append(location)
      builder.append(".bin")
      builder.append("\"")
      builder.append(",")
      builder.append(createPointerSignal(0).name)
      builder.append(");\n")
      return (Some(SignalNew.newSignal("none")),new IdentSegment(builder.toString))
    }

  }

  /** List of initial conditions to help in creating the test */
  class Map(val linkedMap:LinkedHashMap[BaseSignal,Stimulus],segment:ModuleSegment) {

    /** Returns the flop statements for the generator */
    def getStimulusFlopStatements(width:Int):LinkedHashMap[BaseSignal,StatementSegment] = {
      val linkMap = new LinkedHashMap[BaseSignal,StatementSegment]()
      for ((key,value) <- linkedMap) {
        value.getStimulusFlopStatement(width) match {
          case Some(x) => linkMap.put(x._1, x._2)
          case None    => 
        }
      }
      return linkMap
    }
    /** Returns a list of signals which is created for the initial block */
    def getInitialSignals:List[SignalNew] = {
        linkedMap.values.flatMap(x => x.getInitialSignals(segment.simLength)).toList
    }
    /** Create the structure used to create the initial block for the test bench */
    def createInitialMap:LinkedHashMap[Option[BaseSignal],StatementSegment] = {
      val linkMap = new LinkedHashMap[Option[BaseSignal],StatementSegment]()
      for ((key,value) <- linkedMap) {
        val (retSignal,retValue):(Option[BaseSignal],StatementSegment) = value.createInitial
        linkMap.put(retSignal,retValue)
      }
      return linkMap
    }

  }

}