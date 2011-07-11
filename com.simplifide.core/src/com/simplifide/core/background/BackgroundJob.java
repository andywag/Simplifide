/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.background;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

public class BackgroundJob extends Job {
	
	private BackgroundObject object;
	public BackgroundJob(String name, BackgroundObject object) {
		super(name);
		this.object = object;
	}

	public static void runJob(String name, BackgroundObject object) {
		BackgroundJob job = new BackgroundJob(name,object);
		
		job.schedule();
	}
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return object.run(monitor);
	}
}
