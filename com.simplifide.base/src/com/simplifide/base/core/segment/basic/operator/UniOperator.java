/*
 * UniOperator.java
 *
 * Created on December 21, 2005, 2:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.operator;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author awagner
 */
public class UniOperator extends TopOperator{
    

    private ReferenceItem item;
     /** Creates a new instance of MultiOperator */
    public UniOperator() {}
    public UniOperator(String name) {super(name);}
    public UniOperator(String name, ReferenceItem codeseg){
        super(name);
        this.setReference(codeseg);
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
    	if (item != null) {
    		if (this.equals(item.getObject())) {
    			return null;
    		}
    		return item.getDependants();
    	}
        return new ModuleObjectWithList("").createReferenceItem();
    }
    

    public ReferenceItem<TypeVar> getTypeReference() {
    	
        if (item != null) {
        	if (this.equals(item.getObject())) return null;
        	return (ReferenceItem<TypeVar>)item.getTypeReference();
        }
        return null;
    }

    
	public void setItem(ReferenceItem reference) {
		this.item = reference;
	}
	public ReferenceItem getItem() {
		return item;
	}

    


   

    
   
}
