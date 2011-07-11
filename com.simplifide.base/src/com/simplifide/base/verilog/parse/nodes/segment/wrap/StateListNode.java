/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.wrap;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectExpressionItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// TODO Function Calls ignored
public class StateListNode extends TopASTNode{
	
	private void convertReturnObject(SequenceStatements list, ModuleObject object) {
		if (object instanceof ReferenceItem) {
			list.addObject(object);
		}
		else if (object instanceof NoSortList) {
			NoSortList<ModuleObject> l = (NoSortList<ModuleObject>) object;
			for (ReferenceItem ref : l.getGenericSelfList()) {
				list.addObject(ref);
			}
		}
		else if (object instanceof ModuleObjectExpressionItem) { // Function Call Ignore for Now
			
		}
	}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		SequenceStatements<ModuleObject> list = new SequenceStatements<ModuleObject>();
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			ModuleObject item = (ModuleObject) child.generateModule(context);
			this.convertReturnObject(list, item);
			//list.addObject(item);
			child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}

}
