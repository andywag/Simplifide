/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.generate;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.hierarchy.GenerateConnectorHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class GenerateStatement extends ModuleObjectWithList<ModuleObjectNew>{

	private ReferenceItem<GenerateConnectorHolder> connectHolderRef;
	
	private String blockName;
	private String blockText;
	
	public GenerateStatement(String name) {
		super(name);
		
	}
	
	public int getSearchType() {
	    return ReferenceUtilities.REF_GENERATE_STATEMENT;
        }
        
        public void cleanObject() {
        	ArrayList<ReferenceItem<? extends ModuleObjectNew>> itemList = this.getGenericSelfList();
        	for (int i=itemList.size()-1;i>=0;i--) {
        		ReferenceItem item = itemList.get(i);
        		if (item.getType() != ReferenceUtilities.REF_MODINSTANCE_CONNECT) {
                    this.removeObject(item);
                }
        	}
        	
            for (ReferenceItem item : this.getGenericSelfList()) {
                
            }
        }
	
	public void deleteObject() {
		this.setConnectHolderRef(null);
		super.deleteObject();
	}

	public void setConnectHolderRef(ReferenceItem<GenerateConnectorHolder> connectHolderRef) {
		this.connectHolderRef = connectHolderRef;
	}

	public ReferenceItem<GenerateConnectorHolder> getConnectHolderRef() {
		if (this.connectHolderRef == null) {
			this.connectHolderRef = new GenerateConnectorHolder(this.createReferenceItem()).createReferenceItem();
		}
		return connectHolderRef;
	}
	
	/** Returns addition objects which should be used for searching */
	public ReferenceItem getAdditionalItems() {
		return null;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockText(String blockText) {
		this.blockText = blockText;
	}

	public String getBlockText() {
		return blockText;
	}
	
	
}
