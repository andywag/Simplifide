/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor;


import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WordRule;

import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourceScanner;
import com.simplifide.core.baseeditor.color.SourceWordDetector;
import com.simplifide.core.vhdl.editor.templates.VhdlTemplateProcessor;

public class VhdlScanner extends SourceScanner{

	public static final String[] keywords = new String[] {"entity","architecture","package","of","begin","end","postponed","process",
		"assert","generate","break","array","report","severity","attribute","tolerance","use","for","when","case","others",
		"component","until","else","configuration","constant","to","downto","after","entity","procedure","function","package",
		"type","subtype","signal","variable","label","literal","units","group","file","nature","subnature","terminal","box",
		"all","exit","abs","not","if","generic","map","then","elsif","in","out","bus","while","library","body","and","or","nand",
		"nor","xor","xnor","loop","inout","buffer","linkage","mod","rem","port","record","return","with","select","on","wait",
		"register","pure","impure","shared","unaffected","is","alias","open","protected","integer","range","context"};
	
	public static final String[] specialWords = new String[] {"std_logic_vector","signed","usigned","std_logic","integer"};
	
        public static String[] totalkeywords;

        
        protected String[] getSpecialWords() {return specialWords;}
	
	public VhdlScanner(ColorManager provider) {
		super(provider);
	}

	
	public static boolean isKeyWord(String key) {
		for (String keyw : keywords) {
			if (keyw.equalsIgnoreCase(key)) return true;
		}
		return false;
	}
	
	 public WordRule createWordRule(IToken other) {
	    	WordRule wordRule = new WordRule(new SourceWordDetector(), other,true);
	    	return wordRule;
	    }
	
	 public String[] getTickWords() {
		 return VhdlTemplateProcessor.TICK_VALUES;
	 }
	 
	@Override
	protected String[] getKeywords() {
            if (totalkeywords == null) {
                totalkeywords = new String[keywords.length*2];
                for (int i=0;i<keywords.length;i++) {
                    totalkeywords[2*i] = keywords[i];
                    totalkeywords[2*i+1] = keywords[i].toUpperCase();
                }
            }
		return totalkeywords;
	}

	@Override
	public IPredicateRule getSingleLineCommentRule(IToken tok) {
		return new EndOfLineRule("--", tok);
	}

	@Override
	public IPredicateRule getStringRule(IToken tok) {
		return new SingleLineRule("\"", "\"", tok, '\\');
	}
	
	public IPredicateRule getStringRule2(IToken tok) {
		//return new SingleLineRule("'", "'", tok, '\\');
		return null;
	}

	@Override
	public IPredicateRule getSingleHdlRule(IToken tok) {
		return new EndOfLineRule("--*",tok);
	}


	@Override
	protected IPredicateRule getDefinedRule(IToken tok) {
		return null;
	}


	@Override
	protected IPredicateRule getAltNumberRule(IToken tok) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
