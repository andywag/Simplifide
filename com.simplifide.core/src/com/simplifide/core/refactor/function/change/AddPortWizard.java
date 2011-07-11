/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.function.change;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class AddPortWizard extends TopRefactoringWizard{

    public AddPortWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    @Override
    protected void addUserInputPages() {
        
        this.addPage(new AddPortUserInputPage("Rename Input Page", this.getAddPortRefactoring()));
    }
    
    public AddPortRefactoring getAddPortRefactoring() {
        return (AddPortRefactoring) this.getRefactoring();
    }
    
    

}
