/*
 * ModuleObjectFindItem.java
 *
 * Created on May 11, 2006, 6:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.finder;

import java.io.Serializable;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.DelegateVar;
import com.simplifide.base.core.var.realvars.SystemVar;

/**
 *
 * @author awagner
 */
public abstract class ModuleObjectFindItem extends ModuleObject implements Serializable
{
    /** Creates a new instance of ModuleObjectFindItem */

    private ModuleObjectFindItem next;
    private int startpos = 0;

    public ModuleObjectFindItem() {}
    public ModuleObjectFindItem(String name) {super(name);}
    
   
  
    
    /** The main workhorse of this class. It cycles through the objects looking for 
     *  the correct reference item */
    public ReferenceItem findRealReferenceItem(ReferenceItem item, int type) {
    	if (item == null) return null;
    	if (item.getname() == null) return null;
        return this.findRealReferenceItemBase(item, type, true);
    }
    
    /** Extension of findRealReferenceItem to allow appending the base function used for completing sensitvity
     * lists
     * @param item
     * @param type
     * @param base
     * @return
     */
    public ReferenceItem findRealReferenceItemBase(ReferenceItem item, int type, boolean lookForBase) {
        
        if (item == null) return null; // BaseLine Feature
        ReferenceItem returnItem;
        if (this.getNext() == null) returnItem =  this.findFinalReferenceItem(item, type);
        else returnItem =  this.findIntermediateReferenceItem(item,type,lookForBase);
        return returnItem;
    }
    
    private ReferenceItem findIntermediateReferenceItem(ReferenceItem item,int type, boolean lookForBase) {
        ReferenceItem returnItem =  this.findNewObject(item, ReferenceUtilities.REF_MODULEOBJECT);
        if (returnItem == null) return null;
        
        ReferenceItem<SystemVar> baseItem = null;
        boolean lookAgain = true;
        if (lookForBase) {
            if (ReferenceUtilities.checkType(returnItem.getType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
                baseItem = returnItem;
                lookAgain = false;
            }
            else if (ReferenceUtilities.checkType(returnItem.getType(), ReferenceUtilities.REF_PORT_TOP) == 0) {
                PortTop port = (PortTop) returnItem.getObject();
                baseItem = port.getLocalVarReference();
                lookAgain = false;
            }
        }
        if (this.getNext() instanceof ModuleObjectBaseItem && 
        	(this.getNext().getname().endsWith("all") || this.getNext().getname().endsWith("ALL")) && 
        	ReferenceUtilities.checkType(returnItem.getType(), type) == 0) {
        		return returnItem;
        }
        ReferenceItem realReturn = this.getNext().findRealReferenceItemBase(returnItem, type, lookAgain);
        if (realReturn == null && baseItem != null) {
        	if (ReferenceUtilities.checkType(baseItem.getType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) realReturn = baseItem;
        	else return null;
        }
        if (baseItem != null) {
            if (ReferenceUtilities.checkType(realReturn.getType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
            	
                ReferenceItem<SystemVar> realVarRef = (ReferenceItem<SystemVar>) realReturn;
                if (realVarRef.getObject() instanceof DelegateVar) {
                	DelegateVar dvar = (DelegateVar) realVarRef.getObject();
                	dvar.setBaseVar(baseItem);
                	return dvar.createReferenceItemWithLocation(baseItem.getLocation());
                }
                
            }
        }
        return realReturn;
        
    }
    
    private ReferenceItem findFinalReferenceItem(ReferenceItem item, int type) {
        ReferenceItem returnItem =  this.findNewObject(item, type);
        if (returnItem == null) return null;
        return returnItem;
    }
    
    
   
    
    
    /** Method which returns the reference item corresponding to this search */
    public ReferenceItem findNewObject(ReferenceItem item, int type) {
        return null;
    }    
    
    public String getFindName() {
        return this.getname();
    }

    public ModuleObjectFindItem getNext() {
        return next;
    }

    public void setNext(ModuleObjectFindItem next) {
        this.next = next;
    }

    public int getStartpos() {
        return startpos;
    }

    public void setStartpos(int startpos) {
        this.startpos = startpos;
    }
    
}
