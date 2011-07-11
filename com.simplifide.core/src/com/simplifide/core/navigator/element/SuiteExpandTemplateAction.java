package com.simplifide.core.navigator.element;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.builder.HardwareBuilder;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.python.SaveActionInterpreter;
import com.simplifide.core.pythonext.console.TemplateConsole;
import com.simplifide.core.source.design.DesignFile;

/**
 * Action to do a suite wide expanding of templates
 * Steps
 * 1. Disable the Hardware Build so No Compile Operations Take Place
 * 2. Save all the files
 * 3. Close all the files
 * 4. Run the expand template command from script/interface.py
 * 5. Clean and Build 
 */
public class SuiteExpandTemplateAction {

	private EclipseSuite suite;
	
	public SuiteExpandTemplateAction(EclipseSuite suite) {
		this.suite = suite;
	}
	
	/** Save and Close the files */
	private void closeSaveFiles(IProgressMonitor monitor,ArrayList<ReferenceItem<? extends DesignFile>> dlist) {
		SubProgressMonitor sub = new SubProgressMonitor(monitor,dlist.size());
		sub.beginTask("Saving Files", IProgressMonitor.UNKNOWN);
		HardwareBuilder.disableBuild();
		for (ReferenceItem<? extends DesignFile> dfile : dlist) {	
			if (dfile != null && dfile.getObject() != null) {
				SourceEditor edit = dfile.getObject().getEditor();
				if (edit != null) edit.close(true);
			}
		}
		HardwareBuilder.enableBuild();
		sub.done();
	}
	
	/** Runs through all of the files in the project and expands the templates */
	private void expand(IProgressMonitor monitor,ArrayList<ReferenceItem<? extends DesignFile>> dlist) {
		SubProgressMonitor sub = new SubProgressMonitor(monitor,dlist.size());
		for (ReferenceItem<? extends DesignFile> dfile : dlist) {	
			TemplateConsole.getDefault().writeOutputMessage("Expanding Templates for " +  dfile.getname() + "\n");
			if (dfile.getObject() != null && dfile.getObject().getBuilder() != null) {
				dfile.getObject().getBuilder().expandTemplates();
			}
		}
		sub.done();
	}
	
	
	private void refreshWorkspace(IProject project,IProgressMonitor monitor) {
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private void clean(IProject project,IProgressMonitor monitor) {
		try {
			project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	
	}

	private void pythonRun(IProgressMonitor monitor) {
		
		HardwareBuilder.disableBuild();
		SubProgressMonitor sube = new SubProgressMonitor(monitor,IProgressMonitor.UNKNOWN);
		monitor.beginTask("Running Expand Template Script",IProgressMonitor.UNKNOWN);
		SaveActionInterpreter.getDefault().expand();
		sube.done();
		//4. Refresh the workspace in case files which are created are used
		SubProgressMonitor sub1 = new SubProgressMonitor(monitor,IProgressMonitor.UNKNOWN);
		sub1.beginTask("Refreshing Workspace", IProgressMonitor.UNKNOWN);
		this.refreshWorkspace(this.suite.getProject(), sub1);
		sub1.done();
		HardwareBuilder.enableBuild();
		this.clean(this.suite.getProject(), sub1);
		
	

	}
	
	private void internalRun(IProgressMonitor monitor) {
		IProject project = suite.getProject();
		// 1. Disable the Build
		HardwareBuilder.disableBuild();
		// 2. Save and Close all Files
		ArrayList<ReferenceItem<? extends DesignFile>> dlist = suite.getCompileList().getGenericSelfList();
		this.closeSaveFiles(monitor, dlist);
		// 3. Run the expand template set from the script directory (Interface.py)
		SubProgressMonitor sube = new SubProgressMonitor(monitor,IProgressMonitor.UNKNOWN);
		monitor.beginTask("Running Expand Template Script",IProgressMonitor.UNKNOWN);
		SaveActionInterpreter.getDefault().expand();
		sube.done();
		//4. Refresh the workspace in case files which are created are used
		SubProgressMonitor sub1 = new SubProgressMonitor(monitor,IProgressMonitor.UNKNOWN);
		sub1.beginTask("Refreshing Workspace", IProgressMonitor.UNKNOWN);
		this.refreshWorkspace(project, sub1);
		sub1.done();
		// 5. Clean and Build the Project	
	    SubProgressMonitor sub = new SubProgressMonitor(monitor,IProgressMonitor.UNKNOWN);
		HardwareBuilder.enableBuild();
		this.clean(project, sub);
		HardwareBuilder.disableBuild();
		// 5. Expand Templates for the Project
		HardwareBuilder.disableBuild();
		this.expand(monitor, dlist);
		HardwareBuilder.enableBuild();
		// 6. Rebuild the project
		HardwareBuilder.enableBuild();
		this.clean(project, sub);
		HardwareBuilder.disableBuild();

	}

	
	public IStatus expand() {
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException{
					internalRun(monitor);
				};
			}, null);
			// TODO Auto-generated method stub
			return Status.OK_STATUS;
		}
		catch (CoreException e) {
			HardwareLog.logError(e);
			return Status.OK_STATUS;
		}
		
	}
	
	public IStatus expandPython() {
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException{
					pythonRun(monitor);
				};
			}, null);
			// TODO Auto-generated method stub
			return Status.OK_STATUS;
		}
		catch (CoreException e) {
			HardwareLog.logError(e);
			return Status.OK_STATUS;
		}
		
	}
	

}
