package com.simplifide.scala2.core.module

import com.simplifide.scala2.core.basic.StatementSegment
import com.simplifide.scala2.core.{SegmentReturn, CodeWriter}
import com.simplifide.scala2.util.FileOps
import com.simplifide.scala2.core.basic.vars.BaseSignal
import collection.mutable.ListBuffer

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 2/24/11
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */

/**Class which contains a project which consists of a segment it's corresponding module and test information
 * @param module Information associated with the module
 * @param test   Information associated with creating the test for the module
  */
abstract class Project[+T <: ModuleSegment](val name:String,
                       val location:String) extends StatementSegment {

  /** Segment used for this project */
  val moduleSegment:T


  /** Create the set of test directories associated with this project.
   *  Currently this consists of
   *  design
   *    gen
   *  test
   *    src
   *    data
   *    matlab
   *
   **/
  private def createDirectories {
     FileOps.createDirectory(location,Some("design/gen"))
     FileOps.createDirectory(location,Some("test/src"))
     FileOps.createDirectory(location,Some("test/data"))
     FileOps.createDirectory(location,Some("test/matlab"))
  }

  /** Returns the location of the design directory */
  protected def getDesignLocation:String = location + "/design/gen"
  /** Returns the test location */
  protected def getTestLocation:String = location + "/test"
  /** Returns the location of the design directory */
  protected def getTestSourceLocation:String = location + "/test/src"
    /** Returns the location of the design directory */
  protected def getMatlabLocation:String = location + "/test/matlab"
  /** Returns the design module */
  private def createModule:Module = new Module.Segment(name,getDesignLocation,moduleSegment)
  /** Returns the name of the test */
  private def getTestName:String = name + "_test"
  /* return the module corresponding to the testbench */
  private def createTestbench:Module = {
    val tb = new TestBench(name + "_test",moduleSegment)
    new Module.Segment(name + "_test",getTestSourceLocation,tb)
  }
  private def prependV:String = "V" + getTestName


  private def createMatlab {
    FileOps.createFile(getMatlabLocation,getTestName + ".m",moduleSegment.getStorageMap.getMatlabLoadCommands)
  }

  private def createCpp  {
    val builder = new StringBuilder
    if (moduleSegment.trace) {
        builder.append("#include")
        builder.append("\"verilated_vcd_c.h\"\n")
    }

    builder.append("#include <verilated.h>\n")
    builder.append("#include \""); builder.append(prependV); builder.append(".h\"\n\n")
    builder.append("unsigned int main_time = 0;\n\n")
    builder.append("double sc_time_stamp () {\n")
    builder.append("    return main_time;\n")
    builder.append("}\n\n\n")
    builder.append("\n\n")
    builder.append(prependV)
    builder.append(" *top;\n\n")
    builder.append("int main(int argc, char** argv) {\n")
    builder.append("    Verilated::commandArgs(argc, argv);\n")
    builder.append("    top = new "); builder.append(prependV); builder.append(";\n")
    if (moduleSegment.trace) {
        builder.append("Verilated::traceEverOn(true);\n")
        builder.append("VerilatedVcdC* tfp = new VerilatedVcdC;\n")
        builder.append("top->trace (tfp, 99);\n")
        builder.append("tfp->open (\"simx.vcd\");\n")
    }
    builder.append("    while (!Verilated::gotFinish()) {\n")
    builder.append("       if ((main_time % 4) == 2) {\n")
    builder.append("              top->clk = 1;\n")
    builder.append("       }")
    builder.append("       else if ((main_time % 4) == 0) {\n")
    if (moduleSegment.trace) {builder.append("              tfp->dump(main_time);\n") }
    builder.append("              top->clk = 0;\n")
    builder.append("       } ")
    builder.append("       top->eval();\n")
    builder.append("       main_time++;\n")
    builder.append("    }\n")
    builder.append(" top->final();\n\n")
    if (moduleSegment.trace) {
      builder.append("tfp->close();\n")
    }
    builder.append("}\n\n")
    FileOps.createFile(getTestSourceLocation,getTestName + ".cpp",builder)
  }

  private def createMakefile {
      val builder = new StringBuilder
      builder.append("DESIGN    = ../design/gen\n")
      // Project Location Handling
      for (project <- moduleSegment.projects) {
        builder.append(project.name.capitalize); builder.append("LOCATION = ")
        builder.append(project.location); builder.append("/design/gen\n")
      }
      builder.append("ARGS      = -cc -Wno-COMBDLY")
      if (moduleSegment.trace) builder.append(" --exe --trace ")
      builder.append("\n\n\n")
      builder.append("SOURCE    = ")
      val files = new ListBuffer[String]
      files.append("src/" + getTestName)
      files.appendAll(moduleSegment.getFileList.map(x => "${DESIGN}/" + x))
      //files.append("${DESIGN}/" + moduleSegment.name )
      for (project <- moduleSegment.projects) {
        val na = "${" + project.name.capitalize + "LOCATION}/"
        project.moduleSegment.getFileList.foreach(x => files.append(na + x))
      }

      var first = true
      for (file <- files) {
        if (!first) builder.append(" \\\n        ")
        builder.append(file + ".v")
        first = false
      }
      builder.append("\n\n")
      builder.append("CSOURCE   = src/")
      builder.append(getTestName)
      builder.append(".cpp\n\n")

      builder.append("run :\n")
	    builder.append("\tverilator ${ARGS} ${SOURCE} -exe ${CSOURCE};\n")
	    builder.append("\tmake -C obj_dir -j -f ");
      builder.append(prependV)
      builder.append(".mk ")
      builder.append(prependV)
      builder.append(";\n")
	    builder.append("\tcp obj_dir/")
      builder.append(prependV)
	//complex2vector.sh data/xr_in.txt data/xr_in.bin 8 8;
	    builder.append(" .;\n")
      builder.append("\t./")
      builder.append(prependV)

	//vector2complex.sh data/data_out.bin data/data_out.txt 8 8;
	//vector2complex.sh data/data_in.bin data/data_in.txt 8 8;

      FileOps.createFile(getTestLocation,getTestName + ".mk",builder)
  }

  override def createCode(writer:CodeWriter):SegmentReturn = {
    // Creat the directories for the modules assuming they don't already exist
    createDirectories
    // Create the module code
    writer.createCode(createModule)
    // Create the test bench
    writer.createCode(createTestbench)
    // Create the SystemC Testbench
    createCpp
    // Create the Makefile for the Simulation
    createMakefile
    // Create the Matlab File for Loading the Data
    createMatlab
    return SegmentReturn.segment("")

  }

  override def createCode(writer:CodeWriter, output:BaseSignal):SegmentReturn = createCode(writer)


}

object Project {

  class Basic[+T<: ModuleSegment](override val name:String,
              override val location:String,
              override val moduleSegment:T) extends Project[T](name,location) {

  }



}