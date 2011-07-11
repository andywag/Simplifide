package com.simplifide.core.navigator.action;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.ConfigurationDirectoryLoader2;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.make.ProjectMakeGenerator;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;

public class CreateMakeAction extends Action{

	protected File location;
	protected String name;
	
	public CreateMakeAction(String name, File location) {
		super(name);
		this.name = name;
		this.location = location;
		this.setEnabled(HardwareChecker.isBuildEnabled());
	}
	
	
	
	public static class Suite extends CreateMakeAction {
		private EclipseSuite suite;
		public Suite(File location, EclipseSuite suite) {
			super("Create/Update Makefile", location);
			this.suite = suite;
		}
		
		public void run() {
			ProjectMakeGenerator.createSuiteMakeFile(suite);
			
		}
		
	}
	
	public static class Project extends CreateMakeAction {
		private EclipseBaseProject project;
		public Project(File location, EclipseBaseProject project) {
			super("Create/Update Makefile", location);
			this.project = project;
		}
		
		public void run() {
			ProjectMakeGenerator.createMakefile(project);
		}
	}
	
	public static class EditProject extends CreateMakeAction {
		private EclipseBaseProject project;
		public EditProject(File location, EclipseBaseProject project) {
			super("Edit Makefile Template", location);
			this.project = project;
		}
		public void run() {
			File open = ConfigurationDirectoryLoader2.getOrCreateFromPath(ConfigurationDirectoryLoader2.MAKEFILE_LOCATION);
			try {
				
				IFileStore store = EFS.getStore(open.toURI());
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(store.getName());
				String id = null;
				if (desc != null) id = desc.getId();
				else id = org.eclipse.ui.editors.text.EditorsUI.DEFAULT_TEXT_EDITOR_ID;
				
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileStoreEditorInput(store), id);
				
			} catch (CoreException e) {
				HardwareLog.logError(e);
			} 
		}
	}
}
	
