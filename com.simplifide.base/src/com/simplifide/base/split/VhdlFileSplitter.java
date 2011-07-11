/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.split;

import java.io.File;

public class VhdlFileSplitter extends SourceFileSplitter{

	
	
	private String safeName;
	
	public VhdlFileSplitter(File file) {
		super(file);
	}
	
	
	
	private void applyModuleName(String str) {
				this.setModuleName(str);
				this.setModuleExtension("vhd");
				return;
	}
	
	
	
	protected boolean checkLine(String line) {
		try {
			int eind = line.indexOf("entity");
			if (eind >= 0) {
				int index = line.indexOf("is");
				if (index >= 0) {
					String ename = line.substring(eind + 7,index-1).trim();
					this.safeName = ename;
					this.applyModuleName(ename + "_ent");
				}
				
				return false;
			}
			int aind = line.indexOf("architecture");
			if (aind >= 0) {
				int sind = line.indexOf("of");
				int eeind = line.indexOf("is");
				if (sind > 0 && eeind > 0) {
					this.safeName = line.substring(aind + 13,sind-1).trim();
					String ename = line.substring(sind + 3,eeind-1).trim();
					this.applyModuleName(ename + "_arch");
				}
				
				return false;
			}
			eind = line.indexOf("end");
			if (eind >= 0) {
				int lind = line.indexOf(";");
				if (lind > 0) {
					String uname = line.substring(eind + 4,lind).trim();
					if (uname.equalsIgnoreCase(this.safeName)) return true;
				}
				
			}
			
			return false;
		}
		catch (StringIndexOutOfBoundsException e) {
			return false;
		}
		
		
		
	}
	
	
	public void removeSplit() {
		
	}
	
}
