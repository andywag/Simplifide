/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.color;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public abstract class SourcePartitionScanner extends RuleBasedPartitionScanner {

	public final static String SCRIPT_COMMENT = "script_comment";
	public final static String MULTI_COMMENT  = "multi_comment";
	public final static String MULTI_DOC      = "mult_doc";
   
	public abstract IPredicateRule getScriptRule(IToken token);
	public abstract IPredicateRule getMultiCommentRule(IToken token);
	public abstract IPredicateRule getDocRule(IToken token);
	
    
	public SourcePartitionScanner() {
		
		IToken script = new Token(SCRIPT_COMMENT);
		IToken multi  = new Token(MULTI_COMMENT);
		IToken doc    = new Token(MULTI_DOC);
		
		ArrayList rules = new ArrayList<IPredicateRule>();
		if (this.getScriptRule(script) != null) rules.add(this.getScriptRule(script));
		if (this.getMultiCommentRule(multi) != null) rules.add(this.getMultiCommentRule(multi));
		if (this.getDocRule(doc) != null) rules.add(this.getDocRule(doc));
		IPredicateRule[] preds = (IPredicateRule[]) rules.toArray(new IPredicateRule[rules.size()]);
		setPredicateRules(preds);
	}
}
