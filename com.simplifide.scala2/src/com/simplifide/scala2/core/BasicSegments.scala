/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core

import com.simplifide.scala2.core.basic.StatementSegment
import scala.collection.mutable.ListBuffer
import com.simplifide.scala2.core.basic.vars.BaseSignal

import com.simplifide.scala2.context.CurrentContext


class BasicSegments extends StatementSegment {

  override def createCode(writer:CodeWriter,output:BaseSignal):SegmentReturn = {
	return createCode(writer)
  }
}

class IdentSegment(val name:String) extends BasicSegments {

  override def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment(name)
 
}

class NumberSegment(val value:Int) extends BasicSegments {
  override def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment(value.toString)
}

class QuoteSegment(val name:String) extends BasicSegments {
  override def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment( "'" + name + "'")
}

class StringSegment(val name:String) extends BasicSegments {
   override def createCode(writer:CodeWriter):SegmentReturn = SegmentReturn.segment(name)
}

class ListSegment(val segments:List[BaseCodeSegment]) extends BasicSegments {
 
  override def createCode(writer:CodeWriter):SegmentReturn = {
     val builder = new StringBuilder();
     for (segment <- segments) {
       builder.append(writer.createSimpleCode(segment));
      }
      return SegmentReturn.segment(builder.toString())
  }

  

}

class ListBufferSegment extends BasicSegments {

  val segments = new ListBuffer[BaseCodeSegment]

  override def createCode(writer:CodeWriter):SegmentReturn = {
     val builder = new StringBuilder();
     for (segment <- segments) {
       builder.append(writer.createSimpleCode(segment));
      }
      return SegmentReturn.segment(builder.toString)
  }

 

}



object BasicSegments {

  def ident(in:String):IdentSegment = new IdentSegment(in);
  def string(in:String):StringSegment = new StringSegment(in);
  def quote(in:String):QuoteSegment = new QuoteSegment(in);
  def number(in:Int):NumberSegment = new NumberSegment(in);

}
