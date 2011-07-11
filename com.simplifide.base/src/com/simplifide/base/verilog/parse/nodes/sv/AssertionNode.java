package com.simplifide.base.verilog.parse.nodes.sv;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.sv.SvAssertion;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class AssertionNode extends TopASTNodeGeneric<ReferenceItem<SvAssertion>>{
	
	public ReferenceItem<SvAssertion> createObjectSmall(ParseContext context) {
		StatementNamePrefixNode preNode = (StatementNamePrefixNode) this.getFirstASTChild();
		TopASTNode assertNode = preNode.getNextASTSibling();
		
		ReferenceItem<ModuleObjectNew> assertName = preNode.createObject(context);
		String name = assertNode.getRealText();
		ReferenceLocation loc = context.createReferenceLocation(this);
		if (assertName != null) {
			name = assertName.getname();
			loc = assertName.getLocation();
		}
		SvAssertion assert1 = new SvAssertion(name);
		return assert1.createReferenceItemWithLocation(loc);
		
	}

}
