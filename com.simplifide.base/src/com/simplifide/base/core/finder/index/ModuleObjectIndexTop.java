/*
 * ModuleObjectIndexTop.java
 *
 * Created on March 12, 2007, 11:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.finder.index;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy
 */
public class ModuleObjectIndexTop {
    
    /** Base Item to Search Over */
    private ReferenceItem base;
    /** Convenience operator for now to handle old functionallity */
    private ModuleObjectFindItem item;
    
    public ModuleObjectIndexTop(ReferenceItem base, ModuleObjectFindItem item) {
        this.base = base;
        this.item = item;
    }

    /** Creates a copy of this object which contains the original ModuleObjectFindItem
     *  and a new ReferenceItem with a different type */
    public ModuleObjectIndexTop copyWithNewType(int type) {
        ReferenceItem cItem = new ReferenceItem(base.getname(),type,null);
        return new ModuleObjectIndexTop(cItem,this.getItem());
    }
    
    public ReferenceItem getBase() {
        return base;
    }

    public void setBase(ReferenceItem base) {
        this.base = base;
    }

    public ModuleObjectFindItem getItem() {
        return item;
    }

    public void setItem(ModuleObjectFindItem item) {
        this.item = item;
    }
  
}
