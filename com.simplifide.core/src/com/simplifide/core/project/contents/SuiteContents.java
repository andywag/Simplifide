package com.simplifide.core.project.contents;

import java.util.ArrayList;

import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;

public class SuiteContents extends TopContents{

	private ArrayList<ProjectContents> projects = new ArrayList<ProjectContents>();
	private ArrayList<LibraryContents> libraries = new ArrayList<LibraryContents>();
	
	private StructureDirectory scriptHolder = new StructureDirectory("script");
	
	private StructureFile structureXmlFile;
	private StructureFile contentXmlFile;
	private StructureFile allFile;

	
	public SuiteContents() {
		super("suite");
	}
	
	public void addScriptFiles() {
		StructureFile sfile = new StructureFile("Start.py");
    	StructureFile pfile = new StructureFile("Paths.py");
    	
    	sfile.setLinkType(StructureDirectory.LINK_COPY_DISTRIBUTION);
    	pfile.setLinkType(StructureDirectory.LINK_COPY_DISTRIBUTION);

    	sfile.setLocation("resources/script/Start.py");
    	pfile.setLocation("resources/script/Paths.py");
    	
        this.getScriptHolder().addChild(sfile);
        this.getScriptHolder().addChild(pfile);
	}
	

	public void attachLibrary(String name) {
		LibraryContents lib = new LibraryContents(name + ".har");
		lib.setLink(StructureFile.LINK_COPY_DISTRIBUTION, "resources/libraries/" + name + ".har");
		lib.setFileLink(true);
        getLibraries().add(lib);
	}
	
	public void attachVhdlLibs() {
		this.attachLibrary("ieee");
		this.attachLibrary("std");
	}
	
	
	private void createLinks(WorkspaceDirectoryStructure wstr) {
		if (structureXmlFile != null) wstr.getSuiteStructure().getStructureDirectory().addChild(structureXmlFile);
		if (contentXmlFile != null)   wstr.getSuiteStructure().getStructureDirectory().addChild(contentXmlFile);

		//StructureFile ufile = new StructureFile("ProjectStructure.xsd");
		//ufile.setLocation("schema/projectStructure.xsd");
		//ufile.setLinkType(StructureBase.LINK_DISTRIBUTION);
		//wstr.getSuiteStructure().getStructureDirectory().addChild(ufile);
	}
	
	private void createProjects(WorkspaceDirectoryStructure wstr) {
		for (ProjectContents project : this.getProjects()) {
			project.createStructureDirectory(wstr);
		}
	}
	private void createLibraries(WorkspaceDirectoryStructure wstr) {
		for (LibraryContents project : this.getLibraries()) {
			project.createStructureDirectory(wstr);
		}
	}
	private void createScripts(WorkspaceDirectoryStructure wstr) {
		StructureDirectory struct = wstr.getSuiteStructure().getScriptStructure();
		if (struct == null) return;
		for (StructureBase base : this.getScriptHolder().getChildren()) {
			struct.addChild(base);
		}
	}
	
	private void createProjectContents() {
		StructureFile allfile = this.getAllFile();
		if (allfile != null) {
			ProjectContents contents = new ProjectContents("work");
			contents.setAllFiles(allFile);
			this.getProjects().add(contents);
		}
	}
	
	public StructureDirectory createStructureDirectory() {
		
		WorkspaceDirectoryStructure wstr = RootStructureLoader.loadWorkspaceDirectory(structureXmlFile);
		
		this.createProjectContents();
		
		this.createLinks(wstr);
		this.createProjects(wstr);
		this.createLibraries(wstr);
		this.createScripts(wstr);
		
		return wstr.getSuiteStructure().getStructureDirectory();
	}
	
	public void setProjects(ArrayList<ProjectContents> projects) {
		this.projects = projects;
	}



	public ArrayList<ProjectContents> getProjects() {
		return projects;
	}



	public void setLibraries(ArrayList<LibraryContents> libraries) {
		this.libraries = libraries;
	}



	public ArrayList<LibraryContents> getLibraries() {
		return libraries;
	}







	public void setScriptHolder(StructureDirectory scriptHolder) {
		this.scriptHolder = scriptHolder;
	}



	public StructureDirectory getScriptHolder() {
		return scriptHolder;
	}



	public void setStructureXmlFile(StructureFile structureXmlFile) {
		this.structureXmlFile = structureXmlFile;
	}



	public StructureFile getStructureXmlFile() {
		return structureXmlFile;
	}



	public void setContentXmlFile(StructureFile contentXmlFile) {
		this.contentXmlFile = contentXmlFile;
	}



	public StructureFile getContentXmlFile() {
		return contentXmlFile;
	}

	public void setAllFile(StructureFile allFile) {
		this.allFile = allFile;
	}

	public StructureFile getAllFile() {
		return allFile;
	}


	


	
	
	
	
}
