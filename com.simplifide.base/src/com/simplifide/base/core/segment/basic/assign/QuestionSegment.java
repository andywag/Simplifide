/*
 * SignalAssignment.java
 *
 * Created on December 22, 2005, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.assign;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.CodeSegment;

/**
 *
 * @author awagner
 */
public class QuestionSegment extends CodeSegment {
  
    
    private ReferenceItem trueRef; 
    private ReferenceItem falseRef;
    private ReferenceItem condRef;
    
    /** Creates a new instance of SignalAssignment */
    public QuestionSegment() {}
    
    public QuestionSegment(String name, ReferenceItem cond, ReferenceItem true1, ReferenceItem false1) {
        super(name);
        this.condRef = cond;
        this.trueRef = true1;
        this.falseRef = false1;
    }

    private ReferenceItem<ModuleObjectWithList> getDepList(ReferenceItem item) {
        if (item != null) {
            return item.getDependants();
        }
        return new ModuleObjectWithList("Empty").createReferenceItem();
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        ModuleObjectWithList nlist = new ModuleObjectWithList("DepList");
        nlist.addAll(this.getDepList(trueRef).getObject());
        nlist.addAll(this.getDepList(falseRef).getObject());
        nlist.addAll(this.getDepList(condRef).getObject());
        return nlist.createReferenceItem();
    }
   
    
     public ReferenceItem getTrueRef() {
        return trueRef;
    }

    public void setTrueRef(ReferenceItem trueRef) {
        this.trueRef = trueRef;
    }

    public ReferenceItem getFalseRef() {
        return falseRef;
    }

    public void setFalseRef(ReferenceItem falseRef) {
        this.falseRef = falseRef;
    }

    public ReferenceItem getCondRef() {
        return condRef;
    }

    public void setCondRef(ReferenceItem condRef) {
        this.condRef = condRef;
    }
    
    
    
    
}
