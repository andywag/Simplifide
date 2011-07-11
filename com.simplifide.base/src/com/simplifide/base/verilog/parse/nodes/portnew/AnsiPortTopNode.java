package com.simplifide.base.verilog.parse.nodes.portnew;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObjectRangeListInitial;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;
import com.simplifide.base.verilog.core.types.VerilogArrayType;
import com.simplifide.base.verilog.core.var.VerilogVar;

public class AnsiPortTopNode extends TopASTNodeGeneric<ReferenceItem<VerilogPortDefault>>{

	// port_expression : /*port_reference |*/ LCURLY port_reference ( COMMA port_reference )* RCURLY
	/* TODO Not Really Supported for the full expression*/
	public static class Expression extends AnsiPortTopNode {
		
		public ReferenceItem<VerilogPortDefault> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // LCURLY
			TopASTNode ref = child.getNextASTSibling(); // port_reference
			
			ReferenceLocation loc = context.createReferenceLocation(ref);
			SystemVar tvar = new VerilogVar(ref.getRealText(),null,null);
	        tvar.createReferenceItemWithLocation(loc);
			VerilogPortDefault def = new VerilogPortDefault(tvar.createReferenceItem());
	        return def.createReferenceItemWithLocation(loc);
		}
		
	}
	
	// ansi_port_declaration : ansi_port_headerQ (ansi_port_declaration_normal | ansi_port_declaration_dot)
	public static class Declaration extends AnsiPortTopNode {
		
		public ReferenceItem<VerilogPortDefault> createObjectSmall(ParseContext context) {
			AnsiPortHeaderNode headNode = (AnsiPortHeaderNode) this.getFirstASTChild();
			AnsiPortDeclarationNode decNode = (AnsiPortDeclarationNode) headNode.getNextASTSibling();
			
			ReferenceItem<SystemVar> sysTypeR = headNode.createObject(context);
			ReferenceItem<ModuleObjectRangeListInitial> sysR = decNode.createObject(context);
			
			
			ReferenceItem<TypeVar> typeR = null;
			OperatingTypeVar op = null;
			if (sysTypeR != null && sysTypeR.getObject() != null) {
				SystemVar tvar = sysTypeR.getObject();
				op = tvar.getOpTypeVar();
				typeR = (ReferenceItem<TypeVar>) sysTypeR.getTypeReference();
				//if (typeR == null) typeR = VerilogBaseTypes.BIT.createReferenceItem();
				
				ArrayList<ReferenceItem<VarRange>> ranges = sysR.getObject().getRanges();
				for (ReferenceItem<VarRange> range: ranges) {
					typeR = new VerilogArrayType(range,typeR).createReferenceItem();
				}	
			}
			VerilogVar ver = new VerilogVar(sysR.getname(),typeR,op);
			ver.setDefaultValue(sysR.getObject().getExpressionR());
			ver.createReferenceItem().setLocation(sysR.getLocation());

			
			VerilogPortDefault def = new VerilogPortDefault(ver.createReferenceItem());
			return def.createReferenceItemWithLocation(sysR.getLocation());
		}
	}
	
}
