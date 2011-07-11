/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.pythonext.basic;

import java.util.List;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.source.design.DesignFile;

public final  class PythonSuite {

	private static PythonSuite instance;
	
	private PythonSuite() {}
	
	public static PythonSuite getDefault() {
		if (instance == null) instance = new PythonSuite();
		return instance;
	}
	
	public EclipseSuite getActiveSuite() {
		return ActiveSuiteHolder.getDefault().getSuite();
	}

	public List getCompileList() {
		return this.getActiveSuite().getCompileList().getRealSelfList();
	}
	
	public TopCompile.Suite projectList() {
		UniqueList<DesignFile> desList = this.getActiveSuite().getCompileList();
		TopCompile.Suite slist = new TopCompile.Suite();
		slist.clearLists();
		for (DesignFile dfile : desList.getRealSelfList()) {
			slist.addFile(dfile);
		}
		return slist;
	}
	
	
	
}
