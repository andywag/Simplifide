/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top

import com.simplifide.scala2.core.BaseCodeSegment
import scala.collection.mutable.HashMap

class ScalaContext {}

object ScalaContext {

  val objects = new HashMap[String,BaseCodeSegment];

  def getObjectType[T <: BaseCodeSegment](key:String):Option[T] = {
    objects.get(key) match {
      case Some(x:T) => return Some(x)
      case _         => return None
    }
  }

}
