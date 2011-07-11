package com.simplifide.core.make;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.source.design.DesignFile;

public class ProjectWrapper {

	private EclipseBaseProject project;
	
	public ProjectWrapper(EclipseBaseProject project) {
		this.project = project;
	}
	
	public String getName() {return project.getname();}
	
	public List<ProjectWrapper> getRequired() {
		ArrayList<ProjectWrapper> reqs = new ArrayList<ProjectWrapper>();
		List<CoreProjectBasic> projects = project.getRequiredProjects();
		for (CoreProjectBasic project : projects) {
			reqs.add(new ProjectWrapper((EclipseBaseProject)project));
		}
		return reqs;
	}
	
	public List<DesignWrapper> getFiles() {
		List<DesignFile> dfiles = project.getCompileArrayList();
		ArrayList<DesignWrapper> files = new ArrayList<DesignWrapper>();
		for (DesignFile dfile : dfiles) {
			files.add(DesignWrapper.createWrapper(dfile));
		}
		return files;
	}
	
	
	
	
	public String getSuiteLocation() {
		EclipseSuite suite = (EclipseSuite)  this.project.getSuiteReference().getObject();
		URI suiteL   = suite.getProject().getLocationURI();
		URI projectL = project.getBaseFolder().getLocationURI();
		URI dif = suiteL.relativize(projectL);
		String path = dif.getPath();
		File ufile = new File(path);
		StringBuilder builder = new StringBuilder();
		
		String sep = System.getProperty("file.separator");
		sep = "/";
		boolean first = true;
		while (ufile != null) {
			
			if (!first) builder.append(sep);
			else first = false;
			builder.append("..");
			ufile = ufile.getParentFile();
		}
		
		return builder.toString();
	}
	
	public URI getSuiteURI() {
		EclipseSuite suite = (EclipseSuite)  this.project.getSuiteReference().getObject();
		URI suiteL   = suite.getProject().getLocationURI();
		return suiteL;
	}
	
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
	
	public String getDesignLocation() {
		URI base =  this.getSuiteURI();
		URI build = this.project.getDesignIFolder().getLocationURI();
		URI dif = base.relativize(build);
		return dif.getPath();
	}
	
	
	
	public String osPath(String path) {
		String sep = System.getProperty("file.separator");
		if (sep.equals("\\")) return path.replace("/", "\\");
		return path;
	}
	
	public List<DesignUnitWrapper> getUnits() {
		ArrayList<DesignUnitWrapper> units = new ArrayList<DesignUnitWrapper>();
		for (DesignWrapper wrap : this.getFiles()) {
			units.addAll(wrap.getUnits());
		}
		return units;
		
	}
	
	
}
