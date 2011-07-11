/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simplifide.scala2.util

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class FileOps {

}

object FileOps {

  /** Create a directory at the location location if it doesn't already exist */
  def createDirectory(location:String,extra:Option[String])  {
    val ufile = extra match {
      case None    => new File(location)
      case Some(x) => new File(location,x)
    }
    if (!ufile.exists) ufile.mkdir
  }

  def createFile(location:String,filename:String,contents:StringBuilder) = {
    val ufile = new File(location)
    val parf = new File(ufile,filename)
    if (!parf.exists) parf.createNewFile
    val out = new BufferedWriter(new FileWriter(parf)); 
    out.write(contents.toString); 
    out.close(); 
    
  }

   def createFile(location:String,filename:String,contents:String) = {
    val ufile = new File(location)
    val parf = new File(ufile,filename)
    if (!parf.exists) parf.createNewFile
    val out = new BufferedWriter(new FileWriter(parf));
    out.write(contents.toString);
    out.close();

  }

}
