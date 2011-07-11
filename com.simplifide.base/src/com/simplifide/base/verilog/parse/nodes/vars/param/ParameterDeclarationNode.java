/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.vars.param;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.realvars.OperatingTypeVar.IOVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;


// parameter_declaration : ("parameter" | "specparam" | "localparam") data_type list_of_param_assignments SEMI

public class ParameterDeclarationNode extends TopASTNode{

	
	private TopObjectBase handleParameterPorts(ParseContext context, ReferenceItem<NoSortList> paramList,
			ReferenceItem<TypeVar> typeRef) {
		ReferenceItem<HardwareModule> modRef = context.getRefHandler().getActiveReference();
		InstanceModule instMod = (InstanceModule) modRef.getObject().getInstModRef().getObject();
		Entity entity = (Entity) instMod.getEntityReference().getObject();
		ModInstanceDefault def = (ModInstanceDefault) entity.getConnectRef().getObject();
		ReferenceItem<PortList> portRef = (ReferenceItem<PortList>) def.getGenericListRef();
		PortList portList;
		if (portRef == null) {
			portList = new PortList("Parameters");
			def.setGenericListRef(portList.createReferenceItem());
		}
		else {
			portList = portRef.getObject();
		} 
		int index = portList.getnumber();
		
		NoSortList<SystemVar> varList = paramList.getObject();
		for (ReferenceItem<? extends SystemVar> var : varList.getGenericSelfList()) {
			var.getObject().setOpTypeVar(new OperatingTypeVar.IOVar(IOVar.PARAMETER));
			var.getObject().setTypeReference(typeRef);
			PortDefault nport = new PortDefault(var);
			nport.setPortNumber(index);
			nport.setDefaultValue(var.getObject().getDefaultValue());
			ReferenceItem nportRef = nport.createReferenceItemWithLocation(var.getLocation());
			portList.addObject(nportRef);
			index++;
		}
		return null;
	}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		//TopASTNode signNode = child.getNextASTSibling();
		//TopASTNode dimNode = signNode.getNextASTSibling();
		TopASTNode typeNode = child.getNextASTSibling();
		TopASTNode parList = typeNode.getNextASTSibling();
		
		ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
		if (typeRef == null) {
			typeRef = VerilogBaseTypes.BIT.createReferenceItem();
		}
		

		ReferenceItem<NoSortList> paramList = (ReferenceItem<NoSortList>) parList.generateModule(context);
		// This is the case where the value is a real input parameter. In this case, we add 
		// directly to the entity, but only when this doesn't occur inside the module header
		if (child.getType() == SystemVerilogTokenTypes.LITERAL_parameter && 
				context.getRefHandler().getActiveReference().getSearchType() == ReferenceUtilities.REF_HARDWARE_MODULE) {
			return this.handleParameterPorts(context, paramList, typeRef);
		}
		NoSortList<SystemVar> varList = paramList.getObject();
		for (ReferenceItem<? extends SystemVar> varRef : varList.getGenericSelfList()) {
			varRef.getObject().setTypeReference(typeRef);
		}

		return paramList;
	}
	
}
