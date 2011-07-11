/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.condition;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.IfConditionStatement;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// priority_unique "if" parop statement_or_null

public class ConditionHeadNode extends TopASTNode{

	
	public static class First extends ConditionHeadNode {
		
		 public void format(FormatPosition position) {
			 TopASTNode child = this.getFirstASTChild();
			 child = child.getNextASTSibling();
			 child = child.getNextASTSibling();
			 if (this.getNumberOfChildren() == 4) child = child.getNextASTSibling();
			 child.addFormatIndent(position);
		 }
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); //unique_priority
			child = this.getFirstASTChild(); // if
			TopASTNode cond = child.getNextASTSibling();
			TopASTNode body = cond.getNextASTSibling();
			
			ReferenceItem condRef = (ReferenceItem) cond.generateModule(context);
			ReferenceItem bodyRef = (ReferenceItem) body.generateModule(context); 
			IfConditionStatement iftop = new IfConditionStatement("If-Condition Statement",
					condRef, bodyRef);
	       
	        return iftop.createReferenceItem();
		}
	}
	// "else" (condition_head | statement_no_condition)
	public static class Else extends ConditionHeadNode {
		
		 public void format(FormatPosition position) {
			 TopASTNode child = this.getFirstASTChild();
			 child = child.getNextASTSibling();
			 child.addFormatIndent(position);
		 }
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild();
			child = child.getNextASTSibling();
			return child.generateModule(context);
	       
		}
	}
	
	
}
