package com.simplifide.generator.blocks.equalizer.filter

import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.core.{CodeWriter, SegmentReturn}
import com.simplifide.scala2.command._
import collection.mutable.HashMap
import collection.mutable.ListBuffer
import com.simplifide.scala2.util.StringOps
import com.simplifide.scala2.expression.{FixedSegment}
import com.simplifide.generator.blocks.basic.state.{ClockHeadCommand, ClockHeadNode}
import com.simplifide.generator.general.{GeneralSegment, NameLocationParser}
import com.simplifide.scala2.core.module.Instance
import com.simplifide.scala2.parameters.{CoreParameterHolder, CoreParameter}
import com.simplifide.scala2.parser.{BaseTokens, BaseParserInfo, BaseParser}
import com.simplifide.scala2.parser.nodes.{SimpleNode, IntNode, BaseNode, RootNode}

class ParallelFilterSegment

object ParallelFilterSegment {

  def getCommand = Commands

  val TITLE           = "parallel_filter"
  val TAPS            = "taps"
  val FIXED_IN        = "in_fixed"
  val FIXED_OUT       = "out_fixed"
  val FIXED_TAP       = "tap_fixed"
  val FIXED_INTERNAL  = "internal_fixed"
  val NAME_IN         = "input"
  val NAME_OUT        = "output"
  val NAME_TAP        = "tap"

  val KEYWORDS:List[String] =  List(TITLE,"clock_head","length",FIXED_IN, FIXED_OUT, FIXED_TAP,
		  							FIXED_INTERNAL,TAPS,NAME_IN,NAME_OUT,NAME_TAP)

