/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic

import com.simplifide.scala2.core.basic.fixed.SimpleMultiplySegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn


abstract class StatementSegment extends BaseCodeSegment{

  /** Return the segment associated with the input index*/
  def getSegment(index:Int):StatementSegment = null
  /** Returns whether a new statement is */
  val newStatement:Boolean = false
  /** returns the signal associated with this statement. Only required when it is 
      a new statement*/
  def getSignal:BaseSignal = null;
  /** Returns the signal associated with this segment. This is the value
   *  to be used with the expression */
  def getSignal(output:BaseSignal):BaseSignal = {
    if (newStatement) return output;
    return getSignal
  }
  /** Returns the real segment. This only appears to be used by the paren segment */
  def getRealSegment:StatementSegment = this
  
  /** Creates the multiply segment. This is used to define which kind of multiplier to be used. 
   *  For example CSD if it a constant or a normal multiplier */
  def createMultSegment(sig:StatementSegment):Option[SimpleMultiplySegment] = None
  
  /** Creates the select for this segment. Used for the addition term */
  def getSelect(output:BaseSignal):StatementSegment = this.getSignal(output).getSelect(output) 
  
  /** Creates the code for this segment */
  def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn

  /** Get the number of terms for this expression */
  def getNumberOfTerms:Int = 0
  /** Return the term associated with the nth point */
  def getTerm(index:StatementSegment.TermIndex):StatementSegment = null
  /** Converts the terms in this expression to the proper values
   *  Used to get the proper index of the signals
   */
  def convertTerm(index:StatementSegment.TermIndex):StatementSegment = this
  /** Create the segment associated with this code */
  def createShareSegment(states:List[StatementSegment]):StatementSegment=
    states(0)
  

  
  /** General methods for code generation */
  def createVerilogCode(writer:CodeWriter,output:BaseSignal):SegmentReturn     = createCode(writer,output)
  def createVhdlCode(writer:CodeWriter,output:BaseSignal):SegmentReturn        = createCode(writer,output)
  def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn       = createCode(writer,output)
  def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn       = createCode(writer,output)
  def createHeaderCode(writer:CodeWriter,output:BaseSignal):SegmentReturn      = createCode(writer,output)
  
}

object StatementSegment {

  class Simple extends StatementSegment {
    def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = writer.createCode(this)
  }

  class TermIndex(val x:Int,val y:Int)
  
  def createList(segments:List[StatementSegment],writer:CodeWriter):SegmentReturn = {
      val ret:List[SegmentReturn] = segments.map(x => writer.createCode(x))
      return SegmentReturn.combineReturns(ret, List())
  }
}

