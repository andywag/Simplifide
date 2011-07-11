/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class HardwareBundle extends ResourceBundle{

	private static HardwareBundle instance;
	private HashMap<String,String> map;
	
	private HardwareBundle() {
		init();
		
	}
	
	
	
	private void init() {
		map = new HashMap<String,String>();
		map.put("ContentAssistProposal.label", "Open Completion");
		map.put("ContentAssistProposal.tooltip", "Open Completion");
	}
	
	public static HardwareBundle getDefault() {
		if (instance == null) {
			instance = new HardwareBundle();
		}
		return instance;
	}
	
	@Override
	public Enumeration<String> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleGetObject(String arg0) {
		// TODO Auto-generated method stub
		return map.get(arg0);
	}

}
