/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_import;


import java.net.URI;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.IWizardPage;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.generator.SuiteGenerator;
import com.simplifide.core.project.generator.SuiteGeneratorOptions;
import com.simplifide.core.ui.wizard.BaseNewWizard;
import com.simplifide.core.ui.wizard.suite_classic.SuiteLibraryPage;
import com.simplifide.core.ui.wizard.suite_import.project.ProjectListPage;




public class SuiteImportWizard extends BaseNewWizard{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.import.existing";
	
	private SuiteImportMainPage mainPage;
	private SuiteStructureMainPage structPage;
	private ProjectListPage projectPage;
    private SuiteLibraryPage libraryPage; 
	
	public SuiteImportWizard() {super();
		init(); 
	}
	
	private void init() {
		mainPage = new SuiteImportMainPage();
		this.addPage(mainPage);
		
		if (HardwareChecker.isWizardEnabled()) {
			structPage = new SuiteStructureMainPage();
			projectPage = new ProjectListPage();
			libraryPage = new SuiteLibraryPage();
			
			this.addPage(structPage);
			this.addPage(projectPage);
			this.addPage(libraryPage);
		}
		
	}
	
	 public IWizardPage getNextPage(IWizardPage page) {
	       if (page instanceof SuiteImportMainPage) {
	    	   if (((SuiteImportMainPage) page).isDefaultStructure()) {
	    		  return projectPage; 
	    	   }
	       }
	       return super.getNextPage(page);
	    }
	
	
	public boolean needsProgressMonitor() {
        return true;
    }
	
	
	public boolean performFinish() {
		this.createNewProject();
		return true;
	}
	
	
	
	
	private IProject createNewProject() {
		 final IProject newProjectHandle = mainPage.getProjectHandle();
	        
	        // get a project descriptor
	        URI location = null;
	        if (!mainPage.useDefaults()) {
				location = mainPage.getLocationURI();
			}
	        
	        // Set the project location for non default workspace locations
	        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
	        final IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
	        description.setLocationURI(location);
	        
	     
	        SuiteGeneratorOptions.Libraries lib = this.libraryPage.getLibraries();
	        
	        final SuiteGeneratorOptions options = new SuiteGeneratorOptions(false,false); 
	        //options.setAllFiles(mainPage.getSourceFile());
	        if (!mainPage.isDefaultStructure()) options.setStructureXmlFile(structPage.getStructureFile());
	        //options.setContentXmlFile(mainPage.getContentsFile());
	        options.setLibraries(lib);
	        ArrayList<ProjectGeneratorOptions> proj = projectPage.getProjectOptions();
	        if (proj.size() == 0) {
	        	proj.add(new ProjectGeneratorOptions("work"));
	        }
	        
	        options.setNewProjects(projectPage.getProjectOptions());
	        options.setNewLibraries(projectPage.getLibraryOptions());
	        

	        SuiteGenerator.getInstance().createSuite(newProjectHandle, description, options);
	        
	        return newProjectHandle;
	    }
       
    }

 



