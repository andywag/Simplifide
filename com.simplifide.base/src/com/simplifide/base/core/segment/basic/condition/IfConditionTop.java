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
 * @param T 
 * @author Andy Wagner
 */
public class IfConditionTop<T extends IfConditionStatement> extends CodeSegment<T>
{
    
    /** Creates a new instance of Process */
    public IfConditionTop() {}
    /** Create a new Instance of If Statement 
     * @param name 
     */
    public IfConditionTop(String name)
    {
        super(name);
    }

    
    
    public ReferenceItem getOutputs() {
        ModuleObjectWithList list = new ModuleObjectWithList("Outputs");
        for (ReferenceItem<? extends T> st : this.getGenericSelfList()) {
            list.addAll(st.getOutputs().getObject());
        }
        return list.createReferenceItem();
    }

    public ReferenceItem getDependants() {
        ModuleObjectWithList list = new ModuleObjectWithList("Outputs");
        for (ReferenceItem<? extends T> st : this.getGenericSelfList()) {
            list.addAll(st.getDependants().getObject());
        }
        return list.createReferenceItem();
    }

    


    
}
