/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

trait SignalTrait {
  def name:String
  def opType:OpType
  def fixed:FixedType
  def vector:VectorType
  
  def copyAsSignalNew(nam:String,optype:Option[OpType],fix:Option[FixedType],
                       vec:Option[VectorType]):SignalNew;
}
