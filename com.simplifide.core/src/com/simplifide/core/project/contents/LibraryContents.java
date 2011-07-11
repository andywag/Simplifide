package com.simplifide.core.project.contents;

import java.util.ArrayList;

import com.simplifide.core.project.generator.LibraryGenerator;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;

public class LibraryContents extends ProjectTopContents{

	public LibraryContents(String name) {super(name);}
	
	protected StructureDirectory createDefaultStructureDirectory(
			WorkspaceDirectoryStructure wstr,String name) {
		StructureDirectory dir = LibraryGenerator.getDefault().createStructureDirectory(wstr,this).copyShallow();
		
		dir.setName(name);
		
		return dir;
	}

	@Override
	protected StructureDirectory getMainFolder(WorkspaceDirectoryStructure wstr) {
		ArrayList<StructureDirectory> con = wstr.getSuiteStructure().getLibraries();
		if (con.size() == 0) return null;
		return con.get(0);
	}

}
