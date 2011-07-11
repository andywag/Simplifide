/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.finder;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.DelegateVar;
import com.simplifide.base.core.var.realvars.SystemVar;

public abstract class ModuleObjectRangeTop extends ModuleObjectFindItem{

	 	public ModuleObjectRangeTop() {}
		public ModuleObjectRangeTop(String name) {
			super(name);
		}
		
		protected abstract ReferenceItem<ModuleObjectWithList> getDeps();
		
	   /** This method handles the case where a variable is found. In this case the return object should 
	    * be converted to a delegate var which contains the dependencies. The base reference is added in 
	    * findIntermediateReferenceItem
	    * @return
	    */
	    protected ReferenceItem handleVarReturn(ReferenceItem item) {
	    	if (item == null) return null;
	    	ReferenceItem local = item;
	    	if (ReferenceUtilities.checkType(item.getType(), ReferenceUtilities.REF_PORT_TOP) == 0) {
	    		PortTop port = (PortTop) item.getObject();
	    		local = port.getLocalVarReference();
	    	}
	    	if (ReferenceUtilities.checkType(local.getType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
	        	SystemVar tvar = (SystemVar) local.getObject();
	        	DelegateVar dvar = new DelegateVar(tvar,null,this.getDeps());
	        	return dvar.createReferenceItemWithLocation(local.getLocation());
	        }
	    	return item;
	    }
}
