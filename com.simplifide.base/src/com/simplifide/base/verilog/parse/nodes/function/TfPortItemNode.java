/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.ClassTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/** Node which encompasses both functions and tasks */
// tf_port_item : data_type_or_implicit port_identifier variable_dimension ( ASSIGN expression )?

public class TfPortItemNode extends TopASTNodeGeneric<ReferenceItem<PortDefault>>{

	
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			// Node Determination
			//TfPortDirectionNode dirNode = (TfPortDirectionNode) this.getFirstASTChild();
			TopASTNode typeNode = this.getFirstASTChild();
			TopASTNode nameNode = typeNode.getNextASTSibling();
			TopASTNode dimNode = nameNode.getNextASTSibling();
			// Reference Decoding
			//int ioType = dirNode.getIOType();
			int ioType = OperatingTypeVar.IOVar.INPUT;
			ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
			String varName = nameNode.getRealText();
			ReferenceItem<VarRange> range = (ReferenceItem<VarRange>) dimNode.generateModule(context);
			// Return Object Creation
			ReferenceLocation loc = context.createReferenceLocation(nameNode);
			OperatingTypeVar opVar = new OperatingTypeVar.IOVar(ioType);
			ReferenceItem utype = typeRef;
			if (utype != null && utype.getObject() instanceof ClassEntity) {
				ClassEntity ent = (ClassEntity) utype.getObject();
				ClassTypeVar ntype = new ClassTypeVar<SystemVar>(ent.getInstanceModRef());
				typeRef = ntype.createReferenceItemWithLocation(ent.getReference().getLocation());
			}
			else if (utype != null && utype.getObject() instanceof ClassInstanceModule) {
				ClassInstanceModule inst = (ClassInstanceModule) utype.getObject();
				ClassEntity ent = (ClassEntity) inst.getEntity();
				ClassTypeVar ntype = new ClassTypeVar<SystemVar>(ent.getInstanceModRef());
				typeRef = ntype.createReferenceItemWithLocation(ent.getReference().getLocation());
			}
			
			SystemVar var = new SystemVar(varName,typeRef,opVar);
			PortDefault def = new PortDefault(var.createReferenceItemWithLocation(loc));
			
			return def.createReferenceItemWithLocation(loc);
		}
	
	
	
	
}
