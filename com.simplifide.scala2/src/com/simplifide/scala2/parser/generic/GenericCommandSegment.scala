package com.simplifide.scala2.parser.generic

import com.simplifide.scala2.command.{Command, CommandCodeSegment}
import collection.mutable.LinkedHashMap
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.{CodeWriter,SegmentReturn}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/23/11
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */

class GenericCommandSegment(val name:String,
					 val blockName:String,
                     val command:Command,
                     val link:LinkedHashMap[String,Any],
                     val creator:GenericCommandSegment.Creator) extends CommandCodeSegment(name) {

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCode(writer)
    }

  override def createCode(writer:CodeWriter):SegmentReturn = {
    creator.createCode(writer,new ReturnMapHolder(link),blockName)

  }


}

object GenericCommandSegment {

  trait Creator {
    def createCode(writer:CodeWriter,ret:ReturnMapHolder):SegmentReturn
    def createCode(writer:CodeWriter,ret:ReturnMapHolder,name:String):SegmentReturn = createCode(writer,ret)
  }

}