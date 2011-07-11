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

public class ExpandEmacsNavigatorAction extends Action {

	private ExpandEmacseActor actor;
	
	public ExpandEmacsNavigatorAction(String name, ExpandEmacseActor actor) {
		this(name,actor,true);
	}
	
	public ExpandEmacsNavigatorAction(String name, ExpandEmacseActor actor, boolean enabled) {
		super(name);
		this.actor = actor;
		this.setEnabled(enabled);
	}
	
	public void run() {
		CleanOp op = new CleanOp();
		
		try {
			PlatformUI.getWorkbench().getProgressService().run(false, true, op);
		} catch (InvocationTargetException e) {
			HardwareLog.logError(e);
		} catch (InterruptedException e) {
			HardwareLog.logError(e);
		}
		
	}
	
	public interface ExpandEmacseActor {
		public void expandEmacs(IProgressMonitor monitor);
	}
	
	
	public class CleanOp implements IRunnableWithProgress {

		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			actor.expandEmacs(monitor);
			
		}

		
		
	}


	
	
	
	
}
