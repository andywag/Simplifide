/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.background;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.progress.UIJob;

public class BackgroundUIJob extends UIJob{

	private BackgroundObject object;
	public BackgroundUIJob(String name, BackgroundObject object) {
		super(name);
		this.object = object;
	}

	public static void runJob(String name, BackgroundObject object) {
		BackgroundUIJob job = new BackgroundUIJob(name,object);
		job.schedule(1000);
	}
	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return object.run(monitor);
		
	}

}
