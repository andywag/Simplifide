/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.context

abstract class CurrentContext {
  def activeSuite:SuiteContext;
  def activeProject:ProjectContext;
  def activeFile:DesignContext;
  protected def activeModule:ModuleContext;


  def getActiveModule:Option[ModuleContext] = {
    if (activeModule == null) return None;
    else return Some(activeModule)
  }
  /** Finds a component with the optional library and name.
   *  If the library doesn't exist searches the active project
  */
  def findModule(library:Option[String],name:String):Option[ModuleContext] ={
    library match {
      case None    => return activeProject.findModule(name)
      case Some(x) => activeSuite.getProject(x).map(x => x.findModule(name).get)
    }
  }

}
