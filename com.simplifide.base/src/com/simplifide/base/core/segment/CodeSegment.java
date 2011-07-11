/*
 * CodeSegment.java
 *
 * Created on December 6, 2005, 5:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author Andy Wagner
 */
public class CodeSegment<T extends ModuleObject> extends ModuleObjectNew<T> implements CodeSegmentInt {
    private ArrayList<CodeSegInputs> inputList = new ArrayList<CodeSegInputs>();
    /** Creates a new instance of CodeSegment */
    public CodeSegment() {}
    /** Constructor for CodeSegment 
     * @param name 
     */
    public CodeSegment(String name) {
        super(name);
    }
    
    
    public TypeVar getType() {return null;}
    public Integer getNumericValue() {return null;}
    
    public CodeSegInputs addCodeSegInput(CodeSegInputs inval) {
        this.inputList.add(inval);
        return inval;
    }
    
    public ArrayList<CodeSegInputs> getInputList() {
        return inputList;
    }
    
    public void setInputList(ArrayList<CodeSegInputs> inputList) {
        this.inputList = inputList;
    }
    
   
    
    
    
    
}
