package com.simplifide.core.editors.actions.refactor;

import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.function.create.CreateFunctionRefactorProcessor;
import com.simplifide.core.refactor.function.create.CreateFunctionRefactoring;
import com.simplifide.core.refactor.function.create.CreateFunctionWizard;

public abstract class FunctionActionDelegate extends RefactorActionDelegate{

	public FunctionActionDelegate() {}
	public FunctionActionDelegate(SourceEditor editor) {super(editor);}
	
	
	public static class CreateHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new CreateActionDelegate((SourceEditor)editor);
		}
		
	}
	
	public static class CreateActionDelegate extends FunctionActionDelegate {
	
		public CreateActionDelegate() {}
		public CreateActionDelegate(SourceEditor editor) {super(editor);}
		
		@Override
		public void run(GeneralEditor editor1) {
			SourceEditor editor = (SourceEditor) editor1;
			EditorFindItem item = this.getFindItem();
			
			ReferenceItem<InstanceFunction> funcR = this.getFindItemObject();
			CreateFunctionRefactorProcessor processor = new CreateFunctionRefactorProcessor(
					funcR, editor);
			CreateFunctionRefactoring ref = new CreateFunctionRefactoring(processor);
			
			CreateFunctionWizard wizard = new CreateFunctionWizard(ref);
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
					wizard);
			String titleForFailedChecks = ""; //$NON-NLS-1$ 
			try {
				op.run(editor.getEditorSite().getShell(), titleForFailedChecks);
			} catch (InterruptedException e) {
				HardwareLog.logError(e);
			}
		}

		@Override
		public boolean checkEnabled(GeneralEditor editor) {
			if (true) return false;
			if (!HardwareChecker.isRefactoringEnabled()) return false;
			ReferenceItem ref = this.getFindItemObject();
			if (ref != null && ref.getType() == ReferenceUtilities.REF_FUNCTION_INSTANCE) {
				InstanceFunction func = (InstanceFunction) ref.getObject();
				if (func.getHead() == null || func.getBody() == null) return true;
			}
			return false;
		}
		
	}
	
	public static class ChangeMethodHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new ChangeMethodDelegate((SourceEditor)editor);
		}
		
	}
	
	public static class ChangeMethodDelegate extends FunctionActionDelegate {
		
		public ChangeMethodDelegate() {}
		public ChangeMethodDelegate(SourceEditor editor) {super(editor);}
		
		@Override
		public void run(GeneralEditor editor) {
			// TODO Auto-generated method stub	
		}

		@Override
		public boolean checkEnabled(GeneralEditor editor) {
			if (true) return false;
			if (!HardwareChecker.isRefactoringEnabled()) return false;
			ReferenceItem ref = this.getFindItemObject();
			if (ref != null && ref.getType() == ReferenceUtilities.REF_FUNCTION_INSTANCE) {
				return true;
			}
			return false;
		}
		
	}

}
