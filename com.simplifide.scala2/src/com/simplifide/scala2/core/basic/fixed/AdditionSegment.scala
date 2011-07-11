 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.ConstantValue
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.VectorType
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.Statement


abstract class AdditionSegment(val terms:List[StatementSegment]) extends StatementSegment{
   
  override val newStatement:Boolean = true
  override def getSignal = null
  
  override def getNumber:Int = {
    var max = 0;
    terms.foreach(x => if (x.getNumber > max) max = x.getNumber)
    return max
  }
  /** Return the segment associated with the input index*/
  override def getSegment(index:Int):StatementSegment = {
    if (this.getNumber == 0) return this
    val nseg = terms.map(x => x.getSegment(index))
    return this.createShareSegmentInternal(nseg)
  }
  
  
  /** Describes whether this is a shared term which is bypassed */
  private def isSharePass:Boolean = {
	  if (terms.size == 1) {
        val tot = terms(0).getNumberOfTerms
        if (tot >= 1) return true
      }
	  return false
  }
  /** Returns the number of terms associated with this segment 
   *  For this case, it potentially bypasses the segemnt if the single 
   *  child has more than one term
   * */
  override def getNumberOfTerms:Int = {
     if (isSharePass) return terms(0).getNumberOfTerms
     return terms.size
  }
  /** Returns the term at index n */
  override def getTerm(n:StatementSegment.TermIndex):StatementSegment = {
    if (isSharePass) return terms(0).getTerm(n)
    val rterm = terms(n.x)
    return rterm.getTerm(n)
  }
  
  
  
    
  
  def createShareSegmentInternal(states:List[StatementSegment]):StatementSegment
  /** Create a shared segment */
  override def createShareSegment(states:List[StatementSegment]):StatementSegment = {
    
    // If this block is bypassed create the next lower level segment
    if (isSharePass) {
      val seg = terms(0).createShareSegment(states)
      val ret = this.createShareSegmentInternal(List(seg))
      return ret
    }
    
    val segments = new ListBuffer[StatementSegment]();
    var index:Int = 0;
    
    for (term <- terms) {
      segments.append(term.createShareSegment(List(states(index))))
      index = index + 1;
    }
    return createShareSegmentInternal(segments.toList)
  }
      
    
  
  /** Returns the fixed type which is used internally for the add segment */
  def getInternalFixed(output:BaseSignal):FixedType;
  /** Get the signal associated with the clip statement */
  def getClipOutput(output:BaseSignal,  fixC:FixedType) = new SignalNew(output.getName + "C",OpType.Signal,fixC,VectorType.NoVector)
  /** Get the signal associated with the round statement */
  def getRoundOutput(output:BaseSignal, fixC:FixedType) = new SignalNew(output.getName + "R",OpType.Signal,fixC,VectorType.NoVector)
  /** Create the output for this operation which is a new signal if a statement is created */
  def createOutput(output:BaseSignal,fixed:FixedType,index:Int):BaseSignal = {
    val name = output.getName + "_i" + index
    new SignalNew(name,OpType.Signal,fixed,VectorType.NoVector)
  }
  /** Create the basic adder statement */
  
  def createAddStatement(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      val builder = new StringBuilder()
      // Get the internal fixed width associated with this segment
      val fixed:FixedType = getInternalFixed(output)
      // List of extra statements created from this operation
      val statements = new ListBuffer[Statement]
      var index:Int = 0;
      for (term <- terms) {
        // Create a new signal which will be used as the input for this segment
        // This new signal is always passed down when creating the term
        val sig:BaseSignal = createOutput(output,fixed,index)
        index = index + 1
        val returnSeg = writer.createCode(term, sig)
        builder.append(returnSeg.code)
        statements.appendAll(returnSeg.extraStatements)
      }
      val ret = SegmentReturn.segment(builder.toString)
      
      ret.extraStatements.appendAll(statements)
      return ret
    }

}

object AdditionSegment {
  
  /** Truncation Statement */
  class Trunc(override val terms:List[StatementSegment]) extends AdditionSegment(terms) {
    
    override def getInternalFixed(output:BaseSignal):FixedType = output.getFixedType;
    
    def createShareSegmentInternal(states:List[StatementSegment]):StatementSegment = 
      return new Trunc(states)
    
