/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.hierarchy;

import java.util.List;

import com.simplifide.base.core.generate.GenerateStatement;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class GenerateConnectorHolder extends ConnectorHolder{

	private ReferenceItem<GenerateStatement> generate;
	
	public GenerateConnectorHolder(ReferenceItem<GenerateStatement> generate) {
		super(generate.getname());
		this.setGenerate(generate);
	}
	
	  public PathTreeElement createTree(int depth) {
		  if (depth > 100) return null;
	    GeneratePathTreeElement el = new GeneratePathTreeElement(generate.getname());
	    for (ReferenceItem ref : this.getGenericSelfList()) {
	    	ConnectorHolder holder = (ConnectorHolder) ref.getObject();
	    	PathTreeElement nel = holder.createTree(depth+1);
	    	if (nel != null) el.addElement(nel);
	    }
	    
      	return el;
      	//return null;
      }
	
	public int getSearchType() {
        return ReferenceUtilities.REF_CONNECTOR_GENERATE;
    }

	@Override
	public List<ReferenceItem> getNavigatorList() {
		List rlist = this.getGenericSelfList();
		return rlist;
	}
	
	public void setGenerate(ReferenceItem<GenerateStatement> generate) {
		this.generate = generate;
	}

	public ReferenceItem<GenerateStatement> getGenerate() {
		return generate;
	}
	
	
}
