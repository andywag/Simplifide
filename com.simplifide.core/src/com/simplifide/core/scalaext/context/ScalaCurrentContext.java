package com.simplifide.core.scalaext.context;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.scala2.context.CurrentContext;
import com.simplifide.scala2.context.DesignContext;
import com.simplifide.scala2.context.ModuleContext;
import com.simplifide.scala2.context.ProjectContext;
import com.simplifide.scala2.context.SuiteContext;

public class ScalaCurrentContext extends CurrentContext{

	private SuiteContext suite;
	private ProjectContext project;
	private DesignContext design;
	private ModuleContext moduleContext;
	
	public ScalaCurrentContext(EclipseSuite suite, 
							   EclipseBaseProject project, 
							   DesignFile design,
							   InstanceModule module) {
		this.suite   = new ScalaSuiteContext(suite);
		this.project = new ScalaProjectContext(project);
		this.design  = new ScalaDesignContext(design);
		if (module != null) this.moduleContext = new ScalaInstanceModuleContext(module);
	}
	
	@Override
	public DesignContext activeFile() {
		return this.design;
	}

	@Override
	public ProjectContext activeProject() {
		return this.project;
	}

	@Override
	public SuiteContext activeSuite() {
		return this.suite;
	}
	
	

	@Override
	public ModuleContext activeModule() {
		return this.moduleContext;
	}

}
