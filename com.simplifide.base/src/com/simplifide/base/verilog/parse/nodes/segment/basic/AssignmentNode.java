/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.basic;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SignalAssignment;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// left_assign ASSIGN right_assign

public class AssignmentNode extends TopASTNodeGeneric<ReferenceItem<SignalAssignment>>{

	public AssignmentNode() {}
	
	public ReferenceItem<SignalAssignment> createObjectSmall(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		TopASTNode lvalue = child;
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		TopASTNode rvalue = child;
		
		ReferenceItem outRef = (ReferenceItem) lvalue.generateModule(context);
		ReferenceItem inRef  = (ReferenceItem) rvalue.generateModule(context);
		
		SignalAssignment sig = new SignalAssignment("Assign()",inRef,outRef); 
		String assValue = "";
		if (outRef == null) {
			assValue = "Assign(" + lvalue.getRealText() + ")";
		}
		else {
			ModuleObjectWithList<ModuleObject> list = sig.getOutputs().getObject();
	        assValue = "Assign(";
	        for (ModuleObject obj : list.getRealSelfList()) {
	            assValue += obj.getname() + ",";
	        }
	        assValue = assValue.substring(0,assValue.length()-1) + ")";
		}
		
        sig.setname(assValue);
		this.generateAssignedList(sig.getOutputs().getObject());
		this.generateUsedList(sig.getDependants().getObject());
		
		return sig.createReferenceItemWithLocation(context.createReferenceLocation(lvalue));
		
	}
	
}
