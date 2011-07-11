/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.pythonext.console;

import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.resources.IconManager;

public class TopConsole extends MessageConsole{

	
	
	
	private MessageConsoleStream inStream;
	private MessageConsoleStream outStream;
	private MessageConsoleStream errorStream;
	
	public TopConsole(String name) {
		super(name, null);
		init1();
	
	}
	
	
	
	private void init1() {
		this.setImageDescriptor(IconManager.getIcon(ReferenceUtilitiesInterface.REF_DESIGN_FILE));
		inStream = this.newMessageStream();
		outStream = this.newMessageStream();
		errorStream = this.newMessageStream();
		outStream.setColor(new Color(null,0,0,255));
		errorStream.setColor(new Color(null,255,0,0));
	}
	
	public void makeConsoleActive() {
		   
		try {
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchPage page = wb.getActiveWorkbenchWindow().getActivePage();
			String id = IConsoleConstants.ID_CONSOLE_VIEW;
			IConsoleView view;
			view = (IConsoleView) page.showView(id);
			view.display(this);

		} catch (PartInitException e) {
			HardwareLog.logError(e);
		}

	}
	
	public void writeInputMessage(String instring) {
		inStream.print(instring);
	}
	
	public void writeOutputMessage(String instring) {
		outStream.print(instring);
	}
	
	public void writeErrorMessage(String instring) {
		errorStream.print(instring);
	}

}
