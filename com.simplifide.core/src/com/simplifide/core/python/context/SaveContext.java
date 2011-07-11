package com.simplifide.core.python.context;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.source.design.DesignFile;

public class SaveContext implements SaveInterface{

	private DesignFile designFile;
	public SaveContext(DesignFile designFile) {
		this.designFile= designFile;
	}
	
	public SuiteContext getSuiteContext() {
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		return new SuiteContext(suite);
	} 

	
	public DesignContext getDesignContext() {
		return new DesignContext(this.designFile);
	}
	 
}
