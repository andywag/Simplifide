package com.simplifide.generator.blocks.equalizer

import com.simplifide.scala2.core.basic.flop.DelayLine
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.{LinkedHashMap, ListBuffer}
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.complex.{ComplexVectorGain, ComplexVectorArray}
import flop.{DelayLine, SimpleFlopList}
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.{BaseCodeSegment, IdentSegment, SegmentReturn, CodeWriter}
import com.simplifide.scala2.core.module.{ModuleSegment, Stimulus, Storage}
import operator._

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/6/11
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */

class Shifter(override val name:String,
              override val clk:FlopControl,
              val vectorIn:ComplexVectorGain,
              val vectorOut:ComplexVectorGain,
              val range:Int,
              val gainDelay:Int,
              val round:Boolean) extends ModuleSegment(name,clk) {


  def this(name:String,
           clk:FlopControl,
           vectorIn:ComplexVectorGain,
           vectorOut:ComplexVectorGain,
           range:Int,
           gainDelay:Int)      {
    this(name,clk,vectorIn,vectorOut,range,gainDelay,true)
  }


  val signalInternal =  ComplexVectorArray.newSignal(name + "_int",OpType.Signal,vectorOut.signal.ifixed,vectorIn.signal.len)
  val gainInternal   =  vectorIn.gain.copy(vectorIn.gain.name + "_int",Some(OpType.Signal),None,None).asInstanceOf[SignalNew]
  val gainReg        =  {
	val ur = if (gainDelay > 1) gainDelay else 0
    vectorIn.gain.copy("gainReg",Some(OpType.Reg),None,Some(VectorType.newVector(ur))).asInstanceOf[SignalNew]
  }


    /** Set of Xor Signals for Finding the Proper Selection of the Gain Point */
  val signalXor:List[VectorArray] = {
    val buf = new ListBuffer[VectorArray]
    for (i  <- 0 until range-1) {
      buf.append(new VectorArray(name+ "_xor_" + i,OpType.Signal,FixedTypeMain.unsigned(1,0),vectorIn.signal.len))
    }
    buf.toList
  }
  val location = SignalNew.newSignal("location",OpType.Signal,FixedTypeMain.unsigned(range-1, 0))

    /** Default Simulation Length */
  override val simLength:Int = 1000;
  /** Enable or Disables Tracing for Simuation */
  override val trace:Boolean = false
  /** Returns a list of inputs for this module */
  val inputs:List[SignalNew] = {
     val buffer = new ListBuffer[SignalNew]
     buffer.appendAll(clk.getAllSignals(OpType.ModuleInput))
     buffer.append(vectorIn.gain)
     buffer.append(vectorIn.signal)
     buffer.toList
  }
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(vectorOut.gain,vectorOut.signal)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List(signalInternal,gainInternal,location,gainReg,
    vectorOut.signal.copyWithType(OpType.Reg).asInstanceOf[SignalNew],
    vectorOut.gain.copyWithType(OpType.Reg).asInstanceOf[SignalNew]) ::: signalXor




  /** Create the Real Version of the Mux Selection for the Barrel Shifter */
  def getRealSelect(index:Int,ra:Int):StatementSegment = {
    val top = vectorIn.signal.ifixed.width - 1
    val bot = vectorIn.signal.ifixed.width - vectorOut.signal.ifixed.width
    val sel =  vectorIn.signal.getRealSliceSelect(index,top-ra,bot-ra)
    if (round && (bot - ra > 0))  {
      val topSel = vectorIn.signal.getRealSliceSelect(index,top-ra-1,bot-ra)
      val unTop  = new UnaryOperator.NotAnd(topSel)
      val binTop = new BinaryOperator.And(unTop,vectorIn.signal.getRealSliceSelect(index,bot-ra-1,bot-ra-1))
      val cat = new Concat(List(Constant.newConstant(0,vectorOut.signal.ifixed.width-1),binTop))
      return new BinaryOperator.Plus(sel,cat)
    }
    return sel
  }

  /** Create the Imag Version of the Mux Selection for the Barrel Shifter */
  def getImagSelect(index:Int,ra:Int):StatementSegment = {
    val top = vectorIn.signal.ifixed.width - 1
    val bot = vectorIn.signal.ifixed.width - vectorOut.signal.ifixed.width
    val sel =  vectorIn.signal.getImagSliceSelect(index,top-ra,bot-ra)
    if (round && (bot - ra > 0))  {
      val topSel = vectorIn.signal.getImagSliceSelect(index,top-ra-1,bot-ra)
      val unTop  = new UnaryOperator.NotAnd(topSel)
      val binTop = new BinaryOperator.And(unTop,vectorIn.signal.getImagSliceSelect(index,bot-ra-1,bot-ra-1))
      val cat = new Concat(List(Constant.newConstant(0,vectorOut.signal.ifixed.width-1),binTop))
      return new BinaryOperator.Plus(sel,cat)
    }
    return sel
  }



  override def createCode(writer:CodeWriter):SegmentReturn = {
    val stages:ListBuffer[BaseCodeSegment] = new ListBuffer[BaseCodeSegment]()
    // Create the Xor Logic
    stages.append(new Comment.SingleLine("Check if the data can be scaled up or down"))
    for (j <- 0 until range-1) {
      stages.append(new Comment.SingleLine("Check for Row " + j))
       for (i <- 0 until vectorIn.signal.len) {
        val sig = vectorIn.signal
        val rtop = sig.getRealSliceSelect(i,sig.ifixed.width-1-j,sig.ifixed.width-1-j)
        val rmid = sig.getRealSliceSelect(i,sig.ifixed.width-2-j,sig.ifixed.width-2-j)
        val itop = sig.getImagSliceSelect(i,sig.ifixed.width-1-j,sig.ifixed.width-1-j)
        val imid = sig.getImagSliceSelect(i,sig.ifixed.width-2-j,sig.ifixed.width-2-j)
        val rt   = new ParenOperator(new BinaryOperator.Xor(rtop,rmid))
        val it   = new ParenOperator(new BinaryOperator.Xor(itop,imid))
        val up   = new BinaryOperator.Or(rt,it)
        val xor = new Select(signalXor(j),Some(i),Some(i))
        stages.append(new SimpleStatement(xor,up))
      }
      stages.append(new Comment.SingleLine("Or the logic for this movement condition"))
      val lsel = new Select(location,Some(j),Some(j))
      stages.append(new SimpleStatement(lsel,new UnaryOperator.Or(signalXor(j))))
    }

    // Create the Output Muxing Logic
    stages.append(new Comment.SingleLine("Mux the output Data"))
    for (j <- 0 until vectorIn.signal.len) {
      //stages.append(new Comment.SingleLine("Check for Row " + j))
      val lsel = new Select(location,Some(range-1),Some(range-1))
      var rmux:StatementSegment = getRealSelect(j,range-1)
      var imux:StatementSegment = getImagSelect(j,range-1)
      for (i <- 1 until range) {
    	  val lsel2 = new Select(location,Some(range-1-i),Some(range-1-i))
        rmux = new SimpleMux(lsel2,getRealSelect(j,range-1-i),rmux)
        imux = new SimpleMux(lsel2,getImagSelect(j,range-1-i),imux)
      }
      stages.append(new SimpleStatement(signalInternal.getRealSlice(j),rmux))
      stages.append(new SimpleStatement(signalInternal.getImagSlice(j),imux))
    } 
    // Delay the Gain
    stages.append(new Comment.SingleLine("Delay the Gain"))
    if (gainDelay > 0) stages.append(new DelayLine(clk,gainReg,vectorIn.gain))

    // Create the Final Gain Stage
    stages.append(new Comment.SingleLine("Create the Output Gain"))

    val offset = vectorIn.signal.getRealFixedType.integerWidth - vectorOut.signal.getRealFixedType.integerWidth - range + 1
    val gainSig = if (gainDelay == 0) vectorIn.gain else if (gainDelay <= 1) gainReg else gainReg.getSlice(0)
    var mux:StatementSegment    = new BinaryOperator.Plus(gainSig,new IdentSegment(offset.toString))
    for (j <- 1 until range) {
        val lsel = new Select(location,Some(range-1-j),Some(range-1-j))
        val offseta = vectorIn.signal.getRealFixedType.integerWidth - vectorOut.signal.getRealFixedType.integerWidth - range + j + 1
        val add2    = new BinaryOperator.Plus(gainSig,new IdentSegment(offseta.toString))
        mux    = new SimpleMux(lsel,add2,mux)
    }
    stages.append(new SimpleStatement(gainInternal,mux))

    stages.append(SimpleFlopList.newFlop(clk, List(vectorOut.signal,vectorOut.gain),List(signalInternal,gainInternal)))

    return SegmentReturn.combineBufferReturns(writer,stages)
  }

}