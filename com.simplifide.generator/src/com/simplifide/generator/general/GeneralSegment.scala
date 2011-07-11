package com.simplifide.generator.general

import com.simplifide.scala2.expression.FixedSegment
import collection.mutable.HashMap
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.parser.nodes.{SimpleNode, BaseNode}
import com.simplifide.scala2.parser.nodes.{IntNode,IdentNode}
import com.simplifide.scala2.parameters.{CoreParameter, CoreParameterHolder}
import com.simplifide.scala2.parser.{BaseTokens, BaseParserInfo, BaseParser}
import com.simplifide.scala2.core.basic.vars.{SignalNew, OpType, FixedType}
import com.simplifide.scala2.command._

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 1/31/11
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */

class GeneralSegment {}

object GeneralSegment  {
  // Parser
  trait Parser extends BaseParser with FixedSegment.FixedParser with BaseTokens{

    def fixed_definition_internal          =  fixed     ^^ {case fix => new Nodes.FixedDefinitionInternal(fix) }
    def name_definition_internal           = (stringDef | identDef) ^^ {case fix => new Nodes.NameDefinitionInternal(fix) }

    def fixed_definition(name:String)      = name ~ fixed_definition_internal  ^^ {case nam ~ fix => new GeneralSegment.Nodes.FixedDefinition(fix) }
    def name_definition(name:String)       = name ~ name_definition_internal   ^^ {case nam ~ fix => new GeneralSegment.Nodes.NameDefinition(fix) }
      def float_definition(name:String)      = name ~ (floatDef  | identDef) ^^  {case nam ~ flo => new GeneralSegment.Nodes.FloatDefinition(flo)}
      def int_definition(name:String)        = name ~ (numberDef | identDef) ^^ {case nam ~ fix => new GeneralSegment.Nodes.IntDefinition(fix) }
      def object_definition(name:String)     = name ~ identDef ^^ {case nam ~ fix => new GeneralSegment.Nodes.ObjectDefinition(fix) }
      def signal_definition(name:String) = name ~ fixed_definition_internal ~ name_definition_internal ^^
          {case nam ~ fix ~ str => new GeneralSegment.Nodes.SignalDefinition(fix,str) }

      def float_internal = (floatDef  | identDef) ^^  {case  flo => new GeneralSegment.Nodes.FloatDefinition(flo)}
      def float_list(name:String) = name ~ "[" ~ rep(float_internal) ~ "]" ^^ {case nam ~ "[" ~ flo ~ "]" => new GeneralSegment.Nodes.FloatListDefinition(flo)}

  }
  // Nodes
  object Nodes {

    /*
     * Node which defines a fixed type. Used from other definitions
     */
    class FixedDefinitionInternal(fix:FixedSegment.Nodes.Fixed) extends BaseNode {
      def getFixedType(info:BaseParserInfo):FixedType = fix.getFixed(info).getFixedBasic.get
    }
    /**
     * Node which defines a string. Used internally from other definitions
     */
    class NameDefinitionInternal(node:BaseNode) extends BaseNode {
      def getValue:String = {
        node match {
          case SimpleNode.StringNode(v,_) => return v
          case IdentNode(v,_) => {
            val par = CoreParameterHolder.parameters.get(v)
            par match {
              case None => return "NotFound"
              case Some(x) => x match {
                case CoreParameter.Ident(key,value) => return value
                case CoreParameter.Strin(key,value) => return value
                case _ => return "NotFound"
              }
            }
          }
        }
        return "Not Found"
      }
    }

    /** Statement which declares a fixed definition */
    class FixedDefinition(fix:FixedDefinitionInternal) extends BaseNode {
      def getFixedType(info:BaseParserInfo):FixedType = fix.getFixedType(info)
    }
    /** Statement which declares a name */
    class NameDefinition(node:NameDefinitionInternal) extends BaseNode {
      def getValue:String = node.getValue
    }

    /** Node which defines a signal with a name and fixed type*/
    class SignalDefinition(val fix:FixedDefinitionInternal,val name:NameDefinitionInternal) {
      def getSignal(info:BaseParserInfo,optype:OpType):SignalNew = {
        val signalName = name.getValue
        val signalFix  = fix.getFixedType(info)
        return SignalNew.newSignal(signalName,optype,signalFix)
      }
    }



