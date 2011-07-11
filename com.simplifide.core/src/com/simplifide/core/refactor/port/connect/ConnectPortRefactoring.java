/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port.connect;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ConnectPortRefactoring extends Refactoring{

    private ConnectPortRefactorProcessor processor;
    
    public ConnectPortRefactoring(ConnectPortRefactorProcessor processor) {
        this.setProcessor(processor);
    }
    @Override
    public RefactoringStatus checkFinalConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
        return getProcessor().checkFinalConditions(pm, null);
    }

    @Override
    public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
       return this.getProcessor().checkInitialConditions(pm);
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
        return this.getProcessor().createChange(pm);
    }

    @Override
    public String getName() {
       return "Rename Element";
    }
    public void setProcessor(ConnectPortRefactorProcessor processor) {
        this.processor = processor;
    }
    public ConnectPortRefactorProcessor getProcessor() {
        return processor;
    }

}
