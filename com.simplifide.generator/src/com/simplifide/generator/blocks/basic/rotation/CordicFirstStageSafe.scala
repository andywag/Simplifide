package com.simplifide.generator.blocks.basic.rotation

import com.simplifide.scala2.core.complex.ComplexSignal
import com.simplifide.scala2.core.basic.vars._
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.fixed.FixedSelect
import com.simplifide.scala2.core.{BaseCodeSegment, CodeWriter, SegmentReturn}
import com.simplifide.scala2.core.basic.statement.CaseStatement2
import com.simplifide.scala2.core.basic.operator.{Select, UnaryOperator}
import com.simplifide.scala2.core.basic._
/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/13/11
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */

class CordicFirstStageSafe(name:String,
                  //clk:FlopControl,
                  input:ComplexSignal,
                  angleIn:SignalNew,
                  output:ComplexSignal,
                  angleOut:SignalNew) extends StatementSegment {

  override def createCode(writer:CodeWriter):SegmentReturn = {
    val segments = new ListBuffer[BaseCodeSegment]()
    segments.append(new Comment.SingleLine("Rotate the angle input"))

    /*val angleWidth = angleIn.getFixedType.width
    val noRotate = new SimpleStatement(angleOut,angleIn)
    val quad0    = new SimpleStatement(angleOut,new Concat(List(Constant.newConstant(0,2),Select.newSelect(angleIn,angleWidth-3,0))))
    val quad1    = new SimpleStatement(angleOut,new Concat(List(Constant.newConstant(3,2),Select.newSelect(angleIn,angleWidth-3,0))))

    val cas = new CaseStatement2(sel)
    cas.addCondition(Some(Constant.newConstant(0,3)),noRotate)
    cas.addCondition(Some(Constant.newConstant(1,3)),quad1)
    cas.addCondition(Some(Constant.newConstant(2,3)),quad0)
    cas.addCondition(Some(Constant.newConstant(3,3)),quad1)
    cas.addCondition(Some(Constant.newConstant(4,3)),quad0)
    cas.addCondition(Some(Constant.newConstant(5,3)),quad1)
    cas.addCondition(Some(Constant.newConstant(6,3)),quad0)
    cas.addCondition(Some(Constant.newConstant(7,3)),noRotate)
    //segments.append(new TopFlop(None,clk,cas))
    segments.append(AlwaysStar.newAlways(cas))*/

    segments.append(new SimpleStatement(angleOut, new FixedSelect(angleIn,FixedTypeMain.unsigned(angleIn.getFixedType.width,angleIn.getFixedType.frac+2))))

    val angleWidth = angleIn.getFixedType.width
    val sel = new Select(angleIn,Some(angleWidth - 1),Some(angleWidth-3))

    segments.append(new Comment.SingleLine("Calculate the Real Output"))
    val rcas = new CaseStatement2(sel)
    rcas.addCondition(Some(Constant.newConstant(0,3)),new SimpleStatement(output.getReal,input.getReal))
    rcas.addCondition(Some(Constant.newConstant(1,3)),new SimpleStatement(output.getReal,input.getImag))
    rcas.addCondition(Some(Constant.newConstant(2,3)),new SimpleStatement(output.getReal,input.getImag))
    rcas.addCondition(Some(Constant.newConstant(3,3)),new SimpleStatement(output.getReal,new UnaryOperator.Negative(input.getReal)))
    rcas.addCondition(Some(Constant.newConstant(4,3)),new SimpleStatement(output.getReal,new UnaryOperator.Negative(input.getReal)))
    rcas.addCondition(Some(Constant.newConstant(5,3)),new SimpleStatement(output.getReal,new UnaryOperator.Negative(input.getImag)))
    rcas.addCondition(Some(Constant.newConstant(6,3)),new SimpleStatement(output.getReal,new UnaryOperator.Negative(input.getImag)))
    rcas.addCondition(Some(Constant.newConstant(7,3)),new SimpleStatement(output.getReal,input.getReal))
    //segments.append(new TopFlop(None,clk,rcas))
    segments.append(AlwaysStar.newAlways(rcas))

    segments.append(new Comment.SingleLine("Calculate the Real Output"))
    val icas = new CaseStatement2(sel)
    icas.addCondition(Some(Constant.newConstant(0,3)),new SimpleStatement(output.getImag,input.getImag))
    icas.addCondition(Some(Constant.newConstant(1,3)),new SimpleStatement(output.getImag,new UnaryOperator.Negative(input.getReal)))
    icas.addCondition(Some(Constant.newConstant(2,3)),new SimpleStatement(output.getImag,new UnaryOperator.Negative(input.getReal)))
    icas.addCondition(Some(Constant.newConstant(3,3)),new SimpleStatement(output.getImag,new UnaryOperator.Negative(input.getImag)))
    icas.addCondition(Some(Constant.newConstant(4,3)),new SimpleStatement(output.getImag,new UnaryOperator.Negative(input.getImag)))
    icas.addCondition(Some(Constant.newConstant(5,3)),new SimpleStatement(output.getImag,input.getReal))
    icas.addCondition(Some(Constant.newConstant(6,3)),new SimpleStatement(output.getImag,input.getReal))
    icas.addCondition(Some(Constant.newConstant(7,3)),new SimpleStatement(output.getImag,input.getImag))
    //segments.append(new TopFlop(None,clk,icas))
    segments.append(AlwaysStar.newAlways(icas))

    return SegmentReturn.combineReturns(writer,segments)
  }

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
    createCode(writer)
  }

}