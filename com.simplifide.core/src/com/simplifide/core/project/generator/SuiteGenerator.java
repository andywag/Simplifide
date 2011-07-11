package com.simplifide.core.project.generator;

import java.lang.reflect.InvocationTargetException;

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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.progress.IProgressService;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.HardwareNature;
import com.simplifide.core.ui.dialog.OpenHardwareDialog;
import com.simplifide.core.ui.perspective.HardwarePerspectiveFactory;

public final class SuiteGenerator {

	private static SuiteGenerator instance;
	
	private SuiteGenerator() {}
	
	public static SuiteGenerator getInstance() {
		if (instance == null) instance = new SuiteGenerator();
		return instance;
	}
	
	
	
	/** Adds the hardware nature to this project */
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
	
	private void createContents(IProjectDescription description, IProject projectHandle,
    		SuiteGeneratorOptions options, IProgressMonitor monitor) {
		
    	this.addNature(projectHandle);
    	
    	SuiteStructureGenerator.getDefault().createProject(description,projectHandle,options);
    	
        EclipseSuite newSuite = BuildHandler.loadProject(projectHandle);
        BuildHandler.changeMainProject(newSuite);
        
    	
    }
	
	private void createProject(IProjectDescription description, IProject projectHandle,
            IProgressMonitor monitor,SuiteGeneratorOptions options) throws CoreException,
            OperationCanceledException {
        try {
        	monitor.beginTask("Creating Project", 3);//$NON-NLS-1$
        	monitor.subTask("Creating Project");
            projectHandle.create(description, monitor);
            monitor.worked(1);
            monitor.subTask("Opening Project");
            projectHandle.open(IResource.BACKGROUND_REFRESH, monitor);
            monitor.worked(1);
            monitor.subTask("Creating Contents");
            this.createContents(description,projectHandle,options, monitor);
           
        }
        catch (CoreException e) {
			HardwareLog.logError(e);
		}
        finally {
            monitor.done();
        }
    }
	/** Creates a new Hardware Suite */
	public void createSuite(final IProject project, 
			final IProjectDescription desc, 
			final SuiteGeneratorOptions options) {
		// create the new project operation
         WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
            protected void execute(IProgressMonitor monitor)
                    throws CoreException {
                createProject(desc, project, monitor, options);
            }
        };
        
        IProgressService service = PlatformUI.getWorkbench().getProgressService();
        try {
 
			service.run(false, true, op);
		} catch (InvocationTargetException e) {
			HardwareLog.logError(e);
		} catch (InterruptedException e) {
			HardwareLog.logError(e);
		}
		// Open the dialog when the project is finished
		final IWorkbench workbench = PlatformUI.getWorkbench();
        if (!workbench.getActiveWorkbenchWindow().getActivePage().getPerspective().getId().equalsIgnoreCase(HardwarePerspectiveFactory.ID)) {
        	workbench.getDisplay().syncExec(new Runnable() {
            	public void run() {
            		OpenHardwareDialog dialog = new OpenHardwareDialog(workbench);
                    dialog.open();
            		
            	}
            });
        }
	}
	
}
