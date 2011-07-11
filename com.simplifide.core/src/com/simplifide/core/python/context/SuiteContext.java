package com.simplifide.core.python.context;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.library.har.EclipseHarProject;

public class SuiteContext implements ContextInterface.Suite{

	private EclipseSuite suite;
	public SuiteContext(EclipseSuite suite) {
		this.suite = suite;
	}
	public String getname() {
		return suite.getname();
	}
	
	public String getBaseDirectory() {
		return suite.getBaseLocation();
	}
	
	public String getBuildDirectory() {
		IFolder folder = suite.getBuildFolder();
		if (folder == null) {
			folder = suite.getProject().getFolder("build");
		}
		String path = folder.getLocation().toOSString();
		return path;
	}
	
	
	public List getAllProjects() {
		ArrayList<ProjectContext> projects = new ArrayList<ProjectContext>();
		List<CoreProjectBasic> baseList = suite.getAllProjects();
		for (CoreProjectBasic base : baseList) {
			if (!(base instanceof EclipseHarProject)) {
				projects.add(new ProjectContext((EclipseBaseProject)base));

			}
		}
		
		return projects;
	}
	
	public String getMainModule() {
		return suite.getMainModuleName();
	}
	
	public String getMainProject() {
		return suite.getMainProjectName();
	}
	
}
