/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.vars.param;

import java.util.ArrayList;
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


// list_of_param_assignments : param_assignment (COMMA param_assignment)*

public class ParameterListNode extends TopASTNode{

	private static final long serialVersionUID = 1L;

	
	
	public static class  Ordered extends ParameterListNode {
		
		private List<ReferenceItem<PortTop>> getPortList(ParseContext context) {
			Entity entity = (Entity) context.getRefHandler().getSecondaryReference().getObject();
			if (entity != null && entity.getConnectRef() != null) {
				ModInstanceDefault def = (ModInstanceDefault) entity.getConnectRef().getObject();
				PortList plist = (PortList) def.getGenericListRef().getObject();
				List<ReferenceItem<PortTop>>  portList = plist.getOrderedRefList();
				return portList;
			}
			
			return new ArrayList();
		}
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			
			List<ReferenceItem<PortTop>> portList = getPortList(context);
			PortList list = new PortList("Parameters"); 
			
			TopASTNode child = this.getFirstASTChild();
			int index = 0;
			while (child != null) {
				ReferenceItem item = (ReferenceItem) child.generateModule(context);
				if (index < portList.size()) {
					PortTop port = portList.get(index).getObject();
					PortConnect connect = (PortConnect) item.getObject();
					connect.setLocalVarReference(port.getLocalVarReference());
					item.changeName(port.getLocalVarReference().getname());
				}
				if (item != null) {
					list.addObject(item);
				}
				child = child.getNextASTSibling();
				if (child != null) child = child.getNextASTSibling();
				index++;
			}
			return list.createReferenceItem();
		}
	}
	
	public static class Named extends ParameterListNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			PortList list = new PortList("Parameters");
			
			TopASTNode child = this.getFirstASTChild();
			while (child != null) {
				ReferenceItem item = (ReferenceItem) child.generateModule(context);
				list.addObject(item);
				child = child.getNextASTSibling();
				if (child != null) child = child.getNextASTSibling();
			}
			return list.createReferenceItem();
		}
	}
	
}
