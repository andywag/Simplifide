/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars


class Signing {
  val isSigned:Boolean = false;
  val isControl:Boolean = false;
 
}

object Signing {
  case object Signed extends Signing {
    override val isSigned:Boolean = true;
  }
  
  case object UnSigned extends Signing 
  
  case object Control extends Signing {
    override val isControl:Boolean = true
  }
  
}
