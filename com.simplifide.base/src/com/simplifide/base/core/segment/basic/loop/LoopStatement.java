/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.loop;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;

public class LoopStatement extends ModuleObjectNew{

	private ReferenceItem iteratorRef;
	private ReferenceItem<SequenceStatements> seqRef;
	
	public LoopStatement(String name) {}
	public LoopStatement(ReferenceItem itRef, ReferenceItem<SequenceStatements> seqRef) {
		this.iteratorRef = itRef;
		this.seqRef = seqRef;
	}
	
	public ReferenceItem<ModuleObjectWithList> getOutputs() {
		return seqRef.getOutputs();
	}
	
	public ReferenceItem<ModuleObjectWithList> getDependants() {
		ReferenceItem<ModuleObjectWithList> list = iteratorRef.getDependants();
		ReferenceItem<ModuleObjectWithList> nlist = seqRef.getDependants();
		list.getObject().addAll(nlist.getObject());
		return list;
	}
	
	public void setIteratorRef(ReferenceItem iteratorRef) {
		this.iteratorRef = iteratorRef;
	}

	public ReferenceItem getIteratorRef() {
		return iteratorRef;
	}

	private void setSeqRef(ReferenceItem<SequenceStatements> seqRef) {
		this.seqRef = seqRef;
	}

	private ReferenceItem<SequenceStatements> getSeqRef() {
		return seqRef;
	}
	
}
