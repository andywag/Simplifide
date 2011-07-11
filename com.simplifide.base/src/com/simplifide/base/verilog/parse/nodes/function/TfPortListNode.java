/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/** Node which encompasses both functions and tasks */
//tf_port_list : tf_port_item (COMMA tf_port_item )*


public class TfPortListNode extends TopASTNodeGeneric<ReferenceItem<FunctionPortList>>{

	
		public ReferenceItem<FunctionPortList> createObjectSmall(ParseContext context) {
			//NoSortList<PortDefault> portList = new NoSortList<PortDefault>();
			FunctionPortList ports = new FunctionPortList();
			TopASTNode child = this.getFirstASTChild();
			int index = 0;
			while (child != null) {
				ReferenceItem<PortDefault> portRef = (ReferenceItem<PortDefault> )child.generateModule(context);
				portRef.getObject().setPortNumber(index);
				ports.addReferenceItem(portRef);
				child = child.getNextASTSibling();
				if (child != null) child = child.getNextASTSibling();
				index++;
			}
			
			return ports.createReferenceItem();
			
		}
	
	
	
	
}
