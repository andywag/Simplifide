package com.simplifide.scala2.core

import basic.StatementSegment
import basic.vars.BaseSignal

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/21/11
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */

class SimpleSegment(val name:String)  extends StatementSegment{
  override def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment(name)
  override def createCode(writer:CodeWriter,signal:BaseSignal):SegmentReturn = SegmentReturn.segment(name)
}