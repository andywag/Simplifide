/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.indent;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

import com.simplifide.core.HardwareLog;

public class SourceAutoEditStrategy implements IAutoEditStrategy{

	public SourceAutoEditStrategy() {}
	
	private boolean parenOpen = false;
	private boolean quoteOpen = false;
	private boolean singleOpen = false;
	
	public void customizeDocumentCommand(IDocument document, DocumentCommand command)
	{
		try {
			char current = document.getChar(command.offset);
			if(command.text.equals("\""))
	        {
	        	if (!quoteOpen) {
	        		quoteOpen = true;
	        		command.text = "\"\"";
		            configureCommand(command);
	        	}
	        	else {
	        		if (current == '"') {
	        			quoteOpen = false;
	        			command.text = "";
	        			command.offset++;
	        		}
	        	}
				
	        }
			/*else if(command.text.equals("'"))
	        {
	        	if (!quoteOpen) {
	        		quoteOpen = true;
	        		command.text = "''";
		            configureCommand(command);
	        	}
	        	else {
	        		if (current == '\'') {
	        			quoteOpen = false;
	        			command.text = "";
	        			command.offset++;
	        		}
	        	}
				
	        }*/
	        else if (command.text.equals("("))
	        {
	        	if (Character.isWhitespace(current)) {
	        		command.text = "()";
		            configureCommand(command);
		            parenOpen = true;
	        	}
	        	
	        }
	        else if (command.text.equals(")")) {
	        	if (parenOpen && current == ')') {
	        		command.text = "";
	        		command.offset++;
	        	}
	        	parenOpen = false;
	        }
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
	        
	        
	    }

		
	

	    private void configureCommand(DocumentCommand command)
	    {
	        //puts the caret between both the quotes

	        command.caretOffset = command.offset + 1;
	        command.shiftsCaret = false;
	    }

	}


