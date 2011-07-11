/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.operator.Select


class FixedSelect(val signal:BaseSignal, val fixed:FixedType) extends StatementSegment{

  override def getFixedType:FixedType = fixed
	
  override def getSignal:BaseSignal = signal
  def getShift:Int     = 0
  
  def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }
  
  def createCItem(writer:CodeWriter,funcname:String):SegmentReturn = {
    val bot = signal.getFixedType.frac - fixed.frac
    val top = bot + this.fixed.width - 1
    
    val sig = writer.createCode(signal)
    if (bot == 0) {
      return sig
    }
    else {
      return SegmentReturn.segment(funcname + "(" + sig.code + "," + signal.getFixedType.width + "," 
                                   + signal.getFixedType.frac + "," + top + ","+ bot + ")") 
    }
  }
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn = 
    return createCItem(writer,"select_float")
  
  override def createFixedCode(writer:CodeWriter):SegmentReturn = 
     return createCItem(writer,"select_fixed")
  
  
  override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = 
    return createCItem(writer,"select_float")
  
  
  override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = 
     return createCItem(writer,"select_fixed")
  

  
  override def createCode(writer:CodeWriter):SegmentReturn = {
     val bot = signal.getFixedType.frac - fixed.frac + getShift// Bottom of the Select 
     val top = bot + this.fixed.width - 1
     val sel = new Select(signal,Some(top),Some(bot))
     val ret =  writer.createCode(sel)
     if (signal.getFixedType.signed.isSigned) {
       val builder = new StringBuilder
       builder.append("$signed(")
       builder.append(ret.code)
       builder.append(")")
       return SegmentReturn.segment(builder.toString)
     }
     else {
       return ret
     }
     
  }
  
}

object FixedSelect {


  /** Creates a new selection of the signal with the input fixed type fixed */
  def newSelect(signal:BaseSignal,fixed:FixedType) =
    new FixedSelect(signal,fixed)

  /**Creates a new selection of the signal with the input fixed type fixed shift
   * by scale
   */
  def newSelect(signal:BaseSignal,fixed:FixedType,scale:Int) =
    new FixedSelect.Scale(signal,fixed,scale)

  class Scale(override val signal:BaseSignal, override val fixed:FixedType, val scale:Int) extends FixedSelect(signal,fixed) {
    override def getShift:Int     = scale
    override def getSelect(output:BaseSignal):StatementSegment = this
    
    
  override def createCItem(writer:CodeWriter,funcname:String):SegmentReturn = {
    val bot = signal.getFixedType.frac - fixed.frac
    val top = bot + this.fixed.width - 1
    val sig = writer.createCode(signal) 
    if (bot == 0) {
      return sig
    }
    else {
      return SegmentReturn.segment(funcname + "(" + sig.code + "," + signal.getFixedType.width + "," 
                                   + signal.getFixedType.frac + "," + top + ","+ bot + "," + getShift + ")")
      
    }
  }
    
    
    override def createFloatCode(writer:CodeWriter):SegmentReturn = 
       return createCItem(writer,"select_float_scale")
  
    override def createFixedCode(writer:CodeWriter):SegmentReturn = 
       return createCItem(writer,"select_fixed_scale")
  
    override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = 
       return createCItem(writer,"select_float_scale")
 
    override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = 
       return createCItem(writer,"select_fixed_scale")
  }
  
}
