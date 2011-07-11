package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.parser.generic.GenericFloatListType
import com.simplifide.generator.blocks.fft.GenericFFTSection
import com.simplifide.scala2.parser.generic._
import collection.mutable.LinkedHashMap
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.complex.{ComplexVectorArray, ComplexVectorGain}
import com.simplifide.generator.blocks.fft.gain.ParallelFftGain
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.module.Project
import com.simplifide.generator.blocks.equalizer.rotation.ParallelCordicRotation

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/10/11
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */

class CdTopParser extends GenericCommandSegment.Creator {
    def createCode(writer:CodeWriter,ret:ReturnMapHolder):SegmentReturn = {
      val clk:FlopControl = ret.getObject[FlopControl](GenericClockheadType.CLOCKHEAD).get

      val name:String     = ret.getObject[String](CdTopParser.NAME).get
      val location:String = ret.getObject[String](CdTopParser.LOCATION).get
      val length:Int      = ret.getObject[Int](CdTopParser.LENGTH).get
      val stages:Int      = ret.getObject[Int](CdTopParser.STAGES).get
      val zero_tones:Int      = ret.getObject[Int](CdTopParser.ZERO_TONES).get

      val fft           = ret.getObject[Project.Basic[ParallelFftGain]](CdTopParser.FFT).get
      val fstage           = ret.getObject[Project.Basic[CdFilterStage]](CdTopParser.FILTER_STAGE).get
      val cordic           = ret.getObject[Project.Basic[ParallelCordicRotation]](CdTopParser.CORDIC).get
      val ifft           = ret.getObject[Project.Basic[ParallelFftGain]](CdTopParser.IFFT).get


      val input = ret.getObject[SignalNew](CdTopParser.SIGNAL_INPUT).get
      val output = ret.getObject[SignalNew](CdTopParser.SIGNAL_OUTPUT).get


      val vectorInSignal = new ComplexVectorArray(input.getName,OpType.ModuleInput,input.getFixedType,length)
      val inGain   = SignalNew.newSignal(input.getName + "_gain",OpType.ModuleInput,FixedTypeMain.unsigned(ParallelFftGain.GAINWIDTH,0))
      val signalIn = new ComplexVectorGain(vectorInSignal,inGain)

      val vectorOutSignal = new ComplexVectorArray(output.getName,OpType.ModuleInput,output.getFixedType,length)
      val outGain   = SignalNew.newSignal(output.getName + "_gain",OpType.ModuleInput,FixedTypeMain.unsigned(ParallelFftGain.GAINWIDTH,0))
      val signalOut = new ComplexVectorGain(vectorOutSignal,outGain)


      val stage = new CdTop(name,location+ "/design/gen",clk,signalIn,signalOut,fft,ifft,fstage,cordic,stages,zero_tones)
      val project = new Project.Basic(name,location,stage)
      writer.createCode(project)


      return SegmentReturn.segment("")
    }
}

object CdTopParser {
  val TITLE              = "cd_filter_top_section"

  val NAME               = "name"
  val LOCATION           = "location"
  val LENGTH             = "length"
  val STAGES             = "stages"
  val ZERO_TONES         = "zero_tones"

  val SIGNAL_INPUT       = "input"
  val SIGNAL_OUTPUT      = "output"
  val FFT                = "fft"
  val FILTER_STAGE       = "filter_stage"
  val CORDIC             = "cordic"
  val IFFT               = "ifft"

  def createCommand:GenericCommandSection = {
    val map = new LinkedHashMap[String,GenericType]()
    map.put(GenericClockheadType.CLOCKHEAD, new GenericClockheadType)
    map.put(NAME    ,new GenericStringType(NAME,"Name of the Block"))
    map.put(LOCATION,new GenericStringType(LOCATION,"Location of the Block"))
    map.put(LENGTH,new GenericIntType(LENGTH,"Number of parallel inputs"))
    map.put(STAGES,new GenericIntType(STAGES,"Number of Filter Stages"))
    map.put(ZERO_TONES,new GenericIntType(ZERO_TONES,"Number of Zeroed out Tones"))


    map.put(FFT,new GenericObjectType(FFT,"Name of the Input FFT"))
    map.put(FILTER_STAGE,new GenericObjectType(FILTER_STAGE,"Name of Filter Stage Definition"))
    map.put(CORDIC,new GenericObjectType(CORDIC,"Name of Cordic Rotation Definition"))
    map.put(IFFT,new GenericObjectType(IFFT,"Name of the Output IFFT"))


    map.put(SIGNAL_INPUT,new GenericSignalType(SIGNAL_INPUT,"Signal Input"))
    map.put(SIGNAL_OUTPUT,new GenericSignalType(SIGNAL_OUTPUT,"Signal Output"))



    val gen = new GenericCommandSection(TITLE,"Chromatic Dispersion Top Filter Section",map, new CdTopParser)


    return gen
  }

}