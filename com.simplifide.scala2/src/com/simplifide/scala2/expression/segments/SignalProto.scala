/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.complex.ComplexConstant
import com.simplifide.scala2.core.complex.ComplexSignal
import com.simplifide.scala2.top.InterfaceError

abstract class SignalProto(val name:String,val vec:List[Int],
                           val reg:Option[Int],val fix:FixedCall) {
  

  
  /** Main method which creates an actual signal from this prototype */
  def createSignal(fixed:FixedType):BaseSignal 
  /** Creates a complex signal from this prototype */
  def createComplex(fixed:FixedType):BaseSignal = {
    val prot = this.createSignal(fixed).asInstanceOf[SignalTrait]
    return new ComplexSignal(prot)
  }
  
  
  def createSignal(context:ExpressionContext):SignalProto.Return = {
    val fixR:FixedCall.Return = fix.getFixedType(context) 
    fixR.fixed match {
      case Some(x) => return new SignalProto.Return(Some(createSignal(x)),List())
      case None    => return new SignalProto.Return(None,fixR.error)
    }
  }
  def createFixed:Option[BaseSignal] = None
  
}

object SignalProto {
  
  class Complex(val proto:SignalProto) extends SignalProto(proto.name,proto.vec,proto.reg,proto.fix){
    
    override def createSignal(fix:FixedType):BaseSignal = {
      return proto.createComplex(fix)
    }
        
  }
  
  class InputP(override val name:String, override val vec:List[Int],
                    override val reg:Option[Int], override val fix:FixedCall) extends SignalProto(name,vec,reg,fix) {
    override def createSignal(fix:FixedType):BaseSignal = new SignalNew(name,OpType.Input,fix,new VectorType(vec,reg)) 
  }
  class OutputP(override val name:String,  override val vec:List[Int],
                     override val reg:Option[Int],      override val fix:FixedCall) extends SignalProto(name,vec,reg,fix) {
    override def createSignal(fix:FixedType):BaseSignal = new SignalNew(name,OpType.Output,fix,new VectorType(vec,reg)) 

  }
  class SignalP(override val name:String, override val vec:List[Int],
                     override val reg:Option[Int],     override val fix:FixedCall) extends SignalProto(name,vec,reg,fix) {
    override def createSignal(fix:FixedType):BaseSignal = new SignalNew(name,OpType.Signal,fix,new VectorType(vec,reg)) 
  }
  
   class SignalrP(override val name:String, override val vec:List[Int],
                       override val reg:Option[Int],     override val fix:FixedCall) extends SignalProto(name,vec,reg,fix) {
    override def createSignal(fix:FixedType):BaseSignal = new SignalNew(name,OpType.Signalr,fix,new VectorType(vec,reg)) 
  }
  class ConstantP(override val name:String,  override val vec:List[Int],
                       override val reg:Option[Int], override val fix:FixedCall,value:ConstantValue) extends SignalProto(name,vec,reg,fix) {
    override def createSignal(fix:FixedType):BaseSignal = new Constant(name,new VectorType(vec,reg),fix,value) 
    
    override def createComplex(fixed:FixedType):BaseSignal = {
      val prot = this.createSignal(fixed).asInstanceOf[Constant]
      return new ComplexConstant(prot)
    }
    
  }
  class FixedP(override val name:String,  override val vec:List[Int],override val reg:Option[Int],override val fix:FixedCall) extends SignalProto(name,vec,reg,fix) {
     override def createSignal(fix:FixedType):BaseSignal = new Parameter.Fixed(name,OpType.Constant,fix,new VectorType(vec,reg))
     override def createFixed:Option[BaseSignal] = {
    	 val op:Option[FixedType] = fix.getFixedBasic;
         op match {
           case None => return None
           case Some(x) => return Some(createSignal(x))
         }
    	 
     }
  }
 
  

  
  class Return(val signal:Option[BaseSignal],val errors:List[InterfaceError])
}
