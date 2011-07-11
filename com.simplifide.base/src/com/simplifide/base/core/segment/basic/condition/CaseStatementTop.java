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
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy Wagner
 */
public class CaseStatementTop extends NoSortList<CaseConditionStatement>
{
   
    private ReferenceItem conditionRef;
    
    public CaseStatementTop() {}
    public CaseStatementTop(String name, ReferenceItem conditionRef){
        super(name);
        this.conditionRef = conditionRef;
    }
    
    public ReferenceItem<ModuleObjectWithList> getOutputs() {
        return this.getAllChildOutputs();
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        ReferenceItem<ModuleObjectWithList> outList = this.getAllChildDependents();
        if (conditionRef != null) {
            ReferenceItem<ModuleObjectWithList> condList = conditionRef.getDependants();
            outList.getObject().addAll(condList.getObject());
        }
        return outList;
    }
    
    public void setConditionRef(ReferenceItem conditionRef) {
        this.conditionRef = conditionRef;
    }
    public ReferenceItem getConditionRef() {
        return conditionRef;
    }

   


    
}
