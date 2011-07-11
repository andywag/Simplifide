package com.simplifide.connect.model

import com.simplifide.connect.output.DotCreator

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/6/11
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */

class TestModule

object TestModule {

  //val SCLK    = new Signal("clk",Signal.INPUT)
  //val SRESET  = new Signal("reset",Signal.INPUT)
  //val SENABLE = new Signal("enable",Signal.INPUT)

  //val SAIN     = new Signal("a_in",Signal.INPUT)
  //val SAOUT    = new Signal("a_out",Signal.OUTPUT)
 //val SBOUT    = new Signal("b_out",Signal.OUTPUT)
 // val SCOUT    = new Signal("c_out",Signal.OUTPUT)


   /** Write out contents to a file */
  def writeFile(fileName:String, contents:String) {

    val out_file = new java.io.FileOutputStream(fileName)
    val out_stream = new java.io.PrintStream(out_file)
    out_stream.print(contents)
    out_stream.close
    //Logger.writeDebugLn("Written File " + fileName)
  }

  def main(args: Array[String]) = {



    //val leafA   =   Module.Leaf("leafA",List(TestCase.Signals.BCLK, SAIN, SAOUT))
    //val leafB   =   Module.Leaf("leafB",List(TestCase.Signals.BCLK, SAOUT.input, SBOUT))
    //val leafC   =   Module.Leaf("leafC",List(TestCase.Signals.BCLK, SBOUT.input, SCOUT))
    //val branchD =   Module.Branch("branchD", List(TestCase.Modules.leafA.attach, TestCase.Modules.leafB.attach))
    //val branchE =   Module.Branch("branchE", List(TestCase.Modules.leafC.attach))
    //val branchF = Module.Root("branchF",List(branchD.attach, branchE.attach), List(SCOUT))
    //val branchF = Module.Root("branchF",List(branchD.attach, branchE.attach), List(TestCase.Signals.SCOUT))

    val connectedRoot = TestCase.Modules.rootF.connect
    writeFile("c:\\LTE\\graph.dot",new DotCreator().createDotContents(connectedRoot))

    System.out.println(connectedRoot.debugString(""))

  }

}