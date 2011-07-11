/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment

class MultiplyFactor(val term:StatementSegment) extends StatementSegment {
  
  val operator:String = " * "
  
  override def getFixedType:FixedType = term.getFixedType
  
  override def getTerm(index:StatementSegment.TermIndex):StatementSegment = {
    return term.getTerm(index)
  }
  
  override def getSignal = term.getSignal

  
  override def createMultSegment(sig:StatementSegment):Option[SimpleMultiplySegment] = 
	  term.createMultSegment(sig)
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    val sel      = term.getSignal(output)   // Create the output signal and make the proper buswidth
    val segment  = writer.createCode(sel)                           // Create the code for this semgment
    val ret      = SegmentReturn.segment(operator + segment.code)   // Prepend the operator
    
    if (term.newStatement) { // If the term creates a new statement then create it
    	val st = new Statement.Internal(output,term)
    	ret.extraStatements.append(st)
    }
    else { // Otherwise attach the segments associated with it
    	val termR    = writer.createCode(term,output)
    	ret.extraStatements.appendAll(termR.extraStatements)
    }
    
    return ret
  }

}

object MultiplyFactor {
   
  class MultTerm(override val term:StatementSegment) extends MultiplyFactor(term) {
    override val operator = " * "
    override def createShareSegment(states:List[StatementSegment]):StatementSegment=
      new MultTerm(term.createShareSegment(states))
  }
  
  class DivTerm(override val term:StatementSegment) extends MultiplyFactor(term) {
    override val operator = " / "
    override def createShareSegment(states:List[StatementSegment]):StatementSegment=
      new DivTerm(term.createShareSegment(states))
  }
  
  
  class Empty(override val term:StatementSegment) extends MultiplyFactor(term) {
    override val operator = ""
    override def createShareSegment(states:List[StatementSegment]):StatementSegment=
      new Empty(term.createShareSegment(states))
  }
  
}
