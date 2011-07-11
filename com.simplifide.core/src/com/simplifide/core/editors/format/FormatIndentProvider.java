package com.simplifide.core.editors.format;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import com.simplifide.base.sourcefile.antlr.FormatSupportInterface;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class FormatIndentProvider implements FormatSupportInterface{

	private static FormatIndentProvider instance;
	private FormatIndentProvider provider;
	
	public static FormatIndentProvider getInstance() {
		if (instance == null) instance = new FormatIndentProvider();
		return instance;
	}
	
	public int getIndent() {
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
	    int len = EditorsUI.getPreferenceStore().getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
		return len;
	}
	
	public int getModuleIndent() {
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
		return store.getInt(PreferenceConstants.FORMAT_INDENT_MODULE);
	}
	
}
