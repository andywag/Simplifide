package com.simplifide.core.scripteditor;

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourceScanner;
import com.simplifide.core.scalaext.ScalaStartup;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.scala2.top.InterfaceCommands;

public class ScriptScanner extends SourceScanner{

	public static String[] KEYWORDS = new String[] {"simplifide","state_machine","state","transition_if",
		"transition_case","end_simplifide"};
	
	
	public ScriptScanner(ColorManager provider) {
		super(provider);
		init();
		
	}
	
	private void init() {
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
	    RGB color_script = PreferenceConverter.getColor(store, 
	    		PreferenceConstants.COLOR_SCRIPT);
	    Color col = ColorManager.getInstance().getColor(color_script);
	    IToken tok= new Token(new TextAttribute(col,null,0));
	    this.setDefaultReturnToken(tok);
	    
	}

	@Override
	protected IRule getAltNumberRule(IToken tok) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected IPredicateRule getSingleLineCommentRule(IToken tok) {
		return new EndOfLineRule("//", tok);
	}

	@Override
	protected IPredicateRule getStringRule(IToken tok) {
		return new SingleLineRule("\"", "\"", tok, '\\');
	}

	@Override
	protected IPredicateRule getStringRule2(IToken tok) {
		return new SingleLineRule("'", "'", tok, '\\');
	}

	

	@Override
	protected String[] getKeywords() {
		List<String> keys = ScalaStartup.getJavaKeyWords();
		return keys.toArray(new String[keys.size()]);
		
		//return BaseCommands.getJavaKeywords();
	}
	
	@Override
	protected IPredicateRule getDefinedRule(IToken tok) {
		return null;
	}

	

	@Override
	public IPredicateRule getSingleHdlRule(IToken tok) {
		return null;
	}

	

}
