/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.cas;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.CaseStatementTop;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


//case_statement : unique_priority case_keyword case_head matches case_list "endcase"

public class CaseStatementNode extends TopASTNode{

	
	public boolean canFold() {
		return true;
	}
	
	public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		
		
		child.addFormatIndent(position);
		
	}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		TopASTNode head = child.getNextASTSibling();
		child = head.getNextASTSibling();
		TopASTNode body = child.getNextASTSibling();
		
		ReferenceItem headRef = (ReferenceItem) head.generateModule(context);
		
		CaseStatementTop caseState = new CaseStatementTop("",headRef);
		if (context.getPass() == ParseContext.BUILD_FIND_USAGES) {
			body.generateModule(context);
		}
		
		// Too Memory Intensive
		/*ReferenceItem<NoSortList> listRef = (ReferenceItem<NoSortList>) body.generateModule(context);
		NoSortList<ModuleObject> list = listRef.getObject();
		for (ReferenceItem item : list.getGenericSelfList()) {
			caseState.addObject(item);
		}*/
		
		return caseState.createReferenceItem();
		
	}
	
	
}
