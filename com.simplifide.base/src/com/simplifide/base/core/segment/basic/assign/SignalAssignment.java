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
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.segment.CodeSegment;

/**
 *
 * @author awagner
 */
public class SignalAssignment extends CodeSegment {
    
   
    private ReferenceItem outputRef;
    private ReferenceItem inputRef;
    
    /** Creates a new instance of SignalAssignment */
    public SignalAssignment() {}
    /**
     * Constructor for Signal Assignment
     * @param name 
     * @param in 
     * @param out 
     */
    public SignalAssignment(String name, ReferenceItem in, ReferenceItem out) {
        super(name);
        this.inputRef = in;
        this.outputRef = out;
        
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_ASSIGN;
    }

    public ReferenceItem<ModuleObjectWithList> getOutputs() {
        if (this.outputRef != null) {
            return this.outputRef.getOutputs();
        }
        return super.getOutputs();
    }

    public ReferenceItem<ModuleObjectWithList> getDependants() {
        if (this.inputRef != null) {
            return this.inputRef.getDependants();
        }
        return super.getDependants();
    }
   
     /** Getter for Output 
      * @return 
      */
     public ReferenceItem getOutputRef() {
        return outputRef;
    }
    /** Setter for Output 
     * @param outputRef 
     */
    public void setOutputRef(ReferenceItem outputRef) {
        this.outputRef = outputRef;
    }
    /** Getter for Input 
     * @return 
     */
    public ReferenceItem getInputRef() {
        return inputRef;
    }
    /** Setter for Input 
     * @param inputRef 
     */
    public void setInputRef(ReferenceItem inputRef) {
        this.inputRef = inputRef;
    }
    
    
    
    
}
