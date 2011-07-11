/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.console.ExternalProcessConsole;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.design.DesignFile;

public class ExpandEmacsAutoAction extends Action{

	private DesignFile dfile;
	
	public ExpandEmacsAutoAction(DesignFile dfile) {
		super("Expand Emacs Auto Statements");
		this.dfile = dfile;
	}

	@Override
	public void run() {
		SourceEditor edit = this.dfile.getEditor();
		if (edit != null) {
			edit.doSave(null);
		}
		//String file = dfile.getResource().getLocation().toOSString();
		//String command = "emacs --batch " + file + " -f verilog-batch-auto";
		//ExternalProcessConsole.runCommand(command,true);
		runExpandCommand(dfile);
		try {
			edit.getDesignFile().getResource().refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public static void runExpandCommand(DesignFile dfile) {
		String file = dfile.getResource().getLocation().toOSString();
		String command = "emacs --batch " + file + " -f verilog-batch-auto";
		ExternalProcessConsole.runCommand(command,true);
	}
	
	
	
}
