/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.designunits;

import com.simplifide.base.basic.struct.ModuleObjectContextHolder;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.vhdl.parse.node.port.VhdlPortClauseASTNode;




/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

//entity_header
//  : generic_clause_dummy port_clause_dummy


public class VhdlEntityHeaderASTNode extends TopASTNode {
    
    
	private static final long serialVersionUID = 1L;

	
	public void format(FormatPosition position) {
		
		NodePosition pos = this.getPosition();
		FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
		newPosition.setIndent(FormatSupport.getInstance().getIndent());
		super.format(newPosition);
	}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
	    /*
        TopASTNode genericNode = this.getFirstASTChild(); // generic clause
        TopASTNode portNode = genericNode.getNextASTSibling();
        
        ReferenceItem<PortList> genericList = (ReferenceItem) genericNode.generateModule(context);
        
        // Combine the Current Search Reference With the Generic List
        ReferenceItem osearch = context.getRefHandler().getSearchReference();
        ModuleObjectCompositeHolder nsearch = ModuleObjectCompositeHolder.dualHolder("PortComposite",context.getRefHandler().getSearchReference(),genericList);
        context.getRefHandler().setSearchReference(nsearch.createReferenceItem());
        
        ReferenceItem<PortList> portList = (ReferenceItem) portNode.generateModule(context);
        
        context.getRefHandler().setSearchReference(osearch);
        
        ModInstanceDefault instance = new ModInstanceDefault("Temp", genericList, portList);
        ReferenceItem<ModInstanceDefault> instanceRef = instance.createReferenceItem();

        return instanceRef;
        */
	          
	    TopASTNode genericNode = this.getFirstASTChild(); // generic clause
	    TopASTNode portNode = genericNode.getNextASTSibling();
	    
	    ReferenceItem<PortList> genericList = (ReferenceItem) genericNode.generateModule(context);
	    
	    // Combine the Current Search Reference With the Generic List
	    ReferenceItem<ModuleObjectContextHolder> osearch = context.getRefHandler().getSearchReference();
	    if (osearch != null) {
	    	ModuleObjectContextHolder nsearch = osearch.getObject().copy();
		    nsearch.appendObject(genericList);      
		    context.getRefHandler().setSearchReference(nsearch.createReferenceItem());
	    }
	    
	    ReferenceItem<PortList> portList = (ReferenceItem) portNode.generateModule(context);
	    
	    context.getRefHandler().setSearchReference(osearch);
	    ModInstanceDefault instance = new ModInstanceDefault("Temp", genericList, portList);
	    ReferenceItem<ModInstanceDefault> instanceRef = instance.createReferenceItem();
	
	    return instanceRef;
	    
	}
    
    private ReferenceLocation getAllPortLocation(VhdlPortClauseASTNode node, ParseContext context) {
    	return node.getLastPortLocation(context);
    }
    
    public ReferenceLocation getLastGenericLocation(ParseContext context) {
    	TopASTNode genericNode = this.getFirstASTChild().getFirstASTChild();
    	if (genericNode == null) return context.createReferenceLocation(this.getFirstASTChild());
    	return this.getAllPortLocation((VhdlPortClauseASTNode)genericNode, context);
    }
    
    public ReferenceLocation getLastPortLocation(ParseContext context) {
    	TopASTNode portNode = this.getFirstASTChild().getNextASTSibling().getFirstASTChild();
    	if (portNode == null) return context.createReferenceLocation(this.getFirstASTChild());
    	return this.getAllPortLocation((VhdlPortClauseASTNode)portNode, context);
    }
    
}
