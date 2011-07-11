package com.simplifide.connect.model

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/6/11
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */

class Signal(val name:String, val direction:Int) {
  /** Override equals to allow only nuame comparison */
  override def equals(that: Any):Boolean = {
    if (that.isInstanceOf[Signal]) {
      val tsig = that.asInstanceOf[Signal]
      return ((tsig.name.equalsIgnoreCase(name)) && (tsig.direction == this.direction))
    }
    return false
  }
  /** Change Direction */
  def changeDirection(dir:Int) = new Signal(name,dir)

  def input:Signal  = changeDirection(Signal.INPUT)
  def output:Signal = changeDirection(Signal.OUTPUT)
  def wire:Signal   = changeDirection(Signal.WIRE)

  def isInput:Boolean  = (direction == Signal.INPUT)
  def isOutput:Boolean = (direction == Signal.OUTPUT)
  def isWire:Boolean   = (direction == Signal.WIRE)

  def debugString(indent:String):String = indent + (if (isInput) "input " else if (isOutput) "output " else "wire ") +  debugName + "\n"
  def debugName:String = name


}

object Signal {
  val INPUT  = 0
  val OUTPUT = 1
  val WIRE   = 2

  def Input(name:String):Signal = new Signal(name,Signal.INPUT)
  def Output(name:String):Signal = new Signal(name,Signal.OUTPUT)

  class Bus(override val name:String, override val direction:Int, signals:List[Signal]) extends Signal(name,direction){
    override def debugName:String = name + "(BUS)"
  }

}