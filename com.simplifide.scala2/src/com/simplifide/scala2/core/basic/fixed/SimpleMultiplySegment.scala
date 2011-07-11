/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed


import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.Signing
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.VectorType
import scala.collection.mutable.ListBuffer

class SimpleMultiplySegment(first:StatementSegment,second:StatementSegment) extends StatementSegment {
  override def getSignal = null
  def getInternalFixed(output:BaseSignal):FixedType = output.getFixedType;
  
    /** Create the multiplier output */
  def createMultOutput(output:BaseSignal,index:Int):BaseSignal = {
	 val name = output.getName + "_i" + index
	 new SignalNew(name,OpType.Signal,this.getFixedType,VectorType.NoVector)
  }
  
  
  def getMultFixed(sign:Signing):FixedType = {
    
    val wid  = first.getFixedType.width + second.getFixedType.width
    val frac = first.getFixedType.frac + second.getFixedType.frac 
    
    return new FixedTypeMain(sign,wid,frac)
  }

    def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    
      val builder = new StringBuilder()
      val fixed:FixedType = getInternalFixed(output)

      val statements = new ListBuffer[Statement]
      // Handle First Term
      val fsig:BaseSignal = createMultOutput(output,0) // Create the first output term
      val fret:SegmentReturn = first.createCode(writer)
      builder.append(fret.code)
      statements.appendAll(fret.extraStatements)
      // Handle Second Term
      val ssig:BaseSignal = createMultOutput(output,1) // Create the first output term
      val sret:SegmentReturn = second.createCode(writer,output)
      builder.append(sret.code)
      statements.appendAll(sret.extraStatements)
    
      // Create the return portion of the code
      val ret = SegmentReturn.segment(builder.toString)
      ret.extraStatements.appendAll(statements)
      return ret
    }
    
  
  
}

object SimpleMultiplySegment {
  
  class Simple(first:StatementSegment,second:StatementSegment) extends SimpleMultiplySegment(first,second)
  
  class Negative(first:StatementSegment,second:StatementSegment) extends SimpleMultiplySegment(first,second) {
    
  }
  
  class Csd(first:StatementSegment,second:StatementSegment) extends SimpleMultiplySegment(first,second) {
    
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      val con:Constant = first.asInstanceOf[Constant]
      val segments = new ListBuffer[StatementSegment]();
      val items:List[Constant.CSD] = con.createCSD       // Calculate the terms in this multiply
      
      val frac = first.getFixedType.frac
      var fir:Boolean = true;
      for (item <-items) {
    	  val sh:Int = frac - item.value
    	  val signal = second.getSignal
        val fsel:FixedSelect = new FixedSelect.Scale(signal,output.getFixedType,sh)
        val seg = if (fir) {
        	fir = false
        	if (item.negative) new AdditionTerm.SubTerm(fsel)
        	else new AdditionTerm.Empty(fsel);
        }
        else if (item.negative) new AdditionTerm.SubTerm(fsel)
        else                    new AdditionTerm.AddTerm(fsel)
        segments.append(seg)
      }
      val ro = new SimpleAdditionSegment(segments.toList)
      val ret = writer.createCode(ro, output)
      val rret = new SegmentReturn(ret.code,ret.errors)
      rret.extraStatements.appendAll(ret.extraStatements)
      return rret
    }
  }
  
}
