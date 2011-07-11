/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.component

import com.simplifide.scala2.top.InterfaceError

class ComponentErrors {

}

object ComponentErrors {
  
  class EntityNotFound(entity:String) extends InterfaceError.Error(0,"Entity " + entity + " Not Found")
  class EnclosingModuleNotFound extends InterfaceError.Error(0,"Enclosing Module Not Found")
}
