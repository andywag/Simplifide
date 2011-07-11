/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.types;

import com.simplifide.base.basic.struct.ModuleObjectRangeListInitial;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.StructTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogArrayType;


public class StructureNode extends TopASTNode{

	//struct_union : ("struct" | "union") taggedq packedq signing LCURLY struct_union_member_list RCURLY packed_dimension
	public static class Top extends StructureNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // ("struct" | "union")
			child = child.getNextASTSibling(); // taggedq
			child = child.getNextASTSibling(); // packedq
			child = child.getNextASTSibling(); // signing
			child = child.getNextASTSibling(); // LCURLY
			TopASTNode listNode = child.getNextASTSibling();
			child = listNode.getNextASTSibling(); // RCURLY;
			TopASTNode dimNode = child.getNextASTSibling();
			
			NoSortList<SystemVar> vars =  (NoSortList<SystemVar>) listNode.generateModule(context);
			StructTypeVar struct = new StructTypeVar<SystemVar>("struct");
			for (ReferenceItem<? extends SystemVar> varR : vars.getGenericSelfList()) {
				struct.addReferenceItem(varR);
			}
			return struct.createReferenceItem();
		}
	}
	// struct_union_member_list : (struct_union_member)+
	public static class List extends StructureNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild();
			NoSortList<SystemVar> returnVars = new NoSortList<SystemVar>();
			while (child != null) {
				NoSortList<SystemVar> vars = (NoSortList<SystemVar>) child.generateModule(context);
				returnVars.addAll(vars);
				child = child.getNextASTSibling();
			}
			return returnVars;
		}
		
	}
	
	// struct_union_member : data_type_or_void list_of_variable_identifiers SEMI
	public static class Item extends StructureNode {
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			// Nodes
			TopASTNode typeNode = this.getFirstASTChild(); // ("struct" | "union")
			TopASTNode listNode = typeNode.getNextASTSibling();
			// Items
			ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
			NoSortList<ModuleObjectRangeListInitial> objects = (NoSortList<ModuleObjectRangeListInitial>)listNode.generateModule(context);
			
			NoSortList<SystemVar> vars = new NoSortList<SystemVar>();
			for (ReferenceItem<? extends ModuleObjectRangeListInitial> object : objects.getGenericSelfList()) {
				ModuleObjectRangeListInitial robject = object.getObject();
				ReferenceItem<TypeVar> uTypeRef = typeRef;
				for (ReferenceItem<VarRange> range : robject.getRanges()) {
					VerilogArrayType arr = new VerilogArrayType(range,typeRef);
					typeRef = arr.createReferenceItem();
				}
				
				SystemVar tvar = new SystemVar(robject.getname(),uTypeRef,new OperatingTypeVar.ConstantVar());
				vars.addObject(tvar.createReferenceItemWithLocation(object.getLocation()));
			}
			
			return vars;
		}
	}
	
	
	
}
