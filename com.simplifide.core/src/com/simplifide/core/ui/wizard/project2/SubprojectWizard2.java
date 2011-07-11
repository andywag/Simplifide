/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.project2;


import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.navigator.element.BasicProjectElement;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.generator.SubProjectGenerator;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureDirectory;




public class SubprojectWizard2 extends ProjectWizardTop2{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.project.ProjectWizard";

	@Override
	public IContainer getBaseProjectLocation(EclipseSuite suite) {
		return suite.getMainProjectFolder();
	}

	@Override
	public String getProjectName() {
		return "Subproject";
	}

	
	protected void createMainPage() {
		IStructuredSelection sel = (IStructuredSelection) this.getSelection();
		Object obj = sel.getFirstElement();
		if (obj instanceof IFolder) {
			this.setLocation((IFolder)obj);
		}
		else if (obj instanceof BasicProjectElement.Project) {
			BasicProjectElement.Project element = (BasicProjectElement.Project)obj;
			EclipseBaseProject project = (EclipseBaseProject) element.getItem().getObject();
			this.setLocation(project.getSubProjectFolder());
		}
		else {
			EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
			if (suite.getProjectList().size() < 0) {
				EclipseBaseProject project = suite.getProjectList().get(0);
				this.setLocation(project.getSubProjectFolder());
			}
		}
		super.createMainPage();
	}
	
	@Override
	public void createProjectWithLinkedFolder(IContainer folder, ProjectGeneratorOptions options) {
		SubProjectGenerator.getDefault().createProjectfromWizard(folder, options);
	}

	
	public StructureDirectory getDefaultStructure() {
		return RootStructureLoader.loadSubProjectStructure(null).getStructureDirectory();
	}

	@Override
	public int getProjectType() {
		return ProjectPage2.TYPE_SUBPROJECT;
	}
	
	
	
	
	
	
	
    
}
