/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

import com.simplifide.core.baseeditor.color.SourcePartitionScanner;


public class VhdlPartitionScanner extends SourcePartitionScanner{
	@Override
	public IPredicateRule getScriptRule(IToken token) {
		return new MultiLineRule("-- simplifide","-- end_simplifide",token);
		
	}

	@Override
	public IPredicateRule getMultiCommentRule(IToken token) {
		return null;
	}

	@Override
	public IPredicateRule getDocRule(IToken token) {
		return null;
	}
	
}
