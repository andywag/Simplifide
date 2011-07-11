package com.simplifide.core.editors.outline;

import org.eclipse.jface.action.MenuManager;

public class OutlineFilterMenu extends MenuManager{

	private static OutlineFilterMenu instance;
	private SourceContentPane page;
	
	private OutlineFilterActions variableFilter;
	private OutlineFilterActions portFilter;
	private OutlineFilterActions typeFilter;
	private OutlineFilterActions functionFilter;
	private OutlineFilterActions assignFilter;
	private OutlineFilterActions processFilter;
	private OutlineFilterActions componentFilter;
	private OutlineFilterActions generateFilter;
	private OutlineFilterActions propertyFilter;
	private OutlineFilterActions sequenceFilter;
	private OutlineFilterActions clockingFilter;
	private OutlineFilterActions assertionFilter;
	
	public static OutlineFilterMenu getInstance() {
		if (instance == null) instance = new OutlineFilterMenu();
		return instance;
	}
	
	public OutlineFilterMenu() {
		super("Filter");
		this.variableFilter = new OutlineFilterActions.VariableFilter(this);
		this.portFilter = new OutlineFilterActions.PortFilter(this);
		this.typeFilter = new OutlineFilterActions.TypeFilter(this);
		this.functionFilter = new OutlineFilterActions.FunctionFilter(this); 
		this.assignFilter = new OutlineFilterActions.AssignmentFilter(this); 
		this.processFilter = new OutlineFilterActions.ProcessFilter(this);
		this.componentFilter = new OutlineFilterActions.ComponentFilter(this);
		this.generateFilter = new OutlineFilterActions.GenerateFilter(this);
		this.propertyFilter = new OutlineFilterActions.PropertyFilter(this); 
		this.sequenceFilter = new OutlineFilterActions.SequenceFilter(this);
		this.clockingFilter = new OutlineFilterActions.ClockingFilter(this);
		this.assertionFilter = new OutlineFilterActions.AssertionFilter(this);
		
		this.add(this.variableFilter);
		this.add(this.portFilter);
		this.add(this.typeFilter);
		this.add(this.functionFilter);
		this.add(this.assignFilter);
		this.add(this.processFilter);
		this.add(this.componentFilter);
		this.add(this.generateFilter);
		this.add(this.propertyFilter);
		this.add(this.sequenceFilter);
		this.add(this.clockingFilter);
		this.add(this.assertionFilter);
		
	}
	
	public void actionChanged() {
		if (page != null) {
			page.changeInput(null);
		}
	}
	
	public boolean isVariableChecked() {return this.variableFilter.isChecked();}
	public boolean isPortChecked() {return this.portFilter.isChecked();}
	public boolean isTypeChecked() {return this.typeFilter.isChecked();}
	public boolean isFunctionChecked() {return this.functionFilter.isChecked();}
	public boolean isGenerateChecked() {return this.generateFilter.isChecked();}
	public boolean isAssignChecked() {return this.assignFilter.isChecked();}
	public boolean isProcessChecked() {return this.processFilter.isChecked();}
	public boolean isComponentChecked() {return this.componentFilter.isChecked();}
	public boolean isPropertyChecked() {return this.propertyFilter.isChecked();}
	public boolean isSequenceChecked() {return this.sequenceFilter.isChecked();}
	public boolean isClockingChecked() {return this.clockingFilter.isChecked();}
	public boolean isAssertionChecked() {return this.assertionFilter.isChecked();}
	
	public void setPage(SourceContentPane page) {
		this.page = page;
	}

	public SourceContentPane getPage() {
		return page;
	}

}
