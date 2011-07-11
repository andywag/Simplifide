/*
 * ModuleInstanceNode.java
 *
 * Created on April 24, 2007, 11:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.instance;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceConnectNotFound;
import com.simplifide.base.core.interfac.InterfaceInstanceConnect;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.instance.InstanceTopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 *
 * @author Andy
 */
//module_instance : IDENTIFIER LPAREN list_of_port_connections RPAREN
public class ModuleInstanceNode extends InstanceTopASTNode{

	private static final long serialVersionUID = -3973416835212592784L;

	/** Creates a new instance of ModuleInstanceNode */
	public ModuleInstanceNode() {}
	
	public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();  // Identifier
		TopASTNode head = child.getNextASTSibling(); // LParen
		int start = head.getPosition().getStartPos();
		int stop  = this.getPosition().getEndPos();
		
		int indent = FormatSupport.getInstance().getModuleIndent() + position.getIndent();
		FormatPosition npos = position.addNewPosition(start, start+1);
		npos.setMinimum(indent);
		npos.setIndent(indent);
		npos = position.addNewPosition(start+1, stop);
		npos.setMinimum(indent+1);
		npos.setIndent(indent+1);
	}

	 public TopASTNode getPortListNode() {
	    	TopASTNode ch = this.getFirstASTChild(); // port
	    	if (ch != null) ch = ch.getNextASTSibling(); // (
	    	if (ch != null) ch = ch.getNextASTSibling(); // portlist
	    	return ch;
	    }
	

	public TopObjectBase generateModuleSmallNew(ParseContext context) {

		TopASTNode instNameNode = this.getFirstASTChild();
		TopASTNode child = instNameNode.getNextASTSibling();
		TopASTNode portListNode = child.getNextASTSibling();

		ReferenceLocation loc = context.createReferenceLocation(instNameNode);


		ReferenceItem<EntityHolder> entityRef = context.getRefHandler().getSecondaryReference();
		if (entityRef == null) return null;
		
		ModInstanceConnect connect;
		if (entityRef.getType() == ReferenceUtilities.REF_MODULEOBJECT) { // Instance Found Condition
			connect = new ModInstanceConnectNotFound(instNameNode.getRealText(),entityRef.getname());
			entityRef = null;
		}
		else { // Instance Not Found Condition
			if (entityRef.getType() == ReferenceUtilities.REF_ENTITY_INTERFACE) {
				connect = new InterfaceInstanceConnect(instNameNode.getRealText(),entityRef);
			}
			connect = new ModInstanceConnect(instNameNode.getRealText(),entityRef);
			connect.setEnclosingModuleReference(context.getRefHandler().getModuleReference().getObject().getInstModRef());
			
			
			ReferenceItem<PortList<PortTop>> plist = (ReferenceItem<PortList<PortTop>>) portListNode.generateModule(context);
			if (plist != null) {
				plist.changeName("Ports");
				connect.setPortListRef(plist);
			}
			
		}
		
		// The correct place for this is in the node above this
		//connect.setFullLocation(context.createReferenceLocation(this));


		ReferenceItem<ModInstanceConnect> connectRef = connect.createReferenceItemWithLocation(loc);
		this.attachConnectorModule(entityRef,
							context.getActiveReference(), 
							connectRef, 
							context.getActiveReference(),
							context);

		this.handleVariables(connectRef.getObject());



		return connectRef;
	}


}
