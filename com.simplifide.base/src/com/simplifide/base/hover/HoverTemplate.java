/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.hover;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.instance.Component;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceTop;
import com.simplifide.base.core.interfac.InterfaceEntity;
import com.simplifide.base.core.interfac.InterfaceInstanceModule;
import com.simplifide.base.core.interfac.ModPort;
import com.simplifide.base.core.interfac.ProgramEntity;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.ArrayTypeVar;
import com.simplifide.base.core.var.types.EnumTypeVar;
import com.simplifide.base.core.var.types.StructTypeVar;
import com.simplifide.base.core.var.types.SubTypeVar;

public class HoverTemplate {

	
	public static String getTextHover(ModuleObject object) {
		String templateName = "moduleobject";
		if (object instanceof ClassEntity) templateName = "class";
		else if (object instanceof InterfaceEntity) templateName = "interface";
		else if (object instanceof ProgramEntity) templateName = "program";
		else if (object instanceof Entity) templateName =  "entity";
		else if (object instanceof Component) templateName =  "component";
		else if (object instanceof PackageModule) templateName = "package";
		else if (object instanceof SubTypeVar) templateName = "type_sub";
		else if (object instanceof StructTypeVar) templateName = "type_struct";
		else if (object instanceof ArrayTypeVar) templateName = "type_array";
		else if (object instanceof EnumTypeVar) templateName = "type_enum";
		else if (object instanceof SystemVar) templateName = "variable";
		else if (object instanceof PortTop) templateName = "port";
		else if (object instanceof BaseFunction) templateName = "function";
		else if (object instanceof InterfaceInstanceModule) templateName = "interface";
		else if (object instanceof ClassInstanceModule) templateName = "class"; 
		else if (object instanceof DefineObject) templateName = "define"; 
		else if (object instanceof ModPort) templateName = "modport";
		else if (object instanceof ModInstanceTop) templateName = "instance";
		return templateName;
	}
	
}
