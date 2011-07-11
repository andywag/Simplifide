package com.simplifide.generator.blocks.basic.rotation

import com.simplifide.scala2.core.complex.ComplexSignal
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.{Statement, StatementSegment,Comment}
import com.simplifide.scala2.core.basic.fixed.{FixedSelect, AdditionStatement, QuestionSegment}
import com.simplifide.scala2.core.basic.operator.{Select, UnaryOperator, BinaryOperator}
import com.simplifide.scala2.clocks.FlopControl

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/13/11
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */

class CordicStage(name:String,
                  input:ComplexSignal,
                  angleIn:SignalNew,
                  output:ComplexSignal,
                  angleOut:SignalNew,
                  internal:FixedType,
                  angleInternal:FixedType,
                  stage:Int) extends StatementSegment {

  override def createCode(writer:CodeWriter):SegmentReturn = {

    val ustage = stage - 1;
    val segments = new ListBuffer[SegmentReturn]()
    val cordAng = math.atan(math.pow(2.0,-ustage))/math.Pi
    //val cordVal = math.pow(2.0,angleIn.getFixedType.frac)

    // Create Signals
    val direction   = SignalNew.newSignal(name+"_dir")
    val angleSel    = SignalNew.newSignal(name+"_angle_internal",angleInternal)
    val internalSel = ComplexSignal.newComplex(name+"_internal",internal)
    // Create the Declarations
    val sigs:List[SignalNew] = List(direction,angleSel,internalSel)
    val decs = sigs.flatMap(x => x.getSignalDeclaration)
    segments.appendAll(decs.map(x => writer.createCode(x)))

    // Create the Rotation Direction Condition
    val dirCond   = new UnaryOperator.Not(Select.newSelect(angleIn,angleIn.getFixedType.width-1))
    val dirState = new Statement(direction,dirCond)
    segments.append(writer.createCode(new Comment.SingleLine("Calculating the Rotation Direction")))
    segments.append(writer.createCode(dirState))
    // Create the Angle Portion
    def ang    = QuestionSegment.newStatement(angleSel,direction,Constant.newConstant(-cordAng,angleInternal),Constant.newConstant(cordAng,angleInternal))
    def angAdd = AdditionStatement.createRound(angleOut,angleIn,angleSel,angleInternal)
    segments.append(writer.createCode(new Comment.SingleLine("Angle Operations")))
    segments.append(writer.createCode(ang))
    segments.append(writer.createCode(angAdd))
    // Create the Signal Portion
    val ish = FixedSelect.newSelect(input.getImag,internal,ustage)
    val rsh = FixedSelect.newSelect(input.getReal,internal,ustage)
    def rsel   = QuestionSegment.newStatement(internalSel.getReal,direction,ish,new UnaryOperator.Negative(ish))
    def rAdd   = AdditionStatement.createRoundClip(output.getReal,input.getReal,rsel,internal)
    def isel   = QuestionSegment.newStatement(internalSel.getImag,direction,new UnaryOperator.Negative(rsh),rsh)
    def iAdd   = AdditionStatement.createRoundClip(output.getImag,input.getImag,isel,internal)
    segments.append(writer.createCode(new Comment.SingleLine("Data Operations")))
    segments.append(writer.createCode(rsel))
    segments.append(writer.createCode(rAdd))
    segments.append(writer.createCode(isel))
    segments.append(writer.createCode(iAdd))

    return SegmentReturn.combineReturns(segments.toList,List())
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }

}