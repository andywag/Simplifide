/*
 * ModuleObjectRange.java
 *
 * Created on May 11, 2006, 6:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.finder;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;

/**
 *
 * @author awagner
 */
public class ModuleObjectRange extends ModuleObjectRangeTop{

    private ReferenceItem<VarRange> rangeReference;

    /** Creates a new instance of ModuleObjectRange */
    public ModuleObjectRange() {}
    public ModuleObjectRange(ReferenceItem<VarRange> range)
    {
        super("Range");
        this.rangeReference = range;
    }
   
    @Override
	protected ReferenceItem<ModuleObjectWithList> getDeps() {
		if (this.rangeReference != null) {
			return this.rangeReference.getDependants();
		}
		return new ModuleObjectWithList().createReferenceItem();
	}
    
    /** @todo Clearly not properly done */
    public ReferenceItem findNewObject(ReferenceItem item, int type) {
        ModuleObjectIndexTop index = new ModuleObjectIndexTop(rangeReference,this);
        ReferenceItem uitem = item.findSliceReference(index);
        return this.handleVarReturn(uitem);
        
     }

    public ReferenceItem<VarRange> getRangeReference() {
        return rangeReference;
    }

    public void setRangeReference(ReferenceItem<VarRange> rangeReference) {
        this.rangeReference = rangeReference;
    }
	
    
   
    

   
    
}
