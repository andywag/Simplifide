/*
 * SuperModuleLocator.java
 *
 * Created on August 18, 2006, 5:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.project;

import com.simplifide.base.core.reference.ObjectLocator;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy Wagner
 * 
 * This class will serve as a locator for the hardware sources to be used for location
 * This is not the most elegant solution but the easiest for the time being. Attempting
 * to open and reseach the modules added unwanted complexity
 * 
 */
public class HardwareProjectLocator extends ObjectLocator<ReferenceItem<CoreProjectBasic>>{
    
   
    /** Creates a new instance of SuperModuleLocator */
    public HardwareProjectLocator(){}

    public CoreProjectBasic getProject(int index)
    {
        ReferenceItem<CoreProjectBasic> basic = this.getObject(index);
        if (basic == null) return null;
        return basic.getObject();
    }

   
    
    
    
}
