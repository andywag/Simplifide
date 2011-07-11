/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.module;

import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Jan 23, 2004
 * Time: 11:51:51 AM
 * To change this template use Options | File Templates.
 */

public class TopModule<T extends ModuleObject> extends ModuleObjectWithList<T> implements SearchReferenceHolder {
    
   
	private static final long serialVersionUID = 1L;

	public static String DECLARATIONPOSITION = "Declaration";
    
    private ReferenceItem searchReference;
    private ReferenceItem context;
    
    public TopModule() {super(); }
    public TopModule(String name1) {
        super(name1);
        init();
    }
    
    private void init(){}
    
    public int getSearchType() {return ReferenceUtilities.REF_TOP_MODULE;}
     
    public ReferenceLocation getRefactorAdditionLocation() {
    	return null;
    }
    
    /** Convenience Method to get a list of types from the module */
    public List<ReferenceItem> getTypes() {
    	List<ReferenceItem> typeList = (List<ReferenceItem>) this.findPrefixItemList(new ModuleObjectBaseItem(""),ReferenceUtilities.REF_TYPEVAR);
        return typeList;
    }
    
    public List<ReferenceItem> getConnections() {
    	List<ReferenceItem> typeList = (List<ReferenceItem>) this.findPrefixItemList(new ModuleObjectBaseItem(""),ReferenceUtilities.REF_MODINSTANCE_CONNECT);
    	return typeList;
    }
    
    /** Convenience Method to get a list of vars from the module */
    public List<SystemVar> getVars() {
        List<SystemVar> typeList = this.findRealPrefixItemList(new ModuleObjectBaseItem(""),ReferenceUtilities.REF_SYSTEMVAR);
       
        return typeList;
    }
    
    
    
    
    public void finalizeReferences() {
    	this.finalizeReferenceChildren();
    }
    
    
    public ReferenceItem getSearchReference() {
        return searchReference;
    }
    
    public void setSearchReference(ReferenceItem searchReference) {
        this.searchReference = searchReference;
    }
	public void setContext(ReferenceItem context) {
		this.context = context;
	}
	public ReferenceItem getContext() {
		return context;
	}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
