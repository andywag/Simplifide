package com.simplifide.core.actions.emacs;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.console.ExternalProcessConsole;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class EmacsActionDelegate extends EditorActionDelegate{

	public EmacsActionDelegate() {}
	public EmacsActionDelegate(SourceEditor editor) {super(editor);}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		return true;
	}

	public abstract String getCommand();
	
	public void runExpandCommand(DesignFile dfile, String command) {
		String prefix = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_PREFIX);
		String file = dfile.getResource().getLocation().toOSString();
		String ucommand = prefix + file + command;
		ExternalProcessConsole.runCommand(ucommand,true);
	}
	
	public void run(GeneralEditor editor) {
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

}
