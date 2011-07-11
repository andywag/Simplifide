package com.simplifide.scala2.connect.model

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 5/3/11
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */

class Module(override val name:String, val ports:Map[String,Port], val instances:List[Instance]) extends ModelObject(name) {

}