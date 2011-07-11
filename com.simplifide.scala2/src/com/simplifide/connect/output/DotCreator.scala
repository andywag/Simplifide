package com.simplifide.connect.output

import com.simplifide.connect.model.{ConnectedInstance, ConnectedModule}

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: 5/9/11
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */

class DotCreator {

  private def createInternalConnection(connection:ConnectedModule.Connector,indent:String) = {
    val builder = new StringBuilder
    connection.destination.foreach(x => builder.append(indent + connection.source.name + " -> " + x.name + ";\n"))
    builder.toString
  }

  private def createSubGraph(instance:ConnectedInstance, indent:String):String = {
    val builder = new StringBuilder
    builder.append("subgraph cluster_" + instance.name + "{\n" + indent)
    builder.append("label = \"" + instance.name+ "\";\n" + indent)
    instance.module.instances.foreach(x => builder.append(createSubGraph(x, indent + "  ")))
    instance.module.getInternalConnections.foreach(x => builder.append(createInternalConnection(x,indent)))
    builder.append("}\n\n" + indent)
    builder.toString
  }
  /** Create the Dot Contents for this module */
  def createDotContents(module:ConnectedModule):String = {
    val builder = new StringBuilder
    builder.append("digraph " + module.name + " {\n")
    module.instances.foreach(x => builder.append(createSubGraph(x,"  ")))
    builder.append("}\n\n")

    builder.toString
  }
}