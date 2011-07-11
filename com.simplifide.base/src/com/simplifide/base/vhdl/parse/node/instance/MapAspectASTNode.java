/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.instance;

import antlr.Token;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
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

public class MapAspectASTNode extends TopASTNode
{

	private static final long serialVersionUID = 1L;

	public MapAspectASTNode() {}
    public MapAspectASTNode(Token tok)
    {
        super(tok);
    }
    
    public void format(FormatPosition position) {
		
		NodePosition pos = this.getPosition();
		if (pos != null) {
			FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
			newPosition.setIndent(FormatSupport.getInstance().getIndent());
			super.format(newPosition);
		}
		
	}
    
    public TopASTNode getPortListNode() {
    	TopASTNode ch = this.getFirstASTChild(); // port
    	if (ch == null) return null;
    	ch = ch.getNextASTSibling(); // map
    	if (ch == null) return null;
    	ch = ch.getNextASTSibling(); // (
    	if (ch == null) return null;
    	return ch.getNextASTSibling();
    }
   
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode ch = this.getFirstASTChild(); // port
        if (ch == null) return null;
        ch = ch.getNextASTSibling(); // map
        ch = ch.getNextASTSibling(); // (
        ch = ch.getNextASTSibling(); // Port List
        ReferenceItem<PortList> port = (ReferenceItem<PortList>) ch.generateModule(context);
        return port;
    }
    
    public ReferenceLocation getLastPortLocation(ParseContext context) {
    	if (this.getFirstASTChild() == null) return context.createReferenceLocation(this);
    	TopASTNode node = this.getFirstASTChild().getNextASTSibling().getNextASTSibling().getNextASTSibling().getNextASTSibling();
    	return context.createReferenceLocation(node);
    }
 
    public static class Port extends MapAspectASTNode {
        
		private static final long serialVersionUID = 1L;
		public Port() {}
        public Port(Token tok) {super(tok);}
    
    }

    public static class Generic extends MapAspectASTNode {
      
		private static final long serialVersionUID = 1L;
		public Generic() {}
        public Generic(Token tok) {super(tok);}
    
    }
    

}
