/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top

abstract class InterfaceError(val line:Int,val message:String) extends InterfaceMessageItem{
     override val isTopError:Boolean   = true
     override def getLine:Int       = line
     override def getMessage:String = message
     
     def newErrorwithOffset(line:Int):InterfaceError
  
}

object InterfaceError {
  
   class Error(override val line:Int, override val message:String) extends InterfaceError(line,message) {
     override val isError:Boolean    = true
     override def newErrorwithOffset(off:Int) = new Error(line + off,message)
   }
   class Warning(override val line:Int, override val message:String) extends InterfaceError(line,message) {
     override val isWarning:Boolean   = true
     override def newErrorwithOffset(off:Int) = new Warning(line + off,message)
   }
   class Info(override val line:Int, override val message:String) extends InterfaceError(line,message) {
     override val isInfo:Boolean   = true
     override def newErrorwithOffset(off:Int) = new Info(line + off,message)
   }
   
   def getRealOffset(message:String,offset:Int):Int = 0
  
}
