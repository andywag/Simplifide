package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.newfunction.VerilogFunctionDeclaration;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class FunctionHeadNode extends TopASTNodeGeneric<ReferenceItem<FunctionDeclaration>>{

	
	
	// task_head_declaration : task_identifier task_function_port_list SEMI;
	public static class Task extends FunctionHeadNode {
		
		 /** Method used to find the context of the node, used for completion and navigation operations */
	    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
	        // Check to see if this is the name if so return active context
	    	TopASTNode child = this.getFirstASTChild();
	    	NodePosition npos = child.getPosition();
	    	if (pos >= npos.getStartPos() && pos <= npos.getEndPos()) return context.getActiveReference();
	    	return null;
	    }
		
		public ReferenceItem<FunctionDeclaration> createObjectSmall(ParseContext context) {
			// Node Handling
			TopASTNode nameNode = this.getFirstASTChild();
			TaskFunctionPortListNode portNode = (TaskFunctionPortListNode) nameNode.getNextASTSibling();
		
			// Object Creation
			String functionName = nameNode.getRealText();
			ReferenceItem<FunctionPortList> portsR =  portNode.createObject(context);
		
			FunctionDeclaration dec = new VerilogFunctionDeclaration(functionName,null,portsR);
			ReferenceItem decR = dec.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
			return decR;
		}
	}
	
	
	//function_head_declaration_no_return : function_identifier task_function_port_list SEMI
	public static class NoReturn extends FunctionHeadNode {
		 /** Method used to find the context of the node, used for completion and navigation operations */
	    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
	    	TopASTNode child = this.getFirstASTChild();
	    	NodePosition npos = child.getPosition();
	    	if (pos > npos.getStartPos() && pos < npos.getEndPos()) {
	    		if (child.getNumberOfChildren() == 1) return context.getActiveReference(); // Case of Identifier
	    		child = child.getFirstASTChild();
	    		npos = child.getPosition();
	    		if (pos > npos.getStartPos() && pos < npos.getEndPos()) {
	    			return context.getRefHandler().getModuleReference();
	    		}
	    		else {
	    			ModuleObject obj = context.getActiveReference().getObject();
	    			if (obj instanceof BaseFunction) {
	    				BaseFunction base = (BaseFunction) obj;
	    				String cprefix = base.getClassPrefix();
	    				ReferenceItem ref = context.getRefHandler().getGlobalReference().findReference(cprefix, ReferenceUtilities.REF_MODULEOBJECT);
	    				return ref;
	    			}
	    			//BaseFunction base = context.getActiveReference().getObject();
	    			//base.getC
	    		}
	    		return context.getActiveReference();
	    		
	    	}
	    	return null;

	    }
		
		public ReferenceItem<FunctionDeclaration> createObjectSmall(ParseContext context) {
			// Node Handling
			TopASTNode nameNode = this.getFirstASTChild();
			TaskFunctionPortListNode portNode = (TaskFunctionPortListNode) nameNode.getNextASTSibling();
		
			if (context.getPass() == ParseContext.BUILD_FIND_USAGES) {
				nameNode.generateModule(context);
			}
			// Object Creation
			String functionName = nameNode.getRealText();
			ReferenceItem<FunctionPortList> portsR =  portNode.createObject(context);
		
			FunctionDeclaration dec = new VerilogFunctionDeclaration(functionName,null,portsR);
			ReferenceItem decR = dec.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
			return decR;
		}
	}
	// function_head_declaration_return : function_data_type_or_implicit function_head_declaration_no_return
	public static class Return extends FunctionHeadNode {
		
		public ReferenceItem<FunctionDeclaration> createObjectSmall(ParseContext context) {
			// Node Handling
			TopASTNode typeNode = this.getFirstASTChild(); 
			FunctionHeadNode funcNode = (FunctionHeadNode) typeNode.getNextASTSibling();
			// Object Creation
			ReferenceItem<TypeVar> typeR = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
			ReferenceItem<FunctionDeclaration> decR = (ReferenceItem<FunctionDeclaration>) funcNode.generateModule(context);
			
			decR.getObject().setOutputType(typeR);
			return decR;
			//BaseFunction bfunc = new BaseFunction(decR);
			//ReferenceItem<BaseFunction> bfuncR = bfunc.createReferenceItemWithLocation(decR.getLocation());
			
			//return bfuncR;
		}
		
		
	}
	
}
