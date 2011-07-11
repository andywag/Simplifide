package com.simplifide.core.python.context;

import java.io.File;

import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.vhdl.describer.VhdlFile;

public class DesignContext implements ContextInterface.Source{

	private DesignFile design;
	
	public DesignContext(DesignFile design) {
		this.design = design;
	}

	public String getname() {
		return design.getname();
	}

	public String getPath() {
		File ufile = new File(design.getUri());
		String path = ufile.getAbsolutePath();
		return path;
	} 

	public boolean isVHDLFile() {
		if (design instanceof VhdlFile) return true;
		return false;
	}
	
	
}
