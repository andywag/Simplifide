/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.source.SourceViewer;

public class FormatAction extends Action{
	

	private SourceViewer viewer;
	
	public FormatAction(SourceViewer viewer) {
		this.setText("Format");
		this.viewer = viewer;
		this.setEnabled(false);
		
		
       
	}
	
	public void run() {
		viewer.doOperation(SourceViewer.FORMAT);
	}
	
	
	
	
}
