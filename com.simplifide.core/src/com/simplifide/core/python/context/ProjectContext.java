package com.simplifide.core.python.context;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.source.design.DesignFile;


public class ProjectContext implements ContextInterface.Project{
	private EclipseBaseProject project;

	public ProjectContext(EclipseBaseProject project) {
		this.project = project;
	}

	public String getname() {
		return project.getname();
	} 
	
	public String getBaseDirectory() {
		IFolder folder = project.getBaseFolder();
		String path = folder.getLocation().toOSString();
		return path;
	}
	
	public String getBuildDirectory() {
		IFolder folder = project.getBuildFolder();
		String path = folder.getLocation().toOSString();
		return path;
	}

	
	public List<Source> getVerilogList() {
		List<Source> sources = new ArrayList<Source>();
		List<DesignFile> files = project.getVerilogList();
		for (DesignFile file : files) {
			sources.add(new DesignContext(file));
		}
		return sources;
	}

	
	public List<Source> getVhdlList() {
		List<Source> sources = new ArrayList<Source>();
		List<DesignFile> files = project.getVhdlList();
		for (DesignFile file : files) {
			sources.add(new DesignContext(file));
		}
		return sources;
	}

	
	public List<ContextInterface.Project> getDependentProjects() {
		List<CoreProjectBasic> projects = this.project.getProjectArrayList();
		ArrayList<ContextInterface.Project> nprojects = new ArrayList<ContextInterface.Project>();
		for (CoreProjectBasic project : projects) {
			EclipseBaseProject base = (EclipseBaseProject) project;
			ProjectContext nproject = new ProjectContext(base);
			nprojects.add(nproject);
		}
		return nprojects;
	}

	
	public String getMainModule() {
		return project.getMainModule();
	}
	

	
}