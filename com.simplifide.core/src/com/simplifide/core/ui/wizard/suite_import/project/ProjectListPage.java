package com.simplifide.core.ui.wizard.suite_import.project;

import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.core.project.generator.ProjectGeneratorOptions;

public class ProjectListPage extends WizardPage{

	private ProjectListComposite libraryComposite;
	private String type;
	
	public ProjectListPage() {
		super("Project Contents");
		this.type = type;
		this.setTitle("Project Contents");
		this.setDescription("Define the projects and libraries for this project.");
	}

	public void createControl(Composite parent) {
		this.libraryComposite = new ProjectListComposite(parent, SWT.None); 
		this.setControl(this.libraryComposite);
	}
	
	public String[] getLibraries() { 
		return libraryComposite.getLibraries();
	}
	
	public ArrayList<ProjectGeneratorOptions> getProjectOptions() {
		return libraryComposite.getProjectOptions();
	}
	
	public ArrayList<ProjectGeneratorOptions> getLibraryOptions() {
		return libraryComposite.getLibraryOptions();
	}
}
