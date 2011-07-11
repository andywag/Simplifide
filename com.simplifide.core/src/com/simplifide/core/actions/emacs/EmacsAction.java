package com.simplifide.core.actions.emacs;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.console.ExternalProcessConsole;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.design.DesignFile;

public abstract class EmacsAction extends Action{

	private DesignFile designFile;
	
	public EmacsAction(String name, DesignFile dfile) {
		super(name);
		this.designFile = dfile;
	}

	public abstract String getCommand();
	
	public void runExpandCommand(DesignFile dfile, String command) {
		String file = dfile.getResource().getLocation().toOSString();
		String ucommand = "emacs --batch " + file + " -f " + command + " -f save-buffer";
		ExternalProcessConsole.runCommand(ucommand,true);
	}
	
	public void run() {
		SourceEditor edit = this.getDesignFile().getEditor();
		if (edit != null) {
			edit.doSave(null);
		}
		runExpandCommand(this.getDesignFile(),this.getCommand());
		try {
			edit.getDesignFile().getResource().refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public void setDesignFile(DesignFile designFile) {
		this.designFile = designFile;
	}

	public DesignFile getDesignFile() {
		return designFile;
	}
}
