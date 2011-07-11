package com.simplifide.scala2.core.basic.test

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.core.basic.{Statement, StatementSegment}
import collection.mutable.{LinkedHashMap, ListBuffer}
import com.simplifide.scala2.core._
import module.{Storage, Stimulus}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/25/11
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class which contains an initial statement. This will initialize values for testbenches
 */
class Initial(val segment:BaseCodeSegment) extends StatementSegment {

  override def createCode(writer:CodeWriter):SegmentReturn = null
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = createCode(writer)

  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    val builder = new StringBuilder
    builder.append("initial begin\n")
    builder.append(StringOps.indentLines(writer.createCode(segment).code, 1))
    builder.append("end\n\n")
    return SegmentReturn.segment(builder.toString)
  }

}

object Initial {
  /*
  class Simple(val outputs:List[StatementSegment],inputs:List[StatementSegment]) extends StatementSegment{
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = createCode(writer)
    override def createCode(writer:CodeWriter):SegmentReturn = null
  }
  */

  /** Initial Statement which is based on a stimulus file for a test bench */
  class TestBench(val stimMap:Stimulus.Map, val storeMap:Storage.Map) extends StatementSegment.Simple {
    override def createCode(writer:CodeWriter):SegmentReturn = {
      val statements = new ListBuffer[StatementSegment]()
      // Create the Initial Stimulus Statements
      val initialMap:LinkedHashMap[Option[BaseSignal],StatementSegment] = stimMap.createInitialMap
      for ((key,value) <- initialMap) {
        key match {
          case Some(x) => {
            if (x.getName.equalsIgnoreCase("none")) statements.append(value)
            else statements.append(new Statement.Simple(x,value))
          }
          case None    => statements.append(value)
        }
      }
      // Create the Initial Source Statements
      for ((key,value) <- storeMap.createInitialMap) {
        statements.append(new Statement.Simple(key,value))
      }
      val listSegment = new ListSegment(statements.toList)
      val init = new Initial(listSegment)
      return writer.createCode(init)
    }
  }
}