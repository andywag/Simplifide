/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.generator.blocks.fft

import com.simplifide.scala2.expression.segments.VectorGroup
import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Comment
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.flop.SimpleFlopList
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.Constant
import com.simplifide.scala2.core.basic.vars.ConstantValue
import com.simplifide.scala2.core.basic.vars.FixedType
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.basic.vars.SignalDeclarationNew
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.core.basic.vars.VectorType
import com.simplifide.scala2.core.complex.ComplexCSDMultiply
import com.simplifide.scala2.core.complex.ComplexConstant
import com.simplifide.scala2.core.complex.ComplexSignal
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.fixed.AdditionSegment
import com.simplifide.scala2.core.basic.fixed.AdditionTerm
import com.simplifide.scala2.core.basic.flop.SimpleFlop

class ButterflySegmentDif(name:String,clk:FlopControl,io:ButterflySegment.IO,
                       internal:FixedType,isFFT:Boolean, flopOut:Boolean) extends StatementSegment{
    
 //def createCSD()
 override def createCode(writer:CodeWriter):SegmentReturn = {
   // Create the Signals for this block
   val statements = new ListBuffer[StatementSegment]
   // Variable Creation
   val addOut  = ComplexSignal.newComplex(name+"_add"   ,OpType.Signal,io.out0.getFixedType, VectorType.NoVector)
   val subOut  = ComplexSignal.newComplex(name+"_sub"   ,OpType.Signal,internal,             VectorType.NoVector)
   val subR  = ComplexSignal.newComplex(name+"_subR"   ,OpType.Reg,internal,             VectorType.NoVector)


   val delay0  = ComplexSignal.newComplex(name+"_delay0",OpType.Reg,   io.out0.getFixedType, VectorType.NoVector)
   val delay1  = ComplexSignal.newComplex(name+"_delay1",OpType.Reg,   io.out0.getFixedType, VectorType.NoVector)
   val delay2  = ComplexSignal.newComplex(name+"_delay2",OpType.Reg,   io.out0.getFixedType, VectorType.NoVector)
   val csdOut  = ComplexSignal.newComplex(name+"_csd"   ,OpType.Signal,io.out0.getFixedType, VectorType.NoVector)

   // Statement Creation

   // Signal Declarations
   val comment_dec  = new Comment.SingleLine("Signal Declarations")
   val dec          = SignalDeclarationNew.createDeclaration(List(addOut,subOut,subR,delay0,delay1,delay2,csdOut))
   statements.append(comment_dec)
   dec.foreach(x => statements.append(x))
   
   // Addition Term
   val comment_add = new Comment.SingleLine("Butterfly Addition")
   val pl  = new AdditionSegment.RoundClip(List(new AdditionTerm.Empty(io.in0),new AdditionTerm.AddTerm(io.in1)),internal)
   val plv = new VectorGroup(new Statement(addOut,pl))
   val comment_sub = new Comment.SingleLine("Butterfly Subtraction")
   val su = new AdditionSegment.RoundClip(List(new AdditionTerm.Empty(io.in0),new AdditionTerm.SubTerm(io.in1)),internal)
   val slv = new VectorGroup(new Statement(subOut,su))

   statements.append(comment_add)
   statements.append(plv)
   statements.append(comment_sub)
   statements.append(slv)
   
   
   // Delay Line Creation
   val comment_del  = new Comment.SingleLine("Butterfly Delay")

   val res          = List(new SimpleFlop.Segment(delay2.getReal,None),
                           new SimpleFlop.Segment(delay2.getImag,None),
                           new SimpleFlop.Segment(delay1.getReal,None),
                           new SimpleFlop.Segment(delay1.getImag,None),
                           new SimpleFlop.Segment(delay0.getReal,None),
                           new SimpleFlop.Segment(delay0.getImag,None),
                           new SimpleFlop.Segment(subR.getReal,None),
                           new SimpleFlop.Segment(subR.getImag,None))

   val ena          = List(new SimpleFlop.Segment(delay2.getReal,Some(addOut.getReal)),
                           new SimpleFlop.Segment(delay2.getImag,Some(addOut.getImag)),
                           new SimpleFlop.Segment(delay1.getReal,Some(delay2.getReal)),
                           new SimpleFlop.Segment(delay1.getImag,Some(delay2.getImag)),
                           new SimpleFlop.Segment(delay0.getReal,Some(delay1.getReal)),
                           new SimpleFlop.Segment(delay0.getImag,Some(delay1.getImag)),
                           new SimpleFlop.Segment(subR.getReal,Some(subOut.getReal)),
                           new SimpleFlop.Segment(subR.getImag,Some(subOut.getImag)))

   val flo          = new SimpleFlopList(Some(name+"_dline"),clk,res,ena)
   statements.append(comment_del)
   statements.append(flo)

   
   
   // CSD Multiplication
   val comment_csd   = new Comment.SingleLine("Butterfly Multiplication")
   val uang          = math.Pi*io.angle
   val ang_val       = if (isFFT) new ConstantValue.Complex(math.cos(uang),-math.sin(uang))
                       else new ConstantValue.Complex(math.cos(uang),math.sin(uang))
   val com           = new Constant("",VectorType.NoVector,io.ang_fixed, ang_val)
   val csd_mult      = new ComplexCSDMultiply(name+"_csd",clk,new ComplexConstant(com),subR,io.out1,internal,2)
   statements.append(comment_csd)
   statements.append(csd_mult)

   statements.append(new Statement(io.out0.getReal,delay0.getReal))
   statements.append(new Statement(io.out0.getImag,delay0.getImag))

   // Output Flop
   /*val comment_output = new Comment.SingleLine("Output Delay")
   val reso          = List(new SimpleFlop.Segment(io.out0.getReal,None),
                            new SimpleFlop.Segment(io.out0.getImag,None),
                            new SimpleFlop.Segment(io.out1.getReal,None),
                            new SimpleFlop.Segment(io.out1.getImag,None))

   val enao          = List(new SimpleFlop.Segment(io.out0.getReal,Some(delay1.getReal)),
                            new SimpleFlop.Segment(io.out0.getImag,Some(delay1.getImag)),
                            new SimpleFlop.Segment(io.out1.getReal,Some(csdOut.getReal)),
                            new SimpleFlop.Segment(io.out1.getImag,Some(csdOut.getImag)))

   val floo          = new SimpleFlopList(Some(name+"_dline"),clk,reso,enao)
   statements.append(comment_output)
   statements.append(floo) */
   
   // Statement Addition




   return StatementSegment.createList(statements.toList, writer)
}  
/** Creates the code for this segment */
 override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = createCode(writer)
}


