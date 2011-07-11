/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft

import com.simplifide.scala2.core.basic.Comment
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.VectorType
import com.simplifide.scala2.core.complex.ComplexSignal
import com.simplifide.scala2.core.module.Module
import scala.collection.mutable.ListBuffer

class ParallelButterflyStage(val name:String,
                             val clk:FlopControl,
                             val inputs:ComplexSignal,
                             val outputs:ComplexSignal,
                             val params:Fft.Param, 
                             val depth:Int,
                             val flop:Boolean) extends StatementSegment{
  
  
    
  override def createCode(writer:CodeWriter):SegmentReturn = {
    val returns = new ListBuffer[SegmentReturn]()

    for (i <- 0 until params.length/2) {
      val info:Fft.ButterflyConstant = Fft.getButterflyConstant(this.depth, 2*i, params)
      val com = new Comment.SingleLine("Butterfly " + i + " " + info.debugString)
      returns.append(writer.createCode(com))
    }
    for (i <- 0 until params.length/2) {
      
      val info:Fft.ButterflyConstant = Fft.getButterflyConstant(this.depth, 2*i, params)
      val com = new Comment.SingleLine("Butterfly " + i + " " + info.debugString)
      returns.append(writer.createCode(com))
      
      val sig = new ButterflySegment.IO(inputs.getSlice(info.address0).asInstanceOf[ComplexSignal],
                                        inputs.getSlice(info.address1).asInstanceOf[ComplexSignal],
                                        outputs.getSlice(info.address0).asInstanceOf[ComplexSignal],
                                        outputs.getSlice(info.address1).asInstanceOf[ComplexSignal],
                                        info.angle,
                                        params.angleInternal)
      
      val butterfly = if (params.isDit) {
         new ButterflySegment(this.name + "butterfly_" + depth + "_" + i,
                                           this.clk,
                                           sig,
                                           this.params.butterflyInternal(depth),
                                           params.isFft,
                                           flop)
      }
      else {
         new ButterflySegmentDif(this.name + "butterfly_" + depth + "_" + i,
                                           this.clk,
                                           sig,
                                           this.params.butterflyInternal(depth),
                                           params.isFft,
                                           flop)
      }
      
      returns.append(writer.createCode(butterfly))
    }
    
    
    return SegmentReturn.combineReturns(returns.toList, List())
  }
  
  override def createCode(writer:CodeWriter,out:BaseSignal):SegmentReturn = createCode(writer)
  
}

object ParallelButterflyStage {
   case class ModuleBase(override val name:String,
                         override val location:String, 
                                  val fft:ParallelButterflyStage) extends Module(name,location) {
     
     override def getInputs:List[SignalNew] = {
        val buffer = new ListBuffer[SignalNew]
        buffer.appendAll(fft.clk.getAllSignals(OpType.ModuleInput))
        buffer.append(fft.inputs)
        return buffer.toList
      }

      override def getOutputs:List[SignalNew]         = List(fft.outputs)
     
      override def getSignals:List[SignalNew]         = List()
      
      override def getSegments:List[StatementSegment] = List(fft)
    
    
    }
}
