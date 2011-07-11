/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.vars

/** Class which defines different types of constant definitions */
import com.simplifide.scala2.core.complex.ComplexNumber
import com.simplifide.scala2.jep.JepParser

abstract class ConstantValue {
  def getFloatValue(fixed:FixedType):Float; 
  def getComplexValue(fixed:FixedType):ComplexNumber;
  def getRealValue(fixed:FixedType):Float = getFloatValue(fixed)
  def getImagValue(fixed:FixedType):Float = (0.0).toFloat
}

object ConstantValue {
  
  class IntegerValue(val value:Int) extends ConstantValue {
    def getFloatValue(fixed:FixedType):Float = value.toFloat/math.pow(2.0, fixed.frac).toFloat
    def getComplexValue(fixed:FixedType):ComplexNumber = new ComplexNumber(value.toDouble,0.0)
    override def toString = value.toString
  }
  
  class FloatValue(val value:Float) extends ConstantValue {
    def getFloatValue(fixed:FixedType):Float = value
    def getComplexValue(fixed:FixedType):ComplexNumber = new ComplexNumber(value.toDouble,0.0)
    override def toString = value.toString
  }
  
  class StringValue(val value:String) extends ConstantValue {
    def getFloatValue(fixed:FixedType):Float = return JepParser.parse(fixed, value).toFloat
    def getComplexValue(fixed:FixedType):ComplexNumber = return JepParser.parseComplex(fixed, value)
    override def getRealValue(fixed:FixedType):Float = getComplexValue(fixed).real.toFloat
    override def getImagValue(fixed:FixedType):Float = getComplexValue(fixed).imag.toFloat
  }
  
  class Complex(val real:Double, val imag:Double) extends ConstantValue{
    def getFloatValue(fixed:FixedType):Float = return real.toFloat
    def getComplexValue(fixed:FixedType):ComplexNumber = return new ComplexNumber(real,imag)
    override def getRealValue(fixed:FixedType):Float = real.toFloat
    override def getImagValue(fixed:FixedType):Float = imag.toFloat
    override def toString = real.toString + "+j" + imag.toString

  }
  
}
