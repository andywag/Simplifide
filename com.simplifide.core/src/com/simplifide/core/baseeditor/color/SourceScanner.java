/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.color;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.color.PunctuationRule;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class SourceScanner extends RuleBasedScanner {

	public static String quote = "'";
	public static char[] punct = new char[] {'/','\\','~','*','|','!','@','#','$','%','^','&',
		'(',')','+','=','<','>','?','[',']','{','}',';',':',',','.','-','`','"',quote.toCharArray()[0]};
	
	//public abstract IPredicateRule getMultiHdlRule(IToken tok);
	public abstract IPredicateRule getSingleHdlRule(IToken tok);
	protected abstract IPredicateRule getSingleLineCommentRule(IToken tok);
    //protected abstract IPredicateRule getMultiLineCommentRule(IToken tok);
    protected abstract IPredicateRule getStringRule(IToken tok);
    protected abstract IPredicateRule getStringRule2(IToken tok);
    protected abstract IPredicateRule getDefinedRule(IToken tok);
    protected abstract IRule getAltNumberRule(IToken tok);
    
    protected abstract String[] getKeywords();
    protected String[] getTickWords()  {return new String[0];}
    protected String[] getTypeWords()  {return new String[0];}
    protected String[] getSpecialWords() {return new String[0];}
	
    protected IRule getNumberRule(IToken tok) {return new SourceNumberRule(tok);}
    
    public SourceScanner(ColorManager provider) {

    IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
    
    
    RGB color_def = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_DEFAULT);	
    RGB color_key = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_KEY); 
    RGB color_string = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_STRING);
    RGB color_doc = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_HDL_DOC);
    RGB color_com = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_COMMENT);
    RGB color_num = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_NUMBER);
    RGB color_punc = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_PUNCTUATION);
    RGB color_define = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_DEFINE);
    RGB color_type = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_TYPE);
    
    int tdef =  store.getBoolean(PreferenceConstants.BOLD_DEFAULT) ? SWT.BOLD : SWT.NULL ;
    int tkey  =  store.getBoolean(PreferenceConstants.BOLD_KEY) ? SWT.BOLD : SWT.NULL ;
    int tstr  =  store.getBoolean(PreferenceConstants.BOLD_STRING) ? SWT.BOLD : SWT.NULL ;
    int tcom  =  store.getBoolean(PreferenceConstants.BOLD_COMMENT) ? SWT.BOLD : SWT.NULL ;
    int tdoc  = store.getBoolean(PreferenceConstants.BOLD_HDL_DOC) ? SWT.BOLD : SWT.NULL ;
    int tnum =  store.getBoolean(PreferenceConstants.BOLD_NUMBER) ? SWT.BOLD : SWT.NULL ;
    int tpun =  store.getBoolean(PreferenceConstants.BOLD_PUNCTUATION) ? SWT.BOLD : SWT.NULL ;
    int tdefine =  store.getBoolean(PreferenceConstants.BOLD_DEFINE) ? SWT.BOLD : SWT.NULL ;
    int ttype =  store.getBoolean(PreferenceConstants.BOLD_TYPE) ? SWT.BOLD : SWT.NULL ;
    
    tdef  |= store.getBoolean(PreferenceConstants.ITALIC_DEFAULT) ? SWT.ITALIC : SWT.NULL ;
    tkey  |=  store.getBoolean(PreferenceConstants.ITALIC_KEY) ? SWT.ITALIC : SWT.NULL ;
    tstr  |=  store.getBoolean(PreferenceConstants.ITALIC_STRING) ? SWT.ITALIC : SWT.NULL ;
    tcom  |=  store.getBoolean(PreferenceConstants.ITALIC_COMMENT) ? SWT.ITALIC : SWT.NULL ;
    tdoc  |= store.getBoolean(PreferenceConstants.ITALIC_HDL_DOC) ? SWT.ITALIC : SWT.NULL ;
    tnum  |=  store.getBoolean(PreferenceConstants.ITALIC_NUMBER) ? SWT.ITALIC : SWT.NULL ;
    tpun  |= store.getBoolean(PreferenceConstants.ITALIC_PUNCTUATION) ? SWT.ITALIC : SWT.NULL ;
    tdefine  |= store.getBoolean(PreferenceConstants.ITALIC_DEFINE) ? SWT.ITALIC : SWT.NULL ;
    ttype  |= store.getBoolean(PreferenceConstants.ITALIC_TYPE) ? SWT.ITALIC : SWT.NULL ;

  
    
    IToken keyword= new Token(new TextAttribute(provider.getColor(color_key),null,tkey));
    IToken string= new Token(new TextAttribute(provider.getColor(color_string),null,tstr));
    IToken comment= new Token(new TextAttribute(provider.getColor(color_com),null,tcom));
    IToken hdldoc =new Token(new TextAttribute(provider.getColor(color_doc),null,tdoc));
    IToken number =new Token(new TextAttribute(provider.getColor(color_num),null,tnum));
    IToken other= new Token(new TextAttribute(provider.getColor(color_def),null,tdef));
    IToken punc= new Token(new TextAttribute(provider.getColor(color_punc),null,tpun));
    IToken define = new Token(new TextAttribute(provider.getColor(color_define),null,tdefine));
    IToken type = new Token(new TextAttribute(provider.getColor(color_type),null,tdefine));
    
    //IPredicateRule mhdl     = this.getMultiHdlRule(hdldoc);
    IPredicateRule shdl     = this.getSingleHdlRule(hdldoc);
    IPredicateRule scomment = this.getSingleLineCommentRule(comment);
    //IPredicateRule mcomment = this.getMultiLineCommentRule(comment);
    IPredicateRule str = this.getStringRule(string);
    IPredicateRule str2 = this.getStringRule2(string);
    IPredicateRule defineRule = this.getDefinedRule(define);
    IRule numb = this.getNumberRule(number);
    IRule numb2  = this.getAltNumberRule(number);
    
    List rules= new ArrayList();
    rules.add(numb);
    //if (mhdl != null) rules.add(mhdl);
    if (shdl != null) rules.add(shdl);
    if (scomment != null) rules.add(scomment);
    //if (mcomment != null) rules.add(mcomment);
    if (str != null) rules.add(str);
    if (str2 != null) rules.add(str2);
    if (defineRule != null) rules.add(defineRule);
    if (numb2 != null) rules.add(numb2);
    
    
    // Add word rule for keywords.
    WordRule wordRule = this.createWordRule(other);
    String[] fgKeywords = this.getKeywords();
    for (int i= 0; i < fgKeywords.length; i++)
        wordRule.addWord(fgKeywords[i], keyword);
    //rules.add(wordRule);
    // Add word rule for defines.
    //wordRule = this.createWordRule(other);
    fgKeywords = this.getTickWords();
    for (int i= 0; i < fgKeywords.length; i++)
        wordRule.addWord(fgKeywords[i], define);
    //rules.add(wordRule);
    // Add word rule for defines.
    //wordRule = this.createWordRule(other);
    fgKeywords = this.getSpecialWords();
    for (int i= 0; i < fgKeywords.length; i++)
        wordRule.addWord(fgKeywords[i], type);
    rules.add(wordRule);
    
    rules.add(new PunctuationRule(punct,punc));
        
   
    /*
    WordRule punctRule = SourcePunctuationDetector.createWordRule(punc);
    rules.add(punctRule);
    */

    IRule[] result= new IRule[rules.size()];
    rules.toArray(result);
    setRules(result);

    }

    public WordRule createWordRule(IToken other) {
    	WordRule wordRule = new WordRule(new SourceWordDetector(), other);
    	return wordRule;
    }
    
}
