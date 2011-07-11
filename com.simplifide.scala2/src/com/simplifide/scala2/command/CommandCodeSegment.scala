/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.command

import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.{SegmentReturn, CodeWriter, BaseCodeSegment}

/**Segment which contains a class which can be created from the paser and contains a command */
abstract class CommandCodeSegment(name:String) extends StatementSegment{

  /** Template used for creating the state machine */
  val command:Command;
  val commands:ListBuffer[Command] = new ListBuffer[Command]();
  val matchingC:Boolean = false

  def createCode(writer:CodeWriter,output:BaseSignal) = {
    SegmentReturn.segment("")
  }

}
