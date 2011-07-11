/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.background.BackgroundJob;
import com.simplifide.core.background.BackgroundObject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.HardwareNature;

/** This class contains static methods to handle new project creation 
 *  and build operations
 * @author andy
 *
 */
public class BuildHandler {
	
	//public static QualifiedName SUITE = new QualifiedName(CoreActivator.PLUGIN_ID,"Suite");
	public static QualifiedName MAIN_PROJECT = new QualifiedName(CoreActivator.PLUGIN_ID,"Main_Project");
	public static String MAIN_PROJECT_TRUE = "True";
	
	/** Create a project and add it to the suite holder */
	public static EclipseSuite loadProject(IProject proj)  {
		
		try {
			if (proj.hasNature(HardwareNature.ID)) {
				String suiteName = proj.getFullPath().lastSegment();
				EclipseSuite suite = new EclipseSuite(suiteName, proj,true);
				ActiveSuiteHolder.getDefault().addSuite(proj.getName(), suite);
				return suite;
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	
	/** Loads up all of the projects. Needed to create the children for the navigator*/
	public static void loadProjects() {
		
			IProject[] projectArr = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject proj : projectArr) {
				if (proj.isAccessible()) {
					loadProject(proj);
				}
			}
	}
	
	/** Builds only the main project (persistent property of suite) */
	public static void buildProjects() {
		IProject[] projectArr = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		IProject uproject = null;
		
		for (IProject project : projectArr) {
			if (project.isOpen()) {
				try {
					if (project.hasNature(HardwareNature.ID)) {
						if (uproject == null) uproject = project;
						String pers = project.getPersistentProperty(MAIN_PROJECT);
						if (pers != null) {
							if (pers.equalsIgnoreCase(MAIN_PROJECT_TRUE)) {
								uproject = project;
								break;
							}
						}
					}
				} catch (CoreException e) {
					HardwareLog.logError(e);
				}
			}
		}
		if (uproject != null) {
			InitialBuild build = new InitialBuild(uproject);
			BackgroundJob.runJob("Scanning Projects", build);
		}
	}
	
	/** Build the project on startup called from CoreActivator */
	public static void buildInitialProject() {
		BackgroundJob.runJob("Loading Projects", new BuildTask());
		// Load the Projects 
		//loadProjects();
		//buildProjects();
	}
	
	/** Change the Main Project which is in use */
	public static void changeMainProject(EclipseSuite newSuite) {
		EclipseSuite current = ActiveSuiteHolder.getDefault().getSuite();
		changeMainProject(current,newSuite,false);
	}
	
	/** Change the Main Project from 'current' to 'next' */
	public static void changeMainProject(EclipseSuite current, EclipseSuite next, boolean build) {
		ActiveSuiteHolder.getDefault().setSuite(next);
		try {
			if (current != null && current.getProject() != null && current.getProject().exists()) {
				current.getProject().deleteMarkers(null, true, IResource.DEPTH_INFINITE);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}	
		try{
			if (build && next != null && next.getProject() != null && next.getProject().exists()) {
				next.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, null);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		if (current != null && current.getProject() != null && current.getProject().exists()) {
			current.deleteObject();
		}
	}
	
	/** Convenience method to return the active suite */
	public static EclipseSuite getSuite(IProject project) {
		return ActiveSuiteHolder.getDefault().getMapSuite(project.getName());
	}
	
	public static class InitialBuild implements BackgroundObject {

		public IProject project;

		public InitialBuild(IProject project) {
			this.project = project;
		}

		public IStatus run(IProgressMonitor monitor) {
			
			monitor.setTaskName("Scanning Workspace Projects");
			monitor.beginTask("Scanning Workspace Projects", 1000);
			
			try {
				this.project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				ActiveSuiteHolder.getDefault().setSuite(getSuite(this.project));
				project.build(IncrementalProjectBuilder.FULL_BUILD,null);
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
			finally {
				monitor.done();
			}
			return Status.OK_STATUS;
		}

	}
	
	public static class BuildTask implements BackgroundObject {

		public IStatus run(IProgressMonitor monitor) {
			if (!HardwareBuilder.isBuildEnabled()) return Status.OK_STATUS;
			loadProjects();
			buildProjects();
			if (ActiveSuiteHolder.getDefault().getTreeContent() != null) {
				ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
			}
			return Status.OK_STATUS;
		}
		
	}
	
}
