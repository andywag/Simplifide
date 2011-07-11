package com.simplifide.base.verilog.parse.nodes.constraint;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.sv.SvConstraint;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// constraint_declaration : staticQ "constraint" constraint_identifier constraint_block

public class ConstraintDeclarationNode extends TopASTNodeGeneric<ReferenceItem<SvConstraint>>{

	public ReferenceItem<SvConstraint> createObjectSmall(ParseContext context) {
		TopASTNode child = this.getFirstASTChild(); // staticQ
		child = child.getNextASTSibling(); // "constraint"
		TopASTNode nameNode = child.getNextASTSibling();
		
		SvConstraint con = new SvConstraint(nameNode.getRealText());
		return con.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
	}
	
}
