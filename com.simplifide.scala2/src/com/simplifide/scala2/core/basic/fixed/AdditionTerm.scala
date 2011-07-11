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

abstract class AdditionTerm(val term:StatementSegment) extends StatementSegment {
  
  val operator:String = " + "
  
  override def getNumber:Int = return term.getNumber
  override def getSegment(index:Int):StatementSegment = {
    if (getNumber == 0) return this
    return createTerm(term.getSegment(index))
  }
  
  def createTerm(term:StatementSegment):AdditionTerm
  
  override def getSignal = term.getSignal
  override def getFixedType:FixedType = term.getFixedType
  /** Return the term associated with the nth point */
  override def getTerm(n:StatementSegment.TermIndex):StatementSegment = {
    return term.getTerm(n)
  }
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    val sel      = term.getSelect(output)            // Create the output signal and make the proper buswidth
    val segment  = writer.createCode(sel,output)     // Create the code for this semgment
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

object AdditionTerm {
   
  class AddTerm(override val term:StatementSegment) extends AdditionTerm(term) {
   override def createTerm(term:StatementSegment):AdditionTerm = 
     new AddTerm(term)
   
   override def createShareSegment(states:List[StatementSegment]):StatementSegment=
      new AddTerm(term.createShareSegment(states))
  }
  
  class SubTerm(override val term:StatementSegment) extends AdditionTerm(term) {
    override val operator = " - "
    override def createTerm(term:StatementSegment):AdditionTerm = 
     new SubTerm(term)
    override def createShareSegment(states:List[StatementSegment]):StatementSegment=
      new SubTerm(term.createShareSegment(states))
  }
  
  
  
  class Empty(override val term:StatementSegment) extends AdditionTerm(term) {
    override val operator = ""
    override def createTerm(term:StatementSegment):AdditionTerm = 
     new Empty(term)
    override def createShareSegment(states:List[StatementSegment]):StatementSegment=
      new Empty(term.createShareSegment(states))
  }
  
}
