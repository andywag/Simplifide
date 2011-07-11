package com.simplifide.core.ui.wizard.suite_import.project;

import java.util.ArrayList;

import com.simplifide.core.project.generator.ProjectGeneratorOptions;

public class ProjectListCompositeData {

	private ArrayList<ProjectGeneratorOptions> projects  = new ArrayList<ProjectGeneratorOptions>();
	private ArrayList<ProjectGeneratorOptions> libraries = new ArrayList<ProjectGeneratorOptions>();
	
	public void remove(String name) {
		for (ProjectGeneratorOptions project : projects) {
			if (project.getName().equalsIgnoreCase(name)) {
				projects.remove(project); return;
			}
		}
		for (ProjectGeneratorOptions library : libraries) {
			if (library.getName().equalsIgnoreCase(name)) {
				libraries.remove(library); return;
			}
		}
	}
	
	public void get(String name) {
		ProjectGeneratorOptions opt = getProject(name);
		if (opt == null) opt = getLibrary(name);
	}
	
	public void addProject(ProjectGeneratorOptions project) {
		this.getProjects().add(project);
	}
	public void removeProject(ProjectGeneratorOptions project) {
		this.getProjects().remove(project);
	}
	public ProjectGeneratorOptions getProject(String project) {
		for (ProjectGeneratorOptions opt : getProjects()) {
			if (opt.getName().equalsIgnoreCase(project)) return opt;
		}
		return null;
	}
	
	public void addLibrary(ProjectGeneratorOptions project) {
		this.getLibraries().add(project);
	}
	public void removeLibrary(ProjectGeneratorOptions project) {
		this.getLibraries().remove(project);
	}
	public ProjectGeneratorOptions getLibrary(String project) {
		for (ProjectGeneratorOptions opt : getLibraries()) {
			if (opt.getName().equalsIgnoreCase(project)) return opt;
		}
		return null;
	}
	public void setProjects(ArrayList<ProjectGeneratorOptions> projects) {
		this.projects = projects;
	}
	public ArrayList<ProjectGeneratorOptions> getProjects() {
		return projects;
	}
	public void setLibraries(ArrayList<ProjectGeneratorOptions> libraries) {
		this.libraries = libraries;
	}
	public ArrayList<ProjectGeneratorOptions> getLibraries() {
		return libraries;
	}
	
}
