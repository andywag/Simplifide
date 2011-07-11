/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

class FixedTypeMain(val signed:Signing, val width:Int,val frac:Int) extends FixedType{
  
  /*def getWireDeclaration:String = {
    val builder = new StringBuilder()
    builder.append("wire ")
    if (signed.isSigned) builder.append("signed ")
    if (width > 0) {
      builder.append("[")
      builder.append(width-1)
      builder.append(":0] ")
    }
    
    return builder.toString
  }*/
}

object FixedTypeMain {

  def signed(fixed:FixedType):FixedTypeMain ={
    signed(fixed.width,fixed.frac)
  }
  /**Create a new Fixed Type Block with with and frac**/
  def signed(width:Int, frac:Int):FixedTypeMain = {
    new FixedTypeMain(Signing.Signed,width,frac)
  }

  /**Create a new Fixed Type Block with with and frac**/
  def unsigned(width:Int, frac:Int):FixedTypeMain = {
    new FixedTypeMain(Signing.UnSigned,width,frac)
  }

  object Simple extends FixedTypeMain(Signing.UnSigned,1,0)

}

