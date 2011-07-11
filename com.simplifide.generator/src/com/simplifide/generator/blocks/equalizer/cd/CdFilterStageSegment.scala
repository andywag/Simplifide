package com.simplifide.generator.blocks.equalizer.cd

import com.simplifide.scala2.parser.nodes.{IntNode, BaseNode, RootNode}
import com.simplifide.scala2.context.CurrentContext
import com.simplifide.scala2.parser.{BaseParserInfo, BaseParser}
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
import com.simplifide.scala2.core.basic.vars.OpType
import com.simplifide.scala2.core.complex.ComplexVectorArray


class CdFilterStageSegment {
	
}



object CdFilterStageSegment {

  def getCommand = Commands

  val TITLE              = "cd_filter_section"

  val LENGTH             = "length"

  val CD_GAIN            = "cd_gain"
  val CSD_WIDTH          = "csd_width"
  val CSD_DELAY          = "csd_delay"

  val FILTER_TAPS        = "filter_taps"
  val FILTER_DELAY       = "filter_delay"
  val FILTER_INWIDTH     = "filter_inwidth"
  val FILTER_OUTWIDTH    = "filter_outwidth"
  val FILTER_INTWIDTH    = "filter_intwidth"

  val ADDER_WIDTH        = "adder_width"

  val SIGNAL_INPUT       = "input"
  val SIGNAL_OUTPUT      = "output"

  val KEYWORDS:List[String] =  List(TITLE,"clock_head",LENGTH,CD_GAIN,CSD_WIDTH,CSD_DELAY,FILTER_TAPS,FILTER_DELAY,
                                    FILTER_INWIDTH,FILTER_OUTWIDTH,FILTER_INTWIDTH, SIGNAL_INPUT, SIGNAL_OUTPUT)

