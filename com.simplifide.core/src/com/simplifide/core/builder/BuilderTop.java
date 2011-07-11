/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.builder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;

public abstract class BuilderTop extends IncrementalProjectBuilder{

	
	 public abstract String getDescriptionId();
////////////////////////////////////////////////////////////////////////////
	   //
	   // Utility methods
	   //
	   ////////////////////////////////////////////////////////////////////////////

	   /**
	    * Add this builder to the specified project if possible.
	    * Do nothing if the builder has already been added.
	    *  
	    * @param project the project (not <code>null</code>)
	    */
	   public static void addBuilderToProject(IProject project, String id) {
	      
	      // Cannot modify closed projects.
	      if (!project.isOpen())
	         return;

	      // Get the description.
	      IProjectDescription description;
	      try {
	         description = project.getDescription();
	      }
	      catch (CoreException e) {
	         HardwareLog.logError(e);
	         return;
	      }
	      
	      // Look for builder already associated.
	      ICommand[] cmds = description.getBuildSpec();
	      for (int j = 0; j < cmds.length; j++)
	         if (cmds[j].getBuilderName().equals(id))
	            return;

	    
	      // Associate builder with project.
	      ICommand newCmd = description.newCommand();
	      newCmd.setBuilderName(id);
	      List<Object> newCmds = new ArrayList<Object>();
	      newCmds.addAll(Arrays.asList(cmds));
	      newCmds.add(newCmd);
	      description.setBuildSpec(
	         (ICommand[]) newCmds.toArray(
	            new ICommand[newCmds.size()]));
	      try {
	         project.setDescription(description, null);
	      }
	      catch (CoreException e) {
	         HardwareLog.logError(e);
	      }
	   }
	   
	  
	   
	   /**
	    * Remove this builder from the specified project if possible.
	    * Do nothing if the builder has already been removed.
	    *  
	    * @param project the project (not <code>null</code>)
	    */
	   public static void removeBuilderFromProject(IProject project, String id) {

	      // Cannot modify closed projects.
	      if (!project.isOpen())
	         return;
	      
	      // Get the description.
	      IProjectDescription description;
	      try {
	         description = project.getDescription();
	      }
	      catch (CoreException e) {
	         HardwareLog.logError(e);
	         return;
	      }
	      
	      // Look for builder.
	      int index = -1;
	      ICommand[] cmds = description.getBuildSpec();
	      for (int j = 0; j < cmds.length; j++) {
	         if (cmds[j].getBuilderName().equals(id)) {
	            index = j;
	            break;
	         }
	      }
	      if (index == -1)
	         return;
	         
	      // Remove builder from project.
	      List<Object> newCmds = new ArrayList<Object>();
	      newCmds.addAll(Arrays.asList(cmds));
	      newCmds.remove(index);
	      description.setBuildSpec(
	         (ICommand[]) newCmds.toArray(
	            new ICommand[newCmds.size()]));
	      try {
	         project.setDescription(description, null);
	      }
	      catch (CoreException e) {
	    	  HardwareLog.logError(e);
	      }
	   }
	}

	
