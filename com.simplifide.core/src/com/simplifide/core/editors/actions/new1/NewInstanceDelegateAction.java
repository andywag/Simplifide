package com.simplifide.core.editors.actions.new1;

import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.instance.CreateInstanceRefactorProcessor;
import com.simplifide.core.refactor.instance.CreateInstanceRefactoring;
import com.simplifide.core.refactor.instance.CreateInstanceWizard;

public class NewInstanceDelegateAction extends NewActionDelegate{

	public NewInstanceDelegateAction() {}
	public NewInstanceDelegateAction(SourceEditor editor) {
		super(editor);
	}
	
	public void updateEnabled() {

	}
	
	@Override
	public boolean checkEnabled(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;

        ReferenceItem ref = this.getFindItemObject();
        if (ref == null) {
        	ParseContext context = EditorUtilities.getParseContext(editor.getDesignFile().getParseDescriptor(), editor.getCaretPosition());
        	ref = context.getRefHandler().getActiveReference();
        }
        if (ref != null && ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_HARDWARE_MODULE) == 0){
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
		 CreateInstanceRefactorProcessor processor = new CreateInstanceRefactorProcessor( null ,
				 instanceModule,editor,loc );
         CreateInstanceRefactoring ref = new CreateInstanceRefactoring( processor ); 
         CreateInstanceWizard wizard = new CreateInstanceWizard( ref );
         RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard ); 
         try { 
             String titleForFailedChecks = ""; //$NON-NLS-1$ 
             op.run(editor.getEditorSite().getShell(), titleForFailedChecks ); 
         } catch( InterruptedException irex ) { 
             
         }
	}
	
	public static class NewHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new NewInstanceDelegateAction((SourceEditor)editor);
		}
		
	}

}
