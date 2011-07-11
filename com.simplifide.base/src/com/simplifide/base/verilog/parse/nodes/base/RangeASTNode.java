/*
 * RangeASTNode.java
 *
 * Created on April 23, 2007, 2:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.base;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy
 */
public class RangeASTNode extends TopASTNodeGeneric<ReferenceItem<VarRange>>{
    
    /** Creates a new instance of RangeASTNode */
    public RangeASTNode() {}

    @Override
    
    public ReferenceItem<VarRange> createObjectSmall(ParseContext context) {
    	
    	 TopASTNode child = this.getFirstASTChild();
         TopASTNode topNode = child.getNextASTSibling();
         child = topNode.getNextASTSibling();
         TopASTNode botNode = child.getNextASTSibling();
         
     	ReferenceItem top = (ReferenceItem) topNode.generateModule(context);
     	if (top != null && top.getname().equalsIgnoreCase("op")) {
     		ModuleObjectNew str = new ModuleObjectNew(topNode.getRealText());
     		top = str.createReferenceItem();
     	}
     	
     	ReferenceItem bot;
     	if (botNode != null) {
     		bot = (ReferenceItem) botNode.generateModule(context);
     		if (bot != null && bot.getname().equalsIgnoreCase("op")) {
         		ModuleObjectNew str = new ModuleObjectNew(botNode.getRealText());
         		bot = str.createReferenceItem();
         	}
     	}
     	else {
     		bot = top;
     	}
       
         VarRange range = new VarRange(top,bot,VarRange.NONE);
         return range.createReferenceItem();
    }
    
    public static class Question extends RangeASTNode {
    	
    	public ReferenceItem<VarRange> createObjectSmall(ParseContext context) {
       	 	RangeASTNode child = (RangeASTNode) this.getFirstASTChild();
       	 	return child.createObject(context);
       }
    }

    
}
