/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.expression

import com.simplifide.scala2.core.basic.vars.FixedType
import segments.FixedCall
import com.simplifide.scala2.command.{IntParameter, CommandParameter, Command}
import collection.mutable.HashMap
import com.simplifide.scala2.parser.nodes.{SimpleNode, ChoiceNode, IdentNode, BaseNode}
import com.simplifide.scala2.parser.{BaseParserInfo, BaseParser}
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.core.basic.vars.{Signing, FixedTypeMain}

class FixedSegment

object FixedSegment {
	trait FixedParser extends BaseParser{
  
		lexical.delimiters ++= List("<",">",",")
		lexical.reserved   ++= List("s","u","c")
  
		def fixed_opt_sign = choiceDef(List("s","u","c")) ~ "," ^^ {case ch ~ "," => ch}
  
		def fixed_def = opt(fixed_opt_sign) ~ anyDef ~ "," ~ anyDef ^^ 
			{case ch ~ wid ~ "," ~ frac => new FixedSegment.Nodes.FixedDef(ch,wid,frac)}
  
		def fixed_param = identDef ^^ {case id => new FixedSegment.Nodes.FixedParam(id)}
  
		def fixed  = "<" ~ (fixed_def|fixed_param) ~ ">" ^^ {case "<" ~ fix ~ ">" => fix}

		//def fixed_type(name:String) = name ~ fixed ^^ {case nam ~ fix => FixedSegment.Nodes.FixedType(fix) }
  }

  // NODE SECTION
  object Nodes {

    abstract class Fixed extends BaseNode {
      def getFixed(info:BaseParserInfo):FixedCall
    }

    case class FixedParam(val name:IdentNode) extends Fixed {
      override def getFixed(info:BaseParserInfo):FixedCall = {
    	  new FixedCall.Param(name.value,info.line + name.position.line )
      }
    }

    case class FixedDef(typ:Option[ChoiceNode],val width:SimpleNode, val frac:SimpleNode) extends Fixed {
    	
      def getFixedType:FixedType = {
    	  val signing = typ match {
    	  case Some(x) => x.value match {
            case "s" => Signing.Signed;
            case "u" => Signing.UnSigned
            case _   => Signing.Control
        }
        case None    => Signing.Signed;
      }
      val fix = new FixedTypeMain(signing,this.width.value.toInt,this.frac.value.toInt)
      return fix
      }
      
      override def getFixed(info:BaseParserInfo):FixedCall = {
        val fix = getFixedType
        new FixedCall.Fixed(fix)
      }
    }

    class FixedName()

  }

  // COMMAND SECTION
  object Commands {
    /** Fixed Type of Angle */
    class FixedCommand(commandName:String) extends Command(commandName) {
      override val command:String = {
      StringOps.createLineSpace(List(commandName,"<${width},${frac}>"), 20)
    }
    override val description:String = "Width of the twiddle factors in the butterfly";
    override val commandMap:HashMap[String,CommandParameter] = {
      HashMap("width" -> new IntParameter("Total Width"),
              "frac"  -> new IntParameter("Number of Fractional Bits"))
      }
    }
  }
}


