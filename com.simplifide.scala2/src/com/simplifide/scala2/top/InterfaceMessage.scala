/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top

class InterfaceMessageList() {

}

class InterfaceMessage {
  
  var errored:Boolean = false;
  
  val messages = new java.util.ArrayList[InterfaceMessageItem]()

  def getMessages():java.util.ArrayList[InterfaceMessageItem] = return messages

  def addMessage(message:InterfaceMessageItem) {
    if (message.isError) this.errored = true
    messages.add(message)
  }
	
}

