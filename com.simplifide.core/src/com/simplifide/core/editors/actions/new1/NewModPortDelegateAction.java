package com.simplifide.core.editors.actions.new1;

import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

import com.simplifide.base.core.interfac.InterfaceTopModule;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.modport.CreateModPortRefactorProcessor;
import com.simplifide.core.refactor.modport.CreateModPortRefactoring;
import com.simplifide.core.refactor.modport.CreateModPortWizard;

public class NewModPortDelegateAction extends NewActionDelegate{

	public NewModPortDelegateAction() {}
	public NewModPortDelegateAction(SourceEditor editor) {
		super(editor);
	}
	

	public boolean checkEnabled(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;

		if (!HardwareChecker.isRefactoringEnabled()) return false;
        ReferenceItem ref = this.getFindItemObject();
        if (ref == null) {
        	ParseContext context = EditorUtilities.getParseContext(editor.getDesignFile().getParseDescriptor(), editor.getCaretPosition());
        	ref = context.getRefHandler().getActiveReference();
        }
       if (ref != null && ref.getObject() instanceof InterfaceTopModule) {
    	   return true;
       }
        return false;
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
		   CreateModPortRefactorProcessor processor = new CreateModPortRefactorProcessor(instanceModule,editor,loc );
		   CreateModPortRefactoring ref             = new CreateModPortRefactoring( processor ); 
	       CreateModPortWizard wizard               = new CreateModPortWizard( ref );
	       RefactoringWizardOpenOperation op       = new RefactoringWizardOpenOperation(wizard ); 
	        try { 
	            String titleForFailedChecks = ""; //$NON-NLS-1$ 
	            op.run(editor.getEditorSite().getShell(), titleForFailedChecks ); 
	        } catch( InterruptedException irex ) { 
	            
	        }
	}
	
	public static class NewHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new NewModPortDelegateAction((SourceEditor)editor);
		}
		
	}

}
