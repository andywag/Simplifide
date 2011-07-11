/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.top.InterfaceError
import scala.collection.mutable.ListBuffer

class SimpleAdditionSegment(override val terms:List[StatementSegment]) extends AdditionSegment(terms){
  //override val newStatement:Boolean = true
  //override def getSignal = null
  override def getInternalFixed(output:BaseSignal):FixedType = output.getFixedType;
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    val outS = getRoundOutput(output,output.getFixedType)
      this.createAddStatement(writer, outS)
    }
  override def createShareSegmentInternal(states:List[StatementSegment]):StatementSegment = 
        return new SimpleAdditionSegment(states)  
  //def getInternalFixed(output:Signal):FixedType;
  //def getInternalFixed:FixedType = null
  /*
  override def createCode(writer:CodeWriter,output:Signal):SegmentReturn = {
	  //val outS = getRoundOutput(output,output.fixed)
       val builder = new StringBuilder
       val extra = new ListBuffer[Statement]();
       val errors = new ListBuffer[InterfaceError]();
       for (term <- terms) {
         val nret = term.createCode(writer, output)
         builder.append(nret.code)
         extra.appendAll(nret.extraStatements)
         errors.appendAll(nret.errors)
       }
    
       val ret =  new SegmentReturn(builder.toString,errors.toList)
       ret.extraStatements.appendAll(extra)
       return ret;
    }*/
}
