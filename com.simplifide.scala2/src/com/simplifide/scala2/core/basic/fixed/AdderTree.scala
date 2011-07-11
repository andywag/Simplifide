/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.clocks.FlopControl
import com.simplifide.scala2.core.CodeWriter
import com.simplifide.scala2.core.SegmentReturn
import com.simplifide.scala2.core.basic.Statement
import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.basic.flop.SimpleFlopList
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.flop.SimpleFlop
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.basic.Comment

/**Adder Tree which adds the values defined in the constants keeping
  *the internal width. and containing levels of flops
 */
class AdderTree(val name:String,
                val clk:FlopControl,
                val constants:List[AdderTree.Value],
                val internal:FixedType,
                val levels:Int,
                val flop:Boolean) extends StatementSegment{

  def this(name:String,
           clk:FlopControl,
           constants:List[AdderTree.Value],
           internal:FixedType,
           levels:Int) {
    this(name,clk,constants,internal,levels,true)
  }

  /** Method to handle the case when the input is the output */
  def createBypass(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
		  return null;
  }

  def getFlopRows(depth:Int):List[Int] = {
    val incr = depth.toDouble/levels.toDouble

    val buf = new ListBuffer[Int]()
    val alev = if (flop) levels else levels-1 // Don't Flop if output not required
    for (i <- 0 until levels) {
      val row = math.round((i+1)*incr).toInt-1
      buf.append(row)
    }
    return buf.toList
  }



  override def createCode(writer:CodeWriter, output:BaseSignal):SegmentReturn = {
    // Create the nodes sorted by the shift
    var nodes = constants.flatMap(x => x.createNode).sortBy(e => e.scale)
    // Create a Special Case for a Bypass
    //if (nodes.size == 1) return createBypass(writer,output)

    // Calculate depth of tree
    val size_nodes = nodes.size.toDouble
    val div = math.log10(size_nodes)/math.log10(2.0)
    val depth = math.floor(div + 1.0).toInt
    // Create the Rows of the adder tree
    val rows = new ListBuffer[AdderTree.Row]
    val udepth = if (depth < levels) levels else depth
    val flops:List[Int] = getFlopRows(udepth)
    for (i <- 0 until udepth) {
      val first = if (i == 0) true else false
      val row = if (flops.contains(i)) new AdderTree.FlopRow(clk,name,nodes,internal,udepth-i-1,first)
                else new AdderTree.NormalRow(name,nodes,internal,udepth-i-1,first)
      rows.append(row)
      nodes = row.getOutputNodes
    }
    // Create the Variable Declarations
    val comment_decs = writer.createCode(new Comment.SingleLine("Signal Declarations"))
    val decsInt:List[SignalDeclarationNew] = rows.toList.flatMap(x => x.getSignalDeclarations)
    val decsSort = decsInt.sortBy(x => x.verilogDecType)
    val decs:List[SegmentReturn] = decsSort.toList.map(x => writer.createCode(x))

    val states:List[SegmentReturn] = rows.toList.map(x => writer.createCode(x))
    val outStage = writer.createCode(new Statement(output,nodes(0).signal))

    return SegmentReturn.combineReturns(List(comment_decs) ::: decs ::: states ::: List(outStage), List())
  }

}

object AdderTree {

  class Normal(val tree:AdderTree, val output:BaseSignal) extends StatementSegment.Simple {
    override def createCode(writer:CodeWriter, output:BaseSignal) = writer.createCode(tree,output)
    override def createCode(writer:CodeWriter) = writer.createCode(tree,output)

  }

  class Value(val constant:BaseConstant,val signal:SignalNew) {
    def createNode:List[AdderTree.Node] = {
      
      val con:List[Constant.CSD]  = constant.createCSD
      return con.map(x => new AdderTree.Node(signal,constant.getFixedType.frac - x.value,x.negative))
    }
  }
  class Node(val signal:SignalNew,val scale:Int,val neg:Boolean)  {
    def getFixedSelect(internal:FixedType, first:Boolean):StatementSegment = {
        if (first) {
          val sh:Int = scale
          return new FixedSelect.Scale(signal,internal,sh)
        }
        return signal
    }

    def getFirstAdditionTerm(internal:FixedType, first:Boolean):AdditionTerm =
      if (neg) return new AdditionTerm.SubTerm(getFixedSelect(internal,first))
      else return new AdditionTerm.Empty(getFixedSelect(internal,first))

    def getAdditionTerm(internal:FixedType, first:Boolean):AdditionTerm =
      if (neg) return new AdditionTerm.SubTerm(getFixedSelect(internal,first))
      else return new AdditionTerm.AddTerm(getFixedSelect(internal,first))
  }
  
