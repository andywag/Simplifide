/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft.expression

import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.command._
import com.simplifide.scala2.parser.BaseParser
import scala.collection.mutable.HashMap
import com.simplifide.generator.general.NameLocationParser
import com.simplifide.generator.blocks.basic.state.ClockHeadCommand

//import com.simplifide.scala2.parser.general.NameLocationParser

object FftCommand extends CommandSection("parallel_fft") {
  
 def it:CommandSection = this	
	
  val TITLE:String = "parallel_fft"
  
  override val description:String = "Parallel FFT"
  /** List of Commands internal to this command */
  override val commands:List[Command] = List(ClockHeadCommand,NameLocationParser.NameCommand, NameLocationParser.LocationCommand,FftCommand.Length,
                                              FftCommand.AngleFixed, FftCommand.ButterlyFixed, FftCommand.InternalFixed,
                                              FftCommand.Input, FftCommand.Output) 
  /** Keywords associated with this command */
  override val keywords:List[String] = List("length","twiddle_fixed","butterfly_fixed","internal_fixed",
                                "name","location","input_fixed","output_fixed","input","output")
  /** Parser associate with this command */
  def getParser():BaseParser = FftParser
  
  override val commandMap = combineMaps(ClockHeadCommand.commandMap, NameLocationParser.NameCommand.commandMap,  NameLocationParser.LocationCommand.commandMap,FftCommand.Length.commandMap,
                                              FftCommand.AngleFixed.commandMap, FftCommand.ButterlyFixed.commandMap, FftCommand.InternalFixed.commandMap,
                                              FftCommand.Input.commandMap, FftCommand.Output.commandMap)

  override val command = combineCommands(FftCommand.TITLE,commands)
  

  /** Name of the Top Level Module
  object Name extends Command("name") {
    override val command:String = {
      StringOps.createLineSpace(List("name","\"${name}\""), 20)
    }
    override val description:String = "Name of the Top Level Module";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("name" -> new IntParameter("Name of the Module"))
    }
  }

  object Location extends Command("location") {
    override val command:String = {
      StringOps.createLineSpace(List("location","\"${location}\""), 20)
    }
    override val description:String = "Location of the Modules in this block";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("name" -> new IntParameter("Name of the Module"))
    }
  }     */

  /** Length of FFT */
  object Length extends Command("length") {
    override val command:String = {
      StringOps.createLineSpace(List("length","${length}"), 20)
    }
    override val description:String = "Total Length of the FFT (Must be power of 2)";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("length" -> new IntParameter("Length of FFT"))
    }
  }
  /** Fixed Type of Angle */
  object AngleFixed extends Command("twiddle_fixed") {
    override val command:String = {
      StringOps.createLineSpace(List("twiddle_fixed","<${width},${frac}>"), 20)
    }
    override val description:String = "Width of the twiddle factors in the butterfly";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("width" -> new IntParameter("Total Width"),
              "frac"  -> new IntParameter("Number of Fractional Bits"))
    }
  }
  /** Fixed Type of Angle */
  object ButterlyFixed extends Command("butterfly_fixed") {
    override val command:String = {
      StringOps.createLineSpace(List("butterfly_fixed","(<${width0},${frac0}>,<${width1},${frac1}>)"), 20)
    }
    override val description:String = "Internal width inside the butterfly structure";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("width0" -> new IntParameter("Total Width"),
              "frac0"  -> new IntParameter("Number of Fractional Bits"),
              "width1" -> new IntParameter("Total Width"),
              "frac1"  -> new IntParameter("Number of Fractional Bits"))
    }
  }
 /** Fixed Type of Angle */
  object InternalFixed extends Command("internal_fixed") {
    override val command:String = {
      StringOps.createLineSpace(List("internal_fixed","(<${iwidth0},${ifrac0}>,<${iwidth1},${ifrac1}>)"), 20)
    }
    override val description:String = "Internal width inside the butterfly structure";
    override val commandMap:HashMap[String,CommandParameter] = {
       HashMap("iwidth0" -> new IntParameter("Total Width"),
               "ifrac0"  -> new IntParameter("Number of Fractional Bits"),
               "iwidth1" -> new IntParameter("Total Width"),
               "ifrac1"  -> new IntParameter("Number of Fractional Bits"))
    }
  }
  /** Input Command */
  object Input extends Command("input") {
    override val command:String = {
      StringOps.createLineSpace(List("input","\"${iname}\"","<${inwidth},${infrac}>"), 20)
    }
    override val description:String = "Name and Fixed Type of Input Signal";
    override val commandMap:HashMap[String,CommandParameter] = {
       HashMap("iname" -> new IntParameter("Name of Input Signal"),
               "inwidth"  -> new IntParameter("Width of Input Signal"),
               "infrac" -> new IntParameter("Number of Fractional Bits in Input Signal"))
    }
  }
   /** Input Command */
  object Output extends Command("output") {
    override val command:String = {
      StringOps.createLineSpace(List("output","\"${oname}\"","<${outwidth},${outfrac}>"), 20)
    }
    override val description:String = "Name and Fixed Type of Output Signal";
    override val commandMap:HashMap[String,CommandParameter] = {
       HashMap("oname" -> new IntParameter("Name of Output Signal"),
               "outnwidth"  -> new IntParameter("Width of Output Signal"),
               "outfrac" -> new IntParameter("Number of Fractional Bits in Output Signal"))
    }
  }
}
