/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

import com.simplifide.scala2.core._
import basic.fixed.{SafeSimpleMultiplySegment, FixedSelect, SimpleMultiplySegment}
import basic.operator.Select
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.flop.SimpleFlop
import com.simplifide.scala2.expression.segments.SignalIndex
import scala.collection.mutable.ListBuffer


/**
 * Class which controls the signals for all generation operations.
 *
 * @param name Signal Name
 * @param opType Operating type of the Signal (Input/Output/Reg/Wire)
 * @param fixed Fixed type for this operation following standard <s,w,f> notation
 * @param vector Possibility to create a signal which is a vector
 */
class SignalNew(val name:String,
                val opType:OpType,
                val fixed:FixedType,
                val vector:VectorType) extends BaseSignal with SignalTrait {

  val arrayLength:Int = 0

  /** Returns the real fixed type operation for normal usage. Overriden by vectors */
  def getRealFixedType:FixedType = fixed;

   /** Get the number of elements that this segment has */
   override def getNumber:Int = {
     if (vector.arr.size == 0) return 0
     return vector.arr(0)
   }
   override def getName:String = name
   override def getBaseName:String = name
   override def getFixedType:FixedType = fixed
   
  /** Returns a register slice for this variable */
   def getRegSlice(index:Int):SignalNew = {
     vector.reg match {
       case Some(x) => {
           val n = if (index == 0) this.name else this.name + "_" + (index-1)
           return this.copyAsSignalNew(n, Some(OpType.Reg), None, Some(VectorType.NoVector))
       }
       case None    => return this
     }
   } 
   override def getSegment(index:Int):StatementSegment = getSlice(index)
   /** Return the value of this signal at the given index */
   def getSlice(index:Int):SignalNew = {
     if (vector.arr.size > 0) {
       val nvec = vector.arr.slice(1, vector.arr.size)
       return this.copyAsSignalNew(this.name + "_" + index, None, None,Some(new VectorType(nvec,vector.reg)))
     }
     return this
   }
   /** Return a list of signals contained in this variable */
   def getFullSignalList:List[SignalNew] = {
     if (getNumber > 0) {
        val sigs = new ListBuffer[SignalNew]()
        for (i <- 0 until getNumber) {
          val sig = getSlice(i)
          // Null Check is due to possible Complex Signal in the list ----- Very kludgy
          if (sig != null) sigs.appendAll(sig.getFullSignalList)
        }
        return sigs.toList
     }
     return List(this)
   }
   
  def getDelaySignalList:List[SignalNew] = {
     val sigs = getFullSignalList
     val nsigs = new ListBuffer[SignalNew]
     for (sig <- sigs) {
       sig.vector.reg match {
         case None    => 
         case Some(x) => {
             for (i <- 1 to x) {
               nsigs.append(sig.getRegSlice(i))
             }
         }
       }
     }
     return nsigs.toList
  }

  def simpleSlice(top:Int):String = name + "[" + top + "]"

   override def copyWithType(optype:OpType) = copy(name,Some(optype),None,None)

  
   override def copyWithName(name1:String):BaseSignal = new SignalNew(name1,opType,fixed,vector)
   /** Create the code. Scales this signal to the output width*/ 
   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn  = {
     val sel = new FixedSelect(this,output.getFixedType)
     return writer.createCode(sel)
   }
   /** Create the code. Scales this signal to the output width*/ 
   override def createCode(writer:CodeWriter):SegmentReturn  = {
     return SegmentReturn.segment(getName)
   }
   
   def createCCode(writer:CodeWriter):SegmentReturn       = {
	   if (this.opType.isOutput) return SegmentReturn.segment("*"+getName)
	   else return SegmentReturn.segment(getName)
   }
   override def createFloatCode(writer:CodeWriter):SegmentReturn       = createCCode(writer)
   override def createFixedCode(writer:CodeWriter):SegmentReturn       = createCCode(writer)
   

  override def createMultSegment(sig:StatementSegment):Option[SimpleMultiplySegment] = 
    new Some(new SafeSimpleMultiplySegment(this,sig))
  
   override def getDelta(index:SignalIndex):BaseSignal = {
     return new SignalDelta(this,index)
   } 
   
   /** Copy the signal and return the actual type */
   def copyAsSignalNew(nam:String,optype:Option[OpType],fix:Option[FixedType],
                       vec:Option[VectorType]):SignalNew = {
     val del = copy(nam,optype,fix,vec)
     return del.asInstanceOf[SignalNew]
   }
    
   /** Copies the signal with different options */
   override def copy(nam:String,optype:Option[OpType],fix:Option[FixedType],vec:Option[VectorType]):BaseSignal = {
     val op  = optype match {case Some(x) => x; case None => this.opType}
     val fi  = fix    match {case Some(x) => x; case None => this.fixed}  
     val ve  = vec    match {case Some(x) => x; case None => this.vector}
     return new SignalNew(nam,op,fi,ve)
   }
  
   def getDeclarationType:String = "wire"


   override def getFlopSegments:List[SimpleFlop.Segment] = {
      def getDelta(signal:SignalNew,index:Int) = {
    	  new SignalDelta.Call(this,null,index)
      }
      val sigs = getFullSignalList
      val nsigs = new ListBuffer[SimpleFlop.Segment]
      for (sig <- sigs) {
       sig.vector.reg match {
         case None    => 
         case Some(x) => {
             for (i <- 0 until x) {
               val seg = new SimpleFlop.Segment(sig.getRegSlice(i+1),Some(sig.getRegSlice(i))) 
               nsigs.append(seg)
             }
         }
       }
     }
     return nsigs.toList
   }
  
   /** Returns a set of signal declarations associated with this signal */
   override def getSignalDeclaration:List[SignalDeclarationNew] = {
	   val base    = this.getFullSignalList
     val signals = base ::: this.getDelaySignalList;
	   val decs = signals.flatMap(x => x.opType.getSignalDeclaration(x))
     return decs
    
   } 
   
   override def getIODeclaration:List[SignalDeclarationNew] = {
     val signals = this.getFullSignalList;
     return signals.flatMap(x => opType.getIODeclaration(x))
   }
  override def getSelect(output:BaseSignal):StatementSegment = new FixedSelect(this,output.getFixedType)

   override def getSignal:BaseSignal = this

   /** Creates and integer value which can be assigned to this variable */
   def createConstant(value:Int):Constant = Constant.newConstant(value,this.getFixedType)
   
   
  
   
}

