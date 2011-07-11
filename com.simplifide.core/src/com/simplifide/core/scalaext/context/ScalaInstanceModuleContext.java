package com.simplifide.core.scalaext.context;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.scala2.context.ComponentContext;
import com.simplifide.scala2.context.EntityContext;
import com.simplifide.scala2.context.InstanceContext;
import com.simplifide.scala2.context.ModuleContext;

public class ScalaInstanceModuleContext extends ModuleContext{

	private InstanceModule module;
	public ScalaInstanceModuleContext(InstanceModule module) {
		super(module.getname());
		this.setModule(module);
	}
	
	public EntityContext entity() {
		if (getModule().getEntity() != null) return new ScalaEntityContext(getModule().getEntity());
		return null;
	}
	
	public ComponentContext component() {
		if (getModule().getComponentReference() != null) return new ScalaComponentContext(getModule().getComponent());
		return null;
	}
	
	public List<InstanceContext> javaConnections() {
		ArrayList<InstanceContext> outputs = new ArrayList<InstanceContext>();
		List<ModInstanceConnect> connects = module.getConnections();
		for (ModInstanceConnect connect : connects) {
			outputs.add(new ScalaModInstanceConnectContext(connect));
		}
		return outputs;
	}

	public void setModule(InstanceModule module) {
		this.module = module;
	}

	public InstanceModule getModule() {
		return module;
	}


	
}
