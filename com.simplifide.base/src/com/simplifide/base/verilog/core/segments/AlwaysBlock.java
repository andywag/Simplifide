/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.core.segments;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.process.ProcessBody;
import com.simplifide.base.core.segment.basic.process.ProcessStatement;

public class AlwaysBlock extends ProcessStatement{

	 private String blockName;
	
	 public AlwaysBlock(String name,  ReferenceItem<NoSortList> obj) {
	    	super(name,obj,null);
	 }
	    
	 public AlwaysBlock(String name, ReferenceItem<NoSortList> headRef,
			 ReferenceItem<ProcessBody> bodyRef) {
	    	super(name,headRef,bodyRef);
	 }
	 
	 public String getDisplayName() {
		 String outStr = this.getname();
		 if (blockName != null) outStr = blockName + " : " + outStr; 
	     return outStr;
	    }

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getBlockName() {
		return blockName;
	}
}
