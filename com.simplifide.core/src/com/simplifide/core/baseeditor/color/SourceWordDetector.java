/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.color;

import org.eclipse.jface.text.rules.IWordDetector;

public class SourceWordDetector implements IWordDetector{

	public SourceWordDetector() {}
	
	public boolean isWordPart(char c) {
		boolean tval = Character.isJavaIdentifierStart(c);
		boolean nval = Character.isDigit(c);
		return tval | nval;
	}

	public boolean isWordStart(char c) {
		
		return Character.isJavaIdentifierPart(c);
	}

}
