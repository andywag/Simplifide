/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.complex

import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.basic.{Comment, Statement, StatementSegment}
import com.simplifide.scala2.core.basic.flop.SimpleFlopList
import com.simplifide.scala2.core.basic.flop.SimpleFlop
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.basic.fixed.{AdditionTerm, AdditionSegment, MultiplySegment, SimpleMultiplySegment}
import com.simplifide.scala2.core.module.Module

class ComplexMultiply(name:String,
					  val clk:FlopControl,
                      val firstC:ComplexSignal,
                      val secondC:ComplexSignal,
                      val output:ComplexSignal,
                      conjugate:Boolean,
                      internal:FixedType) extends SimpleMultiplySegment(firstC,secondC) {

   override def createCode(writer:CodeWriter):SegmentReturn = {
      val outComplex = output.asInstanceOf[ComplexSignal]
     val statements = new ListBuffer[StatementSegment]
     // Create the Signals
     // Multiplier Outputs
     val rMultOut = new ComplexSignal(new SignalNew(output.getBaseName + "_rmult",OpType.Signal,internal,VectorType.NoVector))
     val iMultOut = new ComplexSignal(new SignalNew(output.getBaseName + "_imult",OpType.Signal,internal,VectorType.NoVector))
     // Registered Multiplier Outputs
     val rMultOutr = new ComplexSignal(new SignalNew(output.getBaseName + "_rmult_d",OpType.Signal,internal,VectorType.NoVector))
     val iMultOutr = new ComplexSignal(new SignalNew(output.getBaseName + "_imult_d",OpType.Signal,internal,VectorType.NoVector))
     // Adder Outputs
     val addOut    = new ComplexSignal(new SignalNew(output.getBaseName + "_add",OpType.Signal,internal,VectorType.NoVector))
     // Signal Declarations
     val signals = List(rMultOut,iMultOut,rMultOutr,iMultOutr,addOut)

     val decs = signals.flatMap(x => x.getSignalDeclaration)
     statements.append(new Comment.SingleLine("Internal Signal Declarations"))
     statements.appendAll(decs)
     // Create the Multiply Segments

     val rrMultSeg = new Statement(rMultOut.getReal,new MultiplySegment.RoundClip(firstC.getReal,secondC.getReal,internal))
     val iiMultSeg = new Statement(rMultOut.getImag,new MultiplySegment.RoundClip(firstC.getReal,secondC.getImag,internal))
     val riMultSeg = new Statement(iMultOut.getReal,new MultiplySegment.RoundClip(firstC.getImag,secondC.getImag,internal))
     val irMultSeg = new Statement(iMultOut.getImag,new MultiplySegment.RoundClip(firstC.getImag,secondC.getReal,internal))
     statements.append(new Comment.SingleLine("Multiplier Declarations"))
     statements.appendAll(List(rrMultSeg,iiMultSeg,riMultSeg,irMultSeg))
     // Delay Line Creation
     val res          = List(new SimpleFlop.Segment(rMultOutr.getReal,None),
                             new SimpleFlop.Segment(rMultOutr.getImag,None),
                             new SimpleFlop.Segment(iMultOutr.getReal,None),
                             new SimpleFlop.Segment(iMultOutr.getImag,None))

     val ena          = List(new SimpleFlop.Segment(rMultOutr.getReal,Some(rMultOut.getReal)),
                             new SimpleFlop.Segment(rMultOutr.getImag,Some(rMultOut.getImag)),
                             new SimpleFlop.Segment(iMultOutr.getReal,Some(iMultOut.getReal)),
                             new SimpleFlop.Segment(iMultOutr.getImag,Some(iMultOut.getImag)))

     val flo          = new SimpleFlopList(Some(name+"_dline"),clk,res,ena)
     statements.append(flo)

     // Create Addition Segments
     val first  = if (conjugate) new AdditionTerm.SubTerm(iMultOutr.getReal) else new AdditionTerm.AddTerm(iMultOutr.getReal)
     val second = if (conjugate) new AdditionTerm.SubTerm(rMultOutr.getImag) else new AdditionTerm.Empty(rMultOutr.getImag)
     val radd = new AdditionSegment.RoundClip(List(new AdditionTerm.Empty(rMultOutr.getReal),first),internal)
     val iadd = new AdditionSegment.RoundClip(List(second,new AdditionTerm.AddTerm(iMultOutr.getImag)),internal)
     // Create the Addition Statements
     val rs = new Statement(addOut.getReal,radd)
     val is = new Statement(addOut.getImag,iadd)
     statements.append(new Comment.SingleLine("Addition Declaration"))
     statements.append(rs)
     statements.append(is)
     // Output Delay Creation
     val reso          = List(new SimpleFlop.Segment(outComplex.getReal,None),
                              new SimpleFlop.Segment(outComplex.getImag,None))

     val enao          = List(new SimpleFlop.Segment(outComplex.getReal,Some(addOut.getReal)),
                              new SimpleFlop.Segment(outComplex.getImag,Some(addOut.getImag)))

     val floo          = new SimpleFlopList(Some(name+"_dline"),clk,reso,enao)
     statements.append(floo)

     val segs = statements.toList.map(x => writer.createCode(x))
     return SegmentReturn.combineReturns(segs,List())
   }

   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCode(writer)
   }

}

object ComplexMultiply {
  class ModuleBase(override val name:String,
                           override val location:String,
                           val mult:ComplexMultiply) extends Module(name,location) {

       override def getInputs:List[SignalNew] = {
          val buffer = new ListBuffer[SignalNew]
          buffer.appendAll(mult.clk.getAllSignals(OpType.ModuleInput))
          buffer.append(mult.firstC)
          buffer.append(mult.secondC)
          return buffer.toList
        }

        override def getOutputs:List[SignalNew]         = List(mult.output)

        override def getSignals:List[SignalNew]         = List()

        override def getSegments:List[StatementSegment] = List(mult)

        def getRealIn0Name:String = mult.firstC.getReal.getName
        def getImagIn0Name:String = mult.firstC.getImag.getName
        def getRealIn1Name:String = mult.secondC.getReal.getName
        def getImagIn1Name:String = mult.secondC.getImag.getName
        def getRealOutName:String = mult.output.getReal.getName
        def getImagOutName:String = mult.output.getImag.getName

      }

}
