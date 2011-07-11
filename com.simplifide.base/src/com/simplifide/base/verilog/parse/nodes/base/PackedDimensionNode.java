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
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 *
 * @author Andy
 */
public class PackedDimensionNode extends TopASTNode {
    
    /** Creates a new instance of PackedDimensionNode */
    public PackedDimensionNode() {}

    /** @todo : Only works for a single range */
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        if (this.getNumberOfChildren() > 0) {
           return this.getFirstASTChild().generateModule(context);
        }
        return null;
    }
    
    /** Seperate method which handles multiple ranges */
    public ArrayList<ReferenceItem<VarRange>> createObjectSmallNew(ParseContext context) {
    	ArrayList<ReferenceItem<VarRange>> ranges = new ArrayList<ReferenceItem<VarRange>>();
    	TopASTNode child = this.getFirstASTChild(); 
    	while (child != null) {
    		ReferenceItem<VarRange> range = (ReferenceItem<VarRange>) child.generateModule(context);
    		ranges.add(range);
    		child = child.getNextASTSibling();
    	}
         return ranges;
    }

    
    
    
}