    // PARSER SECTION
  object Parser extends BaseParser with NameLocationParser with FixedSegment.FixedParser 
  								   with GeneralSegment.Parser {
      val commandSection = Commands
      val commandString:String = TITLE

      lexical.reserved   ++= KEYWORDS
      lexical.delimiters ++= List("(",")",";",",")



      def clk    = addPosition("clock_head" ~ identDef("ClockHead")  ^^
        {case "clock_head" ~ ident   => new ClockHeadNode(ident)})

      def length = int_definition(LENGTH) ^^ {case le => new Nodes.Length(le)}

      def csd    = float_definition(CD_GAIN) ~ int_definition(CSD_DELAY) ~ fixed_definition(CSD_WIDTH) ^^
        {case gain ~ delay ~ width => new Nodes.CSD(gain,delay,width)}

      def filter = float_list(FILTER_TAPS) ~ int_definition(FILTER_DELAY) ~ fixed_definition (FILTER_INWIDTH) ~ fixed_definition(FILTER_OUTWIDTH) ~ fixed_definition(FILTER_INTWIDTH) ^^
        {case taps ~ del ~ inw ~ outw ~ intw => new Nodes.Filter(taps,del,inw,outw,intw)}

      def adder = fixed_definition(ADDER_WIDTH) ^^
        {case add => new Nodes.Adder(add)}

      def signals = signal_definition(SIGNAL_INPUT) ~ signal_definition(SIGNAL_OUTPUT) ^^
        {case in ~ out => new Nodes.Signals(in,out)}

                                                                                               
      def top = clk ~ length ~ name_location ~ csd ~ filter ~ adder ~ signals ^^
        {case cl ~ le ~ na ~ cs ~ fil ~ add ~ si => new Nodes.Total(cl,na,le,cs,fil,add,si)}

      def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(top)(tokens)
    }
  }
  // NODE SECTION
  object Nodes {
    class Total(val clk:ClockHeadNode,
                val loc:NameLocationParser.TopNode,
                val len:Length,
                val csd:CSD,
                val filter:Filter,
                val adder:Adder,
                val signals:Signals) extends RootNode{
      val command:Command = Commands
    	def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {

        /*val param = new CdFilterStage.Params(csd.cdGain.getValue,
                                             csd.width.getFixedType(info),
                                             csd.delay.getValue,
                                             filter.taps.getValue.map(x => x.toFloat),
                                             filter.delay.getValue,
                                             filter.inWidth.getFixedType(info),
                                             filter.outWidth.getFixedType(info),
                                             filter.intWidth.getFixedType(info),
                                             adder.addWidth.getFixedType(info))*/

        val inSignal  = signals.in.getSignal(info,OpType.ModuleInput)
        val outSignal = signals.out.getSignal(info,OpType.ModuleOutput)

        val inVec = ComplexVectorArray.newSignal(inSignal.name,OpType.ModuleInput,inSignal.getFixedType,len.length.getValue)
        val outVec = ComplexVectorArray.newSignal(inSignal.name,OpType.ModuleInput,inSignal.getFixedType,len.length.getValue)


        /*val signal = new CdFilterStage.Signals(clk.createFlopHead,
                                               inVec,
                                               outVec)

    	  return new CdFilterStage(name,loc.getLocation,param,signal)
        */
        return null
      }
    }

    class     Length(val length:GeneralSegment.Nodes.IntDefinition) extends BaseNode

    class     CSD(val cdGain  : GeneralSegment.Nodes.FloatDefinition,
                  val delay   : GeneralSegment.Nodes.IntDefinition,
                  val width   : GeneralSegment.Nodes.FixedDefinition) extends BaseNode

    class     Filter(val taps:GeneralSegment.Nodes.FloatListDefinition,
                     val delay:GeneralSegment.Nodes.IntDefinition,
                     val inWidth:GeneralSegment.Nodes.FixedDefinition,
                     val outWidth:GeneralSegment.Nodes.FixedDefinition,
                     val intWidth:GeneralSegment.Nodes.FixedDefinition) extends BaseNode

    class     Adder(val addWidth:GeneralSegment.Nodes.FixedDefinition)

    class     Signals(val in   : GeneralSegment.Nodes.SignalDefinition,
                      val out  : GeneralSegment.Nodes.SignalDefinition) extends BaseNode

  }
  // SEGMENT SECTION
  case class Segment(blockName:String,
                     name:String,
                     location:String,
                     //signal:CdFilterStage.Signals,
                     params:CdFilterStage.Params) extends CommandCodeSegment(TITLE) {
    /** Template used for creating the state machine */
    override val command:Command = Commands
    override val commands:ListBuffer[Command] = new ListBuffer[Command]();
    override val matchingC:Boolean = false

    override def createCode(writer:CodeWriter):SegmentReturn = {
      //val fft = new CdFilterStage(name,location,params,signal)
      //val model = new CdFilterStage.ModuleBase(name,location,fft)
      //writer.createCode(model)
      //val instance = new Instance(name,model,new collection.immutable.HashMap())
      //return writer.createCode(instance)
      //val obj = new CoreParameter.Object(blockName,model)
      //CoreParameterHolder.parameters.put(blockName,obj)
      return SegmentReturn.segment("")
    }
  }
    object Commands extends CommandSection(TITLE) {

     def it = this

     override val description:String = "CD Filter Stage"
     /** List of Commands internal to this command */
     override val commands:List[Command] = List(ClockHeadCommand,
                           NameLocationParser.NameCommand,
                           NameLocationParser.LocationCommand,
                           new GeneralSegment.Commands.IntDefinition(LENGTH),

                           new GeneralSegment.Commands.FloatDefinition(CD_GAIN),            // CD Parameters
                           new GeneralSegment.Commands.IntDefinition(CSD_DELAY),
                           new GeneralSegment.Commands.FixedDefinition(CSD_WIDTH),

                           new GeneralSegment.Commands.FloatListDefinition(FILTER_TAPS),   // Filter Parameters
                           new GeneralSegment.Commands.IntDefinition(FILTER_DELAY),
                           new GeneralSegment.Commands.FixedDefinition(FILTER_INWIDTH),
                           new GeneralSegment.Commands.FixedDefinition(FILTER_OUTWIDTH),
                           new GeneralSegment.Commands.FixedDefinition(FILTER_INTWIDTH),

                           new GeneralSegment.Commands.FixedDefinition(ADDER_WIDTH),       // Adder Parameters

                           new GeneralSegment.Commands.SignalDefinition(SIGNAL_INPUT),     // Signal Parameters
                           new GeneralSegment.Commands.SignalDefinition(SIGNAL_OUTPUT))

     /** Keywords associated with this command */
     override val keywords:List[String] = KEYWORDS
     /** Parser associate with this command */
     override def getParser():BaseParser = CdFilterStageSegment.Parser



     override val commandMap = combineMaps(ClockHeadCommand.commandMap,
                           NameLocationParser.NameCommand.commandMap,
                           NameLocationParser.LocationCommand.commandMap,
                           new GeneralSegment.Commands.IntDefinition(LENGTH).commandMap,

                           new GeneralSegment.Commands.FloatDefinition(CD_GAIN).commandMap,            // CD Parameters
                           new GeneralSegment.Commands.IntDefinition(CSD_DELAY).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(CSD_WIDTH).commandMap,

                           new GeneralSegment.Commands.FloatListDefinition(FILTER_TAPS).commandMap,   // Filter Parameters
                           new GeneralSegment.Commands.IntDefinition(FILTER_DELAY).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(FILTER_INWIDTH).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(FILTER_OUTWIDTH).commandMap,
                           new GeneralSegment.Commands.FixedDefinition(FILTER_INTWIDTH).commandMap,

                           new GeneralSegment.Commands.FixedDefinition(ADDER_WIDTH).commandMap,       // Adder Parameters

                           new GeneralSegment.Commands.SignalDefinition(SIGNAL_INPUT).commandMap,     // Signal Parameters
                           new GeneralSegment.Commands.SignalDefinition(SIGNAL_OUTPUT).commandMap)

     override val command = combineCommands(TITLE,this.commands)


 }


}
