/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.instance;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class CreateInstanceWizard extends TopRefactoringWizard{

    public CreateInstanceWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    @Override
    protected void addUserInputPages() {
        
        this.addPage(new CreateInstanceUserInputPage("Create Instance Page", this.getCreateInstanceRefactoring()));
    }
    
    public CreateInstanceRefactoring getCreateInstanceRefactoring() {
        return (CreateInstanceRefactoring) this.getRefactoring();
    }
    
    

}
