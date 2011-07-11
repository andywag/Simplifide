package com.simplifide.connect.model

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/6/11
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */

/** Prototype Instance which is a module connection */
class Instance(val name:String, val module:Module, val connections:Instance.ConnectionMap) {

    /** Creates the instance */
    def connect:ConnectedInstance = {
      val mod     = module.connect                               // Connect the module
      val ports = mod.signals.map(connections.connectSignal(_))  // Creates the ports based on the signals
      new ConnectedInstance(name,mod,ports)
    }
}

object Instance {
  class ConnectionMap {
    /** Simple Signal Connection */
    def connectSignal(signal:Signal):ConnectedInstance.Port = new ConnectedInstance.Port(signal,signal)

  }


}