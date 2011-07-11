/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.assign;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.vhdl.parse.node.LabelColonWrapNode;


/**
 *
 * @author Andy Wagner
 */

// concurrent_signal_assignment_statement : label_colon_wrap postponedQ ( conditional_signal_assignment | selected_signal_assignment )

public class VhdlConcurrentSignalAssignmentNode extends TopASTNode
{
    
   
	private static final long serialVersionUID = 1L;


	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlConcurrentSignalAssignmentNode() {}
   
    
    private void handleVariableAssignments(ModuleObject returnValue) {
        if (returnValue != null) {
            this.generateAssignedList((ModuleObjectWithList)returnValue.getOutputs().getObject());
            this.generateUsedList((ModuleObjectWithList)returnValue.getDependants().getObject());
        }
    }
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
    	if (context.getPass() == BuildInterface.BUILD_FILE_CONTEXT || 
    			context.getPass() == BuildInterface.BUILD_FILE_CLOSED 	) return null;
    	
       
        LabelColonWrapNode labelColonNode = (LabelColonWrapNode) this.getFirstASTChild(); // label_colon_wrap
        TopASTNode postponedNode = labelColonNode.getNextASTSibling(); // postponedQ
        TopASTNode signalNode = postponedNode.getNextASTSibling(); // signal assignment
        
        String name = labelColonNode.getText();
        
        ReferenceItem signal = (ReferenceItem) signalNode.generateModule(context);
        if (name != null && signal != null) {
        	
        	signal.setname(name);
        	signal.getObject().setname(name);
        	signal.setLocation(context.createReferenceLocation(labelColonNode));
        }
        this.handleVariableAssignments(signal);
        return signal;
        
        
    }
    
   
    
}
