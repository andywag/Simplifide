package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.core.basic.fixed.AdditionStatement
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.module.ModuleSegment
import com.simplifide.scala2.core.basic.flop.SimpleFlopList
import com.simplifide.scala2.core.basic.{SimpleStatement, Statement, Comment}
import com.simplifide.scala2.core.basic.operator.{Select, Concat}
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexVectorArray}

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

abstract class CdOverlapBlock(override val name:String,
                      val location:String,
                      override val clk:FlopControl,
                      val signalIn0:ComplexVectorGain,
                      val signalOut:ComplexVectorGain) extends ModuleSegment(name,clk) {



  val internalIn0  = signalIn0.createSignal("internal",signalIn0.signal.ifixed)

  /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
        buffer.append(signalIn0.signal)
        buffer.append(signalIn0.gain)
        buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(signalOut.signal, signalOut.gain)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(internalIn0.signal, internalIn0.gain)






  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }


}

object CdOverlapBlock {

  class Add(override val name:String,
            override val location:String,
            override val clk:FlopControl,
            override val signalIn0:ComplexVectorGain,
            override val signalOut:ComplexVectorGain) extends CdOverlapBlock(name,location,clk,signalIn0,signalOut) {

    val current           = ComplexVectorArray.newSignal("current1",OpType.Signal,signalIn0.fixed,signalIn0.len)
    val delay             = ComplexVectorArray.newSignal("delay1",OpType.Signal,signalIn0.fixed,signalIn0.len)
    val gain:SignalNew            = SignalNew.newSignal("internal_gain",OpType.Signal,signalIn0.gain.fixed)

    override val signals:List[SignalNew] = List(current, delay, gain)

    val cdScale = new CdStageAdditionScale(name + "_scale",location,clk,signalIn0,current,delay,gain)

    override def getFileList:List[String] = cdScale.getFileList ::: List(name)


    override def createCode(writer:CodeWriter):SegmentReturn = {

      val segments = new ListBuffer[BaseCodeSegment]()

      segments.append(cdScale.createInstance(location,writer))
      segments.append(SimpleFlopList.newFlop(clk,List(signalOut.signal,signalOut.gain),List(new Concat(List(delay,current)),gain)))
      return SegmentReturn.combineReturns(writer,segments)
    }

  }

  class Remove(override val name:String,
            override val location:String,
            override val clk:FlopControl,
            override val signalIn0:ComplexVectorGain,
            override val signalOut:ComplexVectorGain) extends CdOverlapBlock(name,location,clk,signalIn0,signalOut) {

    override def createCode(writer:CodeWriter):SegmentReturn = {

      val segments = new ListBuffer[BaseCodeSegment]()
      segments.append(new SimpleStatement(signalOut.signal,Select.newSelect(signalIn0.signal,signalIn0.signal.fixed.width/2-1,
                                                                            0)))
      segments.append(new SimpleStatement(signalOut.gain,signalIn0.gain))

      return SegmentReturn.combineReturns(writer,segments)
    }

  }



}
