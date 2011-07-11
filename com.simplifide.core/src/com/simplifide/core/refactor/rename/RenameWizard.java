/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.rename;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class RenameWizard extends TopRefactoringWizard{

    public RenameWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    public int getWidth() {return 100;}
    public int getHeight() {return 100;}
    
    @Override
    protected void addUserInputPages() {
        this.addPage(new RenameUserInputWizardPage("Rename Input Page", this.getRenameRefactoring()));
    }
    
    public RenameRefactoring getRenameRefactoring() {
        return (RenameRefactoring) this.getRefactoring();
    }

}
