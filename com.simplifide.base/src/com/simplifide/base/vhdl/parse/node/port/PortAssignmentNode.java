/*
 * ModeASTNode.java
 *
 * Created on April 12, 2007, 9:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.port;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy
 */
public class PortAssignmentNode extends TopASTNode{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ModeASTNode */
    public PortAssignmentNode() {}
    
    
    public String getDefaultString() {
    	if (this.getNumberOfChildren() < 2) return "";
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	String ustr = child.getRealText();
    	return child.getRealText();
    }
   
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	if (this.getNumberOfChildren() < 2) return null;
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	
    	ModuleObject obj =  (ModuleObject) child.generateSearchTypeNew(context, ParseContext.SEARCHREFERENCECONTEXT, ReferenceUtilities.REF_MODULEOBJECT);
        return this.convertModuleObject(obj);
    }
    
}
