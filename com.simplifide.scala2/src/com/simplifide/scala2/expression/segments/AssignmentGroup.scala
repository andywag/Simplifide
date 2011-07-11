/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars.Signing
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Comment
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.statement.StatementMux
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.VectorType
import scala.collection.mutable.ListBuffer

class AssignmentGroup(val flop:FlopControl,val states:List[Statement]) extends StatementSegment{

  def getComment:Option[Comment] = None
  override def getSignal = null
  
  
  def createBaseCode(writer:CodeWriter,output:Option[BaseSignal]):SegmentReturn = {
     if (states.length == 0) return new SegmentReturn("",List()) // 
     else if (states.length == 1) return { // if Only 1 segment just create the code
    	 val state = states(0)
    	 output match {
    		 case None    => {
                     if (state.output.getNumber == 0) {
                       return writer.createCode(state)
                     }
                     else {
                       val vec = new VectorGroup(state)
                       return writer.createCode(vec,state.output)
                     }
    			 
    		 }
    		 case Some(x) => {
                     if (x.getNumber > 0) {
                       val vec = new VectorGroup(state)
                       return writer.createCode(vec,x)
                     }
                    return writer.createCode(state, x)
                 }
    	 }
     }
     
     // Create the Muxes for the Terms in the statement
     var ret   = new SegmentReturn("",List())
     val signals = new ListBuffer[StatementSegment]();
     val template = states(0)
     val nterms = template.input.getNumberOfTerms
     val index = flop.getIndex.get.getSignal // Need to handle errors
     for (i <- 0 to nterms-1) {
       
       // Create the output signal
       val outfix = template.output.getFixedType
       val term1 = template.input.getTerm(new StatementSegment.TermIndex(i,0))
       val termfix = term1.getFixedType
       //template.output.copyWithName(template.output.getName + "_m" + i)
       val na = template.output.getName + "_m" + i
       val rout = template.output.copy(na,Some(OpType.Reg),
                                       Some(termfix),Some(VectorType.NoVector))
       
       signals.append(rout)
       // Create the Declaration
       val rcout   = new SignalNew(rout.getName,OpType.Reg,termfix,VectorType.NoVector)
       val decs    = rcout.getSignalDeclaration
       decs.foreach(x => ret = ret.combine(writer.createCode(x)))
       
       // Get the terms and create the mux associated with it
       //val terms:List[StatementSegment] = states.map(x => x.input.getTerm(i))
      val terms = new ListBuffer[StatementSegment]() 
      for (j <- 0 to states.size - 1) {
          val state = states(j)
          val term = state.input.getTerm(new StatementSegment.TermIndex(i,j))
          terms.append(term)          
       } 
       val mux = new StatementMux(index,rout,terms.toList)
       ret = ret.combine(writer.createCode(mux,rout))
    	 
     }
     // Create the Segment which is used for this object
     val seg = template.input.createShareSegment(signals.toList)
     val state = new Statement(template.output,seg)
     val comb = writer.createCode(state)
     ret = ret.combine(comb)
     return ret
  }
  
   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createBaseCode(writer,Some(output))
  }
  
  override def createCode(writer:CodeWriter):SegmentReturn = {
     return createBaseCode(writer,None)
  }
  
}
