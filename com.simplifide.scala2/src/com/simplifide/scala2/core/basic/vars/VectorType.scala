/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

class VectorType(val arr:List[Int], val reg:Option[Int]) {

}

object VectorType {

  def newVector(len:Int) = new VectorType(List(len),None)
  object NoVector extends VectorType(List[Int](),None)
  
}