/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.instance;

import com.simplifide.base.basic.util.StringOps;

public class ModInstanceConnectNotFound extends ModInstanceConnect{

	private String baseModuleName;
	
	public ModInstanceConnectNotFound(String instanceName, String moduleName) {
		super(instanceName,null);
		this.setBaseModuleName(moduleName);
	}
	
	 public String getDisplayName() {
		 return this.getname() + StringOps.addParens(this.getBaseModuleName());
	}

	public void setBaseModuleName(String baseModuleName) {
		this.baseModuleName = baseModuleName;
	}

	public String getBaseModuleName() {
		return baseModuleName;
	}
}
