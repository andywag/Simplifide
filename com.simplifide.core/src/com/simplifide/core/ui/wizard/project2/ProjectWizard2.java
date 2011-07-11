/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.project2;


import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.ProjectGenerator;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureDirectory;




public class ProjectWizard2 extends ProjectWizardTop2{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.project.ProjectWizard";

	@Override
	public IContainer getBaseProjectLocation(EclipseSuite suite) {
		return suite.getMainProjectFolder();
	}

	@Override
	public String getProjectName() {
		return "Project";
	}

	@Override
	public void createProjectWithLinkedFolder(IContainer folder, ProjectGeneratorOptions options) {
		ProjectGenerator.getDefault().createProjectfromWizard(folder, options);
	}

	
	public StructureDirectory getDefaultStructure() {
		return RootStructureLoader.loadProjectStructure(null).getStructureDirectory();
	}

	@Override
	public int getProjectType() {
		return ProjectPage2.TYPE_PROJECT;
	}
	
	
	
	
	
	
	
    
}
