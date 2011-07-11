/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import scala.collection.mutable.ListBuffer

class VectorGroup(statement:Statement) extends StatementSegment {

  def createCodeInternal(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    if (output.getNumber == 0) return writer.createCode(statement,output)
    val returns = new ListBuffer[SegmentReturn]()
    for (i <- 0 until output.getNumber) {
      val uout = output.getSegment(i).asInstanceOf[BaseSignal]
      val uin  = statement.input.getSegment(i)
      val state = new Statement(uout,uin)
      returns.append(writer.createCode(state,uout))
    }
    return SegmentReturn.combineReturns(returns.toList, List())
  }

  override def createCode(writer:CodeWriter):SegmentReturn =
    createCodeInternal(writer,statement.output)
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCodeInternal(writer,output)
  }
  
}
