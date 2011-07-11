/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.generate;

import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.generate.GenerateStatement;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/* label_colon generation_scheme
 GENERATE
 generate_declarative_part
 generate_statement_part
 END GENERATE ( identifier )? SEMI
 */
public class GenerateStatementNode extends TopASTNode {

	public GenerateStatementNode() {}

	public TopASTNode formatBase() {return this;}
	
	 public void format(FormatPosition position) {
			TopASTNode child = this.getFirstASTChild(); // lcol
			child = child.getNextASTSibling();          // generate
			child = child.getNextASTSibling();
			TopASTNode declare = child.getNextASTSibling();          // declare
			TopASTNode state   = declare.getNextASTSibling();          // state
			
			int start = 0;
			if (declare.getPosition() != null && declare.getPosition().getLength() > 0) start = declare.getPosition().getStartPos();
			else start = state.getPosition().getStartPos();
			int stop = state.getPosition().getEndPos();
			
			FormatPosition npos = position.addNewPosition(start, stop);
			npos.setIndent(position.getIndentOrZero() + FormatSupport.getInstance().getIndent());
			
			if (declare.getPosition() != null) declare.format(npos);
			state.format(npos);
			
			
	    }
	
	private void updateContext(ParseContext context, ReferenceItem decItem) {
		if (decItem != null) {
			ModuleObjectCompositeHolder activeHolder = ModuleObjectCompositeHolder
					.dualHolder("ProcessContext", context.getRefHandler()
							.returnLocalReference(), decItem);
			context.getRefHandler().setLocalReference(
					activeHolder.createReferenceItem());
			
		}
	}

	public void resolveContext(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		ReferenceItem decItem = (ReferenceItem) child.generateModule(context); // Declarative
																				// Processing
		this.updateContext(context, decItem);
	}

	/** Override with Generic Return Type */
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode nameNode  = this.getFirstASTChild();       // Label Colon
		TopASTNode genNode   = nameNode.getNextASTSibling();  // Generate Scheme
		TopASTNode child     = genNode.getNextASTSibling();   // "Generate"
		TopASTNode decNode   = child.getNextASTSibling();     // Declarations
		TopASTNode stateNode = decNode.getNextASTSibling();   // Statements
		
		String genName = nameNode.getFirstASTChild().getRealText();
		ReferenceItem<GenerateStatement> statementRef = (ReferenceItem<GenerateStatement>) genNode.generateModule(context);
		statementRef.getObject().setBlockName(genName);
		statementRef.changeName(genName);
		statementRef.setLocation(context.createReferenceLocation(nameNode));
		
 
		ReferenceItem<ModuleObjectWithList> modRef = (ReferenceItem<ModuleObjectWithList>) stateNode.generateModule(context);
		ReferenceItem additionalItems = statementRef.getObject().getAdditionalItems();
		if (additionalItems != null && modRef != null) {
			modRef.addReferenceItem(additionalItems);
		}

		ReferenceItem oldRef = context.getRefHandler().getActiveReference();
		ReferenceItem local  = context.getRefHandler().getLocalReference();
		
		context.getRefHandler().setLocalReference(oldRef);
		
		context.getRefHandler().setActiveReference(statementRef);
		/*
		context.getRefHandler().setLocalReference(oldRef);
		this.updateContext(context, modRef);
		*/
		stateNode.generateModule(context);
		/*
		context.getRefHandler().setLocalReference(oldLocal);
		*/
		context.getRefHandler().setActiveReference(oldRef);
		context.getRefHandler().setLocalReference(local);

		return statementRef;
	}

	public boolean canFold() {
		return true;
	}

}
