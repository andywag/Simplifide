/*
 * ModuleObjectBaseItem.java
 *
 * Created on May 11, 2006, 6:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.finder;

import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author awagner
 */
public class ModuleObjectBaseItem extends ModuleObjectFindItem {
    public static ModuleObjectBaseItem.All ALL = new ModuleObjectBaseItem.All();
    /** Creates a new instance of ModuleObjectBaseItem */
    public ModuleObjectBaseItem() {}
    public ModuleObjectBaseItem(String name) {super(name);}
    
      
     public ReferenceItem findNewObject(ReferenceItem item, int type) {
         ReferenceItem base = new ReferenceItem(this.getname(),type,null);
         ModuleObjectIndexTop index = new ModuleObjectIndexTop(base,this);
         return item.findBaseReference(index);
     }
    
    
    
    public String getFindName() {
        String ustring = this.getname();
        if (this.getNext() != null) ustring += this.getNext().getFindName();
        return ustring;
    }
    
    public String toString() {
        return this.getname() ;
    }
    
    
    
    
    
    public static class All extends ModuleObjectBaseItem {
        public All() {super("All");}
        
        
        public ReferenceItem findObject(ReferenceItem item, int type) {
            return item;
        }
        
        
        
    }
    
}
