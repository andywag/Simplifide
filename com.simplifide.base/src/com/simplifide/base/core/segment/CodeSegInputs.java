/*
 * CodeSegInputs.java
 *
 * Created on December 23, 2005, 10:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment;

import com.simplifide.base.basic.struct.ModuleObject;

/**
 *
 * @author awagner
 */
public class CodeSegInputs extends ModuleObject
{
    private CodeSegmentInt segment; // Should remove this operation
    private ModuleObject reference;
    /** Creates a new instance of CodeSegInputs */
    public CodeSegInputs() {}
    public CodeSegInputs(String name)
    {
         super(name);
    }
    
    
   
    

    public CodeSegmentInt getSegment() {
        return segment;
    }

    public void setSegment(CodeSegmentInt segment) {
        this.segment = segment;
    }

    public ModuleObject getReference() {
        return reference;
    }

    public void setReference(ModuleObject reference) {
        this.reference = reference;
    }
    
}
