package com.simplifide.connect.model


/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/6/11
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */

/** Fully connected instantiation of the module */
class ConnectedInstance(val name:String, val module:ConnectedModule, val connections:List[ConnectedInstance.Port]) {

  /** Remove the output connections */
  def connectOutputs(outputs:List[Signal]):ConnectedInstance = {
      new ConnectedInstance(name,module.connectOutputs(outputs),connections.filter(x => !outputs.contains(x.external)))
  }
  /** Checks whether this block is the source of this signal */
  def isSource(signal:Signal):Boolean = connections.exists(x => x.external == signal.output)
  /** Checks whether this block is the destinatino of this signal */
  def isDestination(signal:Signal):Boolean = connections.exists(x => x.external == signal.input)


  def debugString(indent:String):String = {
    val builder = new StringBuilder
    builder.append(indent);builder.append(name); builder.append(" "); builder.append(module.debugString(indent + "  "))
    builder.toString
  }
}

object ConnectedInstance {
  class Port(val local:Signal, val external:Signal) {
  }
}