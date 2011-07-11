/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port.connect;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.simplifide.core.refactor.port.TopRefactoringWizard;

public class ConnectPortWizard extends TopRefactoringWizard{

    public ConnectPortWizard(Refactoring refactoring) {
        super(refactoring, RefactoringWizard.NONE);
    }

    public int getWidth() {return 100;}
    public int getHeight() {return 100;}
    
    @Override
    protected void addUserInputPages() {
       
        this.addPage(new ConnectPortUserInputPage("Rename Input Page", this.getAddPortRefactoring()));
    }
    
    public ConnectPortRefactoring getAddPortRefactoring() {
        return (ConnectPortRefactoring) this.getRefactoring();
    }

}
