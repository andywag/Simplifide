/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.color;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class PunctuationRule implements IPredicateRule{

	private char[] start;
	private IToken tok;
	
	public PunctuationRule(char[] start, IToken tok) {
		this.start = start;
		this.tok = tok;
	}
	
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		
		int u = scanner.read();
		for (char c : start) {
			if (u == c) return this.tok;
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	public IToken getSuccessToken() {
		return tok;
	}

	public IToken evaluate(ICharacterScanner scanner) {
		return this.evaluate(scanner,true);
	}

}
