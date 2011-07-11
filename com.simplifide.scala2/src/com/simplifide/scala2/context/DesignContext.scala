/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.context

abstract class DesignContext {
   
   def getLocation:String;
   def getName:String;
   
   def getBaseName:String = {
     val na = getName.split("\\.")
     if (na.length > 0) return na(0)
     return getName
   }
}
