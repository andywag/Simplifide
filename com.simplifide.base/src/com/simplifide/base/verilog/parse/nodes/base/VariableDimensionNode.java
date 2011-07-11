/*
 * PackedDimensionNode.java
 *
 * Created on April 23, 2007, 5:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.base;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 *
 * @author Andy
 */
public class VariableDimensionNode extends TopASTNodeGeneric {
    
    /** Creates a new instance of PackedDimensionNode */
    public VariableDimensionNode() {}

    /** @todo : Only works for a single range */
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        if (this.getNumberOfChildren() > 0) {
           return this.getFirstASTChild().generateModule(context);
        }
        return null;
    }
    
    /** Seperate method which handles multiple ranges */
    public ArrayList<ReferenceItem<VarRange>> createObjectSmallNew(ParseContext context) {
    	PackedDimensionNode pack = (PackedDimensionNode) this.getFirstASTChild();
    	return pack.createObjectSmallNew(context);
    }

    
    
    
}
