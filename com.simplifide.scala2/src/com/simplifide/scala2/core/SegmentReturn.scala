/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core

import basic.{StatementSegment, Statement}
import com.simplifide.scala2.top.InterfaceError
import scala.collection.mutable.ListBuffer
class SegmentReturn(val code:String, val errors:List[InterfaceError]) {

  val extraStatements:ListBuffer[Statement] = new ListBuffer[Statement]()
  
  override def toString():String = code
  
  def combine(in:SegmentReturn ):SegmentReturn = {
    val ret = new SegmentReturn(this.code + in.code, this.errors ::: in.errors)
    ret.extraStatements.appendAll(this.extraStatements)
    ret.extraStatements.appendAll(in.extraStatements)
    return ret
  }

}



object SegmentReturn {

  def segment(code:String)        = new SegmentReturn(code,List())
  def segment(error:InterfaceError) = new SegmentReturn("",List(error))
  def error(error:String)         = segment(new SegmentError(error))


  def combineBufferReturns(writer:CodeWriter,segs:ListBuffer[BaseCodeSegment]):SegmentReturn = {
    val returns = segs.map(x => writer.createCode(x)).toList
    return combineReturns(returns)
  }

  def combineReturns(writer:CodeWriter,segs:ListBuffer[BaseCodeSegment]):SegmentReturn = {
    val returns = segs.map(x => writer.createCode(x)).toList
    return combineReturns(returns)
  }
  def combineReturns(segs:List[SegmentReturn]):SegmentReturn = combineReturns(segs,List())

  def combineReturns(segs:List[SegmentReturn], extra:List[InterfaceError]):SegmentReturn = {
     val builder = new StringBuilder()
     val buffer  = new ListBuffer[InterfaceError]()
     buffer.appendAll(extra)
     for (seg <- segs) {
       builder.append(seg.code)
       buffer.appendAll(seg.errors)
     }
    return new SegmentReturn(builder.toString,buffer.toList)
  }
  
  

  class NotDefined(override val code:String, cla:String) extends SegmentReturn(code,List(new SegmentError(cla + " Not Defined")))
}

 