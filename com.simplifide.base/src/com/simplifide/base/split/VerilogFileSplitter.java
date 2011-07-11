/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.split;

import java.io.File;

public class VerilogFileSplitter extends SourceFileSplitter{

	
	

	
	public VerilogFileSplitter(File file) {
		super(file);
	}
	
	
	
	private void applyModuleName(String str) {
		
		for (int i=0;i<str.length();i++) {
			char uc = str.charAt(i);
			if (uc == ' ' || uc == '\t' || uc == '(') {
				this.setModuleName(str.substring(0,i));
				this.setModuleExtension("v");
				return;
			}
			
		}
	}
	
	protected boolean checkLine(String line) {
		
		if (line.contains("endmodule")) {
			return true;
		}
		int index = line.indexOf("module ") ;
		if (index >= 0) {
			String ustr = line.substring(index + 7).trim();
			this.applyModuleName(ustr);
		}
		return false;
		
		
	}
	
	
	public void removeSplit() {
		
	}
	
}
