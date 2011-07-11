/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.builder.HardwareBuilder;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.hier.HierarchyManager;

public class CoreWorkspaceListener implements IResourceChangeListener, IResourceDeltaVisitor{

	
	
    public CoreWorkspaceListener() {
  
    }

    public void resourceChanged(IResourceChangeEvent event) {
    	if (!HardwareBuilder.isBuildEnabled()) return;
    	try {
    		if (event != null && event.getDelta() != null) {
        		event.getDelta().accept(this);
    		}
			
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		/*
        if (event.getType() == IResourceChangeEvent.PRE_CLOSE ||
            event.getType() == IResourceChangeEvent.PRE_DELETE) {
            HierarchyManager.getInstance().clearList();
        }*/
        
    }
   
    private void handleRemove(IProject project) {
    	
    	EclipseSuite suite = ActiveSuiteHolder.getDefault().getMapSuite(project.getName());
    	if (suite != null) suite.deleteObject();
    	EclipseSuite currentSuite = ActiveSuiteHolder.getDefault().getSuite();
    	if (currentSuite == suite) {
    		ActiveSuiteHolder.getDefault().setSuite(null);
    		BuildHandler.buildProjects();
    		HierarchyManager.getInstance().clearList();
    	}
    	ActiveSuiteHolder.getDefault().removeSuite(project.getName());
    	
    }
    
    private void handleAdd(IProject project) {
    	BuildHandler.loadProject(project);
    	EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
    	//ActiveSuiteHolder.getDefault().addSuite(project.getName(), suite);
    	if (suite == null) {
    		BuildHandler.buildProjects();
    	}
    }
    
    private boolean getBit(int value, int index) {
    	int temp = value >> (index);
    	if (temp % 2 == 1) return true;
    	return false;
    }
    
	public boolean visit(IResourceDelta delta) throws CoreException {
		int flags = delta.getFlags();
		int type  = delta.getKind();
		IResource resource = delta.getResource();
		String uval = "Resource Change" + resource.getName() + "--- " + type;
		if (resource instanceof IProject) {
			boolean acc = resource.isAccessible();
			boolean ch = this.getBit(flags, 14);
			if (acc && ch) {
				this.handleAdd((IProject)resource);
			}
			if (!acc && ch) {
				this.handleRemove((IProject)resource);
			}
			return false;
		}
		
		//HardwareLog.logInfo( uval);
		// TODO Auto-generated method stub
		return true;
	}
    
}
