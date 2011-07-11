/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.ConstantValue
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.VectorType

abstract class RoundSegment(val terms:List[StatementSegment], internal:FixedType) extends StatementSegment{

   def getSegmentsWoRound:List[StatementSegment] = terms
   def getSegments:List[StatementSegment] = terms

   def createCCode(writer:CodeWriter,output:BaseSignal,func:String,outfix:FixedType):SegmentReturn = {
    val trunc = new SimpleAdditionSegment(terms)
    val ret = trunc.createCode(writer,output)
    val builder = new StringBuilder
    builder.append(func)
    builder.append("(")
    builder.append(ret.code)
    builder.append(",")
    builder.append(outfix.width)
    builder.append(",")
    builder.append(outfix.frac)
    builder.append(",")
    builder.append(internal.width)
    builder.append(",")
    builder.append(internal.frac)
    builder.append(")")
    val nret = SegmentReturn.segment(builder.toString)
    nret.extraStatements.prependAll(ret.extraStatements)
    return nret
    //return AdditionSegment.Trunc(getSegments).createCode(writer,output)
  }
   
  
  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
	  //val simple = if (output.getFixedType.equals(internal)) 
	  //	  new SimpleAdditionSegment(getSegmentsWoRound)
	  //else 
	  //	  new SimpleAdditionSegment(getSegmentsWoRound)
      val simple = new SimpleAdditionSegment(getSegments)
    return writer.createCode(simple,output)
  }
  
  
}

object RoundSegment {
  
  class Round(override val terms:List[StatementSegment],val output:BaseSignal, internal:FixedType) extends RoundSegment(terms,internal) {
    
    override def getSegments:List[StatementSegment] = {
    	if (output.getFixedType.equals(internal)) terms
    	else terms ::: List(getRoundConstant)
    }

    def getRoundConstant:AdditionTerm = {
      val doa = math.pow(2.0, -output.getFixedType.frac - 1)
      val constant = new Constant("",VectorType.NoVector,internal,new ConstantValue.FloatValue(doa.toFloat))
      new AdditionTerm.AddTerm(constant)
    }
    
  override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCCode(writer,output,"round_float",this.output.getFixedType)
  }
  override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    return createCCode(writer,output,"round_fixed",this.output.getFixedType)
  }
  }
  
  class Trunc(override val terms:List[StatementSegment],internal:FixedType) extends RoundSegment(terms,internal) {
     override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
       return createCCode(writer,output,"trunc_float",output.getFixedType)
     }
    override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCCode(writer,output,"trunc_fixed",output.getFixedType)
     }
  }
  
  
  
}
