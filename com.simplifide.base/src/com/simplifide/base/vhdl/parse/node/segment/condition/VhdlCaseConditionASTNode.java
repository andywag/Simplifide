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
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;
import com.simplifide.base.core.segment.basic.condition.CaseChoices;
import com.simplifide.base.core.segment.basic.condition.CaseConditionStatement;
import com.simplifide.base.core.segment.basic.condition.CaseConditionStatementMulti;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.vhdl.parse.node.segment.process.VhdlSequenceOfStatementsASTNode;

/**
 *
 * @author Andy Wagner
 */

//WHEN choices ARROW sequence_of_statements

public class VhdlCaseConditionASTNode extends TopASTNodeNew<ReferenceItem<CaseConditionStatement>>
{
    
    private static final long serialVersionUID = 6621158089269226277L;


    /** Creates a new instance of ProcessStatementASTNode */
    public VhdlCaseConditionASTNode() {}
    

    public ReferenceItem<CaseConditionStatement> generateModuleSmallNew(ParseContext context)
    {

        CaseConditionStatementMulti casetop = new CaseConditionStatementMulti("CaseCondition");
        TopASTNode child =  this.getFirstASTChild(); // when
        child = child.getNextASTSibling(); // Condition
        VhdlChoicesASTNode choiceNode = (VhdlChoicesASTNode) child;
        
        ReferenceItem<CaseChoices> choices = choiceNode.generateModule(context);
        casetop.setChoicesRef(choices);

        child = child.getNextASTSibling(); // Arrow
        child = child.getNextASTSibling(); // Statements
        
        VhdlSequenceOfStatementsASTNode stateNode = (VhdlSequenceOfStatementsASTNode) child;
        ReferenceItem<SequenceStatements> stateRef =  (ReferenceItem<SequenceStatements>) stateNode.generateModule(context);
        casetop.setStateRef(stateRef);

        return casetop.createReferenceItemWithLocation(context.createReferenceLocation(this));
    }

    
    
    
}
