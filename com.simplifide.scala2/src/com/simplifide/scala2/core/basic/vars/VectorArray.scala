package com.simplifide.scala2.core.basic.vars

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
class VectorArray(name:String,optype:OpType,fixed:FixedType,val len:Int)
  extends SignalNew(name,optype,
          new FixedTypeMain(Signing.UnSigned,fixed.width*len,0),VectorType.NoVector)  {


  override def getRealFixedType:FixedType = fixed

  /** Get the Bottom Index of this signal */
  def getBottomIndex(index:Int):Int = {
    val opindex = len - index - 1
    val twid = fixed.width
    opindex*twid
  }

   /** Get the slice associate with this input */
  def getSliceSelect(index:Int):Select = {
    val bot = getBottomIndex(index)
    new Select(this,Some(bot+fixed.width-1),Some(bot))
  }

  override def copyWithType(op:OpType):VectorArray = new VectorArray(this.name,op,fixed,len)


}


object VectorArray {


	
  class ArrayToSignal(val array:VectorArray,val signal:SignalNew) extends StatementSegment {

    def createStatement(signal:SignalNew,select:Select):StatementSegment =
        new Statement(signal,select)


    /** Return the imaginary signal at the given index */
    def getSelect(index:Int):Select = {
      val com = signal.getSlice(index)                         // Get the Complex Number
      val wid = com.getFixedType.width                          // Width of the Complex Number
      val topR = array.getFixedType.width-1 - index*com.getFixedType.width  // Index of Select
      new Select(array,Some(topR),Some(topR-wid+1))
    }

    def getAddress(index:Int):Int = index

    override def createCode(writer:CodeWriter):SegmentReturn = {
      val states = new ListBuffer[StatementSegment]()
      for (i <- 0 until signal.getNumber) {
        val com = signal.getSlice(i) // Get the Complex Number
        val stR = this.createStatement(com,getSelect(getAddress(i)))
        states.append(stR)
      }
      SegmentReturn.combineReturns(states.toList.map(x => writer.createCode(x)),List())
    }
    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCode(writer)
    }

  }

  class SignalToArray(override val array:VectorArray,override val signal:SignalNew) extends ArrayToSignal(array,signal) {
       override def createStatement(signal:SignalNew,select:Select):StatementSegment =
          new SimpleStatement(select,signal)
  }




}