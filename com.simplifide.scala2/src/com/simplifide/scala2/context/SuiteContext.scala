/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.context

import scala.collection.mutable.Buffer

abstract class SuiteContext(name:String) extends TopContext(name) {


  def projectContext:java.util.List[ProjectContext];

  /** Returns the projects contained in this suite */
  def projects:Buffer[ProjectContext] = scala.collection.JavaConversions.asScalaBuffer(projectContext)

  /** Returns a project in this suite */
  def getProject(name:String):Option[ProjectContext] = {
    projects.find(x => x.name.equals(name))
  }


}
