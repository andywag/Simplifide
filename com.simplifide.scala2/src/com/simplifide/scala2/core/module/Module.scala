/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.module

import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.util.FileOps
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.core.basic.vars.{OpType, BaseSignal, SignalNew}
import collection.mutable.HashMap

/** Class which defines a module which is consistent with a verilog module */
abstract class Module(val name:String , val location:String) extends StatementSegment{
  
  override def createCode(writer:CodeWriter):SegmentReturn = {
    return null
  }
  /** Creates the code for this segment */
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
     return createCode(writer)
  }
  
  def getInputs:List[SignalNew]
  def getOutputs:List[SignalNew]
  def getSignals:List[SignalNew]
  def getSegments:List[StatementSegment]

  /** Convert all the inputs to an actual module input */
  def getConvertedInput:List[SignalNew] = getInputs.map(x => x.copyWithType(OpType.ModuleInput).asInstanceOf[SignalNew])
  /** Convert all the output to an actual module output */
  def getConvertedOutput:List[SignalNew] = getOutputs.map(x => x.copyWithType(OpType.ModuleOutput).asInstanceOf[SignalNew])


  def createInstance:Instance = createInstance(None,None)
  def createInstance(iname:Option[String],imap:Option[Map[String,String]]):Instance =  {
    val uname = iname.getOrElse(name)
    val umap  = imap.getOrElse(Map[String,String]())
    return new Instance(uname,this,umap)
  }

  /** Create the code for the module head */
  def createHead:String = {
    def commaList(signals:List[SignalNew]):String = {
      val ind = name.length + 7
      val builder = new StringBuilder()
      var first = true
      for (signal <- signals.flatMap(x => x.getFullSignalList)) {
        if (!first) {
          builder.append(",\n"); 
          builder.append(StringOps.writeSpaces(signal.name,ind))
        }
        else {
          builder.append(signal.name)
        }
        first = false;
        
      }
      return builder.toString
    }
    val builder = new StringBuilder()
    builder.append("(")
    builder.append(commaList(getInputs ::: getOutputs))
    //builder.append(commaList(getInputs))
    builder.append(");\n\n")
    return builder.toString
  }
  
  def createSignalDeclaration(signals:List[SignalNew], writer:CodeWriter):String = {
    val decs = signals.flatMap(x => x.getSignalDeclaration)
    val builder = new StringBuilder
    decs.foreach(x => builder.append(writer.createCode(x).code))
    return builder.toString
  }
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn     = {
    val builder = new StringBuilder()
    builder.append("module ")
    builder.append(name)
    builder.append(this.createHead)
    builder.append("\n\n// Input Declarations\n\n")
    builder.append(this.createSignalDeclaration(getConvertedInput,writer))
    builder.append("\n\n// Output Declarations\n\n")
    builder.append(this.createSignalDeclaration(getConvertedOutput,writer))
    builder.append("\n\n// Signal Declarations\n\n")
    builder.append(this.createSignalDeclaration(getSignals,writer))
    builder.append("\n\n// Module Body\n\n")
    this.getSegments.foreach(x => builder.append(writer.createCode(x).code))
    builder.append("endmodule")
    builder.append("\n\n")
    FileOps.createFile(this.location, this.name + ".v",builder)
    return SegmentReturn.segment("")
  }
  
  override def createVerilogCode(writer:CodeWriter,output:BaseSignal):SegmentReturn     = {
    return createVerilogCode(writer)
  }

}

object Module {

  /** Module which is based on a ModuleSegment Class */
  class Segment(override val name:String, override val location:String, val segment:ModuleSegment) extends Module(name,location) {
    def getInputs:List[SignalNew]              = segment.inputs
    def getOutputs:List[SignalNew]             = segment.outputs
    def getSignals:List[SignalNew]             = segment.signals
    def getSegments:List[StatementSegment]     = List(segment)
    val files = segment.getFileList
  }


}
