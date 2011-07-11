package com.simplifide.core.editors.actions.new1;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;

public abstract class NewActionDelegate extends EditorActionDelegate{

	public NewActionDelegate() {super();}
	public NewActionDelegate(SourceEditor editor) {super(editor);}
	
	public boolean isEnabled() {
		boolean enabled = HardwareChecker.isRefactoringEnabled();
		return this.getAction().isEnabled() && enabled;
	}
	@Override
	public boolean checkEnabled(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;
		if (!HardwareChecker.isRefactoringEnabled()) return false;
        ReferenceItem ref = this.getFindItemObject();
        if (ref == null) {
        	ParseContext context = EditorUtilities.getParseContext(editor.getDesignFile().getParseDescriptor(), editor.getCaretPosition());
        	ref = context.getRefHandler().getActiveReference();
        }
        if (ref != null && ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_HARDWARE_MODULE) == 0){
        	return true;
        }
        return false;
	}


	
	
}
