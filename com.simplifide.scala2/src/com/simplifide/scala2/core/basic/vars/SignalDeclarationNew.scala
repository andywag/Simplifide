/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment


/** Class which handles the creation of a signal declaration */
class SignalDeclarationNew(val signal:SignalNew) extends StatementSegment {

 
  /** Returns the verilog declaration type associated with this declaration*/
  val verilogDecType:String = "wire "
  
   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
       writer.createCode(this)
     }
  
  // The basic verilog declaration 
    def createVerilogSignalItem(signal:SignalNew, typ:String, name:String):String = {
        def createWidthDeclaration(signal:SignalNew):String = {
          val builder = new StringBuilder
          if (signal.fixed.width > 1) {
            builder.append("[")
            builder.append(signal.fixed.width-1)
            builder.append(":0] ")
          }
          return builder.toString
        }
        def createArrayDeclaration(signal:SignalNew):String = {
          val builder = new StringBuilder
          if (signal.arrayLength > 0) {
            builder.append("[0:")
            builder.append(signal.arrayLength)
            builder.append("] ")
          }
          return builder.toString
        }
        val builder = new StringBuilder
        builder.append(typ)
        builder.append(" ")
        if (signal.fixed.signed.isSigned) builder.append("signed ")
        builder.append(createWidthDeclaration(signal))
        builder.append(name)
        builder.append(createArrayDeclaration(signal))
        builder.append("; ")
        builder.append(" // ")
        builder.append(signal.fixed.getDescription)
        builder.append("\n")
        return builder.toString
    }
  
  
  // Create the registers which are dependent on this signal
    def createVerilogExtra(signal:SignalNew):String = {
      val builder = new StringBuilder
      for (vec <- signal.vector.arr) {
        for (i <- 0 until vec) {
          builder.append(createVerilogSignalItem(this.signal,"reg",signal.name + "_" + (i+1).toString))
        }
      }
      return builder.toString
    }
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    
   
    
    val builder = new StringBuilder
    builder.append(createVerilogSignalItem(this.signal,verilogDecType,signal.name))
    //builder.append(createVerilogExtra(signal))
    
    return SegmentReturn.segment(builder.toString)
  }
  
   /** Creates the basic signal declaration */
    def createCDeclaration( name:String, prefix:String,postfix:String):String = {
      val builder = new StringBuilder
      builder.append(prefix)
      builder.append(name)
      builder.append(postfix)
      builder.append(";\n")
      builder.toString
    }
    /** Creates the extra registers associated with this signal */
    def createCExtra(signal:SignalNew):String = {
      val builder = new StringBuilder
      for (vec <- signal.vector.arr) {
        for (i <- 0 until vec) {
          builder.append(createCDeclaration(this.signal.name + "_" + (i+1).toString,"static float "," = 0.0"))
        }
      }
      return builder.toString
    }
  
  protected def createCItem(writer:CodeWriter):SegmentReturn = {
   
    val builder = new StringBuilder
    builder.append(createCDeclaration(signal.name,"float ",""))
    builder.append(createCExtra(signal))
    return SegmentReturn.segment(builder.toString)
  }
  
  override def createFloatCode(writer:CodeWriter):SegmentReturn = return createCItem(writer)
  override def createFixedCode(writer:CodeWriter):SegmentReturn = return createCItem(writer)
   
}

object SignalDeclarationNew {
  
  def createDeclaration(signals:List[SignalNew]):List[SignalDeclarationNew] = {
    return signals.flatMap(x => x.getSignalDeclaration)
  }
  
  abstract class IO(override val signal:SignalNew) extends SignalDeclarationNew(signal) {
    
    def getCIoType:String = {
    	if (signal.getFixedType.signed.isControl) "int " 
        else if (signal.opType.isOutput) "float *"
    	else "float ";
    }
    
    override protected def createCItem(writer:CodeWriter):SegmentReturn = {
      val builder = new StringBuilder
      builder.append(getCIoType)
      builder.append(signal.getName)
      return SegmentReturn.segment(builder.toString)
    }
    override def createFloatCode(writer:CodeWriter):SegmentReturn = return createCItem(writer)
    override def createFixedCode(writer:CodeWriter):SegmentReturn = return createCItem(writer)
    override def createHeaderCode(writer:CodeWriter):SegmentReturn = return createCItem(writer)
  }
  
  class Input(override val signal:SignalNew) extends IO(signal) {
    override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
      val builder = new StringBuilder
      builder.append(createVerilogExtra(signal))
      return SegmentReturn.segment(builder.toString)
    }
  }
  class Output(override val signal:SignalNew) extends IO(signal) {
    
  }
  class IOExtra(override val signal:SignalNew) extends IO(signal) {
    override protected def createCItem(writer:CodeWriter):SegmentReturn = {
      val builder = new StringBuilder
      builder.append(this.createCExtra(signal))
      return SegmentReturn.segment(builder.toString)
    }
  }
  
  
  
  class Signal(override val signal:SignalNew) extends SignalDeclarationNew(signal) {
  }
  
  class Reg(override val signal:SignalNew) extends SignalDeclarationNew(signal) {
    override val verilogDecType:String = "reg "
  }
  
  class ModuleInput(override val signal:SignalNew) extends SignalDeclarationNew(signal) {
     override val verilogDecType:String = "input "
  }
  class ModuleOutput(override val signal:SignalNew) extends SignalDeclarationNew(signal) {
    override val verilogDecType:String = "output "
  }
  
}

