/*
 * ModuleObjectBaseItem.java
 *
 * Created on May 11, 2006, 6:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.finder;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author awagner
 *
 *  This expression can contain either a function reference or a range variable
 *  
 *  This operation operates slightly differently than the rest of the search functions
 *  In this case each found object is stored so the correct value can be returned at the 
 *  final search stage. 
 */
public class ModuleObjectExpressionItem extends ModuleObjectRangeTop
{
    private ReferenceItem<NoSortList> expressionList;
    /** Creates a new instance of ModuleObjectBaseItem */
    public ModuleObjectExpressionItem() {}
    public ModuleObjectExpressionItem(String name, ReferenceItem<NoSortList> expList) {
        super(name);
        this.expressionList = expList;
    }

    protected ReferenceItem<ModuleObjectWithList> getDeps() {
    	ModuleObjectWithList deps = new ModuleObjectWithList();
    	NoSortList<ModuleObject> list = this.getExpressionList().getObject();
    	for (ReferenceItem item : list.getGenericSelfList()) {
    		deps.addAll((ModuleObjectWithList)item.getDependants().getObject());
    	}
    	return deps.createReferenceItem();
    }
    
     /** @todo : need to fix this */
    public ReferenceItem findNewObject(ReferenceItem item, int type)
    {
        ModuleObjectIndexTop index = new ModuleObjectIndexTop(this.expressionList,this);
        ReferenceItem uitem = item.findSliceReference(index);
        if (uitem != null) return this.handleVarReturn(uitem);
        if (ReferenceUtilities.checkType(item.getType(),ReferenceUtilities.REF_TYPEVAR) == 0) {
            return item;
        }
        return null;
        // Currents System Var to Delegate Var
        
        
    }

    public ReferenceItem<NoSortList> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(ReferenceItem<NoSortList> expressionList) {
        this.expressionList = expressionList;
    }
   

    

 

   

    
    
}
