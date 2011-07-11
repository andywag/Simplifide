package com.simplifide.base.verilog.parse.base;

import java.util.ArrayList;

import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;

/** Convenience Class to Keep Real Tokens as well as white space information */
public class SystemTokenList {
	private ArrayList<TopASTToken> tokens = new ArrayList<TopASTToken>();
	private int index=0;
	
	public SystemTokenList() {}
	
	public TopASTToken popToken() {
		TopASTToken tok = tokens.get(tokens.size() - 1);
		tokens.remove(tokens.size() -1);
		return tok;
	}
	
	public void addToken(TopASTToken tok) {
		this.tokens.add(tok);
	}
	
	public void addToken(ArrayList<TopASTToken> toks) {
		for (TopASTToken tok : toks) {
			this.tokens.add(tok);
		}
	}
	
	public TopASTToken nextToken() {
		if (index >= this.tokens.size()) return null;
		TopASTToken tok = this.tokens.get(index);
		while (tok.getType() == SystemVerilogTokenTypes.WS_ ||
				tok.getType() == SystemVerilogTokenTypes.NEWLINE_) {
			this.index++;
			if (index >= this.tokens.size()) return null; 
			tok = tokens.get(index);
		}
		this.index++;
		return tok;
	}
	
	public TopASTToken nextAnyToken() {
		if (index >= this.tokens.size()) return null;
		TopASTToken tok = this.tokens.get(index);
		this.index++;
		return tok;
	}
	
	public void rewind(int i) {
		this.index = this.index - i;
	}
	
	public void reset() {
		this.index = 0;
	}
	
	public String getTokensText() {
		StringBuilder build = new StringBuilder();
		for (TopASTToken tok : tokens) {
			build.append(tok.getText());
		}
		return build.toString();
	}
}
