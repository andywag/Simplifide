/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression.segments

import com.simplifide.scala2.core.basic.fixed.function.QuadRotateFunction
import com.simplifide.scala2.core.basic.fixed.function.SignFunction
import com.simplifide.scala2.core.basic.vars.BaseConstant.BaseFloat
import com.simplifide.scala2.core.basic.vars.BaseSignal
import com.simplifide.scala2.core.basic.vars.SignalNew
import com.simplifide.scala2.expression.ExpressionReturn
import com.simplifide.scala2.parser.nodes.SimpleNode
import com.simplifide.scala2.top.InterfaceError
import scala.collection.mutable.ListBuffer

class FunctionExpression(val name1:SimpleNode, val expressions:List[Expression],
                         val index:Option[SignalIndex],override val line:Int) extends SignalCallTop(name1.value,line) {
  
  
      
  def getSignal(context:ExpressionContext):Option[BaseSignal] = {
    val segments = expressions.map(x => x.convertExpression(context))
    val sig = context.getSignal(name1.value) 
    sig match {
      case Some(x) => {
        val ret:ExpressionReturn = handleSignal(sig.get.asInstanceOf[SignalNew],segments)
        ret.statement match {
          case Some(x) => return Some(x.asInstanceOf[SignalNew])
          case None    => return None
        }
      }
      case None    => return None
    }
    
    
    
  }
  
  def handleFunction(segments:List[ExpressionReturn]):ExpressionReturn = {
     name1.value match {
      case "sign" => {
          return SignFunction.handleDecode(segments,name1.position.line)
      }
      case "quad_rotate" => return QuadRotateFunction.handleDecode(segments,name1.position.line + line)
      case _      => {
          val err = new InterfaceError.Error(name1.position.line,"Function " + name1.value + " Not Found")
          return ExpressionReturn.error(err)
      }       
    }
  }
  
  def handleSignal(signal:SignalNew,segments:List[ExpressionReturn]):ExpressionReturn = {
    val errors  = new ListBuffer[InterfaceError]()
    val indexes = new ListBuffer[Int]()
    for (segment <- segments) {
      errors.appendAll(segment.errors)
      segment.statement match {
        case None => {
            val err = new InterfaceError.Error(line,"All terms must be integers")
            return new ExpressionReturn(None,None,errors.toList ::: List(err))
        }
        case Some(x) => {
            if (x.isInstanceOf[BaseFloat]) {
              val par = x.asInstanceOf[BaseFloat]
              indexes.append(par.value.intValue)
            }
            else {
              val err = new InterfaceError.Error(line,"All terms must be integers")
              return new ExpressionReturn(None,None,errors.toList ::: List(err))
            }
        }
      }
    }
    var sig = signal
    for (i <- 0 until indexes.size) {
      sig = sig.getSlice(indexes(i))
    }
    
    index match {
      case Some(x)  => {
          x.offset match {
            case None    => return new ExpressionReturn(None,Some(sig),errors.toList )
            case Some(y) => return new ExpressionReturn(None,Some(sig.getRegSlice(y)),errors.toList )
          }

      }
      case None     => return new ExpressionReturn(None,Some(sig),errors.toList )
    }
    
  }


  override def convertExpression(context:ExpressionContext):ExpressionReturn = {
    val segments:List[ExpressionReturn] = expressions.map(x => x.convertExpression(context))
    // Signal
    val sig = context.getSignal(name1.value)  
    
    sig match {
      case Some(x) => {
          val nsig = x.asInstanceOf[SignalNew]
          return handleSignal(nsig,segments)
      }
      case None    => return handleFunction(segments)
    }
    /*
    name.value match {
      case "sign" => {
          return SignFunction.handleDecode(segments,name.position.line)
      }
      case "quad_rotate" => return QuadRotateFunction.handleDecode(segments,name.position.line + line)
      case _      => {
          val err = new InterfaceError.Error(name.position.line,"Function " + name.value + " Not Found")
          return ExpressionReturn.error(err)
      }                
    }*/
  }
}
