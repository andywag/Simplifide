/*
 * IoDeclarationNode.java
 *
 * Created on April 24, 2007, 1:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 *
 * @author Andy
 */
// ("input" | "inout" | "output") port_type list_of_variable_identifiers
public class IoTopDeclarationNode extends TopASTNode{
    
    
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of IoDeclarationNode */
    public IoTopDeclarationNode() {}

   
    
   
    
    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
    	context.getRefHandler().getModuleReference().getObject().getIoDeclarationList().add(context.createReferenceLocation(this));
    	TopObjectBase base =  child.generateModuleSmallNew(context);
    	
    	child = child.getNextASTSibling();
    	if (child != null) {
    		HdlDoc doc = child.getToken().getDoc();
    		if (doc != null) {
    			NoSortList<ModuleObject> varList = (NoSortList<ModuleObject>) base;
            	ModuleObject tvar = varList.getObject(0).getObject();
            	tvar.setDoc(doc);
    		}
    		
    	}
    	
    	
    	
    	return base;
    }

    
}
