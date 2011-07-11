package com.simplifide.core.editors.actions.copy;

import com.simplifide.base.core.module.InstanceModule;

public class InstanceModuleHolder {

	private static InstanceModuleHolder instance;
	
	private InstanceModule instanceModule;
	
	private InstanceModuleHolder() {}
	
	public static InstanceModuleHolder getInstance() {
		if (instance == null) instance = new InstanceModuleHolder();
		return instance;
	}

	public void setInstanceModule(InstanceModule instanceModule) {
		this.instanceModule = instanceModule;
	}

	public InstanceModule getInstanceModule() {
		return instanceModule;
	}
	
}
