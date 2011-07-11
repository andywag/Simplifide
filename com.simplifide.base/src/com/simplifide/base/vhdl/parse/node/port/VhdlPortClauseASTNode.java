/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.port;

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

/* Need to Split Up Instances from Module Ports ASAP */

// Convenience Holder Node
public abstract class VhdlPortClauseASTNode extends TopASTNode {
    
    
    public VhdlPortClauseASTNode() {}
    
    
    public boolean canFold() {return true;}
    
    public abstract String getFoldName();
    public abstract boolean isGeneric();
    public abstract String getPortListName();
    
    public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling(); // PortList
		
		NodePosition pos = child.getPosition();
		FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
		newPosition.setIndent(FormatSupport.getInstance().getModuleIndent());
		child.format(newPosition);
	}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {

        TopASTNode node = this.getFirstASTChild(); // type (generic/port)

        node = node.getNextASTSibling();           // (
        node = node.getNextASTSibling();           // PortList
        ReferenceItem<PortList> portListRef = (ReferenceItem<PortList>) node.generateModule(context);
        portListRef.getObject().setname(this.getPortListName());
        portListRef.getObject().setGeneric(this.isGeneric());
         
        return portListRef;
    }
    
    public ReferenceLocation getLastPortLocation(ParseContext context) {
    	TopASTNode node = this.getFirstASTChild().getNextASTSibling().getNextASTSibling().getNextASTSibling();
    	return context.createReferenceLocation(node);
    }
    
    public static class Generic extends VhdlPortClauseASTNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Generic() {}

        
        public boolean isGeneric() {return true;}
        public String getFoldName() {return "Generic Ports";}
        public String getPortListName() {return "Generic";}
    }
    
    public static class Port extends VhdlPortClauseASTNode {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Port() {}

        
        public boolean isGeneric() {return false;}
        public String getFoldName() {return "Ports";}
        public String getPortListName() {return "Ports";}
    }
    
    
    
    
    
    
    
}
