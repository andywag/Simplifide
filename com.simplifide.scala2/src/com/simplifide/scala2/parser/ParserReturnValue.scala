/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser


import com.simplifide.scala2.top.InterfaceError
import nodes.RootNode
import com.simplifide.scala2.command.CommandCodeSegment


class ParserReturnValue {


  val isError:Boolean   = false
  val isSegment:Boolean = false
  
  def getError:Option[InterfaceError]       = None
  def getRoot:Option[RootNode]              = None
  def getSegment:Option[CommandCodeSegment] = None

}

object ParserReturnValue {
  
  
  class Error(error:InterfaceError) extends ParserReturnValue {
    override val isError:Boolean   = true
    override def getError:Option[InterfaceError] = Some(error)
  }
  
  class Segment(root:RootNode,segment:CommandCodeSegment) extends ParserReturnValue {
    override val isSegment:Boolean = true
    override def getRoot:Option[RootNode]                = Some(root)
    override def getSegment:Option[CommandCodeSegment]   = Some(segment)
  }
  
}
