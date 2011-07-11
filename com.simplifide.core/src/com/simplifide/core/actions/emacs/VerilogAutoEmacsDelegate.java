package com.simplifide.core.actions.emacs;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class VerilogAutoEmacsDelegate extends EmacsActionDelegate{

	public static class ExpandAuto extends EmacsActionDelegate {
		public ExpandAuto() {}
		public ExpandAuto(SourceEditor editor) {super(editor);}
		
		public String getCommand() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_VERILOG_AUTO);
		}
	}
	
	public static class AutoHandler extends GeneralHandler {
		public AutoHandler() {}
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new ExpandAuto((SourceEditor)editor);
		}
		
	}
	
	public static class InjectAuto extends EmacsActionDelegate {
		public InjectAuto() {}
		public InjectAuto(SourceEditor editor) {super(editor);}
		
		public String getCommand() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_VERILOG_INJECT_AUTO);

		}
	}
	
	public static class InjectHandler extends GeneralHandler {
		public InjectHandler() {}
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new InjectAuto((SourceEditor)editor);
		}
		
	}
	
	public static class DeleteAuto extends EmacsActionDelegate {
		
		public DeleteAuto() {}
		public DeleteAuto(SourceEditor editor) {super(editor);}
		
		public String getCommand() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_VERILOG_DELETE_AUTO);
		}
	}
	
	public static class DeleteHandler extends GeneralHandler {
		public DeleteHandler() {}
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new DeleteAuto((SourceEditor)editor);
		}	
	}
	

	public static class IndentAuto extends EmacsActionDelegate {
		
		public IndentAuto() {}
		public IndentAuto(SourceEditor editor) {super(editor);}
		
		public String getCommand() {
			return CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.EMACS_VERILOG_INDENT);
		}
	}
	
	public static class IndentHandler extends GeneralHandler {
		public IndentHandler() {}
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new IndentAuto((SourceEditor)editor);
		}
		
	}
	

}
