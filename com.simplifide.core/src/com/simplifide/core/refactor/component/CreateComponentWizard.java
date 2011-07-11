/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.component;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class CreateComponentWizard extends TopRefactoringWizard{

    public CreateComponentWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    @Override
    protected void addUserInputPages() {
        
        this.addPage(new CreateComponentUserInputPage("Create Instance Page", this.getCreateInstanceRefactoring()));
    }
    
    public CreateComponentRefactoring getCreateInstanceRefactoring() {
        return (CreateComponentRefactoring) this.getRefactoring();
    }
    
    

}
