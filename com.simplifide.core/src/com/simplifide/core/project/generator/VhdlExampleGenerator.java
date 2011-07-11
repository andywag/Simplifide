/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.generator;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.project.contents.ProjectContents;
import com.simplifide.core.project.contents.SuiteContents;
import com.simplifide.core.project.structure.StructureDirectory;

public final class VhdlExampleGenerator {
	
	private static VhdlExampleGenerator instance;

	private VhdlExampleGenerator() {}
	
	public static VhdlExampleGenerator getDefault() {
		if (instance == null) instance = new VhdlExampleGenerator();
		return instance;
	}
		
	private void addContents(SuiteContents scontents,String baseName) {
		ProjectContents com = new ProjectContents(baseName);
		com.setLink(StructureDirectory.LINK_COPY_DISTRIBUTION, "resources/example/" + baseName );
		scontents.getProjects().add(com);
	}
	
	public void createProject(IProject projectHandle, boolean modelSim) {
		//SuiteGeneratorOptions options = SuiteGeneratorOptions.getDefaultOptions(true, modelSim);
		SuiteContents contents = new SuiteContents();
		contents.addScriptFiles();
		contents.attachVhdlLibs();
		this.addContents(contents,"common");
		this.addContents(contents, "receive");
		this.addContents(contents, "transmit");
		this.addContents(contents, "top");

		StructureDirectory dir = contents.createStructureDirectory();
		GeneralPurposeGenerator.getInstance().create(projectHandle, dir);
		
	}
}