object SignalNew {
  
  // Convenience Constructors
  /** Creates a single bit signal which is a wire */
  def newSignal(name:String) = new SignalNew(name,OpType.Signal,new FixedTypeMain(Signing.UnSigned,1,0),VectorType.NoVector)
  /** Creates a new single bit signal with the OpType optype */
  def newSignal(name:String,optype:OpType) = new SignalNew(name,optype,new FixedTypeMain(Signing.UnSigned,1,0),VectorType.NoVector)
  /** Creates a new signal with a fixed type as well and programmable optype */
  def newSignal(name:String,fixed:FixedType) = new SignalNew(name,OpType.Signal,fixed,VectorType.NoVector)
  /** Creates a new signal with a fixed type as well and programmable optype */
  def newSignal(name:String,optype:OpType,fixed:FixedType) = new SignalNew(name,optype,fixed,VectorType.NoVector)
  /** Creates a new signal */
  def newSignal(name:String,optype:OpType,fixed:FixedType,len:Int) = new SignalNew(name,optype,fixed,VectorType.newVector(len))


  class Array(override val name:String,
              override val opType:OpType,
              override val fixed:FixedType,
              override val arrayLength:Int) extends SignalNew(name,opType,fixed,VectorType.NoVector) {

  }


  
  
  
  
}

