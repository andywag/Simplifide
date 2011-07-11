/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.operator

import com.simplifide.scala2.core.basic.vars.Signing
import com.simplifide.scala2.core.basic.vars.FixedTypeMain
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.BaseCodeSegment
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn

class Select(val signal:BaseSignal, val top:Option[Int], val bot:Option[Int], val floor:Int) extends StatementSegment {

  def this(signal:BaseSignal,top:Option[Int], bot:Option[Int]) {
    this(signal,top,bot,0)
  }
	
  override def getFixedType:FixedType = {
	  top match {
	 	  case None    => return new FixedTypeMain(Signing.UnSigned,1,0)
	 	  case Some(x) => bot match {
	 	 	  case None    => return new FixedTypeMain(Signing.UnSigned,1,0)
	 	 	  case Some(y) => return new FixedTypeMain(Signing.UnSigned,x-y,0)
	 	  }
	  }
	  return new FixedTypeMain(Signing.UnSigned,1,0)
  }
  override def createCode(writer:CodeWriter, output:BaseSignal) =  writer.createCode(this)
  
  override def createVerilogCode(writer:CodeWriter):SegmentReturn = {
    
    top match {
      case None => return writer.createCode(signal)// No Selection 
      case Some(x) => bot match {
          case None    => {
           val builder = new StringBuilder();
           builder.append(writer.createCode(signal).code)
           builder.append("[")
           builder.append(x.toString)
           builder.append("]")
           return SegmentReturn.segment(builder.toString)
          }
          case Some(y) => 
            val builder = new StringBuilder();
            if (x < signal.getFixedType.width) { // No Sign Extension
               if (y < floor) { // Zero Pad
                 builder.append("{")
                 builder.append(writer.createCode(signal).code)
                 builder.append("[")
                 builder.append(x.toString)
                 builder.append(":"); builder.append(floor); builder.append("]")

                 builder.append(",")
                 builder.append((floor-y).toString)
                 builder.append("'d0")
                 builder.append("}")
               }
               else if (y >= 0) { // Truncate
                  builder.append(writer.createCode(signal).code)
                  builder.append("[")
                  builder.append(x.toString)
                  builder.append(":")
                  builder.append(y.toString)
                  builder.append("]")
               }
            }
            else { // Sign Extension
              builder.append("{")
              if (signal.getFixedType.signed.isSigned) { // Signed Solution
                builder.append("{")
                builder.append((x-signal.getFixedType.width+1).toString)
                builder.append("{")
                builder.append(signal.getName)
                builder.append("[")
                builder.append((signal.getFixedType.width-1).toString)
                builder.append("]")
                builder.append("}")
                builder.append("}")
              }
              else { // Unsigned Solution
                builder.append( (x-signal.getFixedType.width).toString)
                builder.append("'d0");
              }
              builder.append(",")
              builder.append(writer.createCode(signal).code)
              if (y < 0) { // Zero Pad
                 builder.append(",")
                 builder.append((-y).toString)
                 builder.append("'d0")
               }
               else if (y > 0) { // Truncate
                  //builder.append(signal.createVerilogCode(context).code)
                  builder.append("[")
                  builder.append((signal.getFixedType.width-1).toString)
                  builder.append(":")
                  builder.append(y.toString)
                  builder.append("]")
               }
              builder.append("}")
            }
            SegmentReturn.segment(builder.toString)
            
      }
    }
    
  }
  
}

object Select {

  def sign(state:BaseSignal) = newSelect(state,state.getFixedType.width-1)
  def newSelect(state:BaseSignal,top:Int):Select = new Select(state,Some(top),None)
  def newSelect(state:BaseSignal,top:Int,bot:Int):Select = new Select(state,Some(top),Some(bot))


}

