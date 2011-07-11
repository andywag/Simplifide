/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.condition;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.IfConditionStatement;
import com.simplifide.base.core.segment.basic.condition.IfConditionTop;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlIfStatementASTNode extends TopASTNodeNew<ReferenceItem<IfConditionTop>>
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlIfStatementASTNode() {}
       
   

    public ReferenceItem<IfConditionTop> generateModuleSmallNew(ParseContext context)
    {
        IfConditionTop iftop = new IfConditionTop("If Statement");
        ReferenceItem<IfConditionTop> ifTopRef = iftop.createReferenceItem();
        if (this.getNumberOfChildren() > 2) {
            for (int i=1;i<this.getNumberOfChildren()-1;i++) {
                VhdlIfConditionASTNode node = (VhdlIfConditionASTNode) this.getNode(i);
                ReferenceItem<IfConditionStatement> ifCond = node.generateModule(context);
                ifTopRef.addReferenceItem(ifCond);
            }
        }
        return ifTopRef;
    }

    public boolean canFold() {return true;}
    public String getFoldName()
    {
        return "If Statment";
    }
    
    
}
