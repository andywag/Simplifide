/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.action;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.simplifide.core.HardwareLog;

public class CreateLibraryAction extends Action {

	private CreateLibraryActor actor;
	
	public CreateLibraryAction(String name, CreateLibraryActor actor) {
		super(name);
		this.actor = actor;
	}
	
	public void run() {
		CreateLibraryOp op = new CreateLibraryOp();
		
		try {
			PlatformUI.getWorkbench().getProgressService().run(false, true, op);
		} catch (InvocationTargetException e) {
			HardwareLog.logError(e);
		} catch (InterruptedException e) {
			HardwareLog.logError(e);
		}
		
	}
	
	public interface CreateLibraryActor {
		public void createLibrary(IProgressMonitor monitor);
	}
	
	
	public class CreateLibraryOp implements IRunnableWithProgress {

		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			actor.createLibrary(monitor);
			
		}

		
		
	}


	
	
	
	
}
