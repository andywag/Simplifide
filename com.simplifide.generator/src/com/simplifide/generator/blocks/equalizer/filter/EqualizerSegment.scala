package com.simplifide.generator.blocks.equalizer.filter

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
import com.simplifide.generator.blocks.fft.ParallelFft
import com.simplifide.scala2.parameters.{CoreParameter, CoreParameterHolder}

class EqualizerSegment

object EqualizerSegment {

  def getCommand = Commands

  val TITLE            = "parallel_equalizer"
  val NAME_IFFT        = "ifft"
  val NAME_FILTER      = "filter"
  val NAME_FFT         = "fft"


  val KEYWORDS:List[String] =  List(TITLE,NAME_IFFT,NAME_FFT,NAME_FILTER)

    // PARSER SECTION
  object Parser extends BaseParser with NameLocationParser with GeneralSegment.Parser {
      val commandSection = Commands
      val commandString:String = TITLE

      lexical.reserved   ++= KEYWORDS
      //lexical.delimiters ++= List("(",")",";",",")

      def top = name_location ~ object_definition(NAME_IFFT) ~ object_definition(NAME_FILTER) ~ object_definition(NAME_FFT) ^^
        {case bl ~ ifft ~ filter ~ fft => Nodes.Total(bl,ifft,filter,fft)}

      def parse(s:String):ParseResult[RootNode] = {
        val tokens = new lexical.Scanner(s)
        phrase(top)(tokens)
    }
  }
  // NODE SECTION
  object Nodes {
    case class Total(loc:NameLocationParser.TopNode,
                     ifft:GeneralSegment.Nodes.ObjectDefinition,
                     filter:GeneralSegment.Nodes.ObjectDefinition,
                     fft:GeneralSegment.Nodes.ObjectDefinition) extends RootNode{

      val command:Command = Commands
    	def createSegment(context:CurrentContext,name:String,info:BaseParserInfo):CommandCodeSegment = {
    	  return new Segment(name,
                           loc.getName,
                           loc.getLocation,
                           ifft.getObject.map(x => x.asInstanceOf[ParallelFft.ModuleBase]),
                           filter.getObject.map(x => x.asInstanceOf[ParallelEqualizerFilter.ModuleBase]),
                           fft.getObject.map(x => x.asInstanceOf[ParallelFft.ModuleBase]))
      }
    }


  }
  // SEGMENT SECTION
  case class Segment(blockname:String,
                     name:String,
                     location:String,
                     ifftO:Option[ParallelFft.ModuleBase],
                     filterO:Option[ParallelEqualizerFilter.ModuleBase],
                     fftO:Option[ParallelFft.ModuleBase]) extends CommandCodeSegment(TITLE) {
    /** Template used for creating the state machine */
    override val command:Command = Commands
    override val commands:ListBuffer[Command] = new ListBuffer[Command]();
    override val matchingC:Boolean = false

    override def createCode(writer:CodeWriter):SegmentReturn = {

      if (ifftO == None) return SegmentReturn.error("IFFT Not Found")
      if (filterO == None) return SegmentReturn.error("Filter Not Found")
      if (fftO == None) return SegmentReturn.error("FFT Not Found")
      val eq = new ParallelEqualizer(name,location,ifftO.get,filterO.get,fftO.get)
      val mod = new ParallelEqualizer.ModuleBase(name,location,eq)
      writer.createCode(mod)
      
      val obj = new CoreParameter.Object(blockname,mod)
      CoreParameterHolder.parameters.put(blockname,obj)

      return SegmentReturn.segment("")
    }
  }
    object Commands extends CommandSection(EqualizerSegment.TITLE) {

     def it = this

     override val description:String = "Frequency Domain Equalizer Section"
     /** List of Commands internal to this command */
     override val commands:List[Command] = List(NameLocationParser.NameCommand,
                                           NameLocationParser.LocationCommand,
                                           new GeneralSegment.Commands.ObjectDefinition(NAME_IFFT),
                                           new GeneralSegment.Commands.ObjectDefinition(NAME_FILTER),
                                           new GeneralSegment.Commands.ObjectDefinition(NAME_FFT))

     /** Keywords associated with this command */
     override val keywords:List[String] = KEYWORDS
     /** Parser associate with this command */
     override def getParser():BaseParser = Parser



     override val commandMap = combineMaps(NameLocationParser.NameCommand.commandMap,
                           NameLocationParser.LocationCommand.commandMap,
                           new GeneralSegment.Commands.ObjectDefinition(NAME_IFFT).commandMap,
                           new GeneralSegment.Commands.ObjectDefinition(NAME_FILTER).commandMap,
                           new GeneralSegment.Commands.ObjectDefinition(NAME_FFT).commandMap)

     override val command = combineCommands(TITLE,this.commands)


   }

}


