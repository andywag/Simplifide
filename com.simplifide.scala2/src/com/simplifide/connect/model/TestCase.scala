package com.simplifide.connect.model

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/9/11
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */

class TestCase

object TestCase {
  object Params {

  }
  object Signals {
    val SCLK     = Signal.Input("clk")
    val SRESET   = Signal.Input("reset")
    val SENABLE  = Signal.Input("enable")

    val BCLK     =  new Signal.Bus("clk",Signal.INPUT,List(SCLK, SRESET, SENABLE))

    val SAIN     = Signal.Input("a_in")
    val SAOUT    = Signal.Input("a_out")
    val SBOUT    = Signal.Input("b_out")
    val SCOUT    = Signal.Output("c_out")
  }

  object Modules {
    val leafA    =   Module.Leaf("leafA",List(TestCase.Signals.BCLK, Signals.SAIN, Signals.SAOUT.output))
    val leafB    =   Module.Leaf("leafB",List(TestCase.Signals.BCLK, Signals.SAOUT.input, Signals.SBOUT.output))
    val leafC    =   Module.Leaf("leafC",List(TestCase.Signals.BCLK, Signals.SBOUT.input, Signals.SCOUT.output))

    val branchD  =   Module.Branch("branchD", List(TestCase.Modules.leafA.attach, TestCase.Modules.leafB.attach))
    val branchE  =   Module.Branch("branchE", List(TestCase.Modules.leafC.attach))
    val rootF    =   Module.Root("rootF",List(branchD.attach, branchE.attach), List(TestCase.Signals.SCOUT))

  }

  object Instances {


  }

}