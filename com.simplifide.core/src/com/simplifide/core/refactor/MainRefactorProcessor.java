package com.simplifide.core.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;
import com.simplifide.core.search.RefactorTreeHolder.File;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.vhdl.editor.VhdlEditor;

public abstract class MainRefactorProcessor extends RefactoringProcessor{

	private SourceEditor editor;
	public MainRefactorProcessor(SourceEditor editor) {
		this.setEditor(editor);
	}
	
	public boolean isVhdlFile() {
    	if (this.getEditor() instanceof VhdlEditor) return true;
    	return false;
    }
	
	/** Convenience Method fo creating a list with a single source match */
    protected List<SourceMatch> singleMatch(String temp, ReferenceLocation loc) {
    	ArrayList<SourceMatch> smatch = new ArrayList<SourceMatch>();
    	ReferenceUsage use = new ReferenceUsage(null,temp,loc);
		smatch.add(new SourceMatch(use));
    	return smatch;
    }
	
	 /* Creates a text file change with text change at location loc */
    protected Change getReplaceChange(ReferenceLocation loc, String change) {
    	//      create a change object for the file that contains the property the 
        // user has selected to rename
        DesignFile dfile = (DesignFile) LocationOperations.getDesignFile(loc);
        IFile file = dfile.getIFile();
        
        
        TextFileChange result = new TextFileChange( file.getName(), file );
        // a file change contains a tree of edits, first add the root of them
        MultiTextEdit fileChangeRootEdit = new MultiTextEdit();
        result.setEdit( fileChangeRootEdit );    
        
        // edit object for the text replacement in the file, this is the only child
        ReplaceEdit edit = new ReplaceEdit( loc.getDocPosition(), 
                                            loc.getLength(), 
                                            change );
        fileChangeRootEdit.addChild( edit );
        return result;
    }
	
	
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException,
			
			OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
        return status;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
        return status;
	}


	@Override
	public Object[] getElements() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isApplicable() throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	 private Change createFileChange(RefactorTreeHolder.File treeusage) {
	        DesignFile sfile = (DesignFile) treeusage.getSource();
	        IFile file = (IFile) sfile.getResource();
	        
	        if (sfile.isOpened()) {
	            sfile.getEditor().syncSave();
	        }
	        TextFileChange result = new TextFileChange(file.getName(),file);
	        
	        result.setSaveMode(TextFileChange.FORCE_SAVE);
	        MultiTextEdit fileChangeRootEdit = new MultiTextEdit();
	        result.setEdit( fileChangeRootEdit );
	        
	        for (int i=0;i<treeusage.getnumber();i++) {
	            RefactorTreeHolder.Element el = (RefactorTreeHolder.Element) treeusage.getObject(i);
	            ReferenceLocation loc = el.getUsage().getUsage().getLocation();
	            int rlen = loc.getLength();
	            if (rlen == 0) rlen = 1;
	            ReplaceEdit edit = new ReplaceEdit(loc.getDocPosition(),rlen,el.getUsage().getUsage().getText());
	            fileChangeRootEdit.addChild(edit);
	        }
	        
	        return result;
	    }
	    
	    private Change createProjectChange(RefactorTreeHolder.Project treeusage) {
	        CompositeChange change = new CompositeChange(treeusage.getDisplayName());
	        for (int i=0;i<treeusage.getnumber();i++) {
	            RefactorTreeHolder.File rfile = (File) treeusage.getObject(i);
	            change.add(this.createFileChange(rfile));
	        }
	        return change;
	    }
	    
	    protected Change createRootChange(RefactorTreeHolder.Root treeusage) {
	        CompositeChange change = new CompositeChange(this.getProcessorName());
	        for (int i=0;i<treeusage.getnumber();i++) {
	            RefactorTreeHolder.Project project = (RefactorTreeHolder.Project) treeusage.getObject(i);
	            change.add(this.createProjectChange(project));
	        }
	        return change;
	    }
	
	
	public void setEditor(SourceEditor editor) {
		this.editor = editor;
	}

	public SourceEditor getEditor() {
		return editor;
	}

}
