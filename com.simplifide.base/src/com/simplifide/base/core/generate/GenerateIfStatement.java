/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.generate;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.reference.ReferenceItem;

public class GenerateIfStatement extends GenerateStatement{

	
	private ReferenceItem<ModuleObject> conditionRef;
	
	private ReferenceItem<Statement> trueRef;
	private ReferenceItem<Statement> falseRef;
	
	
	public GenerateIfStatement(String name, ReferenceItem<ModuleObject> conditionRef) {
		super(name);
		this.conditionRef = conditionRef;
	}

	public String getDisplayName() {
		String prefix = "";
		if (this.getBlockName() != null) prefix = this.getBlockName() + " : ";
		//if (this.conditionRef != null) return " " + prefix + " if " + this.conditionRef.getDisplayName();
		return " " + prefix + " if "  + this.getBlockText();
	}
	
	public boolean hasNavigatorChildren() {
		if (super.hasNavigatorChildren()) return true;
		if (trueRef != null) return true;
		return false;
	}
	
	public List<ReferenceItem> getNavigatorList() {
		if (trueRef == null) return super.getNavigatorList();
		if (falseRef == null) return trueRef.getNavigatorList();
		else {
			ArrayList<ReferenceItem> refs = new ArrayList<ReferenceItem>();
			refs.add(trueRef);
			refs.add(falseRef);
			return refs;
		}
	}
	
	public void setConditionRef(ReferenceItem<ModuleObject> conditionRef) {
		this.conditionRef = conditionRef;
	}

	public ReferenceItem<ModuleObject> getConditionRef() {
		return conditionRef;
	}

	

	public void setTrueRef(ReferenceItem<Statement> trueRef) {
		this.trueRef = trueRef;
	}

	public ReferenceItem<Statement> getTrueRef() {
		return trueRef;
	}

	public void setFalseRef(ReferenceItem<Statement> falseRef) {
		this.falseRef = falseRef;
	}

	public ReferenceItem<Statement> getFalseRef() {
		return falseRef;
	}

	

	public static class Statement extends GenerateStatement {
		public Statement(String name) {
			super(name);
		}
	}
}
