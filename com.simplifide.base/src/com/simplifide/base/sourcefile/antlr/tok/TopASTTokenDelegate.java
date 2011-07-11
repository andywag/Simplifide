package com.simplifide.base.sourcefile.antlr.tok;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.verilog.parse.base.SystemTokenList;

public class TopASTTokenDelegate extends TopASTToken{

	private String baseText;
	private TopASTToken realTok;
	private SystemTokenList tokens;
	
	public TopASTTokenDelegate(TopASTToken realTok, SystemTokenList tokens) {
		this.realTok = realTok;
		this.tokens = tokens;
	}
	
	public String getRealText() {
		return baseText;
	}

	 public String getText() {
		 return this.realTok.getText();
	 }

	 public int getPosition() {
		 return this.realTok.getPosition();
	 }
	 
	 public NodePosition getNodePosition() {
	        return new NodePosition(this.getPosition(),this.getPosition() + this.realTok.getLength(),this.getLine(),this.getColumn());
	 }
	
	public void setRealTok(TopASTToken realTok) {
		this.realTok = realTok;
	}

	public TopASTToken getRealTok() {
		return realTok;
	}

	public void setTokens(SystemTokenList tokens) {
		this.tokens = tokens;
	}

	public SystemTokenList getTokens() {
		return tokens;
	}

	public void setBaseText(String baseText) {
		this.baseText = baseText;
	}

	public String getBaseText() {
		return baseText;
	}
	
}
