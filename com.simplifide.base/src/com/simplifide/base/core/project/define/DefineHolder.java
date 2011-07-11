/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.project.define;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;

public class DefineHolder extends ModuleObjectWithList<ModuleObjectNew>{

	public DefineHolder() {
		super("DefineList");
	}
	
	public DefineObject getDefineObject(String name) {
		ReferenceItem ref = this.findBaseReference(name, ReferenceUtilitiesInterface.REF_MODULEOBJECT );
		return (DefineObject) ref.getObject();
	}
	
	public DefineCall getDefineCall(DefineObject proto) {
		ReferenceItem ref = this.findBaseReference(proto.getname(), ReferenceUtilitiesInterface.REF_MODULEOBJECT );
		if (ref == null) return null;
		
		DefineObject obj = (DefineObject) ref.getObject();
		DefineCall call = obj.getDefineCall(proto);
		
		return call;
		
	}
	
}
