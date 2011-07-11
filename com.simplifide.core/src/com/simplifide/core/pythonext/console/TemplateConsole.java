/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.pythonext.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;

public class TemplateConsole extends TopConsole{

	private static TemplateConsole instance;
		
	public TemplateConsole() {
		super("Templates");	
	}
	
	
	
	public void makeConsoleActive() {
		super.makeConsoleActive();
	}
	public static TemplateConsole getDefault() {
		if (instance == null) {
			instance = new TemplateConsole();
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] {instance});
		}
		return instance;
	}
	

}
