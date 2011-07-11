package com.simplifide.connect.model

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/6/11
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */

/** Class which defines a module/entity for Verilog/VHDL */
abstract class Module(val name:String) {
    def attach:Instance = attach(name,new Instance.ConnectionMap())
    def attach(name:String):Instance = attach(name,new Instance.ConnectionMap())
    def attach(name:String, connections:Instance.ConnectionMap) = new Instance(name,this,connections)
    /** Create the connected module based on this prototype */
    def connect:ConnectedModule

}

object Module {

  def Leaf(name:String) = new Module.Leaf(name)
  def Leaf(name:String,inSignals:List[Signal]) = new Module.Leaf(name) {override val signals = inSignals}

  class Leaf(override val name:String) extends Module(name) {
    val signals:List[Signal]    = List()

    // For this case only returns a copy of the connected module
    def connect:ConnectedModule = new ConnectedModule(name, signals, List())
  }


  def Branch(name:String) = new Branch(name)
  def Branch(name:String, inInstances:List[Instance]) = new Branch(name) {override val instances = inInstances}

  class Branch(override val name:String) extends Module(name ) {
    val instances:List[Instance] = List()

    /** Create a connected version of this module */
    def connect:ConnectedModule = {
        def isIncluded(input:Signal,outputs:List[Signal]):Boolean = outputs.filter(_.name.equalsIgnoreCase(input.name)).size > 0

        val connectedInstance = this.instances.map(_.connect)     // Create the connected instance
        val internalSignals = connectedInstance.flatMap(_.connections).map(_.external).distinct
        val tempInputs  = internalSignals.filter(_.isInput)
        val outputs = internalSignals.filter(_.isOutput)
        val wires   = tempInputs.filter(isIncluded(_,outputs)).map(_.changeDirection(Signal.WIRE))           // Signals which have both an input and output at this level
        val inputs  = tempInputs.filter(!isIncluded(_,wires))

        new ConnectedModule(name,inputs ::: outputs ::: wires,connectedInstance)
    }
  }

  def Root(name:String,iInstances:List[Instance],iSignals:List[Signal]):Module.Root = new Root(name) {
    override val signals   = iSignals
    override val instances = iInstances
  }
  class Root(override val name:String) extends Branch(name) {
    val signals:List[Signal] = List()
    override def connect:ConnectedModule = {
      val noOutMod = super.connect
      noOutMod.connectOutputs(signals.filter(_.isOutput))
      //noOutMod
    }
  }

}