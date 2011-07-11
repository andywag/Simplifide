/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.doc;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;

public class EclipseSuiteDocHandler extends TopDocHandler{
    
    private EclipseSuite suite;
    
    public EclipseSuiteDocHandler(EclipseSuite suite) {
        this.suite = suite;
    }
    
  
    
    public void generateDoc() {
    	
    	
    		try {
    			if (!suite.getDocFolder().exists()) {
    				suite.getDocFolder().create(true, true, null);
    			}
				suite.getDocFolder().exists();
		    	IFile nfile = suite.getDocFolder().getFile("architecture.html");
		        this.createPage(nfile, "architecture/main_arch", suite);
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
    	
    	
    	
        /*
        IFile file = suite.getDocFolder().getFile("suite_index.html");
        this.createPage(file, "doc/suite_page", suite);
        for (EclipseBaseProject project : this.suite.getProjectList()) {
            project.getDocHolder().generateDoc();
        }
        */
    }

}
