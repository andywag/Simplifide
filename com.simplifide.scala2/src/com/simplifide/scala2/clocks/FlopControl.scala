/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.clocks

import com.simplifide.scala2.command._
import com.simplifide.scala2.core._
import com.simplifide.scala2.core.basic._
import scala.collection.mutable.ListBuffer
import vars.{OpType, VectorType, FixedTypeMain, SignalNew}

class FlopControl(name:String,
                  val clock:Clock, 
                  val reset:Option[Reset], 
                  val enable:Option[Enable],
                  val index:Option[Clocks.Index]) extends CommandCodeSegment(name){

  val command:Command = ClockCommands;

  def getClock():Clock  = {return clock}
  def getReset():Option[Reset]  = {return reset}
  def getEnable():Option[Enable] = {return enable}
  def getIndex:Option[Clocks.Index] = index

  /** Returns the signal associated with the clock */
  def getClockSignal(optype:OpType):SignalNew =
    new SignalNew(clock.name,optype,FixedTypeMain.Simple,VectorType.NoVector)

  /** Returns the signal associated with the reset */
  def getResetSignal(optype:OpType):Option[SignalNew] =
    reset.map(x => new SignalNew(x.name,optype,FixedTypeMain.Simple,VectorType.NoVector))

  /** Returns the signal associated with the reset */
  def getEnableSignal(optype:OpType):Option[SignalNew] =
    enable.map(x => new SignalNew(x.name,optype,FixedTypeMain.Simple,VectorType.NoVector))

  def getAllSignals(optype:OpType):List[SignalNew] = {
    val buffer = new ListBuffer[SignalNew]()
    buffer.append(getClockSignal(optype))
    getResetSignal(optype).map(x => buffer.append(x))
    getEnableSignal(optype).map(x => buffer.append(x))
    return buffer.toList
  }


  def createSensitivityList():ListBuffer[BaseCodeSegment] = {

    val lis = new ListBuffer[BaseCodeSegment]()
    lis.append(clock.getSensitivityListItem())
    this.reset match {
      case Some(s) => s.getSensitivityListItem match {case Some(x) =>  lis.append(x); case _ =>}
      case _ =>
    }
    return lis
  }
}

object FlopControl {

}
