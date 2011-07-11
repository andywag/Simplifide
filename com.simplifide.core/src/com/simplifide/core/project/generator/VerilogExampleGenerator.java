/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.generator;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.project.contents.ProjectContents;
import com.simplifide.core.project.contents.SuiteContents;
import com.simplifide.core.project.structure.StructureDirectory;

public final class VerilogExampleGenerator {
	
	private static VerilogExampleGenerator instance;

	private VerilogExampleGenerator() {}
	
	public static VerilogExampleGenerator getDefault() {
		if (instance == null) instance = new VerilogExampleGenerator();
		return instance;
	}
	
	
	private void addContents(SuiteContents scontents) {
		ProjectContents com = new ProjectContents("rtl");
		com.setLink(StructureDirectory.LINK_COPY_DISTRIBUTION, "resources/verilog_example/RTL" );
		scontents.getProjects().add(com);
	}
	
	public void createProject(IProject projectHandle, boolean modelSim) {
		SuiteContents contents = new SuiteContents();
		contents.addScriptFiles();
		this.addContents(contents);
		
		StructureDirectory dir = contents.createStructureDirectory();
		GeneralPurposeGenerator.getInstance().create(projectHandle, dir);
			
	}
}
