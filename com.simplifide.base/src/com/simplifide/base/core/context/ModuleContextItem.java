/*
 * Context.java
 *
 * Created on January 31, 2006, 3:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.context;

import com.simplifide.base.basic.struct.ModuleObject;

/**
 *
 * @author awagner
 */
public class ModuleContextItem extends ModuleObject {
    
    private ModuleObject item;
    /** Creates a new instance of Context */
    public ModuleContextItem() {}
    public ModuleContextItem(ModuleObject item) {
        super(item.getname());
        this.setItem(item);
    }
    
   
    
    public ModuleObject getItem() {
        return item;
    }
    
    public void setItem(ModuleObject item) {
        this.item = item;
    }
     
    
    
    
    
}