  /* Defines a row of the adder tree */
  abstract class Row(val name:String,val nodes:List[AdderTree.Node],val internal:FixedType,val level:Int,val first:Boolean) extends StatementSegment {
    /** Returns the individual output signal for this row */
    def getOutSignal(y:Int):SignalNew =
     return new SignalNew(name + "_" + this.level + "_" + y,OpType.Signal,internal,VectorType.NoVector)
    /** Returns the register signal for this row */
    def getRegSignal(y:Int):SignalNew =
     return new SignalNew(name + "r_" + this.level + "_" + y,OpType.Reg,internal,VectorType.NoVector)
    /** Create the adder tree for the row */
    def createAdder(writer:CodeWriter):SegmentReturn = {
      val adders = new ListBuffer[AdderTree.Segment]
      for (i <- 0 until nodes.size/2) {
        val add = new AdderTree.Segment(nodes(2*i),Some(nodes(2*i+1)),this.getOutSignal(i),internal,first)
        adders.append(add)
      }
      if (nodes.size % 2 == 1) adders.append(new AdderTree.Segment(nodes(nodes.size-1),None,this.getOutSignal(nodes.size/2),internal,first))
      val commentRet = writer.createCode(new Comment.SingleLine("Stage " + level + " Adders"))
      val addReturn =  adders.toList.map(x => x.createCode(writer))
      return SegmentReturn.combineReturns(List(commentRet) ::: addReturn, List())
    }

    /**Get the output signal associated with this row. Could be a register
       or a wire */
    def getRealOutputSignal(x:Int) = getOutSignal(x)
    /** Returns a list of output nodes */
    def getOutputNodes:List[AdderTree.Node] = {
      val onodes = new ListBuffer[AdderTree.Node]
      val len = math.ceil(nodes.size.toDouble/2.0).toInt
      val ulen = if (len > 0) len else 1
      for (i <- 0 until ulen) {
        val sig = getRealOutputSignal(i)
        onodes.append(new AdderTree.Node(sig,0,false))
      }
      return onodes.toList
    }
    def getInternalSignals(x:Int):List[SignalNew] = List(getOutSignal(x))
    /** Returns a list of signals associated with this node */
    def getSignalDeclarations:List[SignalDeclarationNew] = {
      val signals = new ListBuffer[SignalDeclarationNew]
      val len = math.ceil(nodes.size.toDouble/2.0).toInt
      for (i <- 0 until len) {
        val sig = getRealOutputSignal(i)
        signals.appendAll(getInternalSignals(i).flatMap(x => x.getSignalDeclaration))
      }
      return signals.toList
    }
    
  }
 
  
  class NormalRow(override val name:String,
                       override val nodes:List[AdderTree.Node],
                       override val internal:FixedType,
                       override val level:Int,
                       override val first:Boolean) extends Row(name,nodes,internal,level,first){
    
	  override def createCode(writer:CodeWriter):SegmentReturn = {
      return createAdder(writer)
    }
	  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCode(writer)
    }
  }
  
  class FlopRow(val clk:FlopControl,
                     override val name:String,
                     override val nodes:List[AdderTree.Node],
                     override val internal:FixedType,
                     override val level:Int,
                     override val first:Boolean) extends Row(name,nodes,internal,level,first){

    def createFlop(writer:CodeWriter):SegmentReturn = {
      val res = new ListBuffer[SimpleFlop.Segment]
      val ena = new ListBuffer[SimpleFlop.Segment]
      for (i <- 0 until nodes.size/2) {
        res.append(new SimpleFlop.Segment(getRegSignal(i),None))
        ena.append(new SimpleFlop.Segment(getRegSignal(i),Some(getOutSignal(i))))
      }
      if (nodes.size % 2 == 1) {
        val index = math.ceil(nodes.size/2).toInt
        res.append(new SimpleFlop.Segment(getRegSignal(index),None))
        ena.append(new SimpleFlop.Segment(getRegSignal(index),Some(getOutSignal(index))))
      }
      val flop = new SimpleFlopList(None,clk,res.toList,ena.toList)

      val commentRet = writer.createCode(new Comment.SingleLine("Stage " + level + " Registers"))
      val flopRet = writer.createCode(flop)
      return SegmentReturn.combineReturns(List(commentRet) ::: List(flopRet),List())
   }
   override def getRealOutputSignal(x:Int) = getRegSignal(x)
   override def getInternalSignals(x:Int):List[SignalNew] = List(getRegSignal(x),getOutSignal(x))

   override def createCode(writer:CodeWriter):SegmentReturn = {
      val code = List(createAdder(writer),createFlop(writer))
      return SegmentReturn.combineReturns(code,List())
   }
   override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCode(writer)
    }
  }
  
  class Segment(val in1:Node,val in2:Option[Node],val out:SignalNew,val internal:FixedType,val first:Boolean) extends StatementSegment {

    override def createCode(writer:CodeWriter):SegmentReturn = {

      val seg:StatementSegment = in2 match {
        case None    => new AdditionSegment.RoundClip(List(in1.getFirstAdditionTerm(internal,first)),internal)
        case Some(x) => new AdditionSegment.RoundClip(List(in1.getFirstAdditionTerm(internal,first),x.getAdditionTerm(internal,first)),internal)
      }
      val sta = new Statement(out,seg)
      return sta.createCode(writer)
    }

    override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
      return createCode(writer)
    }
  }
   
}



