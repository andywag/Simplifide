package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.complex.ComplexVectorArray
import com.simplifide.scala2.core.basic.statement.CaseStatement2
import com.simplifide.scala2.core.basic.vars.{Constant, SignalNew}
import com.simplifide.scala2.core.{BaseCodeSegment, SegmentReturn, CodeWriter}
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.{AlwaysStar, Comment, Statement, StatementSegment}
import com.simplifide.scala2.core.basic.fixed.FixedSelect
import com.simplifide.scala2.core.module.ModuleSegment
import com.simplifide.scala2.clocks.FlopControl

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 3/20/11
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates. 
 */

class BarrelShifter(override val name:String,
                    override val clk:FlopControl,
                    val signalIn:SignalNew,
                    val signalOut:SignalNew,
                    val condition:SignalNew,
                    val range:Int) extends ModuleSegment(name,clk) {

  val inputs:List[SignalNew] = List(signalIn,condition)
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew] = List(signalOut)
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew] = List()


  override def createCode(writer:CodeWriter):SegmentReturn = {


      val segments = new ListBuffer[BaseCodeSegment]()
      if (range == 0) {
        segments.append(new Statement(signalOut,FixedSelect.newSelect(signalIn,signalOut.getRealFixedType)))
      }
      else {
        val rcas = new CaseStatement2(condition)
        for (j <- 0 until range) {
          val rs = new Statement(signalOut,FixedSelect.newSelect(signalIn,signalOut.getRealFixedType,j))
          rcas.addCondition(Some(condition.createConstant(j)),rs)
        }
        rcas.addCondition(None,new Statement(signalOut,signalOut.createConstant(0)))
        segments.append(AlwaysStar.newAlways(rcas))
      }

      return SegmentReturn.combineBufferReturns(writer,segments)



  }

}