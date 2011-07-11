/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/** @deprecated : Verilog Style Functions */
public class FunctionVarListNode extends TopASTNode{
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		NoSortList list = new NoSortList();
		TopASTNode child = this.getFirstASTChild();
		while (child != null) {
			
			NoSortList<ModuleObject> varList = (NoSortList) child.generateModule(context);
			if (varList != null) {
				for (ReferenceItem item : varList.getGenericSelfList()) {
					if (ReferenceUtilities.checkType(item.getType(), ReferenceUtilities.REF_PORT_TOP) == 0) {
						list.addObject(item);
					}
				}
			}
			child = child.getNextASTSibling();
		}
		return list.createReferenceItem();
	}

}
