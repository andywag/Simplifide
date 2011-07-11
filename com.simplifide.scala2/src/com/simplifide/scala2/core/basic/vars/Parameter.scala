/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

class Parameter(override val name:String,
                override val opType:OpType,
                override val fixed:FixedType,
                override val vector:VectorType) 
  extends SignalNew(name,opType,fixed,vector) {

   override def getSignalDeclaration:List[SignalDeclarationNew] = List() 
  
}

object Parameter {
  
  class Fixed(override val name:String,
                   override val opType:OpType,
                   override val fixed:FixedType,
                   override val vector:VectorType)  
    extends Parameter(name,opType,fixed,vector) with FixedType {
      
    override def signed:Signing = fixed.signed
    override def width:Int      = fixed.width
    override def frac:Int       = fixed.frac
  }
  
  
}
