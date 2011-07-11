package com.simplifide.core.project.contents;

import java.util.ArrayList;

import com.simplifide.core.project.generator.ProjectGenerator;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;

public class ProjectContents extends ProjectTopContents{

	public ProjectContents(String name) {super(name);}

	@Override
	protected StructureDirectory createDefaultStructureDirectory(
			WorkspaceDirectoryStructure wstr,String name) {
		StructureDirectory dir = ProjectGenerator.getDefault().createStructureDirectory(wstr,this).copyShallow();
		dir.setName(name);
		
		return dir;
	}

	protected StructureDirectory getMainFolder(WorkspaceDirectoryStructure wstr) {
		ArrayList<StructureDirectory> con = wstr.getSuiteStructure().getProjects();
		if (con.size() == 0) return null;
		return con.get(0);
	}
	
}
