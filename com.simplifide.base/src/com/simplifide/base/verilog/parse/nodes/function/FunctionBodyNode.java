/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.module.TopBodyNode;


/** Node which encompasses both functions and tasks */
//task_body_declaration : task_function_prefix task_identifier task_function_port_list SEMI 
//  (task_body_item)*  "endtask" (COLON task_identifier)? 

//function_body_declaration : function_data_type_or_implicit task_function_prefix function_identifier task_function_port_list SEMI
// (function_body_item)* "endfunction" (COLON function_identifier)?

public class FunctionBodyNode extends TopASTNodeGeneric<ReferenceItem<BaseFunction>>{

	
	public void format(FormatPosition position) { 
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		child.addFormatIndent(position);
		 
	 }
	
	protected void handleBodyList(ParseContext context, 
			ReferenceItem<BaseFunction> baseR,
			FunctionBodyNode.FunctionBodyList bodyList) {
		ReferenceItem act = context.getRefHandler().getActiveReference();
		ReferenceItem loc = context.getRefHandler().getLocalReference();
		
		context.setActiveReference(baseR);
		if (baseR.getObject().isClassMethod()) {
			ModuleObjectBaseItem classItem = new ModuleObjectBaseItem(baseR.getObject().getClassPrefix());
        	ReferenceItem<ClassInstanceModule> cmodR = context.getRefHandler().findContextObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
    		if (cmodR == null) cmodR = context.getRefHandler().findProjectObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);			
			if (cmodR != null) {
				ModuleObjectCompositeHolder hold = ModuleObjectCompositeHolder.dualHolder("", cmodR, baseR);
				context.getRefHandler().setLocalReference(hold.createReferenceItem());
			}
    		
		}
		if (bodyList != null) bodyList.generateModule(context);
		context.setActiveReference(act);
		context.getRefHandler().setLocalReference(loc);
	}
	
	public static class Task extends FunctionBodyNode {
		
		public boolean canFold() {return true;}
		
		public ReferenceItem<BaseFunction> createObjectSmall(ParseContext context) {
			// Node Declaration
			FunctionHeadNode.Task headNode = (FunctionHeadNode.Task) this.getFirstASTChild();
			FunctionBodyNode.FunctionBodyList bodyNode = (FunctionBodyNode.FunctionBodyList) headNode.getNextASTSibling();
			// Object Generation
			ReferenceItem<FunctionDeclaration> decR = headNode.createObject(context);
			BaseFunction base = new BaseFunction(decR);
			ReferenceItem<BaseFunction> baseR = base.createReferenceItemWithLocation(decR.getLocation());
			this.handleBodyList(context, baseR, bodyNode);
			return baseR;
			
			
			
		}
	}
	// 
	// function_head_declaration ( function_body_list "endfunction" (COLON function_identifier)?)?
	public static class Function extends FunctionBodyNode {
		
		public boolean canFold() {return true;}

		
		
		
		public ReferenceItem<BaseFunction> createObjectSmall(ParseContext context) {
			// Node Declaration
			FunctionHeadNode headNode = (FunctionHeadNode) this.getFirstASTChild(); // function_data_type_or_implicit
			FunctionBodyNode.FunctionBodyList bodyNode = (FunctionBodyNode.FunctionBodyList) headNode.getNextASTSibling();
			// Object Creation
			ReferenceItem<FunctionDeclaration> decR = headNode.createObject(context);
			BaseFunction.Body base = new BaseFunction.Body(decR);
			ReferenceItem<BaseFunction> baseR = base.createReferenceItemWithLocation(decR.getLocation());
			this.handleBodyList(context, baseR, bodyNode);
			
			return baseR;
			
		}
	}
	
	public static class FunctionBodyList extends TopBodyNode {
		
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			ModuleObjectNew mod = (ModuleObjectNew) context.getActiveReference().getObject();
			TopASTNode child = this.getFirstASTChild();
			while (child != null) {
				ModuleObject item = (ModuleObject) child.generateModule(context);
				if (item != null) this.addItem(mod, item, child);
				child = child.getNextASTSibling();
			}
			return null;
		}
		
	}
	
		
}
