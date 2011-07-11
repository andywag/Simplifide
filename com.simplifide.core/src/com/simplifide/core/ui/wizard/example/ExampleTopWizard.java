/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.example;


import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.HardwareNature;
import com.simplifide.core.ui.dialog.OpenHardwareDialog;
import com.simplifide.core.ui.perspective.HardwarePerspectiveFactory;
import com.simplifide.core.ui.wizard.BaseNewWizard;




public abstract class ExampleTopWizard extends BaseNewWizard{

	//public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.SuiteExampleWizard";
	
	private WizardNewProjectCreationPage mainPage;
	
	public ExampleTopWizard() {super();
		init();
	}
	
	private void init() {
		this.mainPage = this.createMainPage2();
		this.addPage(this.mainPage);	
	}
	
	protected WizardNewProjectCreationPage createMainPage2() {
		return new WizardNewProjectCreationPage("New Example Hardware Project"); 
	}
	
	protected abstract void generateProject(IProject handle);
	
	
	public boolean needsProgressMonitor() {
        return true;
    }
	
	
	public boolean performFinish() {
		this.createNewProject();
		return true;
	}
	
	
	
	private IProject createNewProject() {
        
	
        // get a project handle
        final IProject newProjectHandle = getMainPage().getProjectHandle();

        // get a project descriptor
        URI location = null;
        if (!getMainPage().useDefaults()) {
			location = getMainPage().getLocationURI();
		}
        
        
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProjectDescription description = workspace
                .newProjectDescription(newProjectHandle.getName());
        description.setLocationURI(location);
        
        
        // create the new project operation
        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
            protected void execute(IProgressMonitor monitor)
                    throws CoreException {
                createProject(description, newProjectHandle, monitor);    
            }
        };
        final IWorkbench workbench = this.getWorkbench();
        if (!workbench.getActiveWorkbenchWindow().getActivePage().getPerspective().getId().equalsIgnoreCase(HardwarePerspectiveFactory.ID)) {
        	this.getWorkbench().getDisplay().asyncExec(new Runnable() {
            	public void run() {
            		OpenHardwareDialog dialog = new OpenHardwareDialog(workbench);
                    dialog.open();
            		
            	}
            });
        }
        
        try {
        	getContainer().run(true, true, op);
		} catch (InvocationTargetException e) {
                    HardwareLog.logError(e);
		} catch (InterruptedException e) {
                    HardwareLog.logError(e);
		}
        

        return newProjectHandle;
    }

	private void addNature(IProject project) {
		try {
			  IWorkspace workspace = ResourcesPlugin.getWorkspace();
		      IProjectDescription description = project.getDescription();
		      String[] natures = description.getNatureIds();
		      String[] newNatures = new String[natures.length + 1];
		      System.arraycopy(natures, 0, newNatures, 0, natures.length);
		      newNatures[natures.length] = HardwareNature.ID;
		      IStatus status = workspace.validateNatureSet(natures);

		      // check the status and decide what to do
		      if (status.getCode() == IStatus.OK) {
		      	description.setNatureIds(newNatures);
		      	project.setDescription(description, null);
		      } else {
		      	// raise a user error
			
		      }
		   } catch (CoreException e) {
		      HardwareLog.logError(e);
		   }

	}
	
	protected void afterCreation(IProject projectHandle) {
    	this.generateProject(projectHandle);	
    	this.addNature(projectHandle);
    	EclipseSuite newSuite = BuildHandler.loadProject(projectHandle);
	    BuildHandler.changeMainProject(newSuite);
    }
	
    void createProject(IProjectDescription description, IProject projectHandle,
            IProgressMonitor monitor) throws CoreException,
            OperationCanceledException {
        try {
            monitor.beginTask("", 3);//$NON-NLS-1$
            monitor.subTask("Creating Project");
            projectHandle.create(description, monitor);
            monitor.worked(1);
            monitor.subTask("Opening Project");
            projectHandle.open(IResource.BACKGROUND_REFRESH, monitor);
            monitor.worked(1);
            monitor.subTask("Creating Project");
            this.afterCreation(projectHandle);
            monitor.worked(1);
            /*monitor.subTask("Creating Context");
            projectHandle.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
            monitor.worked(1);
            monitor.subTask("Building Suite");
            projectHandle.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
            monitor.worked(1);*/
            
        } finally {
            monitor.done();
        }
    }

	public void setMainPage(WizardNewProjectCreationPage mainPage) {
		this.mainPage = mainPage;
	}

	public WizardNewProjectCreationPage getMainPage() {
		return mainPage;
	}

}
