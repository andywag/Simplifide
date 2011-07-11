/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.hyperlink.DefaultHyperlinkPresenter;
import org.eclipse.swt.graphics.RGB;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.color.SourceColorConstants;

public class SourcePreferenceInitializer extends AbstractPreferenceInitializer {

	public static boolean LOADED = true;
	
	public SourcePreferenceInitializer() {

	}

	public void initializeDefaultPreferences() {
		if (LOADED) {
			try {
				this.initializeDefaultPreferencesInternal();
			}
			catch (Exception e) {
				HardwareLog.logError(e);
			}
		}	
	}
	
	public static void latePreferenceLoader() {
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();

		
PreferenceConverter.setDefault(store, PreferenceConstants.COLOR_DEFAULT,
		SourceColorConstants.DEFAULT);

PreferenceConverter.setDefault(store, PreferenceConstants.COLOR_COMMENT,
		SourceColorConstants.COMMENT);

PreferenceConverter.setDefault(store, PreferenceConstants.COLOR_KEY,
		SourceColorConstants.KEY);

PreferenceConverter.setDefault(store,PreferenceConstants.COLOR_HDL_DOC,
		SourceColorConstants.HDLDOC);

PreferenceConverter.setDefault(store, PreferenceConstants.COLOR_STRING,
		SourceColorConstants.STRING);

PreferenceConverter.setDefault(store, PreferenceConstants.COLOR_NUMBER,
		SourceColorConstants.NUMBER);

PreferenceConverter.setDefault(store, PreferenceConstants.COLOR_PUNCTUATION,
		SourceColorConstants.PUNCTUATION);

PreferenceConverter.setDefault(store,PreferenceConstants.COLOR_SCRIPT, 
		SourceColorConstants.SCRIPT);

PreferenceConverter.setDefault(store,PreferenceConstants.COLOR_GENERATE, 
		SourceColorConstants.GENERATE);

PreferenceConverter
		.setDefault(store, PreferenceConstants.COMPLETE_FOREGROUND,
				new RGB(254, 241, 233));
PreferenceConverter
		.setDefault(store, PreferenceConstants.COMPLETE_BACKGROUND,
				new RGB(128, 128, 128));
				
PreferenceConverter.setDefault(store, PreferenceConstants.EDITOR_MARK_COLOR,
		new RGB(86, 84, 107));
PreferenceConverter.setDefault(store, PreferenceConstants.EDITOR_PAREN_COLOR,
		new RGB(86, 84, 107));
PreferenceConverter.setDefault(store,DefaultHyperlinkPresenter.HYPERLINK_COLOR, SourceColorConstants.KEY);

	}
	
	
	private void initializeDefaultPreferencesInternal() {
		IPreferenceStore store = CoreActivator.getDefault()
				.getPreferenceStore();
		store.setDefault(PreferenceConstants.ERROR_SYNTAX + ".vhdl", true);
		store
				.setDefault(PreferenceConstants.ERROR_NOT_DEFINED + ".vhdl",
						false);
		store.setDefault(PreferenceConstants.ERROR_TYPE_MISMATCH + ".vhdl",
				false);
		store.setDefault(PreferenceConstants.WARNING_LATCH + ".vhdl", false);
		store.setDefault(PreferenceConstants.WARNING_NOT_ASSIGNED + ".vhdl",
				false);
		store.setDefault(PreferenceConstants.WARNING_NOT_USED + ".vhdl", false);

		store.setDefault(PreferenceConstants.ERROR_SYNTAX + ".verilog", true);
		store.setDefault(PreferenceConstants.ERROR_NOT_DEFINED + ".verilog",
				false);
		store.setDefault(PreferenceConstants.ERROR_TYPE_MISMATCH + ".verilog",
				false);
		store.setDefault(PreferenceConstants.WARNING_LATCH + ".verilog", false);
		store.setDefault(PreferenceConstants.WARNING_NOT_ASSIGNED + ".verilog",
				false);
		store.setDefault(PreferenceConstants.WARNING_NOT_USED + ".verilog",
				false);

		// Editor Section

		store.setDefault(PreferenceConstants.INDENT_ENABLE, true);
		store.setDefault(PreferenceConstants.INDENT_LENGTH, 4);
		store.setDefault(PreferenceConstants.INDENT_TAB, false);

		/*
		
		*/

		store.setDefault(PreferenceConstants.BOLD_KEY, true);
		store.setDefault(PreferenceConstants.ITALIC_COMMENT, true);
		store.setDefault(PreferenceConstants.ITALIC_HDL_DOC, true);

		// Completion Section
		store.setDefault(PreferenceConstants.COMPLETE_AUTO_COMMA, true);
		store.setDefault(PreferenceConstants.COMPLETE_AUTO_ACTIVATION, true);
		store.setDefault(PreferenceConstants.COMPLETE_AUTO_INSERT, true);
		store.setDefault(PreferenceConstants.COMPLETE_AUTO_TIME, 300);

		store.setDefault(PreferenceConstants.HOVER_ENABLED, true);
		store.setDefault(PreferenceConstants.HOVER_HTML_ENABLED, true);
		store.setDefault(PreferenceConstants.HOVER_NATURAL, true);
		
		store.setDefault(PreferenceConstants.LICENSE_STRING,
				"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		store.setDefault(PreferenceConstants.BUILD_ENABLED, true);

		store.setDefault(PreferenceConstants.SUITE_DIR_BUILD, true);
		store.setDefault(PreferenceConstants.SUITE_DIR_DOC, false);
		store.setDefault(PreferenceConstants.SUITE_DIR_ROUTE, false);
		store.setDefault(PreferenceConstants.SUITE_DIR_SCRIPT, true);
		store.setDefault(PreferenceConstants.SUITE_DIR_SYNTHESIS, false);
		store.setDefault(PreferenceConstants.SUITE_DIR_TEST, true);

		store.setDefault(PreferenceConstants.PROJECT_DIR_BUILD, true);
		store.setDefault(PreferenceConstants.PROJECT_DIR_DOC, false);
		store.setDefault(PreferenceConstants.PROJECT_DIR_ROUTE, false);
		store.setDefault(PreferenceConstants.PROJECT_DIR_SCRIPT, true);
		store.setDefault(PreferenceConstants.PROJECT_DIR_SYNTHESIS, false);
		store.setDefault(PreferenceConstants.PROJECT_DIR_TEST, true);
		
		store.setDefault(PreferenceConstants.LIBRARY_DIR_BUILD, false);
		store.setDefault(PreferenceConstants.LIBRARY_DIR_DOC, false);
		store.setDefault(PreferenceConstants.LIBRARY_DIR_ROUTE, false);
		store.setDefault(PreferenceConstants.LIBRARY_DIR_SCRIPT, false);
		store.setDefault(PreferenceConstants.LIBRARY_DIR_SYNTHESIS, false);
		store.setDefault(PreferenceConstants.LIBRARY_DIR_TEST, false);
		
		store.setDefault(PreferenceConstants.SCRIPT_RELEASE_PATH, true);
		store.setDefault(PreferenceConstants.SCRIPT_RELEASE_PATH, "");
		store.setDefault(PreferenceConstants.SCRIPT_EXTRA_PATH1, "");
		store.setDefault(PreferenceConstants.SCRIPT_EXTRA_PATH2, "");
		
		store.setDefault(PreferenceConstants.FILE_HEADER, "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		store.setDefault(PreferenceConstants.MAXIMUM_FILE_LENGTH, 500000);
		store.setDefault(PreferenceConstants.EDITOR_MARK_OCCURENCE, true);
		store.setDefault(PreferenceConstants.EDITOR_PAREN_MATCH, true);
		
		store.setDefault(PreferenceConstants.UPDATE_DISABLE, false);
		
		store.setDefault(PreferenceConstants.VHDL_CAP_KEYWORDS, false);
		
		store.setDefault(PreferenceConstants.VHDL_ENABLE, true);
		store.setDefault(PreferenceConstants.VERILOG_ENABLE, true);
		store.setDefault(PreferenceConstants.SYSTEM_VERILOG_ENABLE, true);
		
		//store.setDefault(DefaultHyperlinkPresenter.HYPERLINK_COLOR_SYSTEM_DEFAULT, false);
		//store.setDefault(PreferenceConstants.CONFIG_DIRECTORY, "");
	
		store.setDefault(PreferenceConstants.SCRIPT_PATH_NAME, "Paths.Main");
		store.setDefault(PreferenceConstants.SCRIPT_STARTUP_NAME, "Start.Base");
		store.setDefault(PreferenceConstants.EDITOR_ENABLE_AUTO_EDITS, true);

		store.setDefault(PreferenceConstants.EMACS_PREFIX, "emacs --batch ");
		store.setDefault(PreferenceConstants.EMACS_VHDL_BEAUTY, " -f vhdl-beautify-buffer -f save-buffer");
		store.setDefault(PreferenceConstants.EMACS_VERILOG_AUTO, " -f verilog-auto -f save-buffer");
		store.setDefault(PreferenceConstants.EMACS_VERILOG_INJECT_AUTO, " -f verilog-inject-auto -f save-buffer");
		store.setDefault(PreferenceConstants.EMACS_VERILOG_DELETE_AUTO, " -f verilog-delete-auto -f save-buffer");
		store.setDefault(PreferenceConstants.EMACS_VERILOG_INDENT, " -f verilog-batch-indent -f save-buffer");

		store.setDefault(PreferenceConstants.FORMAT_INDENT_NORMAL, 3);
		store.setDefault(PreferenceConstants.FORMAT_INDENT_MODULE, 8);
		
	}

}
