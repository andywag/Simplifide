/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

trait FixedType {
  
  def signed:Signing
  def width:Int
  def frac:Int
 
  def integerWidth:Int = width - frac

  def getDescription:String = return "<" + width + "," + frac + ">"

  def equals(typ:FixedType):Boolean = {
    if (typ.width == this.width && typ.frac == this.frac) 
    	return true
    else 
    	return false
    
    
  }

  def plus(fix:FixedType):FixedType = {
    return new FixedTypeMain(signed,this.width + fix.width,this.frac + fix.frac)
  }
  
  def getWireDeclaration:String = {
    val builder = new StringBuilder()
    builder.append("wire ")
    if (signed.isSigned) builder.append("signed ")
    if (width > 0) {
      builder.append("[")
      builder.append(width-1)
      builder.append(":0] ")
    }
    
    return builder.toString
  }
}
