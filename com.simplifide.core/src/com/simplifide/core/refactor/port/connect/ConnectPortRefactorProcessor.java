/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port.connect;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

import com.simplifide.base.core.hierarchy.PathTotalElement;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.MainRefactorProcessor;
import com.simplifide.core.refactor.port.TopPortProcessor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;

public class ConnectPortRefactorProcessor extends MainRefactorProcessor{

    private static final String ID = CoreActivator.PLUGIN_ID + "editors.refactor.connect.ConnectPortRefactorProcessor";
    
    private ReferenceItem<Entity> entityRef;
    
    private ArrayList<PathTotalElement> sourceElement;
    private ArrayList<PathTotalElement> destinationElement;
    
    
    
    public ConnectPortRefactorProcessor(ReferenceItem<Entity> entityRef , SourceEditor editor) {
    	super(editor);
        this.setEntityRef(entityRef);
    }
    @Override
    public RefactoringStatus checkFinalConditions(IProgressMonitor pm, CheckConditionsContext context) throws CoreException, OperationCanceledException {
        RefactoringStatus status = new RefactoringStatus();
        return status;
    }

    @Override
    public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
        RefactoringStatus status = new RefactoringStatus();
        return status;
    }

    
    
    /** Returns the processor which will be used to create the refactoring for this design */
    private TopPortProcessor getPortProcessor(PathTotalElement el) {
    	return new TopPortProcessor();
    }
    
    /** Creates the changes for an individual path */
    private List<SourceMatch> createConnectionChange(ArrayList<PathTotalElement> pathList, boolean vhdl) {
    	TopPortProcessor proc = new TopPortProcessor();
    	return proc.createChanges(pathList, vhdl);
    	
    }
    
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
    	RefactorTreeHolder.Root root = new RefactorTreeHolder.Root();
    	
        boolean vhdl = this.isVhdlFile();

        for (PathTotalElement dest : this.getDestinationElement()) {
        	for (PathTotalElement path : this.getSourceElement()) {
                ArrayList<PathTotalElement> pathList = path.resolvePath(dest);
        		root.addElements(this.createConnectionChange(pathList, vhdl));
        	}
        }
    	return this.createRootChange(root);

    }
    
  

    @Override
    public Object[] getElements() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getIdentifier() {
        return ID;
    }

    @Override
    public String getProcessorName() {
        return "Rename Element";
    }

    @Override
    public boolean isApplicable() throws CoreException {
        return true;
    }

    @Override
    public RefactoringParticipant[] loadParticipants(RefactoringStatus status, SharableParticipants sharedParticipants) throws CoreException {
        // TODO Auto-generated method stub
        return null;
    }
    
   
	public void setEntityRef(ReferenceItem<Entity> entityRef) {
		this.entityRef = entityRef;
	}
	public ReferenceItem<Entity> getEntityRef() {
		return entityRef;
	}

	public void setDestinationElement(ArrayList<PathTotalElement> destinationElement) {
		this.destinationElement = destinationElement;
	}
	public ArrayList<PathTotalElement> getDestinationElement() {
		return destinationElement;
	}
	public void setSourceElement(ArrayList<PathTotalElement> sourceElement) {
		this.sourceElement = sourceElement;
	}
	public ArrayList<PathTotalElement> getSourceElement() {
		return sourceElement;
	}
	
	
	
}
