package com.simplifide.scala2.top.parser

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.simplifide.scala2.parser._
import com.simplifide.scala2.top._



/**
 *  Convenience class for locating the correct parser associated with the command name
 */

class TotalParserTop

object TotalParserTop {

   /* Return a list of registered parsers */
  def getParsers():List[BaseParser] = {
    return InterfaceCommands.getCommands.map(x => x.getParser)
  }

  /** Returns a parser if it matches the command string */
  def getParser(com:String):Option[BaseParser] = {
    TotalParserTop.getParsers().foreach(x => if (x.commandString == com) return Some(x))
    return None
  }
}
