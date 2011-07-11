package com.simplifide.base.sourcefile.antlr;

public class FormatSupport {

	private FormatSupportInterface support;
	
	private static FormatSupport instance;
	private FormatSupport() {}
	
	public static FormatSupport getInstance() {
		if (instance == null) instance = new FormatSupport();
		return instance;
	}

	public int getIndent() {
		return support.getIndent();
	}
	public int getModuleIndent() {
		return support.getModuleIndent();
	}
	
	public void setSupport(FormatSupportInterface support) {
		this.support = support;
	}

	public FormatSupportInterface getSupport() {
		return support;
	}
	
}
