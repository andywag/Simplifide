/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

import com.simplifide.scala2.core.basic.fixed.SimpleMultiplySegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment

abstract class BaseConstant extends BaseSignal {

  
	
   def createCSD:List[Constant.CSD]
  
   override def getSelect(output:BaseSignal):StatementSegment = this
  
   def getIntegerValue(value:Float,fix:FixedType):Int = {
      val scale = math.pow(2.0, fix.frac)
      val numb:Int  = math.round(value*scale).toInt
      
      val tot:Int = fix.signed.isSigned match {
        case true  => if (numb > math.pow(2.0, fix.width-1))        math.pow(2.0, fix.width-1).toInt
                      else if (numb < -math.pow(2.0, fix.width-1)) -math.pow(2.0, fix.width-1).toInt
                      else numb
        case false => if (numb > math.pow(2.0, fix.width)-1) numb else (math.pow(2.0, fix.width)-1).toInt
      }
      return tot
   }
  
}

object BaseConstant {
  class BaseFloat(val value:Float) extends BaseConstant {

   def createCSD:List[Constant.CSD] = {
     Constant.createCSD(getIntegerValue(value,getFixedType))
   }

	override def getFixedType:FixedType = deriveFixed
	  
    def deriveFixed:FixedType = {
      val ival = math.ceil(math.log10(math.abs(value))/math.log10(2.0)).toInt
      for (i <- 0 until 63) {
        val pval = math.pow(2.0, i)
        val tval = pval*value
        if (tval - math.floor(tval) == 0) return new FixedTypeMain(Signing.Signed,ival + i, i)
      }
      return new FixedTypeMain(Signing.Signed,63,0)
    }
    
    override def createMultSegment(sig:StatementSegment):Option[SimpleMultiplySegment] = {
   
      val con = new Constant("",VectorType.NoVector,deriveFixed,new ConstantValue.FloatValue(value))
      new Some(new SimpleMultiplySegment.Csd(con,sig))
    }
      
    
    def createCItem(writer:CodeWriter):SegmentReturn = {
    return SegmentReturn.segment(value.toString)
  }
  
    override def createFloatCode(writer:CodeWriter):SegmentReturn       = {
      return createCItem(writer)
    }
  
    override def createFixedCode(writer:CodeWriter):SegmentReturn       = {
     return createCItem(writer)
    }
    
    override def createFloatCode(writer:CodeWriter,output:BaseSignal):SegmentReturn       = {
      return createCItem(writer)
    }
  
    override def createFixedCode(writer:CodeWriter,output:BaseSignal):SegmentReturn       = {
     return createCItem(writer)
    }
    
    override def createVerilogCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      val fixed = output.getFixedType
      val ival = this.getIntegerValue(value, fixed)
    
      val builder = new StringBuilder
      if (ival < 0) builder.append("-")
      builder.append(output.getFixedType.width.toString)
      if (fixed.signed.isSigned) builder.append("'sd") else builder.append("'d")
      builder.append(math.abs(ival).toString)
      return SegmentReturn.segment(builder.toString)
    }
    
      
     
    override def getSignal:BaseSignal = null
  }
}


