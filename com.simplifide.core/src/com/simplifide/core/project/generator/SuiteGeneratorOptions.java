package com.simplifide.core.project.generator;

import java.util.ArrayList;

import com.simplifide.core.project.structure.LibraryStructureHolder;
import com.simplifide.core.project.structure.ProjectStructureHolder;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.structure.SubProjectStructureHolder;
import com.simplifide.core.project.structure.SuiteStructureHolder;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;

public class SuiteGeneratorOptions {

	/** @deprecated */
	private boolean modelSim;
	private boolean existingDir;
	private boolean mainProject;
		
	private StructureFile allFiles;
	private StructureFile structureXmlFile;
	private StructureFile contentXmlFile;
	
	private Libraries libraries = new Libraries();
	private String[]  libraryDirs;

	private ArrayList<ProjectGeneratorOptions> newProjects = new ArrayList<ProjectGeneratorOptions>();
	private ArrayList<ProjectGeneratorOptions> newLibraries= new ArrayList<ProjectGeneratorOptions>();

	private String projectFileLocation;
	
	private WorkspaceDirectoryStructure workspaceStructure;
	
	public SuiteGeneratorOptions() {}
	public SuiteGeneratorOptions(boolean modelSim,
			 boolean existingDir) {
		this.modelSim = modelSim;
		this.existingDir = existingDir;
	}
	
	public static SuiteGeneratorOptions getDefaultOptions(boolean model) {
		SuiteGeneratorOptions options = new SuiteGeneratorOptions(model,false);
		WorkspaceDirectoryStructure struct = new WorkspaceDirectoryStructure();
		struct.createWorkspaceStructure();
		options.setWorkspaceStructure(struct);
		return options;
	}
	
	public void setModelSim(boolean modelSim) {
		this.modelSim = modelSim;
	}

	public boolean isModelSim() {
		return modelSim;
	}


	

	public void setExistingDir(boolean existingDir) {
		this.existingDir = existingDir;
	}

	public boolean isExistingDir() {
		return existingDir;
	}
	
	public SuiteStructureHolder getDirStructure() {
		return this.workspaceStructure.getSuiteStructure();
	}

	public ProjectStructureHolder getProjectStructure() {
		return this.workspaceStructure.getProjectStructure();
	}
	
	public LibraryStructureHolder getLibraryStructure() {
		return this.workspaceStructure.getLibraryStructure();
	}
	
	public SubProjectStructureHolder getSubProjectStructure() {
		return this.workspaceStructure.getSubProjectStructure();
	}
	public void setWorkspaceStructure(WorkspaceDirectoryStructure workspaceStructure) {
		this.workspaceStructure = workspaceStructure;
	}
	public WorkspaceDirectoryStructure getWorkspaceStructure() {
		return workspaceStructure;
	}
	
	public void setMainProject(boolean mainProject) {
		this.mainProject = mainProject;
	}
	public boolean isMainProject() {
		return mainProject;
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
	
	public void setLibraries(Libraries libraries) {
		this.libraries = libraries;
	}
	public Libraries getLibraries() {
		return libraries;
	}

	public void setAllFiles(StructureFile allFiles) {
		this.allFiles = allFiles;
	}
	public StructureFile getAllFiles() {
		return allFiles;
	}

	public void setLibraryDirs(String[] libraryDirs) {
		this.libraryDirs = libraryDirs;
	}
	public String[] getLibraryDirs() {
		return libraryDirs;
	}

	public void setNewProjects(ArrayList<ProjectGeneratorOptions> newProjects) {
		this.newProjects = newProjects;
	}
	public ArrayList<ProjectGeneratorOptions> getNewProjects() {
		return newProjects;
	}

	public void setNewLibraries(ArrayList<ProjectGeneratorOptions> newLibraries) {
		this.newLibraries = newLibraries;
	}
	public ArrayList<ProjectGeneratorOptions> getNewLibraries() {
		return newLibraries;
	}

	public void setProjectFileLocation(String projectFileLocation) {
		this.projectFileLocation = projectFileLocation;
	}
	public String getProjectFileLocation() {
		return projectFileLocation;
	}

	public static class Libraries {
		
		
		
		public boolean ieee = false;
		public boolean ieee_proposed = false;
		public boolean std = false;
		public boolean unisimVHDL = false;
		
		public boolean ovm = false;
		public boolean uvm = false;
		public boolean vmm = false;
		public boolean unisimVerilog = false;
		
	}
	
	
}
