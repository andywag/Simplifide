/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.fixed.AdditionSegment.Trunc
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.VectorType
import com.simplifide.scala2.top.InterfaceError


abstract class MultiplySegment(val first:StatementSegment,val second:StatementSegment) extends StatementSegment {
  
  override def getSignal = null
  override val newStatement:Boolean = true
  override def getFixedType:FixedType = 
	  first.getFixedType.plus(second.getFixedType)
  /** Returns the number of terms associated with this segment */
  override def getNumberOfTerms:Int = 2
  /** Returns the term at index n */
  override def getTerm(n:StatementSegment.TermIndex):StatementSegment = {
    if (n.x == 0) return first.getTerm(n);
    else if (n.x == 1) return second.getTerm(n);
    else return null;
  }
  /** Create a shared segment */
  override def createShareSegment(states:List[StatementSegment]):StatementSegment = {
      val fir = this.first.createShareSegment(List(states(0))) 
      val sec = this.second.createShareSegment(List(states(1)))
      return createShareSegmentInternal(fir, sec)
    }
  
  def createShareSegmentInternal(fir:StatementSegment,sec:StatementSegment):StatementSegment
  
  def createAdderSegment(mult:StatementSegment):StatementSegment;
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
     
     // Create the multiplication segment, Return error if no multiplication segment exists
     val multO = first.createMultSegment(second)
     if (multO == None) return new SegmentReturn("",List(new InterfaceError.Error(0,"Signal or Constant Required")))
     // Create the statement associated with the multiplier */
     val mult = multO.get
     val outM = new SignalNew(output.getName + "M",OpType.Signal,mult.getMultFixed(output.getFixedType.signed),VectorType.NoVector)
     val state = new Statement.Internal(outM,mult)
     // Create the Adders statement associated with this multiplier */
     val adder = createAdderSegment(outM)
     val ret = adder.createCode(writer,output)
     ret.extraStatements.prepend(state)
     return ret    
  }

}

object MultiplySegment {
  class Trunc(override val first:StatementSegment,override val second:StatementSegment) extends MultiplySegment(first,second) {
    
    
    
    def createAdderSegment(mult:StatementSegment):StatementSegment =  
      return new AdditionSegment.Trunc(List(mult))
    
    def createShareSegmentInternal(fir:StatementSegment,sec:StatementSegment):StatementSegment = 
      return new Trunc(fir,sec)
    
  }
  
  abstract class Fixed(override val first:StatementSegment,override val second:StatementSegment,val internal:FixedType) extends MultiplySegment(first,second)
  
  class TruncFixed(override val first:StatementSegment,
                        override val second:StatementSegment,
                        override val internal:FixedType) extends Fixed(first,second,internal) {
    
    def createAdderSegment(mult:StatementSegment):StatementSegment = 
      return new AdditionSegment.TruncFixed(List(mult),internal)

    def createShareSegmentInternal(fir:StatementSegment,sec:StatementSegment):StatementSegment =
      return new TruncFixed(fir,sec,internal)
    
  }

  
 
  
  class Round(override val first:StatementSegment,
                   override val second:StatementSegment,
                   override val internal:FixedType) extends Fixed(first,second,internal) {
    
    def createAdderSegment(mult:StatementSegment):StatementSegment = 
      return new AdditionSegment.Round(List(mult),internal)
   
    def createShareSegmentInternal(fir:StatementSegment,sec:StatementSegment):StatementSegment = {
      
      return new Round(fir,sec,internal)
    }

  }
  
  class TruncClip(override val first:StatementSegment,
                        override val second:StatementSegment,
                        override val internal:FixedType) extends Fixed(first,second,internal) {
   def createAdderSegment(mult:StatementSegment):StatementSegment = 
      return new AdditionSegment.TruncClip(List(mult),internal)
    
   def createShareSegmentInternal(fir:StatementSegment,sec:StatementSegment):StatementSegment = {
      return new TruncClip(fir,sec,internal)
    }
  }
  
  class RoundClip(override val first:StatementSegment,
                       override val second:StatementSegment,
                       override val internal:FixedType) extends Fixed(first,second,internal) {
    
    def createAdderSegment(mult:StatementSegment):StatementSegment = 
      return new AdditionSegment.RoundClip(List(mult),internal)
    
    def createShareSegmentInternal(fir:StatementSegment,sec:StatementSegment):StatementSegment = {
      return new RoundClip(fir,sec,internal)
    }
  }
  
}
