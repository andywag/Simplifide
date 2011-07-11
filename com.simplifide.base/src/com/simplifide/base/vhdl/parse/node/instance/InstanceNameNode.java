/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.instance;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NewNameASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;





/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

// LabelColon (Instance Name )
// ModuleName
// PortMap
// GenericMap

public class InstanceNameNode extends TopASTNode {
    

	private static final long serialVersionUID = 1L;

	public InstanceNameNode() {}
    public InstanceNameNode(Token tok) {
        super(tok);
    }
    
    public String getEntityName() {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	return child.getRealText();
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        return child.generateModule(context);
    }
    
    public String getDecString() {
    	TopASTNode child = this.getFirstASTChild().getNextASTSibling();
    	return child.getRealText();
    }
    
    public ReferenceLocation getDecLocation(ParseContext context) {
    	TopASTNode nameNode = this.getFirstASTChild().getNextASTSibling();
    	if (nameNode instanceof NewNameASTNode) {
    		NewNameASTNode nNode = (NewNameASTNode) nameNode;
    		return nNode.getLastLocation(context);
    	}
    	return context.createReferenceLocation(this.getFirstASTChild().getNextASTSibling());
    }
    
    
    public static class Entity extends InstanceNameNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Entity() {}
        public Entity(Token tok) {super(tok);}
        
        public String getDecString() {
        	TopASTNode child = this.getFirstASTChild().getNextASTSibling();
        	return "entity " + child.getRealText();
        }
        
    }
    
    
    
    
    
    
    
    
    
    
}
