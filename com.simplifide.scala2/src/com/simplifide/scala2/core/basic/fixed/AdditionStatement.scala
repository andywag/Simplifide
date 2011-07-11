/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.core.basic.fixed

import com.simplifide.scala2.core.basic.vars.{BaseSignal, FixedType}
import com.simplifide.scala2.core.basic.{Statement, StatementSegment}

/** Convenience Methods to create Adders */
object AdditionStatement {

  def createFirstTerm(segment:StatementSegment, neg:Boolean):AdditionTerm = {
    neg match {
      case true   => return new AdditionTerm.SubTerm(segment)
      case false  => return new AdditionTerm.Empty(segment)
    }
  }

  def createSecondTerm(segment:StatementSegment, neg:Boolean):AdditionTerm = {
    neg match {
      case true   => return new AdditionTerm.SubTerm(segment)
      case false  => return new AdditionTerm.AddTerm(segment)
    }
  }

  def createTerms(segment1:StatementSegment,segment2:StatementSegment,neg1:Boolean,neg2:Boolean):List[AdditionTerm] = {
    return List(createFirstTerm(segment1,neg1),createSecondTerm(segment2,neg2))
  }

     /** Create an addition circuit with rounds and clipping with both terms positive */
  def createTrunc(output:BaseSignal,segment1:StatementSegment,segment2:StatementSegment,internal:FixedType):Statement =
    createTrunc(output,segment1,segment2,false,false,internal)

  def createTrunc(output:BaseSignal,segment1:StatementSegment,segment2:StatementSegment,
                      neg1:Boolean,neg2:Boolean,internal:FixedType):Statement = {
    val seg = new AdditionSegment.Trunc(createTerms(segment1,segment2,neg1,neg2))
    return new Statement(output,seg)
  }


   /** Create an addition circuit with rounds and clipping with both terms positive */
  def createRound(output:BaseSignal,segment1:StatementSegment,segment2:StatementSegment,internal:FixedType):Statement =
    createRound(output,segment1,segment2,false,false,internal)

  def createRound(output:BaseSignal,segment1:StatementSegment,segment2:StatementSegment,
                      neg1:Boolean,neg2:Boolean,internal:FixedType):Statement = {
    val seg = new AdditionSegment.Round(createTerms(segment1,segment2,neg1,neg2),internal)
    return new Statement(output,seg)
  }

  /** Create an addition circuit with rounds and clipping with both terms positive */
  def createRoundClip(output:BaseSignal,segment1:StatementSegment,segment2:StatementSegment,internal:FixedType):Statement =
    createRoundClip(output,segment1,segment2,false,false,internal)

  def createRoundClip(output:BaseSignal,segment1:StatementSegment,segment2:StatementSegment,
                      neg1:Boolean,neg2:Boolean,internal:FixedType):Statement = {
    val seg = new AdditionSegment.RoundClip(createTerms(segment1,segment2,neg1,neg2),internal)
    return new Statement(output,seg)
  }

}
