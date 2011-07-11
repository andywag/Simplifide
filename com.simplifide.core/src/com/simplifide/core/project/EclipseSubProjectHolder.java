package com.simplifide.core.project;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.HardwareLog;

public class EclipseSubProjectHolder extends ModuleObjectNew{

	private EclipseBaseProject project;
	private ArrayList<EclipseSubProject> projectList = new ArrayList<EclipseSubProject>();

	public EclipseSubProjectHolder(EclipseBaseProject project) {
		this.project = project;
	}
	
	/** Loads up the subprojects in this design on initialization 
	 * 
	 */
	public void loadFiles() {
		IFolder folder = this.project.getSubProjectFolder();
		if (folder != null && folder.exists()) {
			try {
				IResource[] projects = folder.members();
				for (IResource project : projects) {
					if (project instanceof IFolder) {
						IFolder projFolder = (IFolder) project;
						String fname = projFolder.getName();
						ReferenceItem eclipseRef = this.project.getSuiteReference();
						EclipseSubProject eproject = new EclipseSubProject(fname,projFolder,eclipseRef);
						eproject.setParent(this.project);
						eproject.loadFiles();
						this.projectList.add(eproject);
						this.project.getSuiteReference().getObject().getLibraryReference().addReferenceItem(eproject.createReferenceItem());
					}
				}
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
		}
	}
	
	public void setProjectList(ArrayList<EclipseSubProject> projectList) {
		this.projectList = projectList;
	}

	public ArrayList<EclipseSubProject> getProjectList() {
		return projectList;
	}
	
}
