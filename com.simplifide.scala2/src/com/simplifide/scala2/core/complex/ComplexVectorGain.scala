package com.simplifide.scala2.core.complex

import com.simplifide.scala2.core.basic.vars.{FixedTypeMain, FixedType, OpType, SignalNew}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/7/11
 * Time: 8:49 AM
 * To change this template use File | Settings | File Templates.
 */

/** Class which contains a complex vector signal as well as a gain signal */
class ComplexVectorGain(val signal:ComplexVectorArray, val gain:SignalNew) {

  val len = signal.len
  val fixed = signal.getRealFixedType

  def getAllSignals:List[SignalNew] = List(signal,gain)

  def createSignal(name:String,fixed:FixedType):ComplexVectorGain = {
    createSignal(name,fixed,OpType.Signal)
  }

  def createSignal(name:String,fixed:FixedType,optype:OpType):ComplexVectorGain = {
    val sig  = ComplexVectorArray.newSignal(name,optype,fixed,signal.len)
    val ugain = SignalNew.newSignal(name + "_gain",optype,gain.getRealFixedType)
    new ComplexVectorGain(sig,ugain)
  }



}
object ComplexVectorGain {

   def newSignal(name:String,opType:OpType,fixed:FixedType,len:Int):ComplexVectorGain = {
       ComplexVectorGain.newSignal(name,opType,fixed,len,FixedTypeMain.signed(5,0))
   }
   def newSignal(name:String,opType:OpType,fixed:FixedType,len:Int,gfix:FixedType) = {
     val sig = ComplexVectorArray.newSignal(name,opType,fixed,len)
     val gain = SignalNew.newSignal(name+"_gain",opType,gfix)
     new ComplexVectorGain(sig,gain)
   }

}