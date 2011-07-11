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
import com.simplifide.base.core.segment.basic.condition.CaseChoices;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

/*
choice ( BAR choice )*

*/
public class VhdlChoicesASTNode extends TopASTNodeNew<ReferenceItem<CaseChoices>>
{
    

    private static final long serialVersionUID = -3229728582465432204L;


    /** Creates a new instance of ProcessStatementASTNode */
    public VhdlChoicesASTNode() {}
   

    public ReferenceItem<CaseChoices> generateModuleSmallNew(ParseContext context)
    {
        CaseChoices nlist = new CaseChoices();
        TopASTNode child = this.getFirstASTChild();
        while (child != null) {
            ReferenceItem item = (ReferenceItem) child.generateModule(context);
            nlist.addObject(item);
            child = child.getNextASTSibling();
            if (child != null) child = child.getNextASTSibling();
        }
        return nlist.createReferenceItem();
        
    }

    
    
    
}
