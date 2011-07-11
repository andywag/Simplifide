/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.condition;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.condition.CaseConditionStatement;
import com.simplifide.base.core.segment.basic.condition.CaseStatementTop;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;

/**
 *
 * @author Andy Wagner
 */

// label_colon_wrap CASE expression IS( case_statement_alternative )+ case_end

public class VhdlCaseStatementASTNode extends TopASTNodeNew<ReferenceItem<CaseStatementTop>>
{
    
    private static final long serialVersionUID = 1L;

    /** Creates a new instance of ProcessStatementASTNode */
    public VhdlCaseStatementASTNode() {}
   
    public boolean canFold() {
        return true;
    }
    
    public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		
		
		int start = child.getPosition().getStartPos();
		int stop = 0;
		while (child != null) {
			child = child.getNextASTSibling();
			if (child != null && child.getNextASTSibling() != null) stop = child.getPosition().getEndPos();
		}
		FormatPosition npos = position.addNewPosition(start, stop);
		npos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
		super.format(npos);
    }

    /** @todo : Temporarily Removed Case Statement */ 
    public ReferenceItem<CaseStatementTop> generateModuleSmallNew(ParseContext context)
    {
        // Ignore the label colon section
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        
        ReferenceItem expItem = (ReferenceItem) child.generateModule(context);
        CaseStatementTop casetop = new CaseStatementTop("Case Statement",expItem);
        for (int i=4;i<this.getNumberOfChildren()-1;i++) {
            VhdlCaseConditionASTNode caseNode = (VhdlCaseConditionASTNode) this.getNode(i);
            ReferenceItem<CaseConditionStatement> uitem =  caseNode.generateModule(context);
            uitem.setname("condition" + (i-4));
            casetop.addReferenceItem(uitem);
        }
       
        return casetop.createReferenceItemWithLocation(context.createReferenceLocation(this));
       
    }

    
    
    
}
