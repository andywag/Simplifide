package com.simplifide.generator.blocks.fft

import collection.mutable.LinkedHashMap
import com.simplifide.scala2.parser.generic._
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.vars.{FixedType, SignalNew}
import com.simplifide.scala2.clocks.FlopControl
import gain.ParallelFftGain
import com.simplifide.scala2.core.module.{Project, Module}
import com.simplifide.scala2.parameters.{CoreParameter, CoreParameterHolder}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */

class GenericFFTSection extends GenericCommandSegment.Creator {
    override def createCode(writer:CodeWriter,ret:ReturnMapHolder):SegmentReturn = createCode(writer,ret,"")

    override def createCode(writer:CodeWriter,ret:ReturnMapHolder,blockName:String):SegmentReturn = {

      val name:String     = ret.getObject[String](GenericFFTSection.NAME).get
      val location:String = ret.getObject[String](GenericFFTSection.LOCATION).get
      val input:SignalNew  = ret.getObject[SignalNew](GenericFFTSection.INPUT).get
      val output:SignalNew = ret.getObject[SignalNew](GenericFFTSection.OUTPUT).get

      val params = new Fft.Param(ret.getObject[Int](GenericFFTSection.LENGTH).get,
                                 1,
                                 ret.checkBoolean(GenericFFTSection.TYPE,GenericFFTSection.FFT),
                                 ret.checkBoolean(GenericFFTSection.ORDER,GenericFFTSection.DECIMATION_IN_TIME),
                                 input.getFixedType,
                                 output.getFixedType,
                                 ret.getObject[FixedType](GenericFFTSection.TWIDDLE).get,
                                 ret.getObject[List[FixedType]](GenericFFTSection.BUTTERFLY).get,
                                 ret.getObject[List[FixedType]](GenericFFTSection.INTERNAL).get)

      val signals = new Fft.Signals(ret.getObject[FlopControl](GenericClockheadType.CLOCKHEAD).get,
                                    input.getName,
                                    output.getName)

      val fftSegment = new ParallelFftGain(name,location + "/design/gen",signals,params)
      val fft = new Project.Basic[ParallelFftGain](name,location,fftSegment)
      writer.createCode(fft)

      CoreParameterHolder.parameters.put(blockName,new CoreParameter.Object(blockName,fft))


      return SegmentReturn.segment("")
    }
}

object GenericFFTSection {

  val TITLE    = "internal_fft"

  val NAME     = "name"
  val LOCATION = "location"
  val LENGTH   = "length"
  val TYPE     = "fft_type"
  val ORDER    = "fft_order"

  val INPUT    = "input"
  val OUTPUT   = "output"
  val TWIDDLE  = "twiddle"

  val BUTTERFLY = "internal_butterfly"
  val INTERNAL  = "internal_width"

  val DECIMATION_IN_TIME = "dit"
  val DECIMATION_IN_FREQ = "dif"

  val FFT = "fft"
  val IFFT = "ifft"


  def createCommand:GenericCommandSection = {
    val map = new LinkedHashMap[String,GenericType]()
    map.put(GenericClockheadType.CLOCKHEAD, new GenericClockheadType)
    map.put(NAME    ,new GenericStringType(NAME,"Name of the FFT Block"))
    map.put(LOCATION,new GenericStringType(LOCATION,"Location of the FFT Block"))
    map.put(LENGTH,new GenericIntType(LENGTH,"Length of the FFT"))

    map.put(TYPE,new GenericChoiceType(TYPE,"Type of FFT (fft,ifft)",List(FFT,IFFT)))
    map.put(ORDER,new GenericChoiceType(ORDER,"Processing Order of FFT (dit,dif)",List(DECIMATION_IN_TIME,DECIMATION_IN_FREQ)))

    map.put(INPUT, new GenericSignalType(INPUT,"Input to the FFT"))
    map.put(OUTPUT,new GenericSignalType(OUTPUT,"Output of the FFT"))
    map.put(TWIDDLE,new GenericFixedType(TWIDDLE,"Width of Twiddle Factors"))
    map.put(BUTTERFLY,new GenericFixedListType(BUTTERFLY,"Internal Width of the Output of the Butterfly Mutiply"))
    map.put(INTERNAL,new GenericFixedListType(INTERNAL,"Internal Width of Output of the Butterfly Stage"))


    val gen = new GenericCommandSection(TITLE,"New Test Command",map, new GenericFFTSection)


    return gen
  }

}