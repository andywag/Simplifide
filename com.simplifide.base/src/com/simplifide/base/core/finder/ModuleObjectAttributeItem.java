/*
 * ModuleObjectAttributeItem.java
 *
 * Created on May 30, 2006, 6:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.finder;

import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author awagner
 * 
 * This class will contain all of the hardcoded tick elements
 */
public class ModuleObjectAttributeItem extends ModuleObjectFindItem{

    private String text;
    /** Creates a new instance of ModuleObjectAttributeItem */
    public ModuleObjectAttributeItem() {}
    public ModuleObjectAttributeItem(String text)
    {
        super("Attribute");
        this.text = text;
    }
    
    /** @todo : need to fix this */
    public ReferenceItem findNewObject(ReferenceItem item, int type)
    {
        return item;
    }

  


   

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
