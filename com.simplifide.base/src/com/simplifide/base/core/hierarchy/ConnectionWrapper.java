/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.hierarchy;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.module.InstanceModule;

public class ConnectionWrapper extends ModuleObjectWithList{

	
	private InstanceModule module;
	
	public ConnectionWrapper() {}
	
	public ConnectionWrapper(InstanceModule module) {
		this.setModule(module);
		if (module != null) {
			this.setname(module.getname());
		}
	}
	
	public void setModule(InstanceModule module) {
		this.module = module;
	}

	public InstanceModule getModule() {
		return module;
	}

	public static interface Con {
		public ConnectionWrapper createWrapper();
	}
	
}
