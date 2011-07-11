package com.simplifide.core.actions.emacs;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class VhdlBeautifyActionDelegate extends EmacsActionDelegate{

	public VhdlBeautifyActionDelegate() {}
	public VhdlBeautifyActionDelegate(SourceEditor editor) {super(editor);}
	@Override
	public String getCommand() {
		return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_VHDL_BEAUTY);
	}
	
	public static class Handler extends GeneralHandler {
		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new VhdlBeautifyActionDelegate((SourceEditor)editor);
		}
	}
	
	public static class VhdlCustom1 extends EmacsActionDelegate {
		public VhdlCustom1() {}
		public VhdlCustom1(SourceEditor editor) {super(editor);}
		@Override
		public String getCommand() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_CUSTOM1);
		}
	}
	
	public static class Custom1Handler extends GeneralHandler {
		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new VhdlBeautifyActionDelegate.VhdlCustom1((SourceEditor)editor);
		}
	}
	
	public static class VhdlCustom2 extends EmacsActionDelegate {
		public VhdlCustom2() {}
		public VhdlCustom2(SourceEditor editor) {super(editor);}
		@Override
		public String getCommand() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_CUSTOM2);
		}
	}
	
	public static class Custom2Handler extends GeneralHandler {
		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new VhdlBeautifyActionDelegate.VhdlCustom1((SourceEditor)editor);
		}
	}
	

}
