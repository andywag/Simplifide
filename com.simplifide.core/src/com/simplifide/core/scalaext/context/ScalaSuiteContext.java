package com.simplifide.core.scalaext.context;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.scala2.context.ProjectContext;
import com.simplifide.scala2.context.SuiteContext;

public class ScalaSuiteContext extends SuiteContext{

	private EclipseSuite suite;
	
	public ScalaSuiteContext(EclipseSuite suite) {
		super(suite.getname());
		this.suite = suite;
	}

	@Override
	public List<ProjectContext> projectContext() {
		ArrayList<ProjectContext> projects = new ArrayList<ProjectContext>();
		for (CoreProjectBasic project : suite.getAllRealProject()) {
			projects.add(new ScalaProjectContext((EclipseBaseProject)project));
		}
		
		return projects;
	}

}
