package com.simplifide.core.editors.actions.refactor;

import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.rename.RenameRefactorProcessor;
import com.simplifide.core.refactor.rename.RenameRefactoring;
import com.simplifide.core.refactor.rename.RenameWizard;

public class RenameActionDelegate extends RefactorActionDelegate{

	public RenameActionDelegate() {}
	public RenameActionDelegate(SourceEditor editor) {super(editor);}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		if (!HardwareChecker.isRefactoringEnabled()) return false;
		
		EditorFindItem item = this.getFindItem();
		if (item == null) return false;
		return true;
	}

	@Override
	public void run(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;
		EditorFindItem item = this.getFindItem();
		RenameRefactorProcessor processor = new RenameRefactorProcessor( item ,editor );
        RenameRefactoring ref = new RenameRefactoring( processor ); 
        RenameWizard wizard = new RenameWizard( ref );
        RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard ); 
        try { 
            op.run(editor.getEditorSite().getShell(), "" ); 
        } catch( InterruptedException irex ) { 
            
        }
		
	}
	
	public static class Handler extends GeneralHandler {
		public Handler() {super();}
		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new RenameActionDelegate((SourceEditor)editor);
		}
		
	}

}
