/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.console;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.simplifide.core.HardwareLog;

public final class HardwareConsole {

	private static HardwareConsole instance;
	
	
	private HardwareConsole() {
		
	}

	private static MessageConsole findConsole(String name) {
		 ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	}
	
	private static void makeConsoleVisible(MessageConsole console) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		String id = IConsoleConstants.ID_CONSOLE_VIEW;
		IConsoleView view;
		try {
			view = (IConsoleView) page.showView(id);
			view.display(console);
		} catch (PartInitException e) {
		    HardwareLog.logError(e);
		}
		


	}
	
	
	public static HardwareConsole getDefault() {
		if (instance == null) instance = new HardwareConsole();
		return instance;
	}
	
	public static void writeDebugConsole(String name, String output) {
		MessageConsole console = findConsole(name);
		MessageConsoleStream out = console.newMessageStream();
		out.println(output);
	}
	
}
