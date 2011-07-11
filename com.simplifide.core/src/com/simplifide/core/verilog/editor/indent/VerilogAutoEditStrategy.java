/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.indent;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.EditorUtilities;
import com.simplifide.core.editors.indent.SourceAutoEditStrategy;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class VerilogAutoEditStrategy extends SourceAutoEditStrategy{

	public void customizeDocumentCommand(IDocument document, DocumentCommand command)
	{
    	if (CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_ENABLE_AUTO_EDITS)) {    		
    		if (command.text.equals("n")) {
    			this.searchForBegin(document, command);
    			return;
    		}
    	}
		super.customizeDocumentCommand(document, command);			
	}
	
	private void searchForBegin(IDocument document, DocumentCommand command) {
		int caretOffset = command.offset;
		try {
			String ustr = document.get(caretOffset-4, 4);
			if (ustr.equalsIgnoreCase("begi")) {
				String ind = EditorUtilities.getLineIndent(document, caretOffset);
				command.text = "n" + "\n" + ind + "    \n" + ind + "end";
				command.caretOffset = caretOffset + ind.length() + 6;
				command.shiftsCaret = false;
			}
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
	}
	
}
