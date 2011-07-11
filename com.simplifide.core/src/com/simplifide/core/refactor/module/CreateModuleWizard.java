/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.module;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class CreateModuleWizard extends TopRefactoringWizard{

    public CreateModuleWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    @Override
    protected void addUserInputPages() {
        
        this.addPage(new CreateModuleUserInputPage("Create Instance Page", this.getCreateInstanceRefactoring()));
    }
    
    public CreateModuleRefactoring getCreateInstanceRefactoring() {
        return (CreateModuleRefactoring) this.getRefactoring();
    }
    
    

}
