package com.simplifide.scala2.parameters

import collection.mutable.HashMap

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/1/11
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */

object CoreParameterHolder {
  val parameters = HashMap[String,CoreParameter]()

  def getObject(key:String):Any = {
    val par = parameters.get(key)
    par match {
      case None    => return None
      case Some(x) => return x.value
    }

  }

  /*
  def getObject[T](key:String):Option[T] = {
    parameters.get(key) match {
      case None    => return None
      case Some(x) => {
        if (x.isInstanceOf[CoreParameter.Object]) {
          val par = x.asInstanceOf[CoreParameter.Object]
          if (par.value.isInstanceOf[T]) return par.value.asInstanceOf[T]
        }
      }

    }
    return None
  } */

}