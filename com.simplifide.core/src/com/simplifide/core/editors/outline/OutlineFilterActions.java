package com.simplifide.core.editors.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.resources.IconManager;

public class OutlineFilterActions extends Action{

	protected OutlineFilterMenu menu;
	
	public OutlineFilterActions(String name, ImageDescriptor desc,OutlineFilterMenu menu) {
		super(name,Action.AS_CHECK_BOX);
		this.menu = menu;
		this.setChecked(false);
		this.setImageDescriptor(desc);
	}
	
	public void run() {
		if (this.isChecked()) this.setChecked(true);
		else this.setChecked(false);
		this.menu.actionChanged();
		
	}
	
	public static class VariableFilter extends OutlineFilterActions {
		public VariableFilter(OutlineFilterMenu pane) {
			super("Variable",IconManager.getIcon(ReferenceUtilities.REF_SYSTEMVAR),pane);
		}
	}
	
	public static class PortFilter extends OutlineFilterActions {
		public PortFilter(OutlineFilterMenu pane) {
			super("Port",IconManager.getIcon(ReferenceUtilities.REF_PORT_TOP),pane);
		}
	}
	
	public static class TypeFilter extends OutlineFilterActions {
		public TypeFilter(OutlineFilterMenu pane) {
			super("Type",IconManager.getIcon(ReferenceUtilities.REF_TYPEVAR),pane);
		}
	}
	
	public static class FunctionFilter extends OutlineFilterActions {
		public FunctionFilter(OutlineFilterMenu pane) {
			super("Function",IconManager.getIcon(ReferenceUtilities.REF_FUNCTION),pane);
		}
	}
	
	public static class GenerateFilter extends OutlineFilterActions {
		public GenerateFilter(OutlineFilterMenu pane) {
			super("Generate",IconManager.getIcon(ReferenceUtilities.REF_GENERATE_STATEMENT),pane);
		}
	}
	
	public static class AssignmentFilter extends OutlineFilterActions {
		public AssignmentFilter(OutlineFilterMenu pane) {
			super("Assignment",IconManager.getIcon(ReferenceUtilities.REF_ASSIGN),pane);
		}
	}	
	
	public static class ProcessFilter extends OutlineFilterActions {
		public ProcessFilter(OutlineFilterMenu pane) {
			super("Process",IconManager.getIcon(ReferenceUtilities.REF_PROCESS_STATEMENT),pane);
		}
	}
	
	public static class ComponentFilter extends OutlineFilterActions {
		public ComponentFilter(OutlineFilterMenu pane) {
			super("Component",IconManager.getIcon(ReferenceUtilities.REF_COMPONENT),pane);
		}
	}
	
	public static class PropertyFilter extends OutlineFilterActions {
		public PropertyFilter(OutlineFilterMenu pane) {
			super("Property",IconManager.getIcon(ReferenceUtilities.REF_PROPERTY),pane);
		}
	}
	
	public static class SequenceFilter extends OutlineFilterActions {
		public SequenceFilter(OutlineFilterMenu pane) {
			super("Sequence",IconManager.getIcon(ReferenceUtilities.REF_SEQUENCE),pane);
		}
	}
	
	public static class ClockingFilter extends OutlineFilterActions {
		public ClockingFilter(OutlineFilterMenu pane) {
			super("Clocking",IconManager.getIcon(ReferenceUtilities.REF_CLOCKING),pane);
		}
	}
	
	public static class AssertionFilter extends OutlineFilterActions {
		public AssertionFilter(OutlineFilterMenu pane) {
			super("Assertion",IconManager.getIcon(ReferenceUtilities.REF_ASSERTION),pane);
		}
	}
	
}
