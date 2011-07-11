package com.simplifide.base.verilog.parse.nodes.portnew;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObjectRangeListInitial;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.base.PackedDimensionNode;

public class AnsiPortDeclarationNode extends TopASTNodeGeneric<ReferenceItem<ModuleObjectRangeListInitial>>{

	// ansi_port_declaration_normal :  port_identifier packed_dimension assign_expressionQ
	public static class Normal extends AnsiPortDeclarationNode {
		
		public ReferenceItem<ModuleObjectRangeListInitial> createObjectSmall(ParseContext context) {
			TopASTNode identNode = this.getFirstASTChild();
			PackedDimensionNode rangeNode = (PackedDimensionNode) identNode.getNextASTSibling();
			TopASTNode assignNode = rangeNode.getNextASTSibling();
			
		    ArrayList<ReferenceItem<VarRange>> ranges = rangeNode.createObjectSmallNew(context);
		    ReferenceItem ass = (ReferenceItem) assignNode.generateModule(context);
		    
			ModuleObjectRangeListInitial m = new ModuleObjectRangeListInitial(identNode.getRealText(),ranges,ass);
			return m.createReferenceItemWithLocation(context.createReferenceLocation(identNode));
			
			/*
			ReferenceItem nameR = (ReferenceItem) identNode.generateModule(context);
			
			ReferenceItem typeR = VerilogBaseTypes.BIT.createReferenceItem();
		    ArrayList<ReferenceItem<VarRange>> ranges = rangeNode.createObjectSmallNew(context);
            for (ReferenceItem<VarRange> rangeR : ranges) {
                VerilogArrayType arrType = new VerilogArrayType(rangeR,typeR);
                typeR = arrType.createReferenceItem();
            }
			VerilogVar var = new VerilogVar(nameR.getname(), typeR, null);
			return var.createReferenceItemWithLocation(context.createReferenceLocation(identNode));
			*/
		}
	}
	
	// ansi_port_declaration_dot    :  DOT port_identifier LPAREN (expression )? RPAREN
	public static class Dot extends AnsiPortDeclarationNode {
		
		public ReferenceItem<ModuleObjectRangeListInitial> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // DOT
			TopASTNode identNode = child.getNextASTSibling();
			
			TopObjectBase base = identNode.generateModule(context);
			ModuleObjectRangeListInitial init = new ModuleObjectRangeListInitial(identNode.getRealText(), null, null);
			return init.createReferenceItemWithLocation(context.createReferenceLocation(identNode));
			
		}
	}
	
}
