/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor.templates;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.resources.BasicIconManager;

public class TickCompletionProposal implements ICompletionProposal{

	private String name;
	private int offset;
	private int length;
	
	public TickCompletionProposal (String name, int offset, int length) {
		this.name = name;
		this.offset = offset;
		this.length = length;
	}
	
	public void apply(IDocument document) {
		try {
			document.replace(offset, this.length, this.name);
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
	}

	public String getAdditionalProposalInfo() {
		return null;
	}

	public IContextInformation getContextInformation() {
		return null;
	}

	public String getDisplayString() {
		return this.name;
	}

	public Image getImage() {
		return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROPERTY);
	}

	public Point getSelection(IDocument document) {
		return null;
	}
  
}
