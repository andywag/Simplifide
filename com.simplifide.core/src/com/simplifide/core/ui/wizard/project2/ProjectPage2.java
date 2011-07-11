package com.simplifide.core.ui.wizard.project2;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.ui.wizard.suite_import.project.ProjectPropertyComposite;

public class ProjectPage2 extends WizardPage{

	public static final int TYPE_SUITE = 0;
	public static final int TYPE_PROJECT = 1;
	public static final int TYPE_LIBRARY = 2;
	public static final int TYPE_SUBPROJECT = 3;
	
	private ProjectPropertyComposite composite;
	private String type;
	private int projectType;
	
	private StructureDirectory defaultStructure;
	
	public ProjectPage2(String type, StructureDirectory defaultStructure, int projectType) {
        super("New " + type);
        this.defaultStructure = defaultStructure;
        this.type = type;
        this.projectType = projectType;
        this.setTitle("New " + type);
        this.setDescription("Select a name for the project, and then add existing design directories to " +
        		"be linked into the project");
        setPageComplete(true);
    }

	public ProjectGeneratorOptions getProjectGeneratorOptions() {
		return composite.getProjectGeneratorOptions();
	}
	

public void createControl(Composite parent) {
	this.composite = new ProjectPropertyComposite(parent,SWT.NONE, type,defaultStructure,projectType);
	this.setControl(composite);
}
	
}
