/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.module

import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.SignalNew
import scala.collection.immutable.HashMap
import com.simplifide.scala2.util.StringOps
import collection.mutable.ListBuffer

class Instance(val name:String,val module:Module,val map:Map[String,String]) extends StatementSegment{
  /** Creates the code for this segment */
  def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
     return null
  }

  /** Method which connects the signal ports */
  private def createInvidividualSignal(signal:SignalNew):String = {
    val builder = new StringBuilder()
    builder.append(".")
    builder.append(signal.name)
    builder.append("(")
    map.get(signal.name) match {
      case None    => builder.append(signal.name)
      case Some(x) => builder.append(x)
    }

    builder.append(")")
    return builder.toString
  }

  /** Method which splits vector type signals into individual signals */
  private def createComplexSignal(signal:SignalNew):List[String] = {
    val base = signal.getFullSignalList
    val decs = new ListBuffer[String]
    for (i <- 0 until base.length) {
      decs.append(createInvidividualSignal(base(i)))
    }
    return decs.toList
  }

  /** Create the Instance Head */
  private def createHeadList(signals:List[SignalNew],indent:Int):String  = {

    val decs = new ListBuffer[String]()
    for (signal <- signals) {
      decs.appendAll(createComplexSignal(signal))
    }
    val builder = new StringBuilder()
    var first = true
    for (dec <- decs) {
      if (!first) {
        builder.append(",\n")
        builder.append(StringOps.writeSpaces(dec,indent))
      }
      else {
        builder.append(dec)
        first = false
      }
    }
    return builder.toString
  }
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn     = {
    val indent = module.name.size + this.name.size + 2
    val builder = new StringBuilder()
    builder.append(module.name)
    builder.append(" ")
    builder.append(this.name)
    builder.append("(")
    builder.append(createHeadList(module.getInputs ::: module.getOutputs,indent))
    builder.append(");\n\n")
    return SegmentReturn.segment(builder.toString)
  }
  
  override def createVerilogCode(writer:CodeWriter,output:BaseSignal):SegmentReturn     = {
    return createVerilogCode(writer)
  }
  
}

object Instance {

  def simple(module:Module):Instance = new Instance(module.name,module,Map())

}
