/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment

class ParenSegment(val state:StatementSegment) extends StatementSegment {
  
  override val newStatement:Boolean = true
  override def getSignal = state.getSignal
    
  override def getRealSegment:StatementSegment = state
  
  override def getFixedType:FixedType = return state.getFixedType
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    val statement = new Statement.Internal(output,state)
    val ret = SegmentReturn.segment(output.createCode(writer).code)
    ret.extraStatements.append(statement)
    return ret
  }
  
  /** Get the number of terms for this expression */
  override def getNumberOfTerms:Int = 1
  /** Return the term associated with the nth point */
  override def getTerm(index:StatementSegment.TermIndex):StatementSegment = state.convertTerm(index)
  
  override def convertTerm(index:StatementSegment.TermIndex):StatementSegment = new ParenSegment(state.convertTerm(index))

  /** Create the segment associated with this code */
  override def createShareSegment(states:List[StatementSegment]):StatementSegment=
    return new ParenSegment(states(0))
  
  
}
