/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.hover;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.core.baseeditor.GeneralEditor;


public class SourceInformationControlCreator implements IInformationControlCreator {

	
	
	public SourceInformationControlCreator(GeneralEditor editor) {
		
	}
	
	public IInformationControl createInformationControl(Shell parent) {		
		SourceInformationPresenter present = new SourceInformationPresenter();
		DefaultInformationControl def = new DefaultInformationControl(parent,present);
		return def;
	}

}
