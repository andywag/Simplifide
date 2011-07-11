package com.simplifide.scala2.core

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: Aug 24, 2010
 * Time: 11:28:35 PM
 * To change this template use File | Settings | File Templates.
 */

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.top.InterfaceError

 class BaseCodeSegment  {

  /** Get the number of elements that this segment has */
  def getNumber:Int = 0
  
  /** Get the signal type associated with this segment */
  def getFixedType:FixedType = null
  
  /** Get the part select for this part */
  def getSelect(fixed:FixedType):BaseCodeSegment = this

  /** Creates the code for this segment. Called with one of the writers */
  //def createCode(creator:(BaseCodeSegment)=>String):String = ""
  
  /** Creators for the different code types */
  //def createVerilog():String =  createCode(BaseCodeSegment.verilogWriter(_))
  //def createVhdl():String    =  createCode(BaseCodeSegment.vhdlWriter(_))
  //def createC():String       =  ""

  //def createCode:String = return createCode(null)
  
  
  
  /*def createCode(creator:(BaseCodeSegment,CurrentContext) => SegmentReturn, context:CurrentContext):SegmentReturn = {
    return creator(this,context)
  }*/
  
  def createVerilogCode(writer:CodeWriter):SegmentReturn     = createCode(writer)
  def createVhdlCode(writer:CodeWriter):SegmentReturn        = createCode(writer)
  def createFloatCode(writer:CodeWriter):SegmentReturn       = createCode(writer)
  def createFixedCode(writer:CodeWriter):SegmentReturn       = createCode(writer)
  def createHeaderCode(writer:CodeWriter):SegmentReturn      = createCode(writer)

 
  
  
  def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment("")
  
  
  def createList(segments:List[BaseCodeSegment],writer:CodeWriter):SegmentReturn = {
      val ret:List[SegmentReturn] = segments.map(x => writer.createCode(x))
      return SegmentReturn.combineReturns(ret, List())
  }
  
  def createList(segments:List[BaseCodeSegment],writer:CodeWriter,errors:List[InterfaceError]):SegmentReturn = {
      val ret:List[SegmentReturn] = segments.map(x => writer.createCode(x))
      return SegmentReturn.combineReturns(ret, List())
  }

  def createVhdlList(segments:List[BaseCodeSegment], writer:CodeWriter):SegmentReturn = {
    createList(segments,writer)
  }

  def createVerilogList(segments:List[BaseCodeSegment], writer:CodeWriter):SegmentReturn = {
    createList(segments,writer)
  }

 

}

object BaseCodeSegment {
  //def verilogWriter(seg:BaseCodeSegment):String ={return seg.createVerilog;}
  //def vhdlWriter(seg:BaseCodeSegment):String ={return seg.createVhdl;}

  //def verilogCodeWriter(seg:BaseCodeSegment,context:CurrentContext):SegmentReturn ={return seg.createVerilogCode(context);}
  //def vhdlCodeWriter(seg:BaseCodeSegment,context:CurrentContext):SegmentReturn    ={return seg.createVhdlCode(context);}



}