    /** Delegates to a truncation with the output fixed width */
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      val seg = new AdditionSegment.TruncFixed(terms,output.getFixedType)
      return writer.createCode(seg, output)
    }
    
  }
  
  abstract class Fixed(override val terms:List[StatementSegment],val internal:FixedType) extends AdditionSegment(terms) {
    
    override def getInternalFixed(output:BaseSignal):FixedType = internal
    
  }
  
  class TruncFixed(override val terms:List[StatementSegment],override val internal:FixedType) extends Fixed(terms,internal) {
    
    override def getFixedType:FixedType = internal
    def createClipStatement(output:BaseSignal,input:BaseSignal):Option[Statement] = None
    def getRoundConstant(output:BaseSignal):Option[AdditionTerm] = None
    
    override def createShareSegmentInternal(states:List[StatementSegment]):StatementSegment = 
      return new TruncFixed(states,internal)
    /** Create the round statement, defaults to truncation */
     def createRoundStatement(output:BaseSignal,outS:BaseSignal):Statement = {
        val trunc = new RoundSegment.Trunc(terms,internal)//new Trunc(tterms)
        val state = new Statement.Internal(outS,trunc)
        return state;
     }
    
    /** Creates the clipping statement for RoundClip and TruncClip */
    def createRealClip(output:BaseSignal,input:BaseSignal):Option[Statement] = {
      
      val outIntW = output.getFixedType.width - output.getFixedType.frac
      val intIntW = internal.width - internal.frac
      
        if (intIntW <= outIntW) return None
      
        val fixC = new FixedTypeMain(internal.signed,internal.width - (intIntW-outIntW) ,internal.frac)
        val fixS = getClipOutput(output,fixC)
        
        val clip = new ClipSegment(input,fixC)
        val seg  = new Statement.Internal(fixS,clip)
      
        return Some(seg)
    }
    
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
        // Create the Round Statement
        val outS = getRoundOutput(output,internal)
        val state:Statement = this.createRoundStatement(output,outS)
        // Create the Clip Statement
        val clip:Option[Statement] = this.createClipStatement(output,outS) 
        val sel = clip match {
          case None    => new FixedSelect(outS,output.getFixedType)
          case Some(x) => new FixedSelect(x.output,output.getFixedType)
        }
        val seg = writer.createCode(sel)
        seg.extraStatements.append(state) 
        clip.foreach(x => seg.extraStatements.append(x))
        return seg
      
    }
  }
  
  class TruncClip(override val terms:List[StatementSegment],override val internal:FixedType) extends TruncFixed(terms,internal) {
	   override def createClipStatement(output:BaseSignal,input:BaseSignal):Option[Statement] = this.createRealClip(output, input)
      override def createShareSegmentInternal(states:List[StatementSegment]):StatementSegment = 
        return new TruncClip(states,internal) 
  }
  
  class Round(override val terms:List[StatementSegment],
                   override val internal:FixedType) extends TruncFixed(terms,internal) {
    
    override def getFixedType:FixedType = internal
    override def getInternalFixed(output:BaseSignal):FixedType = internal
    override def createShareSegmentInternal(states:List[StatementSegment]):StatementSegment = 
        return new Round(states,internal) 
      
    override def createRoundStatement(output:BaseSignal, outS:BaseSignal):Statement = {
      val seg = new RoundSegment.Round(terms,output,internal)
      return new Statement.Internal(outS,seg);
    }
    
    override def getRoundConstant(output:BaseSignal):Option[AdditionTerm] = {
      //val fi = new FixedType(internal.signed,internal.width, internal.frac)
      val doa = math.pow(2.0, -output.getFixedType.frac - 1)
      val constant = new Constant("",VectorType.NoVector,internal,new ConstantValue.FloatValue(doa.toFloat))
      Some(new AdditionTerm.AddTerm(constant))
     
    }
    
  }
  
   /** Most often used statement which contains a round and a clip */
   class RoundClip(override val terms:List[StatementSegment],override val internal:FixedType) extends Round(terms,internal) {
       override def createClipStatement(output:BaseSignal,input:BaseSignal):Option[Statement] = this.createRealClip(output, input)
    
      override def createShareSegmentInternal(states:List[StatementSegment]):StatementSegment = 
        return new RoundClip(states,internal)  
  }
  
  
  
}
