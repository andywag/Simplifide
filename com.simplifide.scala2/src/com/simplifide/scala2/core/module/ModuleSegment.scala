package com.simplifide.scala2.core.module

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.{BaseSignal, SignalNew}
import com.simplifide.scala2.core.{SegmentReturn, CodeWriter}
import com.simplifide.scala2.clocks.FlopControl
import collection.immutable.HashMap
import collection.mutable.LinkedHashMap

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/24/11
 * Time: 11:41 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Segment which can be incorporated inside a module.
 * @param name Name of the Module
 */
abstract class ModuleSegment(val name:String, val clk:FlopControl) extends StatementSegment {

  /** Default Simulation Length */
  val simLength:Int = 1000;
  /** Enable or Disables Tracing for Simuation */
  val trace:Boolean = false
  /** Returns a list of inputs for this module */
  val inputs:List[SignalNew]
  /** Returns a list of outputs for this module */
  val outputs:List[SignalNew]
  /** Returns a list of internal signals for this module */
  val signals:List[SignalNew]
  /** List of Dependent Projects */
  val projects:List[Project[ModuleSegment]] = List()

  /** Defines a set of default conditions for testing a module */
  def getStimulusMap:Stimulus.Map = new Stimulus.Map(new LinkedHashMap[BaseSignal,Stimulus](),this)
  /** Defines the set of variables to dump */
  def getStorageMap:Storage.Map   = new Storage.Map(new LinkedHashMap[BaseSignal,Storage]())
  /** Returns a list of Dependent Modules */
  def getFileList:List[String] = List(name)

  def createModule(location:String, writer:CodeWriter):Module.Segment = {
    val mod =  new Module.Segment(name,location,this)
    writer.createCode(mod)
    mod
  }

  def createInstance(location:String,writer:CodeWriter):Instance = {
    val mod = createModule(location,writer)
    return mod.createInstance
  }

  def createConnection(name:String, map:Map[String,String]):Instance =  {
    val mod =  new Module.Segment(this.name,"",this)
    return mod.createInstance(Some(name),Some(map))
  }


  def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCode(writer)
  }

}

