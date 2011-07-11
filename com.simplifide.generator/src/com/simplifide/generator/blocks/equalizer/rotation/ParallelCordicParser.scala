package com.simplifide.generator.blocks.equalizer.rotation

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

class ParallelCordicParser extends GenericCommandSegment.Creator {
    def createCode(writer:CodeWriter,ret:ReturnMapHolder):SegmentReturn = 
		SegmentReturn.segment("")
	
	override def createCode(writer:CodeWriter,ret:ReturnMapHolder,blockName:String):SegmentReturn = {
      val clk:FlopControl = ret.getObject[FlopControl](GenericClockheadType.CLOCKHEAD).get

      val name:String     = ret.getObject[String](ParallelCordicParser.NAME).get
      val location:String = ret.getObject[String](ParallelCordicParser.LOCATION).get
      val length:Int      = ret.getObject[Int](ParallelCordicParser.LENGTH).get
      val stages:Int      = ret.getObject[Int](ParallelCordicParser.STAGES).get
      val range:Int       = ret.getObject[Int](ParallelCordicParser.RANGE).get
      val input:SignalNew  = ret.getObject[SignalNew](ParallelCordicParser.INPUT).get
      val angle:SignalNew  = ret.getObject[SignalNew](ParallelCordicParser.ANGLE).get
      val output:SignalNew = ret.getObject[SignalNew](ParallelCordicParser.OUTPUT).get

      val internal    = ret.getObject[List[FixedType]](ParallelCordicParser.INTERNAL_FIXED).get
      val stage       = ret.getObject[List[FixedType]](ParallelCordicParser.STAGE_FIXED).get
      val internalang = ret.getObject[List[FixedType]](ParallelCordicParser.INTERNAL_ANGLE_FIXED).get


      val vectorInSignal = new ComplexVectorArray(input.getName,OpType.ModuleInput,input.getFixedType,length)
      val inGain   = SignalNew.newSignal(input.getName + "_gain",OpType.ModuleInput,FixedTypeMain.unsigned(ParallelFftGain.GAINWIDTH,0))
      val vectorIn = new ComplexVectorGain(vectorInSignal,inGain)

      val angleIn = new VectorArray(angle.getName,OpType.ModuleInput,angle.getFixedType,length)

      val vectorOutSignal = new ComplexVectorArray(output.getName,OpType.ModuleInput,output.getFixedType,length)
      val outGain   = SignalNew.newSignal(output.getName + "_gain",OpType.ModuleInput,FixedTypeMain.unsigned(ParallelFftGain.GAINWIDTH,0))
      val vectorOut = new ComplexVectorGain(vectorOutSignal,outGain)


      val parallelSegment = new ParallelCordicRotation(name,location + "/design/gen",clk,vectorIn,angleIn,vectorOut,internal,stage,internalang,stages,range)
      val project = new Project.Basic(name,location,parallelSegment)
      writer.createCode(project)

      CoreParameterHolder.parameters.put(blockName,new CoreParameter.Object(blockName,project))

      return SegmentReturn.segment("")
    }
}

object ParallelCordicParser {
  val TITLE    = "parallel_cordic"

  val NAME     = "name"
  val LOCATION = "location"
  val LENGTH   = "length"
  val STAGES   = "stages"
  val RANGE    = "shift_range"
  val INPUT    = "input"
  val OUTPUT   = "output"
  val ANGLE    = "angle"

  val INTERNAL_FIXED = "internal_fixed"
  val STAGE_FIXED    = "stage_fixed"
  val INTERNAL_ANGLE_FIXED = "internal_angle_fixed"

  def createCommand:GenericCommandSection = {
    val map = new LinkedHashMap[String,GenericType]()
    map.put(GenericClockheadType.CLOCKHEAD, new GenericClockheadType)
    map.put(NAME    ,new GenericStringType(NAME,"Name of the Cordic"))
    map.put(LOCATION,new GenericStringType(LOCATION,"Location of the Cordic"))
    map.put(LENGTH,new GenericIntType(LENGTH,"Length of the Cordic"))
    map.put(STAGES,new GenericIntType(STAGES,"Number of Cordic Stages"))
    map.put(RANGE,new GenericIntType(RANGE,"Amount to Shift at the output"))

    map.put(INPUT, new GenericSignalType(INPUT,"Input to the Cordic"))
    map.put(ANGLE, new GenericSignalType(ANGLE,"Input Angle for the Cordic"))
    map.put(OUTPUT,new GenericSignalType(OUTPUT,"Output of the Cordic"))

    map.put(INTERNAL_FIXED,new GenericFixedListType(INTERNAL_FIXED,"Fixed point of internals of cordic"))
    map.put(STAGE_FIXED,new GenericFixedListType(STAGE_FIXED,"Fixed point at outputof the cordic stage"))
    map.put(INTERNAL_ANGLE_FIXED,new GenericFixedListType(INTERNAL_ANGLE_FIXED,"Fixed point of angles"))



    val gen = new GenericCommandSection(TITLE,"New Test Command",map, new ParallelCordicParser)


    return gen
  }

}