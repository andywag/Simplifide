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
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SignalAssignment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// conditional_signal_assignment : target LE opts conditional_waveforms SEMI

public class VhdlConditionalSignalAssignmentNode extends TopASTNode {
   
	private static final long serialVersionUID = 1L;

	
	
	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlConditionalSignalAssignmentNode() {}
   

    public ReferenceItem<SignalAssignment> generateModuleSmallNew(ParseContext context)
    {

        TopASTNode outNode = this.getFirstASTChild();
        TopASTNode child = outNode.getNextASTSibling();
        child = child.getNextASTSibling();
        TopASTNode inNode = child.getNextASTSibling();
        
        ReferenceItem outRef = (ReferenceItem) outNode.generateModule(context);   
        ReferenceItem inRef = (ReferenceItem) inNode.generateModule(context); 
        
        
        SignalAssignment sig = new SignalAssignment("Assign()",inRef,outRef);
        String assValue = "";
        if (outRef == null) {
        	assValue = "Assign(" + outNode.getRealText() + ")";
        }
        else {
        	  ModuleObjectWithList<ModuleObject> list = sig.getOutputs().getObject();
              assValue = "Assign(";
              for (ModuleObject obj : list.getRealSelfList()) {
                  assValue += obj.getname() + ",";
              }
              assValue = assValue.substring(0,assValue.length()-1) + ")";
        }
        
      
        sig.setname(assValue);
        return sig.createReferenceItemWithLocation(context.createReferenceLocation(outNode));
    }
    
    
    
}
