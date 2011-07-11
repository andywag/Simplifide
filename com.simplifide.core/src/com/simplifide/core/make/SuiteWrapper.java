package com.simplifide.core.make;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;

public class SuiteWrapper {

	private EclipseSuite suite;
	public SuiteWrapper(EclipseSuite suite) {
		this.suite = suite;
	}
	
	
	public URI getSuiteURI() {
		URI suiteL   = suite.getProject().getLocationURI();
		return suiteL;
	}
	/*
	public String getOsBuild() {
		String ustr = this.getSuiteLocation();
		ustr += "/" + getBuildLocation() + "/";
		return osPath(ustr);
	}
	
	public String getOsBase() {
		String ustr = this.getSuiteLocation();
		ustr += "/" + getBaseLocation() + "/";
		return osPath(ustr);
	}
	
	public String getBaseLocation() {
		URI base =  this.getSuiteURI();
		URI build = this.project.getBaseFolder().getLocationURI();
		URI dif = base.relativize(build);
		return dif.getPath();
	}
	
	public String getBuildLocation() {
		URI base =  this.getSuiteURI();
		URI build = this.project.getBuildFolder().getLocationURI();
		URI dif = base.relativize(build);
		return dif.getPath();
	}
	*/
	public String osPath(String path) {
		String sep = System.getProperty("file.separator");
		if (sep.equals("\\")) return path.replace("/", "\\");
		return path;
	}
	
	public List<ProjectWrapper> getProjects() {
		ArrayList<ProjectWrapper> projects = new ArrayList<ProjectWrapper>();
		for (CoreProjectBasic project :  suite.getAllProjects()) {
			projects.add(new ProjectWrapper((EclipseBaseProject)project));
		}
		return projects;
	}
	
	
}
