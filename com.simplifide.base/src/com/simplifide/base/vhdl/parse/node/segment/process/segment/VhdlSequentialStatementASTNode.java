/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.process.segment;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlSequentialStatementASTNode extends TopASTNode
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** Statement Constructor */
      
    public VhdlSequentialStatementASTNode() {}
    

    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
    	if (context.getPass() == BuildInterface.BUILD_FILE_CONTEXT || 
    			context.getPass() == BuildInterface.BUILD_FILE_CLOSED 	) return null;
    	
    	
        TopASTNode child = this.getFirstASTChild();
        TopObjectBase ret = child.generateModule(context);
        if (context.getPass() == BuildInterface.BUILD_FIND_USAGES ) {
        	child = child.getNextASTSibling();
        	while (child != null) {
        		child.generateModule(context);
        		child = child.getNextASTSibling();
        	}
        }
        
        return ret;
    }

    
    
}
