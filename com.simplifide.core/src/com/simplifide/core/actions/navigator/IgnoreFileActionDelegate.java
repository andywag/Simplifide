package com.simplifide.core.actions.navigator;

import org.eclipse.jface.action.IAction;

import com.simplifide.core.baseeditor.source.SourceFile;

public class IgnoreFileActionDelegate extends TopFileActionDelegate{

	 public void run(IAction action) {
		SourceFile sfile = this.getSourceFile(action);
		if (sfile.isIgnoredFile()) {
			sfile.setIgnoredFile(false);
		}
		else {
			sfile.setIgnoredFile(true);
		}
		this.createCheckMark(action);
	 }


	protected void createCheckMark(IAction action) {
		SourceFile sfile = this.getSourceFile(action);
		boolean ignored = sfile.isIgnoredFile();
		if (ignored) {
			action.setChecked(true);
		}
		else {
			action.setChecked(false);
		}
	}
	
}
