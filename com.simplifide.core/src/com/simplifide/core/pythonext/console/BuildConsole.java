/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.pythonext.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;

public class BuildConsole extends TopConsole{

	private static BuildConsole instance;
		
	public BuildConsole() {
		super("Build");	
	}
	
	
	
	public static BuildConsole getDefault() {
		if (instance == null) {
			instance = new BuildConsole();
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] {instance});
		}
		return instance;
	}
	

}
