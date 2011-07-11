/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.actions;

import org.eclipse.jface.action.Action;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.design.DesignFile;

public class ExpandTemplateAction extends Action{

	private DesignFile dfile;
	
	public ExpandTemplateAction(DesignFile dfile) {
		super("Expand Templates");
		this.dfile = dfile;
	}

	@Override
	public void run() {
		SourceEditor edit = this.dfile.getEditor();
		if (edit != null) {
			edit.doSave(null);
		}
		this.dfile.getBuilder().expandTemplates();
	}
	
	
	
}
