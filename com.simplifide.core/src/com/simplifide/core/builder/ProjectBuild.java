/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.builder;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.background.BackgroundObject;
import com.simplifide.core.project.HardwareNature;

public class ProjectBuild {

	
	public static void initialBuild() {
		InitialBuild build = new InitialBuild();
		build.schedule();
		
	}
	
	public static class OpenBuild implements BackgroundObject {

		private IProject project;
		public OpenBuild(IProject project) {
			this.project = project;
			
		}
		public IStatus run(IProgressMonitor monitor) {
			try {
				project.build(HardwareBuilder.INITIAL_BUILD, monitor);
			} catch (CoreException e) {
				Status status = new Status(2,CoreActivator.PLUGIN_ID,2,"Problem Loading Project",e);
				return status;
			}
			return Status.OK_STATUS;
		}
		
	}
	
	public static class InitialBuild extends Job {

		public InitialBuild() {super("Loading Project");}
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (int i=0;i<projects.length;i++) {
				IProjectNature nature;
				try {
					nature = (IProjectNature) projects[i].getNature(HardwareNature.ID);
					if (nature != null) {
						projects[i].build(HardwareBuilder.INITIAL_BUILD, monitor);
					}
				} catch (CoreException e) {
					HardwareLog.logError(e);
				} 
					
			}
			return Status.OK_STATUS;
		}
		
	}
}
