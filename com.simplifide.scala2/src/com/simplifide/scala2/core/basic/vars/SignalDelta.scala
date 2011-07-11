/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.fixed.FixedSelect
import com.simplifide.scala2.core.basic.fixed.SimpleMultiplySegment
import com.simplifide.scala2.expression.segments.SignalIndex


class SignalDelta(val proto:BaseSignal,
                  val index:SignalIndex) extends BaseSignal {
  
   override def getBaseName:String = proto.getBaseName
   override def getFixedType:FixedType = proto.getFixedType
   override def getSignal:BaseSignal = this
   
   /** Copy with a different name */
   override def copyWithName(name:String):BaseSignal = 
     new SignalDelta(proto.copyWithName(name),index)
   
   override def copy(nam:String,optype:Option[OpType],
                     fix:Option[FixedType],vec:Option[VectorType]):BaseSignal = {
     val prot = proto.copy(nam, optype, fix,vec)
     return new SignalDelta(prot,index)
   }
   
    def getOffset:Int = {
      index.getOffset  match {
          case None    => return 0
          case Some(x) => return x
      } 
    }
        
    override def getTerm(n:StatementSegment.TermIndex):StatementSegment = { 
    	 val diff  = n.y - this.getOffset
        return new SignalDelta.Call(this.proto,this.index,diff)
    }
    
  override def convertTerm(index:StatementSegment.TermIndex):StatementSegment = this.getTerm(index)
    
  override def getSelect(output:BaseSignal):StatementSegment = new FixedSelect(this,output.getFixedType)
  
  //override def getSignalDeclaration:List[SignalDeclarationNew] = List(this.proto.asInstanceOf[SignalNew].opType.getSignalDeclaration(this))
  
  override def createMultSegment(sig:StatementSegment):Option[SimpleMultiplySegment] = 
    new Some(new SimpleMultiplySegment(this,sig))
    
	/** Create the code. Scales this signal to the output width*/ 
   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn  = {
     val sel = new FixedSelect(this,output.getFixedType)
     return writer.createCode(sel)
   }
   
    def createCCode(writer:CodeWriter):SegmentReturn       = {
       val off = this.getRealOffset
       
	   if (this.proto.asInstanceOf[SignalNew].opType.isOutput && off == 0) {
	  	   return SegmentReturn.segment("*"+getName)
	   }
	   else return SegmentReturn.segment(getName)
   }
   override def createFloatCode(writer:CodeWriter):SegmentReturn       = createCCode(writer)
   override def createFixedCode(writer:CodeWriter):SegmentReturn       = createCCode(writer)
   
	
   def getRealOffset:Int = {
	   index.getOffset match {
        case None => return 0 // For no offset return the name
        case Some(x) => {        
            val sh = index.scale match {
              case None    => math.floor(x).toInt
              case Some(y) => math.floor(x/y).toInt
            }
            if (sh == 0) return 0
            else return -sh
        }  
      }
	  return 0
   }
   
    override def getName:String = {
      index.getOffset match {
        case None => return proto.getBaseName // For no offset return the name
        case Some(x) => {        
            val sh = index.scale match {
              case None    => math.floor(x).toInt
              case Some(y) => math.floor(x/y).toInt
            }
            //val sh = math.floor(x).toInt
            if (sh == 0) return proto.getBaseName
            else return proto.getBaseName + "_" + (-sh)
        }  
      }
      
      if (index.equals("0")) return proto.getBaseName
      return proto.getBaseName + "_" + index
    }
    //override def newSignal(name:String,fixed:FixedType) = new SignalDelta(name,List(),fixed,index)
    //override def getDeclarationType:String = "reg"
}

object SignalDelta {
  
  class Call(override val proto:BaseSignal,
                  override val index:SignalIndex,
                  val number:Int) extends SignalDelta(proto,index) {
    
	override def getRealOffset:Int = number
	  
    override def getName:String = {
      if (number == 0) return proto.getName
      else return proto.getName + "_" + number
    }
  }
  
}
