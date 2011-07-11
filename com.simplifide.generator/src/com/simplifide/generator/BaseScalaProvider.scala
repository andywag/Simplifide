package com.simplifide.generator

import blocks.equalizer.cd.{CdTopParser, CdFilterStageParser}
import blocks.equalizer.filter.{EqualizerSegment, EqualizerFilterSegment}
import blocks.equalizer.rotation.ParallelCordicParser
import blocks.fft.gain.ParallelFftGain
import com.simplifide.scala2.parameters.ParameterSegment
//import blocks.equalizer.filter.EqualizerFilterSegment
import com.simplifide.generator.blocks.fft.expression.FftCommand
import com.simplifide.scala2.expression.ExpressionCommands
import com.simplifide.generator.blocks.basic.instance.InstanceCommands
import com.simplifide.generator.blocks.basic.component.ComponentCommands
import com.simplifide.generator.blocks.basic.flop.FlopCommands
import com.simplifide.generator.blocks.basic.state.StateMachineCommand
import com.simplifide.scala2.clocks.ClockCommands
import com.simplifide.scala2.command.CommandSection
import com.simplifide.core.scalaext.ScalaProvider
import com.simplifide.generator.blocks.fft.GenericFFTSection
class BaseScalaProvider() extends ScalaProvider {


  def getClockCommand:CommandSection         = ClockCommands
  def getStateMachineCommand:CommandSection  = StateMachineCommand
  def getFlopCommand:CommandSection          = FlopCommands
  def getComponentCommand:CommandSection     = ComponentCommands
  def getInstanceCommand:CommandSection      = InstanceCommands
  def getExpressionCommand:CommandSection    = ExpressionCommands

  def getParameterCommand:CommandSection           = ParameterSegment.Commands
  def getFftCommand:CommandSection                 = FftCommand
  def getEqualizerFilterCommand:CommandSection     = EqualizerFilterSegment.Commands
  def getEqualizerCommand:CommandSection           = EqualizerSegment.Commands



  def getCommands:java.util.List[CommandSection] = {
	  val commands = new java.util.ArrayList[CommandSection]();
	  commands.add(getClockCommand) 
	  commands.add(getStateMachineCommand)
	  commands.add(getFlopCommand) 
	  commands.add(getComponentCommand) 
	  commands.add(getInstanceCommand)

      commands.add(getParameterCommand)          // Section to Define Parameters/Constants
      commands.add(getExpressionCommand)         // Expression Parser
      commands.add(getFftCommand)                // Parallel FFT Commands
	  commands.add(getEqualizerFilterCommand)    // Equalizer Filter Commands
      commands.add(getEqualizerCommand)          // Equalzier Commands

      commands.add(GenericFFTSection.createCommand)
      commands.add(ParallelCordicParser.createCommand)
      commands.add(CdFilterStageParser.createCommand)
      commands.add(CdTopParser.createCommand)

	  return commands
  }
  
}

object BaseScalaProvider {

}

