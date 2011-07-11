/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.generator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.test.EclipseTest;

public class TestGenerator {

	private static TestGenerator instance;
	
	public TestGenerator() {}
	
	public static TestGenerator getDefault() {
		if (instance == null) instance = new TestGenerator();
		return instance;
	}
	
	public void createTest(IFolder testFolder, String name) {
		IFolder baseFolder = testFolder.getFolder(name);
		IFolder dataFolder = baseFolder.getFolder(EclipseTest.DATA_FOLDER);
		IFolder srcFolder  = baseFolder.getFolder(EclipseTest.SRC_FOLDER);
		
		try {
			baseFolder.create(true, true, null);
			dataFolder.create(true, true, null);
			srcFolder.create(true, true, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		
	}
	
}
