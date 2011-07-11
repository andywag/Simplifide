package com.simplifide.scala2.parser.generic

import collection.mutable.LinkedHashMap

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/24/11
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */

/** Holder of a map containing general types. This class holds convenience methods for dealing with
 *  converting this data back to the proper values.
 */
class ReturnMapHolder(val returnMap:LinkedHashMap[String,Any]) {

  /** Basic class to return the object */
  def getObject[T](key:String):Option[T] = {
    returnMap.get(key) match {
      case Some(x) => if (x.isInstanceOf[T]) return Some(x.asInstanceOf[T]) else return None
      case None    =>  None
    }
  }

  /** Checks to see if the string stored at the key value is equal to the input str */
  def checkBoolean(key:String,str:String):Boolean = {
    getObject[String](key) match {
      case None    => return false
      case Some(x) => if (x.equalsIgnoreCase(str)) return true;
    }
    return false

  }

}