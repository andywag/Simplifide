/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.process;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.segment.basic.process.ProcessStatement;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.vhdl.parse.node.LabelColonWrapNode;


/**
 *
 * @param T 
 * @author Andy Wagner
 */
// process_head : label_colon_wrap postponedQ PROCESS ( LPAREN sensitivity_list RPAREN )? ( IS )?
public class VhdlProcessHeadASTNode extends TopASTNode
{

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlProcessHeadASTNode() {}
   

    public ReferenceLocation getSensitivityLocation(ParseContext context) {
        TopASTNode child = this.getFirstASTChild(); // label_colon
        child = child.getNextASTSibling(); // postponed
        child = child.getNextASTSibling(); // process
        child = child.getNextASTSibling(); // (
        if (child == null) return context.createReferenceLocation(this.getFirstASTChild());
        child = child.getNextASTSibling();
        return context.createReferenceLocation(child);
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {

        LabelColonWrapNode labelNode =  (LabelColonWrapNode) this.getFirstASTChild(); // label_colon_wrap
        TopASTNode postPoneNode = labelNode.getNextASTSibling();
        TopASTNode processNode = postPoneNode.getNextASTSibling(); // process
        TopASTNode child = processNode.getNextASTSibling(); // LPAREN | NULL
        TopASTNode senseNode = null;
        if (child != null) senseNode = child.getNextASTSibling();
        
        String processName = labelNode.getText(); // Null if no label colon
        ReferenceLocation loc;
        if (processName != null) {
        	loc = context.createReferenceLocation(labelNode);
        }
        else {
        	loc = context.createReferenceLocation(processNode);
        }
        
        NoSortList holder = new NoSortList();
        if (senseNode != null) {
        	holder = (NoSortList) senseNode.generateModule(context);
        }
       
        ProcessStatement state = new ProcessStatement(processName, holder.createReferenceItem());
      
        return (ReferenceItem<ProcessStatement>) state.createReferenceItemWithLocation(loc);
        
    }

    
    
    
}
