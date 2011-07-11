/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.complex.ComplexSignal

abstract class Fft extends StatementSegment{

}

object Fft {
  /** Class Describing Signals for the FFT */
  case class Signals(clk:FlopControl,
                     inputName:String,
                     outputName:String)
  /** Class Defining parameters for the FFT */
  case class Param(val length:Int, 
                   val radix:Int,
                   val isFft:Boolean,
                   val isDit:Boolean,
                   val inFixed:FixedType,
                   val outFixed:FixedType,
                   val angleInternal:FixedType,
                   val butterflyInternal:List[FixedType],
                   val stageInternal:List[FixedType]) {
    def depth:Int = (math.log10(length)/math.log10(2.0)).toInt
  }
  /** Class Defining Information for an individual butterfly stage */
  case class ButterflyConstant(address0:Int, address1:Int,angle:Double) {
     val uang          = math.Pi*angle
     val ang_val       = "(" + math.cos(uang) + "+j" + math.sin(uang) + ")"
     def debugString:String = "(" + address0 + "," + address1 + ")" + angle + ang_val
  }


  def bitReverse(value:Int,num:Int):Int = {
    // Kludgey way of handling this. Add a one to the top bit
    // And the remove it to make sure all the bits are created with toBinaryString
    val sh  = value + math.pow(2.0,num).toInt
    val bits = sh.toBinaryString.substring(1)
    var nvalue:Int = 0;
    for (i <- 0 until num) {
      val bit = bits.substring(i,i+1)
      if (bit == "1") nvalue += math.pow(2.0,i).toInt
    }
    return nvalue
  }
  /** Returns the values for the butterfly contents associated with  
      a decimate in time butterfly circuit
   */


  
  def getButterflyConstant(stage:Int,address:Int,param:Param):ButterflyConstant = {
    val stdiv           = math.pow(2.0, stage).toInt                   // Stage Used to Calculate Butterfly Divisor
    val fsize           = param.length/stdiv                           // Size of Sub FFT

    val addressNorm     = address % fsize                              // Remainder of Address Normalized
    val baseNorm        = address / fsize                              // Base of Normalized Address
    val addressNorm1    = (address+1) % fsize                          // Remainder of Address Normalized
    val baseNorm1       = (address+1) / fsize                          // Base of Normalized Address

    val raddress0       = bitReverse(addressNorm ,param.depth - stage) // Bit Reverse the Address for Correct Calcuation
    val raddress1       = bitReverse(addressNorm1,param.depth - stage) // Bit Reverse the Address for Correct Calcuation
    val realAddress0    = fsize*baseNorm  + raddress0
    val realAddress1    = fsize*baseNorm1 + raddress1

    // Decimation in frequency values
    val stdivF          = math.pow(2.0, param.depth - stage-1).toInt     // Divisor for DIF Angle
    val fsizeF          = param.length/stdivF
    val baseNormF       = address/fsizeF

    val baseFAddress     = (address % fsize)/2
    val revFAngle        = bitReverse(baseFAddress,param.depth - stage-1)
    val baseFAngle             = 2.0*revFAngle.toDouble/fsize.toDouble


    val baseAngle       = bitReverse(baseNorm,stage) 
    val angle           = if (param.isDit) baseAngle.toDouble/stdiv.toDouble else baseFAngle

    return new Fft.ButterflyConstant(realAddress0,realAddress1,angle)
  }

  
  
  
}
