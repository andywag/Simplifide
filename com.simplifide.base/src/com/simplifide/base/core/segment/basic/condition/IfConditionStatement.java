/*
 * Process.java
 *
 * Created on December 6, 2005, 5:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.condition;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.CodeSegment;

/**
 *
 * @author Andy Wagner
 */
public class IfConditionStatement extends CodeSegment {
    
    private ReferenceItem<ModuleObject> headRef;
    private ReferenceItem<ModuleObject> bodyRef;
    
    /** Creates a new instance of Process */
    public IfConditionStatement() {}
    /**
     *
     * @param name
     */
    public IfConditionStatement(String name){
        super(name);
    }
    
    public IfConditionStatement(String name, ReferenceItem headRef, ReferenceItem bodyRef) {
    	super(name);
    	this.headRef = headRef;
    	this.bodyRef = bodyRef;
    }
    

    @Override
    public ReferenceItem getOutputs() {
        if (bodyRef != null) {
            return this.bodyRef.getOutputs();
        }
        return super.getOutputs();
    }

    @Override
    public ReferenceItem getDependants() {
        ModuleObjectWithList outList = new ModuleObjectWithList("OutList");
        if (headRef != null) {
            outList.addAll(this.headRef.getDependants().getObject());
        }
        if (bodyRef != null) {
            outList.addAll(this.bodyRef.getDependants().getObject());
        }
        return outList.createReferenceItem();
    }

    
    
    
    /**
     * @return
     */
    public ReferenceItem<ModuleObject> getHeadRef() {
        return headRef;
    }
    
    /**
     *
     * @param headRef
     */
    public void setHeadRef(ReferenceItem<ModuleObject> headRef) {
        this.headRef = headRef;
    }
    
    /**
     *
     * @return reference to if statements
     */
    public ReferenceItem<ModuleObject> getBodyRef() {
        return bodyRef;
    }
    
    /**
     *
     * @param bodyRef
     */
    public void setBodyRef(ReferenceItem<ModuleObject> bodyRef) {
        this.bodyRef = bodyRef;
    }
    
    
    
    
    
    
    
    
}
