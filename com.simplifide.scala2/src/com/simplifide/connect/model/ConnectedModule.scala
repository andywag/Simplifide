package com.simplifide.connect.model

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/6/11
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */

class ConnectedModule(val name:String, val signals:List[Signal], val instances:List[ConnectedInstance]) {

  val isLeaf   = (instances.size == 0)
  val isBranch = !isLeaf

  /** Prune the outputs from the module */
  def connectOutputs(outputs:List[Signal]):ConnectedModule = {
    // Remove the input Signals from the
    val localInputs     = signals.filter(_.isInput)
    val localOutputs    = signals.filter(_.isOutput)
    val filteredOutputs = localOutputs.filter(outputs.contains(_))
    val internals      = localInputs ::: filteredOutputs ::: signals.filter(_.isWire)
    new ConnectedModule(name,internals,instances.map(_.connectOutputs(filteredOutputs)))
  }
  /** Creates a set of Connections for internal variables in this module*/
  def getInternalConnections:List[ConnectedModule.Connector] = {
    def createConnection(signal:Signal):ConnectedModule.Connector = {
      val source       = instances.filter(_.isSource(signal))
      val destinations = instances.filter(_.isDestination(signal))
      new ConnectedModule.Connector(signal,source(0),destinations)
    }
    val wires = signals.filter(_.isWire)        // Gets all of the signals which are wires (internal connections)
    wires.map(x => createConnection(x))
  }

  def debugString(indent:String):String = {
    val builder = new StringBuilder
    builder.append(name); builder.append("\n")
    signals.map  (x => builder.append(x.debugString(indent + "  ")))
    instances.map(x => builder.append(x.debugString(indent + "  ")))
    builder.toString
  }

}

object ConnectedModule {
  class Connector(val signal:Signal, val source:ConnectedInstance, val destination:List[ConnectedInstance])

}