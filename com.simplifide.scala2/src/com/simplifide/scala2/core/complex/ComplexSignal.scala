/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.complex

import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalDeclarationNew
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.SignalTrait
import com.simplifide.scala2.core.basic.vars.VectorType
import scala.collection.mutable.ListBuffer

/** Complex Number which contains a real and imaginary section
    For the most part this is treated like a vector where
    the first part is the real and the second part is the imaginary
 */
class ComplexSignal(val proto:SignalTrait) extends SignalNew(proto.name:String,
                                                             proto.opType,
                                                             proto.fixed,
                                                             proto.vector) {

  def debugString:String =  "Complex (" + name + ") " + proto.fixed.getDescription
  /** Returns the vector number if it is a vector. Otherwise returns 2 (1 real, 1 imag) */
  override def getNumber:Int = {
     if (proto.vector.arr.size == 0) return 2
     return proto.vector.arr(0)
  }
  
   /** Return the sliced value of this signal. Either the vector return or the real or the imaginary 
       portion
   */
   override def getSlice(index:Int):SignalNew = {
     if (proto.vector.arr.size > 0) {
       val nvec = proto.vector.arr.slice(1, proto.vector.arr.size)
       val nproto = proto.copyAsSignalNew(proto.name + "_" + index, None, None,Some(new VectorType(nvec,proto.vector.reg)))
       return new ComplexSignal(nproto)
    }
     if (index == 0) return getReal
     else return getImag
  }
  
   def getComplexSlice(index:Int):ComplexSignal = {
	   return getSlice(index).asInstanceOf[ComplexSignal]
   }
   
  def getReal:SignalNew = 
    return proto.copyAsSignalNew(this.name + "Re" , None, None,Some(new VectorType(List(),vector.reg)))
  
  def getImag:SignalNew = 
    return proto.copyAsSignalNew(this.name + "Im" , None, None,Some(new VectorType(List(),vector.reg)))
  
   /** Copies the signal with different options */
   override def copy(nam:String,optype:Option[OpType],fix:Option[FixedType],vec:Option[VectorType]):BaseSignal = {
     val prot = proto.copyAsSignalNew(nam, optype, fix, vec)
     return new ComplexSignal(prot)
   }
   
   override def getSignalDeclaration:List[SignalDeclarationNew] = { 
     if (proto.isInstanceOf[Constant]) return List()
     val base    = this.getFullSignalList
     val signals = base ::: this.getDelaySignalList;  
     val decs = signals.flatMap(x => x.opType.getSignalDeclaration(x))
     return decs
    
   } 

  
}

object ComplexSignal {

  /** New Signal based on a name and a fixed point type */
  def newComplex(name:String,fixed:FixedType) =
    new ComplexSignal(SignalNew.newSignal(name,fixed))
  /** New Signal based on a name a fixed point type and a length */
  def newComplex(name:String,optype:OpType,fixed:FixedType) =
    new ComplexSignal(SignalNew.newSignal(name,optype,fixed))
  /** New Signal based on a name a fixed point type and a length */
  def newComplex(name:String,optype:OpType,fixed:FixedType,len:Int) =
    new ComplexSignal(SignalNew.newSignal(name,optype,fixed,len))

  def newComplex(name:String,opType:OpType,fixed:FixedType,vector:VectorType):ComplexSignal = {
    val sig = new SignalNew(name,opType,fixed,vector)
    return new ComplexSignal(sig)
  }
  
}


