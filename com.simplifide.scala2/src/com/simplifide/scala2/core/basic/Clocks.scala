/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.command._
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.basic.vars.VectorType
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.command._
import com.simplifide.scala2.clocks._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.Signing

class Clocks {

}

class ClockSignal(name:String) extends BaseCodeSegment{
  
  override def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment(name)
 
}

class Clock(val name:String, posedge:Boolean) extends ClockSignal(name){
  /** Returns the signal associated with this clock */


  def getSensitivityListItem():BaseCodeSegment = {
    if (posedge) return new ClockEdgeHead(this, "posedge ");
    else         return new ClockEdgeHead(this, "negedge ");
  }

}

class Reset(val name:String,async:Boolean,val activeLow:Boolean) extends ClockSignal(name) {
 
  def getSensitivityListItem():Option[BaseCodeSegment] = {
    if (async) {
       if (activeLow) return Some(new ClockEdgeHead(this, "negedge "));
       else           return Some(new ClockEdgeHead(this, "posedge "));
    }
    return None;
  }
}

class Enable(val name:String) extends ClockSignal(name) {

}

class ClockEdgeHead(signal:ClockSignal, edge:String) extends BaseCodeSegment{
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    if (edge != null) return SegmentReturn.segment(edge + signal.createVerilogCode(writer))
    return signal.createVerilogCode(writer);
  }

  override def createVhdlCode(writer:CodeWriter):SegmentReturn = {
    return signal.createVhdlCode(writer);
  }
}
 




object Clocks {

  class EnableSegment(val segment:BaseCodeSegment) extends Enable("") {
      override def createCode(writer:CodeWriter):SegmentReturn = writer.createCode(segment)
  }

  def defaultFlop(clock:String,reset:String,enable:String):FlopControl = {
    val clock1 = new Clock(clock, true);
    val reset1 = Some(new Reset(reset, true, true));
    val enable1 = Some(new Enable(enable));

    return new FlopControl("flop",clock1,reset1,enable1,None)

  }
  
  class Index(name:String,index:Int) {
    
    def getSignal:BaseSignal = {
      
      new SignalNew(name,OpType.Input,new FixedTypeMain(Signing.UnSigned,index,0),VectorType.NoVector)
    }
      
    
  }
  
}