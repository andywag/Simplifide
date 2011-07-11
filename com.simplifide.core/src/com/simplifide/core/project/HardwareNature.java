/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.builder.BuilderTop;
import com.simplifide.core.builder.HardwareBuilder;

public class HardwareNature implements IProjectNature{

	public static String ID = CoreActivator.PLUGIN_ID + ".project.HardwareNature";
	
	private IProject project;

	
	public HardwareNature() {}

	public void configure() throws CoreException {
		BuilderTop.addBuilderToProject(project, HardwareBuilder.BUILDER_ID);
		//BackgroundJob.runJob("Loading Project", new ProjectBuild.OpenBuild(project));
		
	}

	public void deconfigure() throws CoreException {
		BuilderTop.removeBuilderFromProject(project, HardwareBuilder.BUILDER_ID);
	}

	public IProject getProject() {
		return this.project;
	}

	public void setProject(IProject project) {
		this.project = project;
		
	}

}
