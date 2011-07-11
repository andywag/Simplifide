package com.simplifide.scala2.connect.model

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 5/3/11
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */

class Port(val name:String, val signal:Signal, val direction:Int) {

}

object Port {
  def INPUT =  0;
  def OUTPUT = 1;
}