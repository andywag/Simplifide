/*
 * Process.java
 *
 * Created on December 6, 2005, 5:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.condition;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.CodeSegment;

/**
 *
 * @author Andy Wagner
 */
public class CaseConditionStatement extends CodeSegment
{

    private ReferenceItem<CaseChoices> choicesRef;
    
   

    
    
    /** Creates a new instance of Process */
    public CaseConditionStatement() {}
    public CaseConditionStatement(String name) {
        super(name);  
    }
    
    public CaseConditionStatement(String name, ReferenceItem<CaseChoices> choiceRef) {
    	super(name);
    	this.setChoicesRef(choiceRef);
    	
    }
    
    public boolean canFold() {
        return true;
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        ModuleObjectWithList outList = new ModuleObjectWithList("Outputs");
        if (this.getChoicesRef() != null) {
            ReferenceItem<ModuleObjectWithList> chList = this.getChoicesRef().getDependants();
            outList.addAll(chList.getObject());
        }
        
        return outList.createReferenceItem();
    }
	public void setChoicesRef(ReferenceItem<CaseChoices> choicesRef) {
		this.choicesRef = choicesRef;
	}
	public ReferenceItem<CaseChoices> getChoicesRef() {
		return choicesRef;
	}
    
    
    

}
