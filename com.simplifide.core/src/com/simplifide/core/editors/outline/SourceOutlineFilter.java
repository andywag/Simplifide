package com.simplifide.core.editors.outline;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class SourceOutlineFilter extends ViewerFilter{

	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (false || element instanceof ReferenceItem) {
			ReferenceItem ref = (ReferenceItem) element;
			if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
				if (OutlineFilterMenu.getInstance().isVariableChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_PORT_TOP) == 0) {
				if (OutlineFilterMenu.getInstance().isPortChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_TYPEVAR) == 0) {
				if (OutlineFilterMenu.getInstance().isTypeChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_FUNCTION_HOLDER) == 0) {
				if (OutlineFilterMenu.getInstance().isFunctionChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_GENERATE_STATEMENT) == 0) {
				if (OutlineFilterMenu.getInstance().isGenerateChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_ASSIGN) == 0) {
				if (OutlineFilterMenu.getInstance().isAssignChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_PROCESS_STATEMENT) == 0) {
				if (OutlineFilterMenu.getInstance().isProcessChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_COMPONENT) == 0) {
				if (OutlineFilterMenu.getInstance().isComponentChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_PROPERTY) == 0) {
				if (OutlineFilterMenu.getInstance().isPropertyChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_SEQUENCE) == 0) {
				if (OutlineFilterMenu.getInstance().isSequenceChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_CLOCKING) == 0) {
				if (OutlineFilterMenu.getInstance().isClockingChecked()) return false;
			}
			else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_ASSERTION) == 0) {
				if (OutlineFilterMenu.getInstance().isAssertionChecked()) return false;
			}
		}
		return true;
	}

}
