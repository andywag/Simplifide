package com.simplifide.core.project.generator;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.project.contents.ProjectContents;
import com.simplifide.core.project.contents.SuiteContents;
import com.simplifide.core.project.structure.StructureDirectory;

public class SvProjectGenerator {
	
	private static SvProjectGenerator instance;
	
	private SvProjectGenerator() {}
	
	public static SvProjectGenerator getDefault() {
		if (instance == null) instance = new SvProjectGenerator();
		return instance;
	}
	
	private void addContents(SuiteContents scontents,String projName, String loc) {
		ProjectContents com = new ProjectContents(projName);
		com.setLink(StructureDirectory.LINK_COPY_DISTRIBUTION, loc );
		scontents.getProjects().add(com);
	}
	
	public void createProject(IProject projectHandle, String location, String exname) {
		//SuiteGeneratorOptions options = SuiteGeneratorOptions.getDefaultOptions(true, modelSim);
		SuiteContents contents = new SuiteContents();
		contents.addScriptFiles();
		contents.attachLibrary(location);
		String loc = "resources/" + location + "_example/" + exname;
		this.addContents(contents,exname,loc);
		

		StructureDirectory dir = contents.createStructureDirectory();
		GeneralPurposeGenerator.getInstance().create(projectHandle, dir);
		
	}
}
