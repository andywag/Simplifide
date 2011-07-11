package com.simplifide.generator.blocks.fft.gain

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.VectorType
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexSignal, ComplexVectorArray}
import com.simplifide.generator.blocks.fft.{ParallelButterflyStage, ButterflySegmentDif, ButterflySegment, Fft}
import com.simplifide.scala2.core.module.{Instance, ModuleSegment, Module}
import collection.mutable.{ ListBuffer}
import collection.immutable.HashMap
import com.simplifide.generator.blocks.equalizer.Shifter
import com.simplifide.scala2.core.basic.{SimpleStatement, Comment, StatementSegment}

class ParallelButterflyStageGain(override val name:String,
                                 val location:String,
                                 override val clk:FlopControl,
                                 val vectorIn:ComplexVectorGain,
                                 val vectorInt:ComplexVectorGain,
                                 val vectorOut:ComplexVectorGain,
                                 val params:Fft.Param,
                                 val depth:Int) extends ModuleSegment(name,clk){

  /** Set of input signals */
  val inputSignal:ComplexSignal = ComplexSignal.newComplex("internalInput",OpType.Signal,vectorIn.signal.ifixed,vectorIn.signal.len)
  /** Set of output signals */
  val outputSignal:ComplexSignal = ComplexSignal.newComplex("internalOutput",OpType.Signal,vectorInt.signal.ifixed,vectorIn.signal.len)


    /** Returns a list of inputs for this module */
  val inputs:List[SignalNew]  = {
     val buffer = new ListBuffer[SignalNew]
     buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
     buffer.append(vectorIn.gain)
     buffer.append(vectorIn.signal)
     buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(vectorOut.gain,vectorOut.signal)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(inputSignal,outputSignal,vectorInt.signal,vectorInt.gain)


    
  override def createCode(writer:CodeWriter):SegmentReturn = {
    val returns = new ListBuffer[SegmentReturn]()

    val arr2vec = new ComplexVectorArray.ArrayToComplex(vectorIn.signal, inputSignal)
    val vec2arr = new ComplexVectorArray.ComplexToArray(vectorInt.signal, outputSignal)

    //returns.append(SegmentReturn.segment(debugCSD))
    returns.append(writer.createCode(new Comment.SingleLine("Convert the input to the individual complex signals")))
    returns.append(writer.createCode(arr2vec))
    returns.append(writer.createCode(new Comment.SingleLine("Convert the complex outputs to a single signal")))
    returns.append(writer.createCode(vec2arr))
    returns.append(writer.createCode(new SimpleStatement(vectorInt.gain,vectorIn.gain)))

    // Create the Butterfly Stage
    val uname = name + "_int"
    val mod = new ParallelButterflyStage(uname,
                                         clk,
                                         inputSignal.copy(inputSignal.name,Some(OpType.ModuleInput),None,None).asInstanceOf[ComplexSignal],
                                         outputSignal.copy(outputSignal.name,Some(OpType.ModuleOutput),None,None).asInstanceOf[ComplexSignal],
                                         params,
                                         depth,
                                         false)
    val base = new ParallelButterflyStage.ModuleBase(uname,location,mod)
    writer.createCode(base)
    val ins   =  new Instance(uname,base,HashMap())
    returns.append(writer.createCode(ins))
    // Create the shifter at the output
    val shName =  name+ "_shift"
    val sh = new Shifter(shName,
                         clk,
                         vectorInt,
                         vectorOut,
                         4,
                         2)
    val shMod = new Module.Segment(shName,location,sh)
    writer.createCode(shMod)
    val shIns = new Instance(shName,shMod,new HashMap())
    returns.append(writer.createCode(shIns))

    return SegmentReturn.combineReturns(returns.toList, List())
  }
  

}

