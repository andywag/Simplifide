/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.rename;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;

import com.simplifide.base.core.var.realvars.DelegateVar;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.MainRefactorProcessor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.UsagesListUtility;

public class RenameRefactorProcessor extends MainRefactorProcessor{

    private static final String ID = CoreActivator.PLUGIN_ID + "editors.refactor.rename.RenameRefactorProcessor";
    
    private EditorFindItem findItem;
    
    private String newName;
    
    public RenameRefactorProcessor(EditorFindItem findItem , SourceEditor editor) {
    	super(editor);
    	
    	this.findItem = findItem;
        this.newName = findItem.getItem().getname();
        
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
    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
    	boolean global = false;
    	RefactorTreeHolder.Root treeUsage = UsagesListUtility.getTreeUsages(this.findItem,this.newName,global);
        return createRootChange(treeUsage);
    }
    
    public String getInitialName() {
    	return this.findItem.getItem().getname();
    }
     
    public void setNewName(String newName) {
        this.newName = newName;
    }
    public String getNewName() {
        return newName;
    }

}
