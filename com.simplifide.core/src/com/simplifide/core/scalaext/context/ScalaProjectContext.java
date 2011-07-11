package com.simplifide.core.scalaext.context;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.scala2.context.ModuleContext;
import com.simplifide.scala2.context.ProjectContext;

public class ScalaProjectContext extends ProjectContext{

	private EclipseBaseProject project;
	
	
	
	
	public ScalaProjectContext(EclipseBaseProject project) {
		super(project.getname());
		this.project = project;
	}

	public String getCLocation() {
		return project.getCLocation();
	}
	
	@Override
	public List<ModuleContext> internalModules() {
		ArrayList<ModuleContext> modules = new ArrayList<ModuleContext>();
		for (InstanceModule imod : project.getAllInstanceModules()) {
			modules.add((ModuleContext)new ScalaInstanceModuleContext(imod));
		}
		return modules;
	}



}
