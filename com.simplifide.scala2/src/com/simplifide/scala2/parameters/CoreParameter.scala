package com.simplifide.scala2.parameters

import com.simplifide.scala2.core.basic.vars.FixedType

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/1/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */

class CoreParameter(val name:String, val value:Any) {

}

object CoreParameter {

  case class Error(override val name:String) extends CoreParameter(name,"")
  case class Ident(override val name:String,   override val value:String) extends CoreParameter(name,value)
  case class Strin(override val name:String,   override val value:String) extends CoreParameter(name,value)
  case class Integer(override val name:String, override val value:Int) extends CoreParameter(name,value)
  case class Float(override val name:String,   override val value:scala.Float) extends CoreParameter(name,value)
  case class Fixed(override val name:String,   override val value:FixedType) extends CoreParameter(name,value)
  case class Object(override val name:String,  override val value:scala.ScalaObject) extends CoreParameter(name,value)


}