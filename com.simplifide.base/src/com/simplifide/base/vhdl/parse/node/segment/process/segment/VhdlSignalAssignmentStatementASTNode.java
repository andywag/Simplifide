/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.process.segment;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SignalAssignment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlSignalAssignmentStatementASTNode extends TopASTNode
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlSignalAssignmentStatementASTNode() {}
  

    public ReferenceItem<SignalAssignment> generateModuleSmallNew(ParseContext context)
    {
        TopASTNode outNode = this.getFirstASTChild();
        TopASTNode child = outNode.getNextASTSibling();
        TopASTNode inputNode = child.getNextASTSibling();
    	
       
        ReferenceItem outRef = (ReferenceItem) outNode.generateModule(context);
        ReferenceItem inRef  = (ReferenceItem) inputNode.generateModule(context);
        
        SignalAssignment assign = new SignalAssignment("Assign",inRef,outRef);
        return assign.createReferenceItem();
        
    }

    
    
}
