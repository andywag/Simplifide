/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.modport;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class CreateModPortWizard extends TopRefactoringWizard{

    public CreateModPortWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    @Override
    protected void addUserInputPages() {
        
        this.addPage(new CreateModPortUserInputPage("Create ModPort Page", this.getCreateInstanceRefactoring()));
    }
    
    public CreateModPortRefactoring getCreateInstanceRefactoring() {
        return (CreateModPortRefactoring) this.getRefactoring();
    }
    
    

}
