/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.context

import scala.collection.mutable.Buffer

abstract class ModuleContext(name:String) extends TopContext(name) {
 
  def entity:EntityContext;
  def component:ComponentContext;

  protected def javaConnections:java.util.List[InstanceContext]

  def connections:Buffer[InstanceContext] = scala.collection.JavaConversions.asScalaBuffer(javaConnections)

  
}
