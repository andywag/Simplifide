package com.simplifide.scala2.core.basic.flop

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.vars.{BaseSignal, VectorType, OpType, SignalNew}
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import collection.mutable.{ListBuffer, LinkedHashMap, HashMap}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/8/11
 * Time: 7:26 AM
 * To change this template use File | Settings | File Templates.
 */

class DelayLine(val clk:FlopControl,out:SignalNew,in:SignalNew) extends StatementSegment.Simple {

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val segments = new ListBuffer[BaseCodeSegment]()

    val map = new LinkedHashMap[BaseSignal,StatementSegment]()
    out.getNumber match {
      case 0 => map.put(out,in)
      case _ => {
        map.put(out.getSlice(out.getNumber-1),in)
        for (i <- 1 until out.getNumber-2) {
          map.put(out.getSlice(i),out.getSlice(i+1))
        }
        map.put(out.getSlice(0),out.getSlice(1))
      }
    }

    val flop = SimpleFlopList.newFlop(clk,map)
    segments.append(flop)
    return SegmentReturn.combineReturns(writer,segments)

  }

}