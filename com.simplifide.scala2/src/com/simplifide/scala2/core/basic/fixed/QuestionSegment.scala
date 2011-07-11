/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.Statement
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal

class QuestionSegment(val cond:StatementSegment, 
                      val tr:StatementSegment, 
                      val fa:StatementSegment) extends StatementSegment {
  
  override def getFixedType:FixedType = tr.getFixedType
	
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    val co:SegmentReturn  = writer.createCode(cond)
    val trOut = output.copyWithName(output.getBaseName + "T")
    val faOut = output.copyWithName(output.getBaseName + "F")
    val tru:SegmentReturn = writer.createCode(tr, trOut)  
    val fal:SegmentReturn = writer.createCode(fa, faOut)
    
    val extra = new ListBuffer[Statement]()
    extra.appendAll(co.extraStatements)
    extra.appendAll(tru.extraStatements)
    extra.appendAll(fal.extraStatements)
    
    val builder = new StringBuilder
    builder.append(co.code)
    builder.append(" ? ")
    builder.append(tru.code)
    builder.append(" : ")
    builder.append(fal.code)
    
    val ret = SegmentReturn.segment(builder.toString)
    ret.extraStatements .appendAll(extra)
    return ret
    
  }
  
  /** Get the number of terms for this expression */
  override def getNumberOfTerms:Int = 3
  /** Return the term associated with the nth point */
  override def getTerm(index:StatementSegment.TermIndex):StatementSegment = {
	val nterm = new StatementSegment.TermIndex(0,index.y)
    index.x match {
      case 0 => this.cond.getTerm(nterm)
      case 1 => this.tr.getTerm(nterm)
      case _ => this.fa.getTerm(nterm)
    }
  }
  
  override def convertTerm(index:StatementSegment.TermIndex):StatementSegment = 
	  new QuestionSegment(this.cond.convertTerm(index),this.tr.convertTerm(index),this.fa.convertTerm(index))

  /** Create the segment associated with this code */
  override def createShareSegment(states:List[StatementSegment]):StatementSegment=
    new QuestionSegment(states(0),states(1),states(2))

}

object QuestionSegment {

  def newSegment(condition:StatementSegment,tr:StatementSegment,fa:StatementSegment) =
    new QuestionSegment(condition,tr,fa)

  def newStatement(output:BaseSignal,condition:StatementSegment,tr:StatementSegment,fa:StatementSegment) = {
    new Statement(output,new QuestionSegment(condition,tr,fa))
  }

}
