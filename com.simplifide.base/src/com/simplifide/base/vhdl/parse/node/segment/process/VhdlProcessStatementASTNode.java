/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.process;

import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.segment.basic.process.ProcessBody;
import com.simplifide.base.core.segment.basic.process.ProcessStatement;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.segment.process.ProcessStatementASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/*
process_statement
  : process_head
    process_declarative_part
    BEGIN
    process_statement_part 
    END ( POSTPONED )? PROCESS ( identifier )? SEMI
*/

/**
 *
 * @author Andy Wagner
 * @param T tag
 */

public class VhdlProcessStatementASTNode extends ProcessStatementASTNode {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlProcessStatementASTNode() {}

   
    
    public void format(FormatPosition position) {
		NodePosition pos = this.getPosition();
		FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
		newPosition.setIndent(position.getIndent());
		if (newPosition.getIndent() == -1) newPosition.setIndent(0);
		
		TopASTNode child = this.getFirstASTChild();
		TopASTNode head  = child.getNextASTSibling();
		child = head.getNextASTSibling();
		TopASTNode body = child.getNextASTSibling();
		
		
		int st = 0;
		if (head.getPosition() != null) st =  head.getPosition().getStartPos();
		else st = child.getPosition().getStartPos();
		int stop = body.getPosition().getEndPos();
		
		FormatPosition npos = newPosition.addNewPosition(st,stop);
		npos.setIndent(newPosition.getIndent() + FormatSupport.getInstance().getIndent());
		
		head.addFormatIndent(npos);
		body.addFormatIndent(npos);
		
		
 }
   
    
    private void updateContext(ParseContext context, ReferenceItem decItem) {
        if (decItem != null) {
            ModuleObjectCompositeHolder activeHolder = ModuleObjectCompositeHolder.dualHolder("ProcessContext",context.getRefHandler().returnLocalReference(),decItem);
            context.getRefHandler().setLocalReference(activeHolder.createReferenceItem());
        }
    }
    
    public void resolveContext(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        ReferenceItem decItem = (ReferenceItem) child.generateModule(context); // Declarative Processing
        this.updateContext(context,decItem);
    }
    
    
    
    
    public ReferenceItem<ProcessStatement> createObjectSmall(ParseContext context) {
    	
    	if (context.getPass() == BuildInterface.BUILD_FILE_CONTEXT || 
    			context.getPass() == BuildInterface.BUILD_FILE_CONTEXT 	) return null;
    	
        ReferenceLocation loc = context.createReferenceLocation(this);
        
        VhdlProcessHeadASTNode headNode = (VhdlProcessHeadASTNode) this.getFirstASTChild();
        TopASTNode decNode = headNode.getNextASTSibling();
        TopASTNode child = decNode.getNextASTSibling();
        TopASTNode stateNode = child.getNextASTSibling();
        child = child.getNextASTSibling();
        child = child.getNextASTSibling();
        
        // Handle Process Head
        //VhdlProcessHeadASTNode head =  this.returnHeadNode();     
        ReferenceItem<ProcessStatement> processRef =   (ReferenceItem<ProcessStatement>) headNode.generateModule(context);
        ProcessStatement process = processRef.getObject();
        // Handle Declarative Partt
        
        ReferenceItem<ModuleObjectWithList> decItem = (ReferenceItem<ModuleObjectWithList>) decNode.generateModule(context); // Declarative Processing
        
        ReferenceItem localReference = context.getRefHandler().returnLocalReference();
        this.updateContext(context, decItem);
       
        //VhdlProcessStatementPartASTNode state = this.returnStatementNode();
        ReferenceItem<ProcessBody> obj = (ReferenceItem<ProcessBody>) stateNode.generateModule(context); // Handle Body First
        processRef.getObject().setBodyRef(obj);
        
        
        this.handleVariables(context, process, headNode.getSensitivityLocation(context));
        
        if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
            TopASTNode node = child;
            this.handleFindUsagesOverNodes(context, node, ReferenceUtilities.REF_PROCESS_STATEMENT, 2);
        }
        if (process.getname() == null) {
        	String processName = "process" + process.getOutputListName();
        	process.setname(processName);
        	processRef.setname(processName);
        }
        
        return processRef;
    }
    
   
    
    public boolean canFold() {return true;}
    
    
    
    
}
