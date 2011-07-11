/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.assign;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.QuestionSegment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// conditional_waveforms_bi
//: WHEN condition ELSE waveform
//( (WHEN condition ELSE)=> conditional_waveforms_bi )?

public class VhdlConditionalWaveformsBiASTNode extends TopASTNode
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlConditionalWaveformsBiASTNode() {}
   
    
    public ReferenceItem<QuestionSegment> generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // when
        TopASTNode condNode = child.getNextASTSibling(); // condition
        child = condNode.getNextASTSibling(); // else
        TopASTNode falseNode = child.getNextASTSibling(); // falseNode or trueNode for Next Question
        TopASTNode condBiNode = falseNode.getNextASTSibling();
        
        ReferenceItem condRef = (ReferenceItem) condNode.generateModule(context);
        ReferenceItem falseRef = (ReferenceItem) falseNode.generateModule(context);
        
        if (condBiNode != null) {
        	ReferenceItem<QuestionSegment> segRef = (ReferenceItem<QuestionSegment>) condBiNode.generateModule(context);
        	segRef.getObject().setTrueRef(falseRef);
        	falseRef = segRef;
        }
        
        QuestionSegment qu = new QuestionSegment("Question",condRef,null,falseRef);
        return qu.createReferenceItem();
    }
    
    
}
