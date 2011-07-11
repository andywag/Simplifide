/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.generator;


import com.simplifide.core.project.contents.ProjectTopContents;
import com.simplifide.core.project.structure.ProjectStructureHolderTop;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;

public class ProjectGenerator extends ProjectTopGenerator{

	public static ProjectGenerator instance;

	public ProjectGenerator() {}

	public static ProjectGenerator getDefault() {
		if (instance == null) instance = new ProjectGenerator();
		return instance;
	}

	
	
	public StructureDirectory createStructureDirectory(
			WorkspaceDirectoryStructure wstr, ProjectTopContents contents) {
		return wstr.getProjectStructure().getStructureDirectory();
	}
	

	@Override
	public ProjectStructureHolderTop loadStructure() {
		return RootStructureLoader.loadProjectStructure(null);
	}

	
	
	
}
