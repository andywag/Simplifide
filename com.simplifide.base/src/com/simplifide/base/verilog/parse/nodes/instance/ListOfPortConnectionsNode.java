/*
 * ListOfPortConnectionsNode.java
 *
 * Created on April 25, 2007, 2:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.instance;

import java.util.List;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 *
 * @author Andy
 */
public abstract class ListOfPortConnectionsNode extends TopASTNode{


	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ListOfPortConnectionsNode */
	public ListOfPortConnectionsNode() {}
	
	

	public static class Named extends ListOfPortConnectionsNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			PortList plist = new PortList();
			TopASTNode child = this.getFirstASTChild();
			
			while (child != null) {
				ReferenceItem connect1 = (ReferenceItem) child.generateModuleSmallNew(context);
				ReferenceItem connect = connect1;
				if (connect != null) plist.addObject(connect);
				child = child.getNextASTSibling();
				if (child != null) child = child.getNextASTSibling();
			}

			return plist.createReferenceItem();
		}
	}

	public static class Ordered extends ListOfPortConnectionsNode {

		private List<ReferenceItem<PortTop>> getPortList(ParseContext context) {
			Entity entity = (Entity) context.getRefHandler().getSecondaryReference().getObject();
			ModInstanceDefault def = (ModInstanceDefault) entity.getConnectRef().getObject();
			PortList plist = (PortList) def.getPortListRef().getObject();
			List<ReferenceItem<PortTop>>  portList = plist.getOrderedRefList();
			return portList;
		}
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			PortList plist = new PortList();
			TopASTNode child = this.getFirstASTChild();
			List<ReferenceItem<PortTop>> portList = this.getPortList(context);
			int index = 0;
			while (child != null) {
				ReferenceItem ext = (ReferenceItem) child.generateModuleSmallNew(context);
				if (index < portList.size()) {
					PortTop port = portList.get(index).getObject();
					PortConnect connect = new PortConnect(port.getLocalVarReference(),ext);
					plist.addReferenceItem(connect.createReferenceItem());
				}
				
				child = child.getNextASTSibling();
				if (child != null) child = child.getNextASTSibling();
				index++;
			}

			return plist.createReferenceItem();
		}
		
		protected ReferenceItem convertPortItem(ParseContext context, int index, ReferenceItem item) {
			if (item == null) return null;
			ReferenceItem ref = context.getRefHandler().getSecondaryReference();
			if (ref != null) {
				ModInstanceDefault def = (ModInstanceDefault) ref.getObject();
				PortList plist = (PortList) def.getPortListRef().getObject();
			}
			
			PortConnect connect = new PortConnect(item.getname(),item);
			return connect.createReferenceItem();
		}
	}


}
