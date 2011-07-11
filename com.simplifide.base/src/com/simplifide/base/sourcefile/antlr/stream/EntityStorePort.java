/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;

public class EntityStorePort implements Comparable{
	private PortTop port;
	private String entityName;
	
	public EntityStorePort(PortTop port, String entityName) {
		this.setPort(port);
		this.setEntityName(entityName);
	}

	public SystemVar getVar() {
		return port.getLocalVar();
	}
	
	public boolean isInput() {
		if (port.getSearchType() == ReferenceUtilities.REF_PORT_INPUT) return true;
		return false;
	}
	
	public int compareTo(Object o) {
		EntityStorePort compare = (EntityStorePort) o ;
		return getName().compareTo(compare.getName());
	}

	public String getName() {
		if (port.getRealExternalVar() != null) 
			return port.getRealExternalVar().getname();
		else
			return port.getname();
	}
	
	public void setPort(PortTop port) {
		this.port = port;
	}

	public PortTop getPort() {
		return port;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}
	
	
	

}
