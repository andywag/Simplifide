/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.parser.nodes

import com.simplifide.scala2.command._
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser.BaseParserInfo

/** Extension of Node which creates a segment as well. This is used for the top level parsing operation */
abstract class RootNode extends CommandNode {
  /** Creates the Segment Associated With this Node */
  def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment
}
