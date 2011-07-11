package com.simplifide.scala2.core.basic.test

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/26/11
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.module.Storage
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.{SegmentReturn, CodeWriter}

/** Dumps data to a file. */
class Fdisplay(val store:Storage.File) extends StatementSegment.Simple {


  /** Create the slice containing the output data to be sliced */
  private def createSignalSlice:String = {
    val width = store.signal.getFixedType.width
    val num = math.ceil(width/1024.0).toInt
    if (num == 1) return store.getFdisplayName
    val builder = new StringBuilder
    for (i <- 0 until num) {
      val ind = num-i-1
      if (i > 0 ) builder.append(",")
      builder.append(store.getFdisplayName)
      builder.append("[")
      if (i == 0) builder.append(width-1) else builder.append(1024*(ind+1)-1)
      builder.append(":")
      builder.append(1024*(ind))
      builder.append("]")
    }
    return builder.toString
  }
  /** Create the Display Portion for the Variable */
  private def createDisplay:String = {
    val width = store.signal.getFixedType.width
    val num = math.ceil(width/1024.0).toInt
    val builder = new StringBuilder
    for (i <- 0 until num) {
      builder.append("%b")
    }
    return builder.toString
  }
  override def createCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder
    builder.append("$fdisplay(")
    builder.append(writer.createCode(store.createPointerSignal))
    builder.append(",")
    builder.append("\"")
    if (store.binary) builder.append(createDisplay) else builder.append("%d")
    builder.append("\"")
    builder.append(",")
    builder.append(createSignalSlice)
    builder.append(");\n")
    return SegmentReturn.segment(builder.toString)
  }
}