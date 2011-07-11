/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.context

import scala.collection.mutable.Buffer

abstract class ProjectContext(name:String) extends TopContext(name) {

  def getCLocation:String;
  def internalModules:java.util.List[ModuleContext];

   /** Returns the projects contained in this suite */
  def modules:Buffer[ModuleContext] = scala.collection.JavaConversions.asScalaBuffer(internalModules)


  def findModule(name:String):Option[ModuleContext] = {
    modules.find(x => x.name == name)
  }

}
