/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.project2;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.navigator.element.ProjectHolderElement;
import com.simplifide.core.navigator.element.SuiteElement;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.generator.SuiteGeneratorOptions;
import com.simplifide.core.project.generator.SuiteStructureGenerator;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.ui.wizard.BaseNewWizard;




public abstract class ProjectWizardTop2 extends BaseNewWizard{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.ProjectWizard2";
	
	
	private ProjectPage2 projectPage2;
	private IContainer location;
	
	public ProjectWizardTop2() {super();
		init();
	}
	
	
	public abstract String getProjectName();
	public abstract IContainer getBaseProjectLocation(EclipseSuite suite);
	public abstract StructureDirectory getDefaultStructure();
	public abstract int getProjectType();
	
	
	private void init() {
	   
		
	   
                       
	}
	
	protected void createMainPage() {
		IStructuredSelection sel = (IStructuredSelection) this.getSelection();
		Object obj = sel.getFirstElement();
		if (obj instanceof IProject) {
			IProject proj = (IProject) obj;
			EclipseSuite suite = ActiveSuiteHolder.getDefault().getMapSuite(proj.getName());
			this.setLocation(this.getBaseProjectLocation(suite));
		}
		else if (obj instanceof SuiteElement) {
			SuiteElement el = (SuiteElement) obj;
			EclipseSuite suite = el.getItem().getObject();
			this.setLocation(this.getBaseProjectLocation(suite));
		}
		else if (obj instanceof ProjectHolderElement) {
			ProjectHolderElement el = (ProjectHolderElement) obj;
			EclipseSuite suite = el.getItem().getObject();
			this.setLocation(this.getBaseProjectLocation(suite));
		}

		//this.projectPage = new ProjectTopPage(this.getProjectName());
		//this.addPage(this.projectPage);
		
		this.projectPage2 = new ProjectPage2(this.getProjectName(),this.getDefaultStructure(),
				this.getProjectType());
		this.addPage(this.projectPage2);
	}
	
	
	public boolean needsProgressMonitor() {
        return true;
    }
	 
	
	public abstract void createProjectWithLinkedFolder(IContainer folder,ProjectGeneratorOptions options) ;
	
	private void updateContents(ProjectGeneratorOptions options) {
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		SuiteGeneratorOptions op = new SuiteGeneratorOptions();
		ArrayList<ProjectGeneratorOptions> projects = new ArrayList<ProjectGeneratorOptions>();
		for (EclipseBaseProject project : suite.getProjectList()) {
			ProjectGeneratorOptions op1 = new ProjectGeneratorOptions.Existing(project);
			projects.add(op1);
		}
		ArrayList<ProjectGeneratorOptions> libraries = new ArrayList<ProjectGeneratorOptions>();
		for (CoreProjectBasic project : suite.getLibraryList()) {
			ProjectGeneratorOptions op2 = new ProjectGeneratorOptions.Existing((EclipseBaseProject)project);
			libraries.add(op2);
		}
		if (options.isLibrary()) libraries.add(options);
		//else projects.add(options);
		op.setNewProjects(projects);
		op.setNewLibraries(libraries);
		StructureFile sfile = SuiteStructureGenerator.getDefault().createContentFile(op);
		IFile content = suite.getContentXmlFile();
		
		String con = sfile.getContents();
		ByteArrayInputStream bs = new ByteArrayInputStream(con.getBytes());
		try {
			if (content.exists()) {
				content.delete(true, null);
			}
			content.create(bs, true, null);
		} catch (CoreException e) {
				HardwareLog.logError(e);
		}		
	}
	
	public boolean performFinish() {
		
		IContainer folder = this.getLocation();
		if (folder == null) {
			EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
			if (suite == null) {
				this.projectPage2.setErrorMessage("Open Hardware Suite Required for This Opreation");
				return false;
			}
			folder =  this.getBaseProjectLocation(suite);
			if (folder == null) {
				this.projectPage2.setErrorMessage("Open Hardware Suite Required for This Opreation");
				return false;
			}
		}
		ProjectGeneratorOptions options = projectPage2.getProjectGeneratorOptions();
		this.createProjectWithLinkedFolder(folder, options);
		this.updateContents(options);
		try {
			folder.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return true;
	}


	protected void setLocation(IContainer location) {
		this.location = location;
	}


	protected IContainer getLocation() {
		return location;
	}
	
	
	
	
    
}
