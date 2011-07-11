package com.simplifide.scala2.core.complex

import com.simplifide.scala2.core.basic.SimpleStatement
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.operator.Select
import com.simplifide.scala2.core.basic.{Statement, StatementSegment}
import collection.mutable.ListBuffer


/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 1/25/11
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */

/** Compressed Way to Deal with Complex Signals By Storing them in the same signal */
class ComplexVectorArray(override val name:String,override val opType:OpType,val ifixed:FixedType,val len:Int)
  extends SignalNew(name,opType,
                    new FixedTypeMain(Signing.UnSigned,2*ifixed.width*len,0),VectorType.NoVector)  {


  override def getRealFixedType:FixedType = ifixed;

  def createInternalSignal(opType:OpType):ComplexSignal =
      ComplexSignal.newComplex(name+"_internal",opType,getRealFixedType,len)


  def getBottomIndex(index:Int):Int = {
    val opindex = len - index - 1
    val twid = 2*ifixed.width
    opindex*twid
  }

  def getRealSlice(index:Int):Select = {
    val bot = getBottomIndex(index)
    new Select(this,Some(bot+2*ifixed.width-1),Some(bot+ifixed.width))
  }

  def getImagSlice(index:Int):Select = {
    val bot = getBottomIndex(index)
    new Select(this,Some(bot+ifixed.width-1),Some(bot))
  }

  override def copyWithType(op:OpType):ComplexVectorArray =
    new ComplexVectorArray(this.name,op,ifixed,len)

  /** Get the indexes of the real signal at this index */
  private def getRealIndexes(index:Int):(Int,Int) = {
    val bot = getBottomIndex(index)
    return (bot+2*ifixed.width-1,bot+ifixed.width)
  }
  /** Get the indexes of the real signal at this index */
  private def getImagIndexes(index:Int):(Int,Int) = {
    val bot = getBottomIndex(index)
    return (bot+ifixed.width-1,bot)
  }

  /** Returns a select based on the slice of the real signal */
  def getRealSliceSelect(index:Int,top:Int,bot:Int):Select =  {
    val indexes = getRealIndexes(index)
    val to =  indexes._1 - (this.ifixed.width-1 - top)
    val bo =  indexes._2 + bot
    new Select(this,Some(to),Some(bo), indexes._2)
  }

  /** Returns a select base on the slice of the imaginary signal */
  def getImagSliceSelect(index:Int,top:Int,bot:Int):Select =  {
    val indexes = getImagIndexes(index)
    val to =  indexes._1 - (this.ifixed.width-1 - top)
    val bo =  indexes._2 + bot
    new Select(this,Some(to),Some(bo), indexes._2)
  }

}


object ComplexVectorArray {

   def newSignal(name:String,opType:OpType,fixed:FixedType,len:Int):ComplexVectorArray =
      new ComplexVectorArray(name,opType,fixed,len)



	 def bitReverse(value:Int,num:Int):Int = {
    // Kludgey way of handling this. Add a one to the top bit
    // And the remove it to make sure all the bits are created with toBinaryString
    val sh  = value + math.pow(2.0,num).toInt
    val bits = sh.toBinaryString.substring(1)
    var nvalue:Int = 0;
    for (i <- 0 until num) {
      val bit = bits.substring(i,i+1)
      if (bit == "1") nvalue += math.pow(2.0,i).toInt
    }
    return nvalue
  }
	
  class ArrayToComplex(val array:ComplexVectorArray,val complex:ComplexSignal) extends StatementSegment {

    def createStatement(signal:SignalNew,select:Select):StatementSegment =
        new Statement(signal,select)

    /** Return the real signal at the given index */
    def getRealSelect(index:Int):Select = {
      val com = complex.getSlice(index).asInstanceOf[ComplexSignal] // Get the Complex Number
      val wid = com.getFixedType.width                          // Width of the Complex Number
      val topR = array.getFixedType.width-1 - index*2*com.getFixedType.width  // Index of Select
      new Select(array,Some(topR),Some(topR - wid+1))
    }
    /** Return the imaginary signal at the given index */
    def getImagSelect(index:Int):Select = {
      val com = complex.getSlice(index).asInstanceOf[ComplexSignal] // Get the Complex Number
      val wid = com.getFixedType.width                          // Width of the Complex Number
      val topR = array.getFixedType.width-1 - index*2*com.getFixedType.width  // Index of Select
      new Select(array,Some(topR-wid),Some(topR-2*wid+1))
    }

    def getAddress(index:Int):Int = index

    override def createCode(writer:CodeWriter):SegmentReturn = {
      val states = new ListBuffer[StatementSegment]()
      for (i <- 0 until complex.getNumber) {
        val com = complex.getSlice(i).asInstanceOf[ComplexSignal] // Get the Complex Number
        /*val wid = com.getFixedType.width                          // Width of the Complex Number
        val topR = array.getFixedType.width-1 - i*2*com.getFixedType.width  // Index of Select
        val selR = new Select(array,Some(topR),Some(topR - wid+1))
        val selI = new Select(array,Some(topR-wid),Some(topR-2*wid+1)) */
        val stR = this.createStatement(com.getReal,getRealSelect(getAddress(i)))
        val stI = this.createStatement(com.getImag,getImagSelect(getAddress(i)))
        states.append(stR)
        states.append(stI)
      }
      SegmentReturn.combineReturns(states.toList.map(x => writer.createCode(x)),List())
    }
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCode(writer)
    }

  }

  class ComplexToArray(override val array:ComplexVectorArray,override val complex:ComplexSignal) extends ArrayToComplex(array,complex) {
       override def createStatement(signal:SignalNew,select:Select):StatementSegment =
          new SimpleStatement(select,signal)
  }

    class ComplexToArrayBitReverse(override val array:ComplexVectorArray,override val complex:ComplexSignal) extends ComplexToArray(array,complex) {
     override def getAddress(index:Int):Int = {
       val ulen = math.log10(array.len)/math.log10(2.0)
       ComplexVectorArray.bitReverse(index,ulen.toInt)
     }
    }


    class BitReverse(val out:ComplexVectorArray,val in:ComplexVectorArray) extends StatementSegment.Simple {
      override def createCode(writer:CodeWriter):SegmentReturn = {
        val states = new ListBuffer[StatementSegment]()
        for (i <- 0 until out.len) {
          val ulen = math.log10(in.len)/math.log10(2.0)
          val rev  = ComplexVectorArray.bitReverse(i,ulen.toInt)

          val stR = new SimpleStatement(out.getRealSlice(i),in.getRealSlice(rev))
          val stI = new SimpleStatement(out.getImagSlice(i),in.getImagSlice(rev))
          states.append(stR)
          states.append(stI)
        }
      SegmentReturn.combineReturns(states.toList.map(x => writer.createCode(x)),List())
      }
    }



}