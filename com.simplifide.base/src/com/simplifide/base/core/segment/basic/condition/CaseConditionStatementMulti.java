/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.condition;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;

public class CaseConditionStatementMulti extends CaseConditionStatement{

	private ReferenceItem<SequenceStatements> stateRef;
	public CaseConditionStatementMulti(String name) {
		super(name);
	}
	
	public CaseConditionStatementMulti(String name, 
    		ReferenceItem<CaseChoices> choiceRef,
    		ReferenceItem<SequenceStatements> stateRef) {
    	super(name,choiceRef);
    	this.stateRef = stateRef;
    }
	
	public ReferenceItem<ModuleObjectWithList> getOutputs() {
        if (stateRef != null) {
        	return this.stateRef.getOutputs();
        }
        return new ModuleObjectWithList("").createReferenceItem();
    }
	
	public ReferenceItem<ModuleObjectWithList> getDependants() {
        ReferenceItem<ModuleObjectWithList> outList = super.getDependants();
       
        if (this.stateRef != null) {
            ReferenceItem<ModuleObjectWithList> stList = this.stateRef.getDependants();
            outList.getObject().addAll(stList.getObject());
        }
        return outList;
    }
	
	
	public void setStateRef(ReferenceItem<SequenceStatements> stateRef) {
        this.stateRef = stateRef;
    }
    public ReferenceItem<SequenceStatements> getStateRef() {
        return stateRef;
    }
}
