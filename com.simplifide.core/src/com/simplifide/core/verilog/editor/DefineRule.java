package com.simplifide.core.verilog.editor;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class DefineRule implements IPredicateRule{

	private IToken tok;
	public DefineRule(IToken tok) {
		this.tok = tok;
	}
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		int u = scanner.read();
		if (u == '`') {
			u = scanner.read();
			while (Character.isJavaIdentifierPart(u)) {
				u = scanner.read();
			}
			scanner.unread();
			return tok;
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
