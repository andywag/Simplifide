/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser.nodes

import scala.util.parsing.input.NoPosition
import scala.util.parsing.input.Position

class NodePosition(val start:Int, val end:Int, val pos:Position ) {
  
  def line   = pos.line
  def column = pos.column

  def getStart:Int = start
  def getEnd:Int = end
  def getLength:Int = end - start

  def containsOffset(offset:Int):Boolean = {
    if ((offset >= this.start) && (offset <= this.end)) return true
    return false
  }

  override def toString:String = "(" + start + "," + end + "," + line + "," + column + ")"

}

object NoNodePosition extends NodePosition(0,0,NoPosition) {

}

