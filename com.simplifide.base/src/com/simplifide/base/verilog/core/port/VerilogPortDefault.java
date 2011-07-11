/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.core.port;

import java.util.List;

import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.SystemVar;


public class VerilogPortDefault extends PortDefault{

	
	private ReferenceItem<SystemVar> declarationRef;
	private ReferenceItem<SystemVar> secondRef;
	
	public VerilogPortDefault(ReferenceItem<SystemVar> localVarReference) {
		super(localVarReference);
	}
	
	public List<ReferenceItem> getRenameItemList(ReferenceItem item,ReferenceItem enclosingItem) {
		List<ReferenceItem> refs = super.getRenameItemList(item, enclosingItem);
		if (declarationRef != null) refs.add(declarationRef);
		if (secondRef != null) refs.add(secondRef);
		return refs;
	}

	public void setDeclarationRef(ReferenceItem<SystemVar> declarationRef) {
		this.declarationRef = declarationRef;
	}

	public ReferenceItem<SystemVar> getDeclarationRef() {
		return declarationRef;
	}

	public void setSecondRef(ReferenceItem<SystemVar> secondRef) {
		this.secondRef = secondRef;
	}

	public ReferenceItem<SystemVar> getSecondRef() {
		return secondRef;
	}
	
	

	
	
}
