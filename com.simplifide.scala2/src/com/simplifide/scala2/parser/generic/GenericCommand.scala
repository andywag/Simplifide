package com.simplifide.scala2.parser.generic

import collection.mutable.HashMap
import com.simplifide.scala2.command.{CommandParameter, Command}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/24/11
 * Time: 8:48 AM
 * To change this template use File | Settings | File Templates.
 */

class GenericCommand(val typ:GenericType) extends Command(typ.name) {

  /** Template for Creating the Command */
  override val command:String = typ.commandString
  /** Map for describing which types are required for the template */
  override val commandMap:HashMap[String,CommandParameter] = typ.commandMap
  /** Description for this command which shows up in the hover */
  override val description:String = typ.description

}