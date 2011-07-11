/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top

import com.simplifide.scala2.context.ModuleContext

/**Class used to create objects which exists in Verilog or VHDL. This seems to only support instances and components.
 * This block interfaces with java where the instance is places in this block before an object creation
 **/
class ObjectWriter {}

object ObjectWriter {
  var instance:WriterInterface = new WriterInstance();
  def setInstance(ins:WriterInterface) { this.instance = ins}
  
  def createComponent(module:ModuleContext):String = {
    if (instance != null) {
      return instance.createComponent(module)
    }
    return ""
  }

   def createInstance(module:ModuleContext, map:java.util.HashMap[String,String]):String = {
    if (instance != null) {
      return instance.createInstance(module,map)
    }
    return ""
  }

}

/** Abstract class which allows the java method to override this operation */
abstract class WriterInterface {
    def createComponent(context:ModuleContext):String;
    def createInstance(context:ModuleContext,map:java.util.HashMap[String,String]):String;
  }

class WriterInstance extends WriterInterface {
    def createComponent(context:ModuleContext):String = ""
    def createInstance(context:ModuleContext,map:java.util.HashMap[String,String]):String = ""
}
