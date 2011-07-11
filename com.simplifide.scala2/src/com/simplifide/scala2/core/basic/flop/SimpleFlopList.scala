/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.flop

import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.core.basic.operator._
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.context.CurrentContext

import com.simplifide.scala2.core.basic.vars.BaseSignal
import collection.mutable.{LinkedHashMap, ListBuffer}

class SimpleFlopList(val name:Option[String],
                 val head:FlopControl,
                 val reset:List[SimpleFlop.Segment],
                 val enable:List[SimpleFlop.Segment]) extends StatementSegment {

   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
       writer.createCode(this)
     }
  
  /** Creates a reset list for this flop. */
  private def createResetList:BaseCodeSegment = {
    val buffer = new ListBufferSegment()
    for (segment <- reset) {
      val assign = segment.in match {
        case Some(x) => new AssignStatement(segment.out,x)
        case None    => new AssignStatement(segment.out,FlopReset)
      }
      buffer.segments.append(assign)
    }
    return buffer;
  }

  /** Creates an enable list for this flop. */
  private def createEnableList:BaseCodeSegment = {
    val buffer = new ListBufferSegment()
    for (segment <- enable) {
      segment.in match {
        case Some(x) => buffer.segments.append(new AssignStatement(segment.out,x))
        case None    =>
      }
    }
    return buffer;
  }

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val flop = new ResetEnableFlop(name,head,createResetList,createEnableList)
    return writer.createCode(flop)
  }
  
  def createCCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder()
    for (en <- enable.reverse) {
        builder.append(writer.createCode(en.out))
        builder.append(" = ")
        builder.append(writer.createCode(en.in.get))
        builder.append(";\n")
    }
    return SegmentReturn.segment(builder.toString)
  }
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn = {
    return createCCode(writer)
  }
  
  override def createFixedCode(writer:CodeWriter):SegmentReturn = {
    return createCCode(writer)
  }


}

object SimpleFlopList {
  /** Create a new simple flop based the list of inputs and outputs. The outputs are initialized to zero */
  def newFlop(clk:FlopControl,inputs:List[BaseSignal],outputs:List[StatementSegment]):SimpleFlopList = {
    val res = inputs.map(x => new SimpleFlop.Segment(x,None))
    val enas = new ListBuffer[SimpleFlop.Segment]()
    for (i <- 0 until inputs.size) {
      enas.append(new SimpleFlop.Segment(inputs(i),Some(outputs(i))))
    }
    new SimpleFlopList(None,clk,res,enas.toList)
  }
  /** Create a new simple flop based on a linked hashmap */
  def newFlop(clk:FlopControl,linkMap:LinkedHashMap[BaseSignal,StatementSegment]):SimpleFlopList = {
    val res = linkMap.keys.map(x => new SimpleFlop.Segment(x,None)).toList
    val enas = new ListBuffer[SimpleFlop.Segment]()
    for ((key,value) <- linkMap) {
      enas.append(new SimpleFlop.Segment(key,Some(value)))
    }
    new SimpleFlopList(None,clk,res,enas.toList)
  }

}



