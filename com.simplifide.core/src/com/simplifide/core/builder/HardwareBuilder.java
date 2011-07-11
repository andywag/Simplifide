/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.builder;


import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.background.BackgroundJob;
import com.simplifide.core.background.BackgroundObject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.python.PythonStartup;
import com.simplifide.core.python.SaveActionInterpreter;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class HardwareBuilder extends IncrementalProjectBuilder{

	public static final String BUILDER_ID = CoreActivator.PLUGIN_ID + ".builder.HardwareBuilder";

	public static int INITIAL_BUILD = 10000;
	
	private static boolean disable_build;
	
	//private EclipseSuite suite;

	public HardwareBuilder() {}


	public static void disableBuild() {
		disable_build = true;
	}
	
	public static void enableBuild() {
		disable_build = false;
	}
	
	public static boolean isBuildEnabled() {
		return !disable_build;
	}
	
	private EclipseSuite getSuite() {
		return  (EclipseSuite) ActiveSuiteHolder.getDefault().getMapSuite(this.getProject().getName());
		
	}
	
	/** @todo, there seems to be an issue with the active suite being correct */
	private boolean buildEnabled() {
		boolean en = CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.BUILD_ENABLED);
		return en;
	}

	private void initialBuild(IProgressMonitor monitor) {
		
		EclipseSuite suite = this.getSuite();
		
		ActiveSuiteHolder.getDefault().setSuite(suite);
		if (suite == null || !buildEnabled() ) return;
		try {
			//PythonInterpreterHolder.getInstance().updatePath();
		} 
		catch (Exception e) {
			HardwareLog.logError(e);
		}
		suite.build(BuildInterface.SUITE_INITIAL_BUILD, monitor);
			
	}
	
	/** Handle a build based on a resource change which calls a resource change handler */
	private IProject[] buildResourceChange(int kind, Map args, IProgressMonitor monitor) throws CoreException {	
		if (!disable_build) {
			EclipseSuite suite = this.getSuite();
			//PythonInterpreterHolder.getInstance().updatePath();
			BuilderChangeState builder = new BuilderChangeState(suite,this);
			IResourceDelta delta = this.getDelta(this.getProject());
			delta.accept(builder);
			if (builder.validChange()) {
				builder.resolveChangeList();
				suite.createHierarchy();
				DesignFile dfile = builder.getChangedFile();
				SaveActionInterpreter.getDefault().save(dfile);
			}
			else if (builder.projectChange()) {
				suite.clean();
			}
		}
		return null;
	}
	
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {	

		
		if (!this.buildEnabled()) return null; // User Option
		if (kind == IncrementalProjectBuilder.AUTO_BUILD) {
			return this.buildResourceChange(kind, args, monitor);
		}
		
		if (kind == IncrementalProjectBuilder.CLEAN_BUILD) {
			this.clean(monitor);
			PythonStartup.getDefault().cleanUp();
		}
		else if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			this.initialBuild(monitor);
		}
		else if (kind == BuildInterface.BUILD_HDLDOC) {
			HardwareLog.logInfo("test");
		}
		return null;
	}

	public void fullRebuild() {

		if (!this.buildEnabled()) return;

		BackgroundObject obj = new BackgroundObject() {
			public IStatus run(IProgressMonitor monitor) {
				try {
					cleanUp();
					initialBuild(monitor);
				} catch (CoreException e) {
					HardwareLog.logError(e);
				}

				return Status.OK_STATUS; 
			};

		};
		BackgroundJob.runJob("Rebuilding Project", obj);

	}

	private void cleanUp() throws CoreException{

		if (!this.buildEnabled()) return;
		// Remove the markers
		this.getProject().deleteMarkers(null, true, IResource.DEPTH_INFINITE);
		EclipseSuite suite = this.getSuite();
		if (suite != null) { 
			// Delete the Internals of the Suite
			suite.deleteObject();
			// Recreate the Object (This is questionable)
			suite.publicInit();
		}
		//PythonStartup.getDefault().cleanUp();
		
		SaveActionInterpreter.getDefault().clean();
		// These opertions can't occur until the project is cleaned
		//SaveActionInterpreter.getDefault().save(); 
	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		this.cleanUp();
	}

}
