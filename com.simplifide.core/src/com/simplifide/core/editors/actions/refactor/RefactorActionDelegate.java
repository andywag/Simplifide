package com.simplifide.core.editors.actions.refactor;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;

public abstract class RefactorActionDelegate extends EditorActionDelegate{

	public RefactorActionDelegate() {}
	public RefactorActionDelegate(SourceEditor editor) {super(editor);}
	
	public boolean isEnabled() {
		boolean enabled = HardwareChecker.isRefactoringEnabled();
		return this.getAction().isEnabled() && enabled;
	}
	
}
