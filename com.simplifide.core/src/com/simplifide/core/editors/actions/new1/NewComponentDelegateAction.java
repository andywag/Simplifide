package com.simplifide.core.editors.actions.new1;

import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.component.CreateComponentRefactorProcessor;
import com.simplifide.core.refactor.component.CreateComponentRefactoring;
import com.simplifide.core.refactor.component.CreateComponentWizard;

public class NewComponentDelegateAction extends NewActionDelegate{

	public NewComponentDelegateAction() {}
	public NewComponentDelegateAction(SourceEditor editor) {
		super(editor);
	}
	
	public void updateEnabled() {

	}
	

	@Override
	public void run(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;
		ReferenceLocation loc = this.getLocation();
		ReferenceItem<HardwareModule> refi = this.getFindItemObject();
		if (refi == null) {
			ParseContext context = EditorUtilities.getParseContext(editor.getDesignFile().getParseDescriptor(), editor.getCaretPosition());
        	refi = context.getRefHandler().getActiveReference();
		}
		InstanceModule instanceModule = (InstanceModule) refi.getObject().getInstModRef().getObject();
		  CreateComponentRefactorProcessor processor = new CreateComponentRefactorProcessor(instanceModule,editor,loc);
	        CreateComponentRefactoring ref = new CreateComponentRefactoring( processor ); 
	        CreateComponentWizard wizard = new CreateComponentWizard( ref );
	        RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard ); 
	        try { 
	            String titleForFailedChecks = ""; //$NON-NLS-1$ 
	            op.run(editor.getEditorSite().getShell(), titleForFailedChecks ); 
	        } catch( InterruptedException irex ) { 
	            
	        }
	}
	
	public static class ComponentHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new NewComponentDelegateAction((SourceEditor)editor);
		}
		
	}

}
