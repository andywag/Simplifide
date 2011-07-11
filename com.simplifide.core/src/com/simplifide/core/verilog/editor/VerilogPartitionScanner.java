/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

import com.simplifide.core.baseeditor.color.SourcePartitionScanner;


public class VerilogPartitionScanner extends SourcePartitionScanner{

	public VerilogPartitionScanner() {}

	@Override
	public IPredicateRule getScriptRule(IToken token) {
		return new MultiLineRule("/* simplifide", "*/", token);
	}

	@Override
	public IPredicateRule getMultiCommentRule(IToken token) {
		return new MultiLineRule("/*","*/",token);
	}

	@Override
	public IPredicateRule getDocRule(IToken token) {
		return new MultiLineRule("/**","*/",token);
	}
	
	

	
}
