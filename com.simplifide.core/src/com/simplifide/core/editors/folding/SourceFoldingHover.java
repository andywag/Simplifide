/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.folding;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.widgets.Shell;


public class SourceFoldingHover extends DefaultInformationControl{

	
	public SourceFoldingHover(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	
	public static class Factory implements IInformationControlCreator {

		public IInformationControl createInformationControl(Shell parent) {
			return new SourceFoldingHover(parent);
		}
		
	}
	

}
