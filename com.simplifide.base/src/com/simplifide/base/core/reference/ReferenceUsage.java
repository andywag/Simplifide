/*
 * ReferenceUsage.java
 *
 * Created on October 9, 2006, 10:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;

import com.simplifide.base.basic.struct.ModuleObject;

/**
 *
 * @author Andy Wagner
 */
public class ReferenceUsage extends ModuleObject{
    
    private ReferenceItem item;
   
    private String text;
    private ReferenceLocation location;
    // Type of item found
    private int usageType = -1;
    // Found Item
    
    /** Creates a new instance of ReferenceUsage */
    public ReferenceUsage(ReferenceItem item,  String text, ReferenceLocation loc) {
        this(item,text,loc,-1);
    }
    public ReferenceUsage(ReferenceItem item, String text, ReferenceLocation loc, int type) {
    	super("");
        this.item = item;
        this.text = text;
        this.location = loc;
        this.setUsageType(type);
        if (item != null) this.setname(item.getname());
    }

    public ReferenceItem getItem() {
        return item;
    }

    public void setItem(ReferenceItem item) {
        this.item = item;
    }

   

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

 
    public ReferenceLocation getLocation() {
        return location;
    }

    public void setLocation(ReferenceLocation location) {
        this.location = location;
    }
	public void setUsageType(int usageType) {
		this.usageType = usageType;
	}
	public int getUsageType() {
		return usageType;
	}
	
    
    
}
