/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.generate;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.vhdl.project.VhdlStandardTypes;

public class ParameterSpecification extends ModuleObjectNew{

	private ReferenceItem<VarRange> rangeReference;
	private ReferenceItem<SystemVar> varRef;
	
	public ParameterSpecification(String name, ReferenceItem<VarRange> rangeRef) {
		super(name);
		this.setRangeReference(rangeRef);
	}
	
	 /** Method which returns the type of this object. Used for creating type mismatch errors */
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        return VhdlStandardTypes.REF_TYPE_INTEGER;
    }
	
	

	/** Find a Reference Item based on the find item and type */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        return null;
    }
	
	public void setRangeReference(ReferenceItem<VarRange> rangeReference) {
		this.rangeReference = rangeReference;
	}

	public ReferenceItem<VarRange> getRangeReference() {
		return rangeReference;
	}
	
}
