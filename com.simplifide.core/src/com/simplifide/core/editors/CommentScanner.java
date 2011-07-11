package com.simplifide.core.editors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class CommentScanner extends RuleBasedScanner {

	public CommentScanner(ColorManager provider) {
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
	    RGB color_script = getColor();
	    Color col = ColorManager.getInstance().getColor(color_script);
	    IToken tok= new Token(new TextAttribute(col,null,0));
	    this.setDefaultReturnToken(tok);
	}
	
	abstract public RGB getColor();
	
	public static class Comment extends CommentScanner {
		public Comment(ColorManager provider) {
			super(provider);
		}

		@Override
		public RGB getColor() {
			IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
		    RGB color_script = PreferenceConverter.getColor(store, 
		    		PreferenceConstants.COLOR_COMMENT);
		    return color_script;
		}
	}
	
	public static class Doc extends CommentScanner{
		public Doc(ColorManager provider) {
			super(provider);
		}

		@Override
		public RGB getColor() {
			IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
		    RGB color_script = PreferenceConverter.getColor(store, 
		    		PreferenceConstants.COLOR_HDL_DOC);
		    return color_script;
		}
	}
	
}
