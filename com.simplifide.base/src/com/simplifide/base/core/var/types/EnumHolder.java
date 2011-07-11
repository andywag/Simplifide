/*
 * FunctionHolder.java
 *
 * Created on March 6, 2007, 5:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.var.types;

import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectComparator;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;

/**
 *
 * @author awagner
 *
 * This class will hold a set of variables of different types which will allow searching 
 * for enumerations in context
 */
public class EnumHolder<T extends SystemVar> extends ModuleObjectWithList<T> {
    
    private static SystemVarTypeComparator COMPARATOR = new SystemVarTypeComparator();
    
    /** Creates a new instance of FunctionHolder */
    public EnumHolder(String name) {super(name);}
    
    protected ModuleObjectComparator getComparator() {
        return COMPARATOR;
    }
    
    public ModuleObject getBaseSearchClass() {
    	if (this.getObject(0) != null) return this.getObject(0).getObject();
    	return null;
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_ENUM_HOLDER;
    }
    
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item)
    {
        return (List) this.getGenericSelfList();
    }
    
  

   
    private static class SystemVarTypeComparator<T extends SystemVar> implements ModuleObjectComparator<ReferenceItem<T>> {
        
        /** Probably not the correct thing to do, but this operation shouldn't matter */
        public int comparePrefix(ReferenceItem<T> o1, ReferenceItem<T> o2) {
            return this.compare(o1,o2);
        }

        public int compare(ReferenceItem<T> o1, ReferenceItem<T> o2) {
            if (o1.getObject() == null || o2.getObject() == null) return 1;
            ReferenceItem<? extends TypeVar> t1 = o1.getObject().getTypeReference();
            ReferenceItem<? extends TypeVar> t2 = o2.getObject().getTypeReference();
        
            return TypeComparator.getInstance().compare(t1,t2);
        }
       
        
    }
  
    
    
    
}
