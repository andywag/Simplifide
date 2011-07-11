package com.simplifide.core.editors.actions.refactor;

import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.port.add.AddPortRefactorProcessor;
import com.simplifide.core.refactor.port.add.AddPortRefactoring;
import com.simplifide.core.refactor.port.add.AddPortWizard;
import com.simplifide.core.refactor.port.connect.ConnectPortRefactorProcessor;
import com.simplifide.core.refactor.port.connect.ConnectPortRefactoring;
import com.simplifide.core.refactor.port.connect.ConnectPortWizard;
import com.simplifide.core.refactor.port.remove.RemovePortRefactorProcessor;
import com.simplifide.core.refactor.port.remove.RemovePortRefactoring;
import com.simplifide.core.refactor.port.remove.RemovePortWizard;


public abstract class PortActionDelegate extends RefactorActionDelegate{
	
	public PortActionDelegate() {}
	public PortActionDelegate(SourceEditor editor) {super(editor);}
	
	public boolean checkEnabled(GeneralEditor editor) {
		
		if (!HardwareChecker.isRefactoringEnabled()) return false;

		ReferenceItem ref = this.returnEntityR();
		if (ref == null) return false;
		return true;
	}
	
	public ReferenceItem<Entity> returnEntityR() {
		ReferenceItem ref = this.getFindItemObject();
		if (ref == null) return null;
		if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_ENTITY) == 0) {
			return ref;
		}
		else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_INSTANCE_MODULE_TOP) == 0) {
			InstanceModule mod = (InstanceModule) ref.getObject();
			return mod.getEntityReference();
		}
		return null;
		
	}
	
	public static class Add extends PortActionDelegate {
		public Add() {}
		public Add(SourceEditor editor) {super(editor);}
		
		@Override
		public void run(GeneralEditor editor1) {
			SourceEditor editor = (SourceEditor) editor1;

			ReferenceItem<Entity> entr = this.returnEntityR();
			AddPortRefactorProcessor processor = new AddPortRefactorProcessor(
					entr, editor);
			AddPortRefactoring ref = new AddPortRefactoring(processor);
			AddPortWizard wizard = new AddPortWizard(ref);
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
					wizard);
			try {
				String titleForFailedChecks = ""; //$NON-NLS-1$ 
				op.run(editor.getEditorSite().getShell(), titleForFailedChecks);
			} catch (InterruptedException irex) {

			}
			ActiveSuiteHolder.getDefault().clean();
			
		}
	}
	
	public static class AddHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Add((SourceEditor)editor);
		}
		
	}
	
	public static class Remove extends PortActionDelegate {
		public Remove() {}
		public Remove(SourceEditor editor) {super(editor);}
		
		@Override
		public void run(GeneralEditor editor1) {
			SourceEditor editor = (SourceEditor) editor1;

			RemovePortRefactorProcessor processor = new RemovePortRefactorProcessor(
					this.returnEntityR(), editor);
			RemovePortRefactoring ref = new RemovePortRefactoring(processor);
			RemovePortWizard wizard = new RemovePortWizard(ref);
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
					wizard);
			try {
				String titleForFailedChecks = ""; //$NON-NLS-1$ 
				op.run(editor.getEditorSite().getShell(), titleForFailedChecks);
			} catch (InterruptedException irex) {

			}
			
		}
	}
	
	public static class RemoveHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Remove((SourceEditor)editor);
		}
		
	}
	
	public static class Connect extends PortActionDelegate {
		public Connect() {}
		public Connect(SourceEditor editor) {super(editor);}
		
		@Override
		public void run(GeneralEditor editor1) {
			SourceEditor editor = (SourceEditor) editor1;
			ConnectPortRefactorProcessor processor = new ConnectPortRefactorProcessor(
					this.returnEntityR(), editor);
			ConnectPortRefactoring ref = new ConnectPortRefactoring(processor);
			ConnectPortWizard wizard = new ConnectPortWizard(ref);
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
					wizard);
			try {
				String titleForFailedChecks = ""; //$NON-NLS-1$ 
				op.run(editor.getEditorSite().getShell(), titleForFailedChecks);
			} catch (InterruptedException irex) {

			}
			
		}
	}
	
	public static class ConnectHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Connect((SourceEditor)editor);
		}
		
	}
	
	
}
