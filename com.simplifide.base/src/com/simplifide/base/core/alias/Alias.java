/*
 * Alias.java
 *
 * Created on March 28, 2007, 2:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.alias;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author Andy
 */
public class Alias<T extends ModuleObjectNew> extends ModuleObjectNew<T>{
    
    private ReferenceItem<ModuleObjectNew> delegate;
    /** Creates a new instance of Alias */
    public Alias() {}
    public Alias(String name, ReferenceItem<ModuleObjectNew> delegate) {
        super(name);
        this.delegate = delegate;
    }
    
    public int getSearchType() {
        if (delegate != null) {
            return this.delegate.getType();
        }
        return 0;
    }
    
     public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.delegate != null) {
            return this.delegate.getObject().findPrefixItemList(item,type);
        }
        return new ArrayList<ReferenceItem>();
    }
    
    /** Method which searches the reference list based on the reference item */
    public ReferenceItem<? extends T> findReference(ReferenceItem<? extends T> ref) {
        if (this.delegate != null) {
            return this.delegate.getObject().findReference(ref);
        }
        return null;
    }
    
     /** I am not sure what the exact usage of this function is. It appears to be to get a group
     *  of references for objects like instance module */
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item)
    {
        if (this.delegate != null) {
            return this.delegate.getObject().getHyperlinkItemList(item);
        }
        return new ArrayList<ReferenceItem>();
    }
    
   /** Find a Reference Item based on the find item and type */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        if (this.delegate != null) {
            return this.delegate.getObject().findBaseReference(item);
        }
        return null;
    }
    /** Handles a find operation which is based on a parenthesis operation */
    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {
        if (this.delegate != null) {
            return this.delegate.getObject().findSliceReference(top);
        }
        return null;
    }
    
    /** Method which returns the type of this object. Used for creating type mismatch errors */
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        if (this.delegate != null) {
            return this.delegate.getObject().getTypeReference();
        }
        return null;
    }
    
    
    
}
