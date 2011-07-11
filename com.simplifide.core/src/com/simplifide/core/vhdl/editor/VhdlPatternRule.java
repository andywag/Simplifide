package com.simplifide.core.vhdl.editor;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

// TODO Can't Find any uses of this rule file should be deleted
public class VhdlPatternRule implements IPredicateRule{
	
	public static String START = "-- simplifide";
	
	private IToken token;
	public VhdlPatternRule(IToken tok) {
		this.token = tok;
	}

	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		// TODO Auto-generated method stub
		return null;
	}

	public IToken getSuccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean lookForStart(ICharacterScanner scanner) {
		char[] sequence = START.toCharArray();
		for (int i=0;i<sequence.length;i++) {
			int c = scanner.read();
			if (c != sequence[i]) {
				for (int j=0;i<j;j++) scanner.unread();
				return false;
			}
		}
		return true;
	}
	
	public boolean lookForEnd(ICharacterScanner scanner) {
		int c;
		int tot = 0;
		while ((c= scanner.read()) != ICharacterScanner.EOF) {
			tot++;
			if (c == '\n') {
				c = scanner.read();
				if (c != '-') {
					scanner.unread();
					return true;
				}
			}
		}
		for (int i=0;i<tot + START.length();i++) scanner.unread();
		return false;
	}
	
	public IToken evaluate(ICharacterScanner scanner) {
		// TODO Auto-generated method stub
		int c = scanner.read();
		scanner.unread();
		if (c != '-') return Token.UNDEFINED;
		boolean start = this.lookForStart(scanner);
		if (!start) return Token.UNDEFINED;
		boolean end = this.lookForEnd(scanner);
		
		String ustr = "";
		for (int i=0;i<20;i++) {
			ustr += (char) scanner.read();
		}
		for (int i=0;i<20;i++) {
			scanner.unread();
		}
		
		if (!end) return Token.UNDEFINED;
		return this.token;
	}
	
}
