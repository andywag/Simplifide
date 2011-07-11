/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.basic.StatementSegment

abstract class CodeWriter(val context:CurrentContext) {

  val isVerilog:Boolean = false;
  val isVhdl:Boolean = false;
  val isHeader:Boolean = false;
  val isFloat:Boolean = false;
  val isFixed:Boolean = false;
  
  def createCode(segment:BaseCodeSegment):SegmentReturn
  def createCode(segment:StatementSegment,output:BaseSignal):SegmentReturn;
  def createCode(segment:StatementSegment,output:Option[BaseSignal]):SegmentReturn = {
    output match {
      case None    => return createCode(segment)
      case Some(x) => return createCode(segment,x)
    }
  }
  
  def createSimpleCode(segment:BaseCodeSegment):String = createCode(segment).code
}

object CodeWriter {
  class Verilog(override val context:CurrentContext) extends CodeWriter(context){
    override val isVerilog:Boolean = true;
    override def createCode(segment:BaseCodeSegment):SegmentReturn = return segment.createVerilogCode(this)
    override def createCode(segment:StatementSegment,output:BaseSignal):SegmentReturn = segment.createVerilogCode(this,output)
  }
  
  class Vhdl(override val context:CurrentContext) extends CodeWriter(context) {
    override val isVhdl:Boolean = true;
    override def createCode(segment:BaseCodeSegment):SegmentReturn = return segment.createVhdlCode(this)
    override def createCode(segment:StatementSegment,output:BaseSignal):SegmentReturn = segment.createVhdlCode(this,output)
  }
  
  class CHeader(override val context:CurrentContext) extends CodeWriter(context) {
    override val isHeader:Boolean = true;
    override def createCode(segment:BaseCodeSegment):SegmentReturn = return segment.createHeaderCode(this)
    override def createCode(segment:StatementSegment,output:BaseSignal):SegmentReturn = segment.createHeaderCode(this,output)
  }
  
  class Float(override val context:CurrentContext) extends CodeWriter(context) {
    override val isFloat:Boolean = true;
    override def createCode(segment:BaseCodeSegment):SegmentReturn = return segment.createFloatCode(this)
    override def createCode(segment:StatementSegment,output:BaseSignal):SegmentReturn = segment.createFloatCode(this,output)
  }
  
  class Fixed(override val context:CurrentContext) extends CodeWriter(context) {
    override val isFixed:Boolean = true;
    override def createCode(segment:BaseCodeSegment):SegmentReturn = return segment.createFixedCode(this)
    override def createCode(segment:StatementSegment,output:BaseSignal):SegmentReturn = segment.createFixedCode(this,output)
  }
}
