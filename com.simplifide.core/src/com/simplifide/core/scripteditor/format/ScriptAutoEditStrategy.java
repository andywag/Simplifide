package com.simplifide.core.scripteditor.format;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextUtilities;

public abstract class ScriptAutoEditStrategy extends ScriptAutoEditStrategyParent{

	protected abstract void handleNewLine(IDocument d,DocumentCommand c);
	
	
	
	public void customizeDocumentCommand(IDocument d,
			DocumentCommand c) {
		if (c.length == 0 && c.text != null && 
			TextUtilities.endsWith(d.getLegalLineDelimiters(), c.text) != -1) {		
			autoIndentAfterNewLine(d, c);
		}
		else if(c.length == 0 && c.text != null && c.text.equals("\t")) {
			autoTabAfterNewLine(d, c);
		}
	}

	
	public static class Vhdl extends ScriptAutoEditStrategy {

		protected void handleNewLine(IDocument d, DocumentCommand c) {
			c.text += "-- ";
		}
		
	}
	
	public static class Verilog extends ScriptAutoEditStrategy {
		protected void handleNewLine(IDocument d, DocumentCommand c) {
			
		}
	}
	
}
