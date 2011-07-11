/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.statement

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.fixed.SimpleMultiplySegment
import com.simplifide.scala2.core.basic.vars.BaseSignal

abstract class UnarySegment(val value:StatementSegment) extends StatementSegment{
	
  override def getFixedType:FixedType = value.getFixedType
  /** Get the number of terms for this expression */
  override def getNumberOfTerms:Int = 1
  /** Return the term associated with the nth point */
  override def getTerm(index:StatementSegment.TermIndex):StatementSegment = {
    val ter = value.getTerm(index)
    return createSegmentForTerm(ter)
  }
  def createSegmentForTerm(value:StatementSegment):StatementSegment
   
}

object UnarySegment {
  class Negative(override val value:StatementSegment) extends UnarySegment(value) {
    
    override def createMultSegment(sig:StatementSegment):Option[SimpleMultiplySegment] = 
      new Some(new SimpleMultiplySegment(this,sig))
    
    override def createSegmentForTerm(value:StatementSegment):StatementSegment = 
      new Negative(value)
    
    override def createCode(writer:CodeWriter):SegmentReturn = {
      val ret:SegmentReturn = writer.createCode(value)
      val nret:SegmentReturn = new SegmentReturn("-" + ret.code,ret.errors)
      return nret
    }
   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      val ret:SegmentReturn = writer.createCode(value,output)
      val nret:SegmentReturn = new SegmentReturn("-" + ret.code,ret.errors)
      return nret
    }
    
  }
}
