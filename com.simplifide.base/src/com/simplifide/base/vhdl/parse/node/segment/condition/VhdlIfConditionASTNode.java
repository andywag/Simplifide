/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.condition;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.IfConditionStatement;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlIfConditionASTNode extends TopASTNodeNew<ReferenceItem<IfConditionStatement>> {
    
    
    
    
	private static final long serialVersionUID = 1L;


	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlIfConditionASTNode() {}
    
    public boolean canFold() {return true;}
    public String getFoldName()
    {
        return "If Condition";
    }
    
    public void format(FormatPosition position) {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	child = child.getNextASTSibling();
    	child = child.getNextASTSibling();
    	
    	NodePosition pos = child.getPosition();
    	if (pos != null) {
    		FormatPosition npos = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
        	npos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
        	child.format(npos);
    	}
    	
    }
    
    protected ReferenceItem<ModuleObject> handleBody(IfConditionStatement ifstate, ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	child = child.getNextASTSibling();
    	child = child.getNextASTSibling();
        //TopASTNode node = this.getNode(3);
        return this.handleGenerate(child, context);
    }
    
    protected ReferenceItem<ModuleObject> handleHead(IfConditionStatement ifstate, ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
        return this.handleGenerate(child, context);
    }
    
    
    public ReferenceItem<IfConditionStatement> generateModuleSmallNew(ParseContext context) {
        IfConditionStatement iftop = new IfConditionStatement("If-Condition Statement");
        iftop.setHeadRef(this.handleHead(iftop, context));
        iftop.setBodyRef(this.handleBody(iftop, context));
        return iftop.createReferenceItem();
    }
    
    
    /**
     * Default If Condtion
     */
    public static class Default extends VhdlIfConditionASTNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/** Constructor */
        public Default() {}
        
        public void format(FormatPosition position) {
        	TopASTNode child = this.getFirstASTChild();
        	child = child.getNextASTSibling();
        	
        	NodePosition pos = child.getPosition();
        	FormatPosition npos = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
        	npos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
        	child.format(npos);
        }
        
        protected ReferenceItem<ModuleObject> handleBody(IfConditionStatement ifstate, ParseContext context) {
            TopASTNode node = this.getNode(1);
            return this.handleGenerate(node, context);
        }
        
        protected ReferenceItem<ModuleObject> handleHead(IfConditionStatement ifstate, ParseContext context) {
            return null;
        }
        
       
        
    }
    
    
    
}
