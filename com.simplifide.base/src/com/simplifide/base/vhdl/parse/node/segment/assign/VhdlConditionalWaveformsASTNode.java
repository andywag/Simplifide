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
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.QuestionSegment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlConditionalWaveformsASTNode extends TopASTNodeNew
{
    
    private static final long serialVersionUID = 1L;

    /** Creates a new instance of ProcessStatementASTNode */
    public VhdlConditionalWaveformsASTNode() {}
   
    
    // Single Expression corresponds to an equal expression 
    // Double Expression corresponds to a ? statement
    /** @todo : Fix this conditional crap */
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child1 = this.getFirstASTChild();
        TopASTNode child2 = child1.getNextASTSibling();

        ModuleObject sc1 = (ModuleObject) child1.generateModule(context);
        ReferenceItem sc1Ref = this.convertModuleObject(sc1);
        if (child2 == null) return sc1Ref;
        
        ReferenceItem<QuestionSegment> qu = (ReferenceItem<QuestionSegment>) child2.generateModule(context);
        if (qu != null) {
            qu.getObject().setTrueRef(sc1Ref);
        }
        return qu; 
    }
    
   
    
}
