/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.python.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;


public class ExternalConsole extends TopConsole{

	private static ExternalConsole instance;
		
	public ExternalConsole() {
		super("External Python");	
	}
	
	 
	
	public static ExternalConsole getDefault() {
		if (instance == null) {
			instance = new ExternalConsole();
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] {instance});
		}
		return instance;
	}
	

}
