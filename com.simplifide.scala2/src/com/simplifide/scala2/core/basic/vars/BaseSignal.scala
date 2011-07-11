/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.fixed.FixedSelect
import com.simplifide.scala2.core.basic.flop.SimpleFlop
import com.simplifide.scala2.expression.segments.SignalIndex

class BaseSignal extends StatementSegment{
   
  override def getSignal:BaseSignal = null;
  /** Returns the name of this signal */
  def getName:String = "None"
  /** Return the name without prefixes or postfixes */ 
  def getBaseName:String = "None"
  /** Gets the Fixed Type associated with this signal */
  override def getFixedType:FixedType = null
  /** Returns the I/O Declaration */
  def getIODeclaration:List[SignalDeclarationNew] = List()
  /** Returns the signal declaration */
  def getSignalDeclaration:List[SignalDeclarationNew] = List()
  /** Returns a list of flop segments */
  def getFlopSegments:List[SimpleFlop.Segment] = List()
  /** Returns the delta version of the signal */ 
  def getDelta(y:SignalIndex):BaseSignal = null
  /** Detects if this is the same basic signal. Used to combing segments*/
  def sameBaseSignal(comp:BaseSignal):Boolean = (this.getBaseName.equals(comp.getBaseName))
  /** Copy with a different name */
  def copyWithName(name:String):BaseSignal = new BaseSignal
  
  def copy(name:String,optype:Option[OpType],fix:Option[FixedType],vec:Option[VectorType]) = new BaseSignal

  def copyWithType(optype:OpType) = copy(getName,Some(optype),None,None)

  override def getNumberOfTerms = 1
  override def getTerm(n:StatementSegment.TermIndex):StatementSegment = this
  
   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn  = {
     val sel = new FixedSelect(this,output.getFixedType)
     return writer.createCode(sel)
   }
   override def createCode(writer:CodeWriter):SegmentReturn  = {
     return SegmentReturn.segment(getName)
   }
  
   
}
