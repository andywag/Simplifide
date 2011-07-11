/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.command

import com.simplifide.scala2.util.StringOps

import scala.collection.mutable.HashMap

/** This class details the command string which is entered in the editor
  * This information is used for a variety of reasons including templates
 */
abstract class Command(val name:String) {

  /** Template for Creating the Command */
  val command:String;
  /** Map for describing which types are required for the template */
  val commandMap:HashMap[String,CommandParameter];
  /** Description for this command which shows up in the hover */
  val description:String;
  
  /** Convenience function to combine maps. Used for templates consisting of multiple objects */
  def combineMap(ovalue:HashMap[String,CommandParameter],addition:HashMap[String,CommandParameter]) {
    for ((key,value) <- addition) {
      ovalue.put(key,value)
    }
  }

  /** Convenience function to combine maps. Used for templates consisting of multiple objects */
  def combineMaps(ovalue:HashMap[String,CommandParameter],additions:List[HashMap[String,CommandParameter]]) {
    for (addition <- additions) {
      combineMap(ovalue,addition)
    }
  }

  def combineMaps(in:HashMap[String,CommandParameter]*):HashMap[String,CommandParameter] = {
    val map = new HashMap[String,CommandParameter]
    in.foreach(x => combineMap(map, x))
    return map
  }


  
  def createCommandDescription:String = {
    var ostr = command;
    commandMap.foreach( {case (key,value) => {
            ostr = ostr.replace("${" + key + "}",  value.getDescription  ) }
      })
    ostr
  }
  /** Returns a detailed description which is used for a pop up description */
  def getDetailedDescription:String = {
     val builder = new StringBuilder();
     builder.append("<key>Command</key> ")
     builder.append(name)
     builder.append("\n\n")
     builder.append(StringOps.surroundTag(description,"i"))
     builder.append("\n\n")
     builder.append(createCommandDescription)
     builder.toString
  }

  /** Getters -- Required for interfacing with Java */
  def getName():String = name
  def getCommand():String = command
  def getDescription():String = description
  def getCommandMap():HashMap[String,CommandParameter] = commandMap
  def getJavaCommandMap():java.util.Map[String,CommandParameter] = {
    return scala.collection.JavaConversions.asJavaMap(commandMap)
  }

}






