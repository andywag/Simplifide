/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core;

import java.util.HashMap;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.navigator.NavigatorContentProvider;
import com.simplifide.core.project.EclipseSuite;

public final class ActiveSuiteHolder {

    private EclipseSuite suite;
    private static ActiveSuiteHolder instance;
    private NavigatorContentProvider treeContent;
    
    private HashMap<String,EclipseSuite> map = new HashMap();
    
    private ActiveSuiteHolder() {}
    
    public static ActiveSuiteHolder getDefault() {
        if (instance == null) instance = new ActiveSuiteHolder();
        return instance;
    }
    
    public void addSuite(String name, EclipseSuite suite) {
    	this.map.put(name, suite);
    }
    
    public void removeSuite(String name) {
    	this.map.remove(name);
    }
    
    public EclipseSuite getMapSuite(String name) {
    	return this.map.get(name);
    }
    
    public void clean() {
    	try {
			this.suite.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
    }
    
    public void setSuite(EclipseSuite suite) {
    	try {
    		if (this.suite != null) {
    			if (this.suite.getProject().isAccessible()) {
    				this.suite.getProject().setPersistentProperty(BuildHandler.MAIN_PROJECT,null);
    			}
    		}
    		this.suite = suite;
    		if (this.suite != null) {
    			this.suite.getProject().setPersistentProperty(BuildHandler.MAIN_PROJECT, BuildHandler.MAIN_PROJECT_TRUE);
    		}
    		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
    }

    public EclipseSuite getSuite() {
        return suite;
    }

	public void setTreeContent(NavigatorContentProvider treeContent) {
		this.treeContent = treeContent;
	}

	public NavigatorContentProvider getTreeContent() {
		return treeContent;
	}
    
    
}
