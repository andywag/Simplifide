/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port.remove;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;

import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.port.PortRefactorProcessor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;

public class RemovePortRefactorProcessor extends PortRefactorProcessor{

    private static final String ID = CoreActivator.PLUGIN_ID + "editors.refactor.rename.RemovePortRefactorProcessor";
    
  
    public RemovePortRefactorProcessor(ReferenceItem<Entity> entityRef , SourceEditor editor) {
        super(entityRef,editor);
    	     
    }
    
    protected List<SourceMatch> createInstanceConnectionChange(ConnectorHolder holder) {
    	ModInstanceConnect connect = holder.getConnectRef().getObject();
    	ModInstanceConnectWrap wrap = (ModInstanceConnectWrap) new ModInstanceConnectWrap(connect, false);
    	wrap.removePorts(this.getInstanceWrapDelta());
    	return this.createConnectionChange(connect.getFullLocation(), wrap);
    }
    
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
   	
    	RefactorTreeHolder.Root root = new RefactorTreeHolder.Root();
    	root.addElements(this.createPortChange(pm));
    	root.addElements(this.createConnectionChanges(pm));
    	return this.createRootChange(root);
    }
   
    
  

    

    @Override
    public String getIdentifier() {
        return ID;
    }

    @Override
    public String getProcessorName() {
        return "Remove Port";
    }

    
    
  
	
	
}
