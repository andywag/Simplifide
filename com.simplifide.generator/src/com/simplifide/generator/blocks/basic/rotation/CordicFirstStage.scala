package com.simplifide.generator.blocks.basic.rotation

import com.simplifide.scala2.core.complex.ComplexSignal
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.fixed.{FixedSelect, AdditionStatement, QuestionSegment}
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.operator.{Concat, Select, UnaryOperator, BinaryOperator}
import com.simplifide.scala2.core.basic._
import com.simplifide.scala2.clocks.FlopControl
import flop.TopFlop
import statement.{StatementMux, CaseStatement2}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/13/11
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */

class CordicFirstStage(name:String,
                  //clk:FlopControl,
                  input:ComplexSignal,
                  angleIn:SignalNew,
                  output:ComplexSignal,
                  angleOut:SignalNew) extends StatementSegment {

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val segments = new ListBuffer[BaseCodeSegment]()
    val main = new FixedTypeMain(Signing.UnSigned,2,0)
    val sel = new FixedSelect(angleIn,main)

    val value  = math.pow(2.0, input.getRealFixedType.width)
    val maxNeg = input.getReal.createConstant(-math.pow(2.0,input.getRealFixedType.width-1).toInt)
    val posRes = input.getReal.createConstant(math.pow(2.0,input.getRealFixedType.width-1).toInt-1)
   
    val negReal = SimpleMux.newMux(new BinaryOperator.EQ(input.getReal,maxNeg),posRes,new UnaryOperator.Negative(input.getReal))
    val negImag = SimpleMux.newMux(new BinaryOperator.EQ(input.getImag,maxNeg),posRes,new UnaryOperator.Negative(input.getImag))

    segments.append(new Comment("Real Quadrant Mux"))
    segments.append(new StatementMux(sel,output.getReal,List(input.getReal,
                                                             input.getImag,
                                                             negReal,
                                                             negImag)))
    segments.append(new Comment("Imag Quadrant Mux"))
    segments.append(new StatementMux(sel,output.getImag,List(input.getImag,
                                                             negReal,
                                                             negImag,
                                                             input.getReal)))

    segments.append(new Comment("Reduce the angle to the first quadrant"))
    val mres  =  new FixedTypeMain(Signing.UnSigned,angleOut.getFixedType.width-1,angleOut.getFixedType.width-1)
    val usel = new Concat(List(Constant.newConstant(0,1),new FixedSelect(angleIn,mres)))
    segments.append(new Statement(angleOut,usel))
    return SegmentReturn.combineReturns(writer,segments)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }

}