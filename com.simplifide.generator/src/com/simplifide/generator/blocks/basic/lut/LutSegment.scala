/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.basic.lut

import com.simplifide.scala2.command._

class LutSegment(name:String) extends CommandCodeSegment(name){
  val command:Command = LutCommands;
}
