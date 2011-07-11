/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars._

class ExpressionContext(val signals:List[BaseSignal])  {
  
  def getSignal(name:String):Option[BaseSignal] = {
    signals.find(x => x.getName == name)
  }
  
}
