/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.types;

import com.simplifide.base.basic.struct.ModuleObjectRangeInitial;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.EnumTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogArrayType;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;


public class EnumNode extends TopASTNode{

	// enum_type : "enum" enum_dec_type LCURLY enum_list RCURLY	 {
	public static class Top extends EnumNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			// Node Handling
			TopASTNode child = this.getFirstASTChild(); // ("enum")
			TopASTNode typeNode = child.getNextASTSibling(); // enum_dec_type
			child = typeNode.getNextASTSibling(); // LCURLY
			TopASTNode listNode = child.getNextASTSibling();
			
			ReferenceItem<TypeVar> typeR = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
			ReferenceItem<NoSortList>  listR = (ReferenceItem<NoSortList>) listNode.generateModule(context);
			NoSortList<ModuleObjectRangeInitial> lis = listR.getObject();
			
			//EnumVar enu = new EnumVar("",typeR,new OperatingTypeVar.ConstantVar());
			EnumTypeVar<SystemVar> enu = new EnumTypeVar<SystemVar>("");
			for (ReferenceItem<? extends ModuleObjectRangeInitial> r : lis.getGenericSelfList()) {
				ModuleObjectRangeInitial obj = r.getObject();
				ReferenceItem<VarRange> rangeR = obj.getRange();
				if (rangeR != null) {
					VerilogArrayType ty = new VerilogArrayType(rangeR, typeR);
					SystemVar tvar = new SystemVar(obj.getname(),ty.createReferenceItem(),OperatingTypeVar.ConstantVar.TYPE_CONSTANT);
					ReferenceItem<SystemVar> tvarR = tvar.createReferenceItemWithLocation(r.getLocation());
					enu.addReferenceItem(tvarR);
				}
				else {
					SystemVar tvar = new SystemVar(obj.getname(),typeR,OperatingTypeVar.ConstantVar.TYPE_CONSTANT);
					ReferenceItem<SystemVar> tvarR = tvar.createReferenceItemWithLocation(r.getLocation());
					enu.addReferenceItem(tvarR);
				}
			}
			
			return enu.createReferenceItemWithLocation(context.createReferenceLocation(this));
		}
	}
	// enum_dec_type : enum_dec_type_ident signing packed_dimension
	public static class DecType extends EnumNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			// Node Handling
			TopASTNode decNode = this.getFirstASTChild();
			TopASTNode signNode = decNode.getNextASTSibling();
			TopASTNode rangeNode = decNode.getNextASTSibling();
			
			// Item Handling
			ReferenceItem<TypeVar> typeR = (ReferenceItem<TypeVar>) decNode.generateModule(context);
			if (typeR == null) typeR = VerilogBaseTypes.BIT.createReferenceItem();
			
			ReferenceItem<VarRange> rangeR = (ReferenceItem<VarRange>)rangeNode.generateModule(context);
			if (rangeR != null) {
				VerilogArrayType arrType = new VerilogArrayType(rangeR, typeR);
				return arrType.createReferenceItem();
			}
			
			return typeR;
		}
	}
	
	// enum_dec_type_ident : (integer_atom_type | integer_vector_type | IDENTIFIER)?
	// Dummy class which passes through to TypeDecNode.Identifier
	public static class Ident extends TypeDecNode.Identifier {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			if (this.getNumberOfChildren() == 0) return null; // No Type for Enum
			return super.generateModuleSmallNew(context);
		}
	}
	
	// enum_list : enum_name_declaration (COMMA enum_name_declaration)*
	public static class List extends EnumNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild();
			NoSortList<ModuleObjectRangeInitial> nlist = new NoSortList<ModuleObjectRangeInitial>();
			while (child != null) {
				ReferenceItem<ModuleObjectRangeInitial> ref = (ReferenceItem<ModuleObjectRangeInitial>) child.generateModule(context);
				nlist.addReferenceItem(ref);
				child = child.getNextASTSibling();
				if (child != null) child = child.getNextASTSibling();
			}
			return nlist.createReferenceItem();
		}
	}
	// enum_name_declaration : IDENTIFIER enum_name_range enum_name_init
	public static class Item extends EnumNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode nameNode = this.getFirstASTChild(); // Identifier
			TopASTNode rangeNode = nameNode.getNextASTSibling(); // enum_name_range
			TopASTNode assNode   = rangeNode.getNextASTSibling(); // enum_name_init
			
			ReferenceItem<VarRange> rangeR = (ReferenceItem<VarRange>) rangeNode.generateModule(context);
			ModuleObjectRangeInitial range = new ModuleObjectRangeInitial(nameNode.getRealText(), rangeR);
			return range.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
		}
	}
	
	
	
	
	
}
