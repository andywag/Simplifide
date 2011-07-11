/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.statement

import com.simplifide.scala2.core.basic.vars.VectorType
import com.simplifide.scala2.top.InterfaceError
import com.simplifide.scala2.core.IdentSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.AlwaysStar
import com.simplifide.scala2.core.basic.AssignStatement
import com.simplifide.scala2.core.basic.CaseStatement
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import scala.collection.mutable.ListBuffer

class StatementMux(val condition:StatementSegment,
                   val output:BaseSignal,
                   val inputs:List[StatementSegment]) extends StatementSegment{

  override def getSignal = null
  
  def createCode(writer:CodeWriter,routput:Option[BaseSignal]):SegmentReturn = {
     val cas = new CaseStatement2(condition)
    var index = 0;
     
    val errors = new ListBuffer[InterfaceError]()
    val extra  = new ListBuffer[Statement]()
    for (input <- inputs) {
    	val cond = new Constant.Integer("",VectorType.NoVector,condition.getFixedType,index)
    	val nout = this.output.copy(this.output.getBaseName + "_" + index,None,None,None)
    	val sret:SegmentReturn = writer.createCode(input,nout)
    	 
    	val uin = new IdentSegment(sret.code)
    	errors.appendAll(sret.errors)
    	extra.appendAll(sret.extraStatements)
    	val ass  = new Statement.Reg(output,uin)
    	cas.addCondition(Some(cond), ass)
    	index = index + 1;
    }
    val alw = new AlwaysStar(None,cas,new ListBuffer())
    
    var ret = new SegmentReturn("",List[InterfaceError]())
    for (state <- extra) ret = ret.combine(writer.createCode(state))
    
    ret = ret.combine(writer.createCode(alw))
    return ret
  }
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCode(writer,Some(output))
  }
  
  // Need to add the statement creation points as well
  override def createCode(writer:CodeWriter):SegmentReturn = {
   return createCode(writer,None)
  }
  
}