    class FloatDefinition(node:BaseNode) extends BaseNode with BaseTokens{
      def getValue:Float = {
        node match {
          case FloatNode(_,_)   => return node.asInstanceOf[FloatNode].value.toFloat
          case IdentNode(v,_) => {
            val par = CoreParameterHolder.parameters.get(v)
            par match {
              case None => return -1
              case Some(x) => if (x.isInstanceOf[CoreParameter.Float]) return x.asInstanceOf[CoreParameter.Float].value
            }
            return 0
          }
        }
      }
    }
    class IntDefinition(node:BaseNode) extends BaseNode {
      def getValue:Int = {
        node match {
          case IntNode(_,_)   => return node.asInstanceOf[IntNode].getNumber
          case IdentNode(v,_) => {
            val par = CoreParameterHolder.parameters.get(v)
            par match {
              case None => return -1
              case Some(x) => if (x.isInstanceOf[CoreParameter.Integer]) return x.asInstanceOf[CoreParameter.Integer].value
            }
            return 0
          }
        }
      }
    }
    class ObjectDefinition(node:IdentNode) extends BaseNode {
      def getObject:Option[scala.ScalaObject] = {
    	val param:Option[CoreParameter] = CoreParameterHolder.parameters.get(node.value)
        param match {
    		case Some(CoreParameter.Object(_,v)) => return Some(v)
    		case _ => None
    	}
      }
    }
    class FloatListDefinition(nodes:List[FloatDefinition]) extends BaseNode {
      def getValue:List[Float] = nodes.map(x => x.getValue)
    }

  }
  // COMMANDS
  object Commands {
     class FixedDefinition(override val name:String) extends Command(name) {
       override val command:String = {
        StringOps.createLineSpace(List(name,"<${" + name + "_w},${" + name + "_f}>"), 20)
        }
      override val description:String = "Fixed Type";
      override val commandMap:HashMap[String,CommandParameter] = {
       HashMap(name + "_w" ->  new IntParameter("Width"),
               name + "_f" ->  new IntParameter("Fraction"))
      }
    }

    class NameDefinition(override val name:String) extends Command(name) {
       override val command:String = {
        StringOps.createLineSpace(List(name,"\"${" + name + "_nam}\"" ), 20)
        }
      override val description:String = "Fixed Type";
      override val commandMap:HashMap[String,CommandParameter] = {
       HashMap(name + "_nam" ->  new StringParameter("Name"))
      }
    }

    class IntDefinition(override val name:String) extends Command(name) {
       override val command:String = {
        StringOps.createLineSpace(List(name,"\"${" + name + "_nam}\"" ), 20)
        }
      override val description:String = "Integer";
      override val commandMap:HashMap[String,CommandParameter] = {
       HashMap(name + "_nam" ->  new IntParameter("Name"))
      }
    }

    class FloatDefinition(override val name:String) extends Command(name) {
       override val command:String = {
        StringOps.createLineSpace(List(name,"\"${" + name + "_nam}\"" ), 20)
        }
      override val description:String = "Float";
      override val commandMap:HashMap[String,CommandParameter] = {
       HashMap(name + "_nam" ->  new FloatParameter("Name"))
      }
    }


    class ObjectDefinition(override val name:String) extends Command(name) {
       override val command:String = {
        StringOps.createLineSpace(List(name,"${" + name + "_nam}" ), 20)
        }
      override val description:String = "Block Name";
      override val commandMap:HashMap[String,CommandParameter] = {
       HashMap(name + "_nam" ->  new StringParameter("Name"))
      }
    }

    class SignalDefinition(override val name:String) extends Command(name) {
       override val command:String = {
        StringOps.createLineSpace(List(name,"<${" + name + "_w},${" + name + "_f}>","${" + name + "_nam}" ), 20)
        }
      override val description:String = "Block Name";
      override val commandMap:HashMap[String,CommandParameter] = {
        HashMap(name + "_w" ->  new IntParameter("Width"),
                name + "_f" ->  new IntParameter("Fraction"),
                name + "_nam" ->  new StringParameter("Name"))
      }
    }

     class FloatListDefinition(override val name:String) extends Command(name) {
       override val command:String = {
        StringOps.createLineSpace(List(name,"[" + "${" + name + "}" + "]"), 20)
        }
      override val description:String = "Block Name";
      override val commandMap:HashMap[String,CommandParameter] = {
        HashMap(name  ->  new IntParameter("Value"))
      }
    }


  }
}