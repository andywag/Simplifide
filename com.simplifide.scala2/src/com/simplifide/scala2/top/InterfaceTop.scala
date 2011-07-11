/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.top

import com.simplifide.scala2.context.CurrentContext
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import com.simplifide.scala2.command.CommandCodeSegment
import com.simplifide.scala2.parser.ParserReturnValue
import com.simplifide.scala2.core.{SegmentReturn, CodeWriter}
import parser.TotalParser


/** Main Interface which is called to get information from eclipse */
class InterfaceTop {}

object InterfaceTop {

  // TODO Add Null Checking if the return segment from the parser doesn't exist
  private def createRtl(context:CurrentContext,
                        writer:CodeWriter,
                        results:List[ParserReturnValue], 
                        messages:InterfaceMessage)  {
    for (result <- results) { // Create the RTL equivalent of the code
    	val segment:CommandCodeSegment = result.getSegment.get
      val ret:SegmentReturn     = writer.createCode(segment)

      messages.addMessage(new InterfaceMessageItem.Code(ret.code))
      ret.errors.foreach(x => messages.addMessage(x))
    }
  }
  
  private def createFile(location:String,filename:String,contents:StringBuilder) = {
    val ufile = new File(location)
    val parf = new File(ufile,filename)
    val out = new BufferedWriter(new FileWriter(parf)); 
    out.write(contents.toString); 
    out.close(); 
    
  }
  
  private def createCcode(context:CurrentContext,
                          writer:CodeWriter,
                          results:List[ParserReturnValue], 
                          messages:InterfaceMessage)  {
    val cres = results.filter(x => x.getSegment.get.matchingC)
    if (cres.length > 0) {
      val header = new StringBuilder
      val body   = new StringBuilder
      val head_writer = new CodeWriter.CHeader(context)
      val body_fixed = new CodeWriter.Fixed(context)
      val body_float = new CodeWriter.Float(context)
      body.append("#include \"dsp_basic.h\"\n\n")
      for (result <- cres) { // Filter out only the values which contain c-code
        val segment = result.getSegment
        header.append(head_writer.createSimpleCode(segment.get))
        body.append(body_fixed.createSimpleCode(segment.get))
        body.append(body_float.createSimpleCode(segment.get))
      }
      val location = context.activeProject.getCLocation
      val floc = new File(location)
      if (!floc.exists) floc.mkdir()
      
      this.createFile(location, context.activeFile.getBaseName + ".h", header)
      this.createFile(location, context.activeFile.getBaseName + ".c", body)
    }
    
  }
  
  /** Parses the file and returns the information associated with the parsing */
  private def createItem(context:CurrentContext,writer:CodeWriter, command:String):InterfaceMessage = {
    val messages = new InterfaceMessage()
    // Parse the text from the file
    val results:List[ParserReturnValue] = TotalParser.parseCommand(context,command)
    // List of Errors
    val errors:List[ParserReturnValue] = results.filter(x => x.isError)
    for (error <- errors) { // Handle the errors
       messages.addMessage(error.getError.get)
    }
    // Handle the code segments
    val segments:List[ParserReturnValue] = results.filter(x => x.isSegment)
     // Create the RTL from the parsed output
    this.createRtl(context,writer,segments,messages)
    // Create the C code from the output
    if (!messages.errored) this.createCcode(context, writer, segments, messages)
    
    messages
  }

  /** Returns the parser context associated with this document position.
   *  Used for editting functionallity, like completion
   **/
  def getParseContext(command:String,offset:Int):ParserContext = {
    return TotalParser.getParserContext(command, offset)
  }

  /**Creates the Verilog Code based on the text inside command */
  def createVerilog(context:CurrentContext, command:String):InterfaceMessage = {
    return createItem(context, new CodeWriter.Verilog(context),command)
  }

  /**Creates the VHDL Code based on the text inside command */
  def createVhdl(context:CurrentContext, command:String):InterfaceMessage = {
    return createItem(context, new CodeWriter.Vhdl(context),command) 
  }

 

}


