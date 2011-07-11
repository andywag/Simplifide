/*
 * VariableIdentNode.java
 *
 * Created on April 23, 2007, 4:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.vars;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObjectRangeListInitial;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.base.VariableDimensionNode;


/**
 *
 * @author Andy
 */

// variable_ident :   IDENTIFIER variable_dimension (ASSIGN expression)?
public class VariableIdentNode extends TopASTNodeGeneric<ReferenceItem<ModuleObjectRangeListInitial>>{

	private static final long serialVersionUID = 1L;

	/** Creates a new instance of VariableIdentNode */
    public VariableIdentNode() {}

    /** @todo : Add the initial condition */
    public ReferenceItem<ModuleObjectRangeListInitial> createObjectSmall(ParseContext context) {
    	// Nodes
    	TopASTNode nameNode  = this.getFirstASTChild();
    	VariableDimensionNode dimNode   = (VariableDimensionNode) nameNode.getNextASTSibling();
    	TopASTNode assignNode = dimNode.getNextASTSibling(); 
    	
    	
    	// Generate Items
        String text = nameNode.getRealText();
        ReferenceLocation loc = context.createReferenceLocation(nameNode);
        ArrayList<ReferenceItem<VarRange>> dimRef = (ArrayList<ReferenceItem<VarRange>>) dimNode.createObjectSmallNew(context);
        ReferenceItem ref = (ReferenceItem) assignNode.generateModule(context);
        
        ModuleObjectRangeListInitial range = new ModuleObjectRangeListInitial(text, dimRef,ref);
        //range.setExpressionR(ref);
      
        return range.createReferenceItemWithLocation(loc);
    }


    
}
