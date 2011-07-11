/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;


import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.generator.SuiteGenerator;
import com.simplifide.core.project.generator.SuiteGeneratorOptions;
import com.simplifide.core.ui.wizard.BaseNewWizard;




public class SuiteWizard extends BaseNewWizard{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.SuiteWizard";
	
	private SuiteMainPage mainPage;
    private SuiteLibraryPage libraryPage;
	
	public SuiteWizard() {super();
		init(); 
	}
	
	private void init() {
		mainPage = new SuiteMainPage();
		libraryPage = new SuiteLibraryPage();
		
		this.addPage(mainPage);
		this.addPage(libraryPage);
	}
	
	
	
	public boolean needsProgressMonitor() {
        return true;
    }
	
	
	public boolean performFinish() {
		this.createNewProject();
		return true;
	}
	
	
	
	
	private IProject createNewProject() {
        // get a project handle
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
        
     
        // Create the Generator Options Class
        
        //boolean vhdlLibs = this.mainPage.isVhdlSelected();
        boolean existingDir = this.mainPage.existingDirectory();
        //boolean newStructureDir = this.mainPage.isNewStructure();
        SuiteGeneratorOptions.Libraries lib = this.libraryPage.getLibraries();
        
        final SuiteGeneratorOptions options = new SuiteGeneratorOptions(false,existingDir); 
        options.setAllFiles(mainPage.getSourceFile());
        options.setStructureXmlFile(mainPage.getStructureFile());
        options.setContentXmlFile(mainPage.getContentsFile());
        options.setLibraries(lib);
        
        SuiteGenerator.getInstance().createSuite(newProjectHandle, description, options);
        try {
			newProjectHandle.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
			if (options.getAllFiles() != null) {
				newProjectHandle.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
        return newProjectHandle;
    }

	

}
