/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.instance;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// ordered_parameter_assignment : expression // | data_type

public class ParameterAssignmentNode extends TopASTNode{

	
	private static final long serialVersionUID = 1L;

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		ReferenceItem ref = (ReferenceItem) child.generateModule(context);
		if (ref != null) {
			this.generateUsedList((ModuleObjectWithList)ref.getDependants().getObject());
		}
		return ref;
	}
	
	//ordered_parameter_assignment : (data_type) => data_type | expression 
	public static class Ordered extends ParameterAssignmentNode{
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild();
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			// Kludge but required to find the actual parameter overriding data_type
			if (ref == null || ref.getObject() instanceof TypeVar.NotDefined) {
				child = child.getFirstASTChild();
				if (child != null) ref = (ReferenceItem) child.generateSearchTypeNew(context, ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL, ReferenceUtilities.REF_MODULEOBJECT);
			}
			
			
			PortConnect connect;
			if (ref != null && ref.getObject() instanceof PortDefault) {
				PortDefault def = (PortDefault) ref.getObject();
				ReferenceItem<SystemVar> varRef = def.getLocalVarReference();
				connect = new PortConnect(varRef, ref);
			}
			else {
				connect = new PortConnect(ref, ref);
			}
			
			if (ref != null) {
				this.generateUsedList((ModuleObjectWithList)ref.getDependants().getObject());
			}
			return connect.createReferenceItem();
		}
	}
	// DOT IDENTIFIER LPAREN (expression) RPAREN
	public static class Named extends ParameterAssignmentNode{
		
		private static final long serialVersionUID = 1L;
 
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // Dot
			child = child.getNextASTSibling(); // Port Identifier
			TopASTNode local = child; 
			child = child.getNextASTSibling(); // Paren
			child = child.getNextASTSibling(); //
			TopASTNode external = child;
			
			ReferenceItem externRef = (ReferenceItem) external.generateModule(context);
			// ExternRef cannot be null for port creation
			if (externRef == null) externRef = new ModuleObject(external.getRealText()).createReferenceItem();
			
			ReferenceItem localRef = null;
			if (context.getRefHandler().getSecondaryReference() != null) {
				ReferenceItem currentLocal = context.getRefHandler().getLocalReference();
				context.getRefHandler().setLocalReference(context.getRefHandler().getSecondaryReference());
				localRef = (ReferenceItem) local.generateModule(context);
				context.getRefHandler().setLocalReference(currentLocal);
			}
			
			if (localRef != null) {
				if (localRef.getObject() instanceof PortTop) {
					PortTop port = (PortTop) localRef.getObject();
					localRef = port.getLocalVarReference();
				}
			}
			PortConnect connect = new PortConnect(localRef,externRef);
			return connect.createReferenceItem();
			
			
		}
	}
	
}
