/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.color;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WordRule;

public class SourcePunctuationDetector implements IWordDetector{

	public static char[] punct = new char[] {'!','@','#','$','%','%','^','&',
		'(',')','+','=','<','>','?','[',']','{','}',';',':'};
	
	public SourcePunctuationDetector() {}
	
	private boolean checkInclude(char c) {
		for (char o : punct) {
			if (o == c) return true;
		}
		return false;
	}
	
	public boolean isWordPart(char c) {
		if (Character.isJavaIdentifierPart(c)) return false;
		if (c == '-' | c == '*' | c=='/') return false;
		return true;
	}

	public boolean isWordStart(char c) {
		if (Character.isJavaIdentifierPart(c)) return false;
		if (c == '-' | c == '*' | c== '/') return false;
		return true;
	}

	public static WordRule createWordRule(IToken tok) {
		WordRule rule = new WordRule(new SourcePunctuationDetector(),tok);
		for (char p : punct) {
			rule.addWord(Character.toString(p), tok);
		}
		return rule;
	}
	
}
