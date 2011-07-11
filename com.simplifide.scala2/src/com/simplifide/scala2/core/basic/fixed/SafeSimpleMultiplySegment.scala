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

class SafeSimpleMultiplySegment( val first:StatementSegment, val second:StatementSegment) extends SimpleMultiplySegment(first,second) {
  override def getSignal = null
  
  
  
  
 

    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    
      val builder = new StringBuilder()
      val fixed:FixedType = getInternalFixed(output)

      val statements = new ListBuffer[Statement]
      // Handle First Term
      val fsig:BaseSignal = createMultOutput(output,0) // Create the first output term
      val fret:SegmentReturn = writer.createCode(first)
      builder.append(fret.code)
      statements.appendAll(fret.extraStatements)
      
      builder.append(" * ")
      // Handle Second Term
      val ssig:BaseSignal = createMultOutput(output,1) // Create the first output term
      val sret:SegmentReturn = writer.createCode(second)
      builder.append(sret.code)
      statements.appendAll(sret.extraStatements)
    
      // Create the return portion of the code
      val ret = SegmentReturn.segment(builder.toString)
      ret.extraStatements.appendAll(statements)
      return ret
    }
    
  
  
}

object SafeSimpleMultiplySegment {
  

  
}
