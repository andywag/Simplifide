/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.cas;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;
import com.simplifide.base.core.segment.basic.condition.CaseChoices;
import com.simplifide.base.core.segment.basic.condition.CaseConditionStatementMulti;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogLexerTokenTypes;

public class CaseItemNode extends TopASTNode{

	public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		
		
		child.addFormatIndent(position);
		
	}
	
	//case_normal : case_expression COLON statement_or_null
	public static class Normal extends CaseItemNode {
		
		public void format(FormatPosition position) {
			TopASTNode child = this.getFirstASTChild();
			child = child.getNextASTSibling();
			child = child.getNextASTSibling();
	
			child.addFormatIndent(position);
		}
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode choiceNode = this.getFirstASTChild();
			TopASTNode child = choiceNode.getNextASTSibling();
			TopASTNode stateNode = child.getNextASTSibling();
			
			
			ReferenceItem<CaseChoices> choices = (ReferenceItem<CaseChoices>) choiceNode.generateModule(context);
			ReferenceItem<SequenceStatements> statements = (ReferenceItem) stateNode.generateModule(context);
			CaseConditionStatementMulti state = new CaseConditionStatementMulti("",choices,statements);
			return state.createReferenceItem();
		}
	}
	//case_default :  "default" (COLON)? statement_or_null
	public static class Default extends CaseItemNode {
		
		public void format(FormatPosition position) {
			TopASTNode child = this.getFirstASTChild();
			child = child.getNextASTSibling();
			if (this.getNumberOfChildren() == 3) child = child.getNextASTSibling();
	
			child.addFormatIndent(position);
		}
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode child = this.getFirstASTChild();
			child = child.getNextASTSibling();
			if (child.getToken().getType() == SystemVerilogLexerTokenTypes.COLON) {
				child = child.getNextASTSibling();
			}
			CaseChoices.Default def = new CaseChoices.Default();
			ReferenceItem<SequenceStatements> stateRef = (ReferenceItem<SequenceStatements>) child.generateModule(context);
			CaseConditionStatementMulti state = new CaseConditionStatementMulti("",def.createReferenceItem(),stateRef);
			return state.createReferenceItem();
		}
	}
	//case_expression : expression ( COMMA expression )*
	public static class Expression extends TopASTNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			CaseChoices list = new CaseChoices();
			TopASTNode child = this.getFirstASTChild();
			while (child != null) {
				ReferenceItem expression = (ReferenceItem) child.generateModule(context);
				list.addObject(expression);
				child = child.getNextASTSibling();
			}
			return list.createReferenceItem();
		}
		
	}
	
	
}
