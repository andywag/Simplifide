package com.simplifide.core.refactor.port;

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

public class TopRefactoringWizard extends RefactoringWizard{

	public TopRefactoringWizard(Refactoring refactoring, int flags) {
		super(refactoring, flags);
		this.setForcePreviewReview(true);
		this.createSettings();
	}

	private void createSettings() {
		if (this.getWidth() != -1) {
			DialogSettings set = new DialogSettings("Wizard");
			set.put("width", 100);
			set.put("height",100);
			this.setDialogSettings(set);
		}
		
		
	}
	
	public int getWidth() {return -1;}
	public int getHeight() {return -1;}

	
	@Override
	protected void addUserInputPages() {
	}
	
	
	

	

}
