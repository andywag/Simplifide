/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.generate;

import java.util.List;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.vhdl.project.VhdlStandardTypes;

public class GenerateForStatement extends GenerateStatement{

	private ReferenceItem<ParameterSpecification> paramRef;
	
	public GenerateForStatement(String name, ReferenceItem<ParameterSpecification> paramRef) {
		super(name);
		this.setParamRef(paramRef);
	}

	public String getDisplayName() {
		
		String prefix = this.getBlockName();
		if (prefix == null) return this.getBlockText();
		else return this.getBlockName() + " : " + this.getBlockText();
		
	}
	
	public boolean hasNavigatorChildren() {
		return true;
	}
	
	public List<ReferenceItem> getNavigatorList() {
		ReferenceItem ref = this.getObject(0);
		return ref.getObject().getNavigatorList();
	}
	
	/** Returns addition objects which should be used for searching */
	public ReferenceItem getAdditionalItems() {
		SystemVar tvar = new SystemVar(paramRef.getname(),VhdlStandardTypes.REF_TYPE_INTEGER,
				new OperatingTypeVar.SignalVar());
		return tvar.createReferenceItem();
	}
	
	public void setParamRef(ReferenceItem<ParameterSpecification> paramRef) {
		this.paramRef = paramRef;
	}

	public ReferenceItem<ParameterSpecification> getParamRef() {
		return paramRef;
	}

	
	
}
