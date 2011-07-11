/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port.remove;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class RemovePortWizard extends TopRefactoringWizard{

    public RemovePortWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    @Override
    protected void addUserInputPages() {
       
        this.addPage(new RemovePortUserInputPage("Rename Input Page", this.getRemovePortRefactoring()));
    }
    
    public RemovePortRefactoring getRemovePortRefactoring() {
        return (RemovePortRefactoring) this.getRefactoring();
    }

}