    // PARSER SECTION
  object Parser extends BaseParser with NameLocationParser with FixedSegment.FixedParser 
  								   with GeneralSegment.Parser {
      val commandSection = Commands
      val commandString:String = TITLE

      lexical.reserved   ++= KEYWORDS
      lexical.delimiters ++= List("(",")",";",",")

      def clk    = addPosition("clock_head" ~ identDef("ClockHead")  ^^
        {case "clock_head" ~ ident   => new ClockHeadNode(ident)})

      def taps   = "taps" ~ "(" ~ repsep(floatDef,",") ~ ")" ^^
        {case "taps" ~ "(" ~ id ~ ")"   => new Nodes.Taps(id)}

      def widths = fixed_definition(FIXED_IN) ~
                   fixed_definition(FIXED_OUT) ~
                   fixed_definition(FIXED_TAP) ~
                   fixed_definition(FIXED_INTERNAL) ^^
        {case in ~ out ~ tap ~ inte => Nodes.Widths(in,out,tap,inte) }

      def sigs =   name_definition(NAME_IN) ~
                   name_definition(NAME_OUT) ~
                   name_definition(NAME_TAP) ^^
        {case in ~ out ~ tap  => Nodes.Signals(in,out,tap) }

      def top = clk ~ name_location ~ int_definition("length") ~ widths ~ sigs^^
        {case cl ~ na ~ le ~ widths ~ sigs => Nodes.Total(cl,na,le,widths,sigs)}

      def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(top)(tokens)
    }
  }
  // NODE SECTION
  object Nodes {
    case class Total(clk:ClockHeadNode,loc:NameLocationParser.TopNode, length:GeneralSegment.Nodes.IntDefinition,wid:Widths,sig:Signals) extends RootNode{
      val command:Command = Commands
    	def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {

        val param = ParallelEqualizerFilter.Params(length.getValue,
                                                   wid.in.getFixedType(info),
                                                   wid.out.getFixedType(info),
                                                   wid.tap.getFixedType(info),
                                                   wid.inte.getFixedType(info))

        val signal = ParallelEqualizerFilter.Signals(clk.createFlopHead,
                                                     sig.in.getValue,
                                                     sig.tap.getValue,
                                                     sig.out.getValue)

    	  return new Segment(name,loc.getName,loc.getLocation,signal,param)
      }
    }
    case class Taps(taps:List[SimpleNode.FloatTop]) {


    }

    case class Widths(in   : GeneralSegment.Nodes.FixedDefinition,
                      tap  : GeneralSegment.Nodes.FixedDefinition,
                      out  : GeneralSegment.Nodes.FixedDefinition,
                      inte : GeneralSegment.Nodes.FixedDefinition) extends BaseNode
    case class Signals(in   : GeneralSegment.Nodes.NameDefinition,
                       out  : GeneralSegment.Nodes.NameDefinition,
                       tap  : GeneralSegment.Nodes.NameDefinition) extends BaseNode

  }
  // SEGMENT SECTION
  case class Segment(blockName:String,
                     name:String,
                     location:String,
                     signal:ParallelEqualizerFilter.Signals,
                     params:ParallelEqualizerFilter.Params) extends CommandCodeSegment(TITLE) {
    /** Template used for creating the state machine */
    override val command:Command = Commands
    override val commands:ListBuffer[Command] = new ListBuffer[Command]();
    override val matchingC:Boolean = false

    override def createCode(writer:CodeWriter):SegmentReturn = {
      val fft = new ParallelEqualizerFilter(name,location,signal,params)
      val mod = new ParallelEqualizerFilter.ModuleBase(name,location,fft)
      writer.createCode(mod)
      //val instance = new Instance(name,model,new collection.immutable.HashMap())
      //return writer.createCode(instance)
      val obj = new CoreParameter.Object(blockName,mod)
      CoreParameterHolder.parameters.put(blockName,obj)
      return SegmentReturn.segment("")
    }
  }
    object Commands extends CommandSection(TITLE) {

     def it = this

     override val description:String = "Frequency Domain Filter Section"
     /** List of Commands internal to this command */
     override val commands:List[Command] = List(ClockHeadCommand,
                           NameLocationParser.NameCommand,
                           NameLocationParser.LocationCommand,
                           Length,
                           new GeneralSegment.Commands.FixedDefinition(FIXED_IN),
                           new GeneralSegment.Commands.FixedDefinition(FIXED_OUT),
                           new GeneralSegment.Commands.FixedDefinition(FIXED_TAP),
                           new GeneralSegment.Commands.FixedDefinition(FIXED_INTERNAL),
                           new GeneralSegment.Commands.NameDefinition(NAME_IN),
                           new GeneralSegment.Commands.NameDefinition(NAME_OUT),
                           new GeneralSegment.Commands.NameDefinition(NAME_TAP))
     /** Keywords associated with this command */
     override val keywords:List[String] = KEYWORDS
     /** Parser associate with this command */
     override def getParser():BaseParser = Parser



     override val commandMap = combineMaps(ClockHeadCommand.commandMap,
                           NameLocationParser.NameCommand.commandMap,
                           NameLocationParser.LocationCommand.commandMap,
                           Length.commandMap,
                           new GeneralSegment.Commands.FixedDefinition(FIXED_IN).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(FIXED_OUT).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(FIXED_TAP).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(NAME_IN).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(NAME_OUT).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(NAME_TAP).commandMap)

     override val command = combineCommands(TITLE,this.commands)

     /** Length of FFT */
     object Length extends Command("length") {
       override val command:String = {
       StringOps.createLineSpace(List("length","${length}"), 20)
     }
     override val description:String = "Total Length of the FFT (Must be power of 2)";
     override val commandMap:HashMap[String,CommandParameter] = {
       HashMap("length" -> new IntParameter("Length of FFT"))
     }
   }

   /** Fixed Type of Angle */
   object InFixed extends Command("twiddle_fixed") {
     override val command:String = {
       StringOps.createLineSpace(List("twiddle_fixed","<${width},${frac}>"), 20)
     }
     override val description:String = "Width of the twiddle factors in the butterfly";
     override val commandMap:HashMap[String,CommandParameter] = {
       HashMap("width" -> new IntParameter("Total Width"),
               "frac"  -> new IntParameter("Number of Fractional Bits"))
     }
   }


 }


} 