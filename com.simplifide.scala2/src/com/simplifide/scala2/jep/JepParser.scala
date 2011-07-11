package com.simplifide.scala2.jep

import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.complex.ComplexNumber
class JepParser {
	
	
}

object JepParser {
	
    private def getParser(fixed:FixedType):org.nfunk.jep.JEP = {
      val myParser = new org.nfunk.jep.JEP();
      myParser.addStandardFunctions();
      myParser.addStandardConstants();
      myParser.addComplex();
      myParser.addVariable("w", fixed.width);
      myParser.addVariable("f", fixed.frac);
      return myParser;
    }
  
    def parse(fixed:FixedType,expr:String):Double = {
      val myParser = getParser(fixed)
      myParser.parseExpression(expr);
      return myParser.getValue();
    }
        
    def parseComplex(fixed:FixedType,expr:String):ComplexNumber = {
      val myParser = getParser(fixed)
      myParser.parseExpression(expr);
      val com = myParser.getComplexValue();
      return new ComplexNumber(com.re, com.im)
    }
	
}