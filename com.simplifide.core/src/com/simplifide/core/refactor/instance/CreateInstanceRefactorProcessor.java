/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.instance;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.refactor.port.PortRefactorProcessor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;

public class CreateInstanceRefactorProcessor extends PortRefactorProcessor{

    private static final String ID = CoreActivator.PLUGIN_ID + "editors.refactor.create.instance";
    
    private ReferenceItem<ModInstanceConnect> connectRef;
    
    private InstanceModule enclosingModule;
    private InstanceModule instanceModule;
    private ModInstanceConnectWrap connectWrap;    
    private ReferenceLocation location;
    private boolean component;
    
    public CreateInstanceRefactorProcessor(ReferenceItem<ModInstanceConnect> connectRef ,
    		InstanceModule enclosingModule,
    		SourceEditor editor,
    		ReferenceLocation location) {
    	this(connectRef,enclosingModule,editor,location,null);
    }
    
    public CreateInstanceRefactorProcessor(ReferenceItem<ModInstanceConnect> connectRef ,
    		InstanceModule enclosingModule,
    		SourceEditor editor,
    		ReferenceLocation location,
    		InstanceModule instanceModule) {
    	
    	super(null,editor);
    	this.setConnectRef(connectRef);
    	this.enclosingModule = enclosingModule;
    	this.location = location;
    	this.instanceModule = instanceModule;
    	this.createModInstanceConnectWrap();
      
    }
    
    
    private void createModInstanceConnectWrap() {
    	if (this.connectRef != null) {
    		ModInstanceConnect connect = connectRef.getObject();
    		this.connectWrap = new ModInstanceConnectWrap(connect,this.isVhdlFile());
        	this.instanceModule = (InstanceModule) connect.getEntityRef().getObject().getInstanceModRef().getObject();
    	}
    	else if (instanceModule != null) {
    		ModInstanceConnect connect = this.instanceModule.createDefaultConnection();
        	this.connectWrap = new ModInstanceConnectWrap(connect,this.isVhdlFile());
    	}
    	else {
    		EclipseBaseProject proj = (EclipseBaseProject) this.enclosingModule.getProjectRef().getObject();
    		EclipseSuite suite = (EclipseSuite) proj.getSuiteReference().getObject();
    		List<InstanceModule> mods = suite.getAllInstanceModules();
    		if (mods.size() > 0) {
        		this.instanceModule = mods.get(0);
        		ModInstanceConnect connect = this.instanceModule.createDefaultConnection();
            	this.connectWrap = new ModInstanceConnectWrap(connect,this.isVhdlFile());
    		}
    	}
    }
    
    public void changeInstanceModule(InstanceModule instanceModule2) {
    	this.instanceModule = instanceModule2;
    	ModInstanceConnect connect = this.instanceModule.createDefaultConnection();
    	this.connectWrap = new ModInstanceConnectWrap(connect,this.isVhdlFile());
    }

    
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
       	RefactorTreeHolder.Root root = new RefactorTreeHolder.Root();
    	ReferenceLocation loc = this.location;
    	List<SourceMatch> matches =  this.createConnectionChange(loc, this.connectWrap);
       	root.addElements(matches);
        if (this.component) {
        	ReferenceLocation loc2 = this.getEnclosingModule().getArchitecture().getDeclarationStartLocation();
        	 matches = this.createComponentChange(loc2, this.connectWrap);
             root.addElements(matches);
        }
       	
    	return this.createRootChange(root);
    }
    
  


    @Override
    public String getIdentifier() {
        return ID;
    }

    @Override
    public String getProcessorName() {
        return "Create Instance";
    }

	public void setConnectRef(ReferenceItem<ModInstanceConnect> connectRef) {
		this.connectRef = connectRef;
	}

	public ReferenceItem<ModInstanceConnect> getConnectRef() {
		return connectRef;
	}

	public void setConnectWrap(ModInstanceConnectWrap connectWrap) {
		this.connectWrap = connectWrap;
	}

	public ModInstanceConnectWrap getConnectWrap() {
		return connectWrap;
	}

	public void setInstanceModule(InstanceModule instanceModule) {
		this.instanceModule = instanceModule;
	}

	public InstanceModule getInstanceModule() {
		return instanceModule;
	}

	public void setEnclosingModule(InstanceModule enclosingModule) {
		this.enclosingModule = enclosingModule;
	}

	public InstanceModule getEnclosingModule() {
		return enclosingModule;
	}


	public void setComponent(boolean component) {
		this.component = component;
	}


	public boolean isComponent() {
		return component;
	}

	

    
    
	
	
	
	
}
