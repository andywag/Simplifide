package com.simplifide.scala2.core.module

import collection.mutable.LinkedHashMap
import com.simplifide.scala2.core.IdentSegment
import com.simplifide.scala2.core.basic.test.Fdisplay
import com.simplifide.scala2.core.complex.ComplexVectorArray
import com.simplifide.scala2.core.basic.vars._
import com.simplifide.scala2.core.basic.{SimpleStatement, StatementSegment}

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/24/11
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */

abstract class Storage {
  /** Creates the input used for the initial statement */
  def createInitial:(BaseSignal,StatementSegment)
  /** Returns a list of signals created by the Initial Block */
  def getInitialSignals:List[SignalNew]
  /** Returns the statement to store data to a file */
  def getStorageFlopStatement:Option[StatementSegment] = None
  /** getMatlabLoadCommand */
  def getMatlabLoadCommand:String = ""
  /** Returns the Name for the Fdisplay Statement */
  def getFdisplayName:String = ""
  /** Return a list of extra statements */
  def getExtraStatements:List[SimpleStatement] =List()
}

object Storage {
  class File(val location:String, val signal:SignalNew, val binary:Boolean, val path:String) extends Storage  {

    def this(location:String,signal:SignalNew,binary:Boolean) {
      this(location,signal,binary,"")
    }

    override def getExtraStatements:List[SimpleStatement] = {
      if (path.equalsIgnoreCase("")) return List()
      return List(new SimpleStatement(signal,new IdentSegment(path + "." + signal.name)))
    }

    override def getFdisplayName:String = path + signal.name

    def createPointerSignal = SignalNew.newSignal(signal.name + "_optr", OpType.Reg, FixedTypeMain.unsigned(64,0))

    def getInitialSignals:List[SignalNew] = List(createPointerSignal)

    def createInitial:(BaseSignal,StatementSegment) = {
      val builder = new StringBuilder
      builder.append("$fopen(")
      builder.append("\"")
      builder.append("data/")
      builder.append(location)
      if (binary) builder.append(".bin") else builder.append(".txt")
      builder.append("\"")
      builder.append(",")
      builder.append("\"w\"")
      builder.append(")")
      return (createPointerSignal,new IdentSegment(builder.toString))
    }
    override def getStorageFlopStatement:Option[StatementSegment] = Some(new Fdisplay(this))



    override def getMatlabLoadCommand:String = {
      def conv(location:String,signal:SignalNew,index:Option[Int]):String = {
        val builder = new StringBuilder
        builder.append("conv2s(")
        builder.append(location)
        if (index != None) {
          builder.append("(:,")
          builder.append(index.get);
          builder.append(")")
        }
        builder.append(",")
        builder.append(signal.getRealFixedType.width)
        builder.append(",")
        builder.append(signal.getRealFixedType.frac)
        builder.append(")")
        return builder.toString
      }

      val builder = new StringBuilder
      builder.append("% Load the Data ")
      builder.append(signal.name)
      builder.append("\n");

      if (signal.isInstanceOf[ComplexVectorArray] ) {
        val cvec = signal.asInstanceOf[ComplexVectorArray]
        val width = signal.asInstanceOf[ComplexVectorArray].ifixed.width

        builder.append("!vector2complex.sh ../data/")
        builder.append(location)
        builder.append(".bin ../data/")
        builder.append(location)
        builder.append(".txt ")
        builder.append(width)
        builder.append(" ")
        builder.append(cvec.len)
        builder.append("\n")
        builder.append("load ../data/")
        builder.append(location)
        builder.append(".txt;\n")
        builder.append(location)
        builder.append("L = ")
        builder.append(conv(location,cvec,Some(1)))
        builder.append(" + j*")
        builder.append(conv(location,cvec,Some(2)))
        builder.append(";\n\n")
        return builder.toString

      }
      else if (signal.isInstanceOf[VectorArray] ) {
         val cvec = signal.asInstanceOf[VectorArray]
         val width = signal.asInstanceOf[VectorArray].getRealFixedType.width
         builder.append("!vector2complex.sh ../data/")
         builder.append(location)
         builder.append(".bin ../data/")
         builder.append(location)
         builder.append(".txt ")
         builder.append(width)
         builder.append(" ")
         builder.append(cvec.len)

         builder.append("\n")                                 // End Scala Load Command
         builder.append("load ../data/")
         builder.append(location)
         builder.append(".txt;\n")                            // End Matlab Load Command

         builder.append(location)
         builder.append("L = zeros(1,2*length(")
         builder.append(location)
         builder.append("));\n")                              // End variable initialization

         builder.append(location)
         builder.append("L(1:2:end) = ")
         builder.append(conv(location,cvec,Some(1)))
         builder.append(";\n")
         builder.append(location)
         builder.append("L(2:2:end) = ")
         builder.append(conv(location,cvec,Some(2)))
         builder.append(";\n\n")
         return builder.toString
      }
      else {
    	  val builder = new StringBuilder
        builder.append("load ../data/")
        builder.append(location)
        builder.append(".txt;\n")
        builder.append(location)
        builder.append("L = ")
        builder.append(conv(location,signal,None))
        builder.append(" + j*")
        builder.append(conv(location,signal,None))
        builder.append(";\n\n")
        return builder.toString
      }

    }


  }

  class Map(val linkedMap:LinkedHashMap[BaseSignal,Storage]) {

    def getInitialSignals:List[SignalNew] = {
        linkedMap.values.flatMap(x => x.getInitialSignals).toList
    }
    /** Create the structure used to create the initial block for the test bench */
    def createInitialMap:LinkedHashMap[BaseSignal,StatementSegment] = {
      val linkMap = new LinkedHashMap[BaseSignal,StatementSegment]()
      for ((key,value) <- linkedMap) {
        val (retSignal,retValue):(BaseSignal,StatementSegment) = value.createInitial
        linkMap.put(retSignal,retValue)
      }
      return linkMap
    }
    def getStorageFlopSegments:List[StatementSegment] = {
      return linkedMap.values.flatMap(x => x.getStorageFlopStatement).toList
    }

    def getMatlabLoadCommands:String = {
      val builder = new StringBuilder
      linkedMap.values.flatMap(x => builder.append(x.getMatlabLoadCommand))
      return builder.toString
    }

    def getExtraAssignStatements:List[SimpleStatement] = {
      val states = linkedMap.values.flatMap(x => x.getExtraStatements);
      return states.toList
    }


  }
}