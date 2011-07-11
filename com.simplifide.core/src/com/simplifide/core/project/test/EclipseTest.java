/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.test;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.source.EclipseSourceFolder;

public class EclipseTest extends ModuleObjectNew{

	public static String DATA_FOLDER = "data";
	public static String SRC_FOLDER = "design";

	private EclipseSuite suite;
	private EclipseTestProject testproject;
	
	private EclipseSourceFolder designFolder;
	
	public EclipseTest(EclipseSuite suite) {
		super("test");
		this.suite = suite;
		init();
	}

	private void init() {
		if (this.getSrcFolder().exists()) {
			this.designFolder = new EclipseSourceFolder(this.getSrcFolder(),suite.getReference());
		}
	}
	
	private IFolder getSrcFolder() {
		return this.suite.getTestFolder().getFolder(SRC_FOLDER);
	}
	
	public void loadFiles() {
		if (this.designFolder != null) {
			this.designFolder.resolveDesignItems(null, this.getDesignFolder());
		}
	}
	
	public void build(int kind, IProgressMonitor monitor)  {
		if (this.designFolder != null) {
			this.designFolder.build(kind, monitor);
		}
	}
	
	public void setDesignFolder(EclipseSourceFolder designFolder) {
		this.designFolder = designFolder;
	}

	public EclipseSourceFolder getDesignFolder() {
		return designFolder;
	}
	
}
