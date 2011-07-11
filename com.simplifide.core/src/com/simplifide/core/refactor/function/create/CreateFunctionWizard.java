/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.function.create;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class CreateFunctionWizard extends TopRefactoringWizard{

    public CreateFunctionWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    @Override
    protected void addUserInputPages() {
        
        //this.addPage(new CreateFunctionUserInputPage("Rename Input Page", this.getAddPortRefactoring()));
    }
    
    public CreateFunctionRefactoring getAddPortRefactoring() {
        return (CreateFunctionRefactoring) this.getRefactoring();
    }
    
    

}
