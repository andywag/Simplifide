/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.loop;

import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;
import com.simplifide.base.core.segment.basic.loop.LoopIterator;
import com.simplifide.base.core.segment.basic.loop.LoopStatement;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/*

label_colon_wrap ( iteration_scheme )?
        LOOP
        sequence_of_statements 
        END LOOP ( identifier )? SEMI

*/


/** @todo Handle the iteration scheme */
public class LoopStatementNode extends TopASTNode {

	
	private static final long serialVersionUID = 1L;

	public LoopStatementNode() {}
	
	public boolean canFold() {return true;}
	
	
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
	    TopASTNode child   = this.getFirstASTChild(); // label_colon_wrap
	    
        TopASTNode itNode  = child.getNextASTSibling(); // Iteration Statement
        child = itNode.getNextASTSibling();
        TopASTNode seqNode = child.getNextASTSibling(); // Sequence Statement
        
        
        ReferenceItem oldRef = context.getRefHandler().getLocalReference();
        ReferenceItem itRef = (ReferenceItem) itNode.generateModule(context);
        if (itRef.getObject() instanceof LoopIterator) {
        	LoopIterator it = (LoopIterator) itRef.getObject();
        	ReferenceItem oth = it.getOtherContext();
        	ModuleObjectCompositeHolder hold = ModuleObjectCompositeHolder.dualHolder("", context.getRefHandler().getLocalReference(), oth);
        	context.getRefHandler().setLocalReference(hold.createReferenceItem());
        }
        
        ReferenceItem<SequenceStatements> seqRef = (ReferenceItem<SequenceStatements>) seqNode.generateModule(context);
        context.getRefHandler().setLocalReference(oldRef);
        
        LoopStatement loop = new LoopStatement(itRef,seqRef); 
        return loop.createReferenceItem();
        
    }
	
}
