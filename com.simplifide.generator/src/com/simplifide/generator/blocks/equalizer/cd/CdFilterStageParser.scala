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
import com.simplifide.scala2.parameters.{CoreParameterHolder,CoreParameter}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/10/11
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */

class CdFilterStageParser extends GenericCommandSegment.Creator {
    
	def createCode(writer:CodeWriter,ret:ReturnMapHolder):SegmentReturn = 
		SegmentReturn.segment("")
	override def createCode(writer:CodeWriter,ret:ReturnMapHolder,blockName:String):SegmentReturn = {
      val clk:FlopControl = ret.getObject[FlopControl](GenericClockheadType.CLOCKHEAD).get

      val name:String     = ret.getObject[String](CdFilterStageParser.NAME).get
      val location:String = ret.getObject[String](CdFilterStageParser.LOCATION).get
      val length:Int      = ret.getObject[Int](CdFilterStageParser.LENGTH).get

      val tapWidth = ret.getObject[FixedType](CdFilterStageParser.FILTER_TAPWIDTH).get
      val taps = ret.getObject[List[Float]](CdFilterStageParser.FILTER_TAPS).get
      val filter_taps = taps.map(x => Constant.newConstant(x,tapWidth))

      val params = new CdFilterStage.Params(ret.getObject[Float](CdFilterStageParser.CD_GAIN).get,
                                            ret.getObject[FixedType](CdFilterStageParser.CSD_WIDTH).get,
                                            ret.getObject[FixedType](CdFilterStageParser.CSD_TAP_WIDTH).get,
                                            ret.getObject[FixedType](CdFilterStageParser.CSD_INTERNAL_WIDTH).get,
                                           ret.getObject[Int](CdFilterStageParser.CSD_DELAY).get,
                                           filter_taps,
                                           ret.getObject[Int](CdFilterStageParser.FILTER_DELAY).get,
                                           ret.getObject[FixedType](CdFilterStageParser.FILTER_INWIDTH).get,
                                           ret.getObject[FixedType](CdFilterStageParser.FILTER_OUTWIDTH).get,
                                           ret.getObject[FixedType](CdFilterStageParser.FILTER_INTWIDTH).get,
                                           ret.getObject[FixedType](CdFilterStageParser.ADDER_WIDTH).get)

      val input = ret.getObject[SignalNew](CdFilterStageParser.SIGNAL_INPUT).get
      val output = ret.getObject[SignalNew](CdFilterStageParser.SIGNAL_OUTPUT).get


      val vectorInSignal = new ComplexVectorArray(input.getName,OpType.ModuleInput,input.getFixedType,length)
      val inGain   = SignalNew.newSignal(input.getName + "_gain",OpType.ModuleInput,FixedTypeMain.unsigned(ParallelFftGain.GAINWIDTH,0))
      val signalIn = new ComplexVectorGain(vectorInSignal,inGain)

      val vectorOutSignal = new ComplexVectorArray(output.getName,OpType.ModuleInput,output.getFixedType,length)
      val outGain   = SignalNew.newSignal(output.getName + "_gain",OpType.ModuleInput,FixedTypeMain.unsigned(ParallelFftGain.GAINWIDTH,0))
      val signalOut = new ComplexVectorGain(vectorOutSignal,outGain)


      val stage = new CdFilterStage(name,location+ "/design/gen",clk,signalIn,signalOut,params)
      val project = new Project.Basic(name,location,stage)
      writer.createCode(project)
      CoreParameterHolder.parameters.put(blockName,new CoreParameter.Object(blockName,project))


      return SegmentReturn.segment("")
    }
}

object CdFilterStageParser {
  val TITLE              = "cd_filter_section"

  val NAME               = "name"
  val LOCATION           = "location"

  val LENGTH             = "length"

  val CD_GAIN            = "cd_gain"
  val CSD_WIDTH          = "csd_width"
  val CSD_TAP_WIDTH      = "csd_tap_width"
  val CSD_INTERNAL_WIDTH = "csd_internal_width"
  val CSD_DELAY          = "csd_delay"

  val FILTER_TAPS        = "filter_taps"
  val FILTER_DELAY       = "filter_delay"
  val FILTER_INWIDTH     = "filter_inwidth"
  val FILTER_OUTWIDTH    = "filter_outwidth"
  val FILTER_INTWIDTH    = "filter_intwidth"
  val FILTER_TAPWIDTH    = "filter_tapwidth"


  val ADDER_WIDTH                = "adder_width"

  val SIGNAL_INPUT       = "input"
  val SIGNAL_OUTPUT      = "output"

  def createCommand:GenericCommandSection = {
    val map = new LinkedHashMap[String,GenericType]()
    map.put(GenericClockheadType.CLOCKHEAD, new GenericClockheadType)
    map.put(NAME    ,new GenericStringType(NAME,"Name of the Block"))
    map.put(LOCATION,new GenericStringType(LOCATION,"Location of the Block"))
    map.put(LENGTH,new GenericIntType(LENGTH,"Number of parallel inputs"))
    map.put(CD_GAIN, new GenericFloatType(CD_GAIN,"Chromatic Dispersion Amount"))
    map.put(CSD_WIDTH,new GenericFixedType(CSD_WIDTH,"Fixed Type of the Filter Output"))
    map.put(CSD_TAP_WIDTH,new GenericFixedType(CSD_TAP_WIDTH,"Fixed Type of the Filter Output"))
    map.put(CSD_INTERNAL_WIDTH,new GenericFixedType(CSD_INTERNAL_WIDTH,"Fixed Type of the Filter Output"))
    map.put(CSD_DELAY,new GenericIntType(CSD_DELAY,"CSD Tap Delay"))

    map.put(FILTER_TAPS,new GenericFloatListType(FILTER_TAPS,"Filter Taps"))
    map.put(FILTER_DELAY,new GenericIntType(FILTER_DELAY,"CSD Tap Delay"))
    map.put(FILTER_INWIDTH,new GenericFixedType(FILTER_INWIDTH,"Filter Input Width"))
    map.put(FILTER_OUTWIDTH,new GenericFixedType(FILTER_OUTWIDTH,"Filter Output Width"))
    map.put(FILTER_INTWIDTH,new GenericFixedType(FILTER_INTWIDTH,"Filter Internal Width"))
    map.put(FILTER_TAPWIDTH,new GenericFixedType(FILTER_TAPWIDTH,"Width of Filter Taps"))
    map.put(ADDER_WIDTH,new GenericFixedType(ADDER_WIDTH,"Filter Internal Width"))

    map.put(SIGNAL_INPUT,new GenericSignalType(SIGNAL_INPUT,"Signal Input"))
    map.put(SIGNAL_OUTPUT,new GenericSignalType(SIGNAL_OUTPUT,"Signal Output"))



    val gen = new GenericCommandSection(TITLE,"Chromatic Dispersion Filter Stage",map, new CdFilterStageParser)


    return gen
  }

}