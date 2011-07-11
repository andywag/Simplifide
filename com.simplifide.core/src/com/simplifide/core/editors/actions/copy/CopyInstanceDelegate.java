package com.simplifide.core.editors.actions.copy;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;

public class CopyInstanceDelegate extends EditorActionDelegate{

	
	private InstanceModule imod;
	public CopyInstanceDelegate() {}
	public CopyInstanceDelegate(SourceEditor editor) {
		super(editor);
	}
	
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		int cpos = getEditor().getCaretPosition();
		ParseDescriptor desc = getEditor().getDesignFile().getParseDescriptor();
		imod = EditorUtilities.getInstanceModule(desc, cpos);
		
		if (imod != null) return true;
		return false;
	}

	@Override
	public void run(GeneralEditor editor) {
		InstanceModuleHolder.getInstance().setInstanceModule(imod);
	}

	public static class CopyHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new CopyInstanceDelegate((SourceEditor)editor);
		}
		
	}
	
}
