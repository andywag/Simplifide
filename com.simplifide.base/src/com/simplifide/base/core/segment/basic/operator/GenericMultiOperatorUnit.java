/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.operator;

import com.simplifide.base.core.reference.ReferenceItem;

public class GenericMultiOperatorUnit extends MultiOperatorUnit{

	private String op;
	
	public GenericMultiOperatorUnit(String op, ReferenceItem base) {
		super(base);
		this.op = op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}
}
