package com.simplifide.base.verilog.parse.nodes.sv;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.sv.SvClocking;
import com.simplifide.base.core.sv.SvConstraint;
import com.simplifide.base.core.sv.SvProperty;
import com.simplifide.base.core.sv.SvSequence;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class DeclarationNode extends TopASTNodeGeneric {
	
	
	
	public static class Property extends DeclarationNode{

		public boolean canFold() {return true;}
		public ReferenceItem<SvProperty> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // property
			TopASTNode nameNode = child.getNextASTSibling(); // property Name
			SvProperty prop = new SvProperty(nameNode.getRealText());
			return  prop.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
		}
	}
	
	public static class Sequence extends DeclarationNode{
		
		public boolean canFold() {return true;}
		public ReferenceItem<SvSequence> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // property
			TopASTNode nameNode = child.getNextASTSibling(); // property Name
			SvSequence prop = new SvSequence(nameNode.getRealText());
			return  prop.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
		}
	}
	
	public static class Clocking extends DeclarationNode{

		public boolean canFold() {return true;}
		public ReferenceItem<SvClocking> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // property
			TopASTNode nameNode = child.getNextASTSibling(); // property Name
			SvClocking prop = new SvClocking(nameNode.getRealText());
			return  prop.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
		}
	}
	
	public static class Constraint extends DeclarationNode {
		public boolean canFold() {return true;}
		public ReferenceItem<SvConstraint> createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // staticQ
			child = child.getNextASTSibling(); // "constraint"
			TopASTNode nameNode = child.getNextASTSibling();
			
			SvConstraint con = new SvConstraint(nameNode.getRealText());
			return con.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
		}
	}
	
	
}
