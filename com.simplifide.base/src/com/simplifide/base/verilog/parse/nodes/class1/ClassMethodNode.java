package com.simplifide.base.verilog.parse.nodes.class1;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.verilog.parse.nodes.function.FunctionBodyNode;
import com.simplifide.base.verilog.parse.nodes.function.FunctionHeadNode;
import com.simplifide.base.verilog.parse.nodes.function.FunctionTopNode;
import com.simplifide.base.vhdl.parse.node.function.FunctionInterfaceNode;


public abstract class ClassMethodNode extends TopASTNodeGeneric<ReferenceItem<BaseFunction>> implements FunctionInterfaceNode{

	private ReferenceItem<InstanceFunction> returnInstanceFunctionFromDeclaration(ParseContext context,
			ReferenceItem<FunctionDeclaration> funcR) {
		ModuleObjectBaseItem base = new ModuleObjectBaseItem(funcR.getname());
        ReferenceItem<FunctionHolder> holderR = context.getRefHandler().findLocalObject(base,ReferenceUtilities.REF_FUNCTION_HOLDER);
       
        // Check to see if there is an existing instance function 
        BaseFunction baseFunc = new BaseFunction(funcR);
        ReferenceItem<InstanceFunction> instRef = holderR.getObject().findReference(baseFunc.createReferenceItem());
        return instRef;
	}
	/** @TODO : Needs to be merged with SubProgramTopASTNode */
	protected void handleFunctionAddition(ParseContext context, ReferenceItem<FunctionDeclaration> funcR) {
		// Calculate a function holder which is a local object holding functions
		ModuleObjectBaseItem base = new ModuleObjectBaseItem(funcR.getname());
		
        ReferenceItem<FunctionHolder> holderR = null;
        
        if (context.getActiveReference().getObject() instanceof ClassModule) {
        	ClassModule cmod = (ClassModule) context.getActiveReference().getObject();
        	holderR = cmod.getFunctionHolder(funcR.getname());
        }
        else {
            holderR = context.getRefHandler().findActiveObject(base,ReferenceUtilities.REF_FUNCTION_HOLDER);
        }
        
        //if (holderR == null) holderR = context.getRefHandler().findLocalObject(base,ReferenceUtilities.REF_FUNCTION_HOLDER);
        
        //BaseFunction base;
        if (holderR == null) {
            FunctionHolder holder = new FunctionHolder(funcR.getname());
            holderR = holder.createReferenceItem();
            context.getActiveReference().addReferenceItem(holderR);
        }
        // Check to see if there is an existing instance function 
        BaseFunction baseFunc = new BaseFunction(funcR);
        ReferenceItem<InstanceFunction> instRef = holderR.getObject().findReference(baseFunc.createReferenceItem());
        if (instRef == null) {
            InstanceFunction inst = new InstanceFunction(funcR);
            
            instRef = inst.createReferenceItemWithLocation(funcR.getLocation());
            holderR.addReferenceItem(instRef);
        }
        baseFunc.setInstanceRef(instRef);
        //instRef.getObject().setDoc(baseFunc.getDoc());
        BaseFunction.Head head = new BaseFunction.Head(funcR);
        this.handleDoc(head);
        instRef.getObject().setHead(head.createReferenceItemWithLocation(funcR.getLocation()));
        
	}
	
	protected void handleDoc(ModuleObject dec) {
	    TopASTToken tok = getFirstLeafNode().getToken();
	    HdlDoc doc = tok.getDoc();
		dec.setDoc(doc);
		dec.updateHdlDoc(dec);
	}
	
	protected abstract ReferenceItem<FunctionDeclaration> getInstanceRef(ParseContext context);
	
	
	public ReferenceItem<InstanceFunction> returnInstanceFunction(
			ParseContext context) {
		// TODO Auto-generated method stub
		ReferenceItem<FunctionDeclaration> funcR = this.getInstanceRef(context);
		return this.returnInstanceFunctionFromDeclaration(context, funcR);
	
	}
	
	protected void handleSuperMethod(ParseContext context, ReferenceItem<BaseFunction> baseR) {
		if (baseR == null) return;
		ReferenceItem<HardwareModule> hardR = context.getRefHandler().getModuleReference();
		if (hardR == null) return;
		HardwareModule hard = hardR.getObject();
		if (hard instanceof ClassModule) {
			ClassModule class1 = (ClassModule) hard;
			ClassInstanceModule parent = class1.getSuperModule();
			ReferenceItem ref = parent.getArchitecture().findReference(baseR);
			//BaseLog.logInfo("ClassMethodNode");
		}
	}
	
	// class_method_normal : class_method_qualifier_list (function_declaration | task_declaration)
	public static class Normal extends ClassMethodNode{
		public ReferenceItem<BaseFunction> createObjectSmall(ParseContext context) {
			// Node Handling
			TopASTNode qualNode = this.getFirstASTChild();
			FunctionTopNode decNode = (FunctionTopNode) qualNode.getNextASTSibling();

			// Pass on the Documentation to the next node
		    TopASTToken tok = getFirstLeafNode().getToken();
		    decNode.getFirstLeafNode().getToken().setDoc(tok.getDoc());
		    
		   
			// Data Generation
			ReferenceItem<BaseFunction> bfuncR = (ReferenceItem<BaseFunction>) decNode.generateModule(context);
			// Extracting Super Method
			// Need to add the supermodule
			//this.handleSuperMethod(context, bfuncR);
			// Documentation 
			if (bfuncR != null && bfuncR.getObject() != null) {
				BaseFunction base = bfuncR.getObject();
				this.handleDoc(base);
			}
			//return bfuncR;
			return null;
		}

		
		protected ReferenceItem<FunctionDeclaration> getInstanceRef(ParseContext context) {
			TopASTNode qualNode = this.getFirstASTChild();
			TopASTNode decNode1 = qualNode.getNextASTSibling();
			if (decNode1 instanceof FunctionBodyNode) {
				FunctionBodyNode decNode = (FunctionBodyNode) decNode1.getNextASTSibling();
				ReferenceItem<BaseFunction> bR = decNode.createObject(context);
				return bR.getObject().getDeclarationRef();
			}
			else if (decNode1 instanceof FunctionTopNode) {
				FunctionTopNode funcNode = (FunctionTopNode) decNode1;
				ReferenceItem<InstanceFunction> instRef = funcNode.returnInstanceFunction(context);
				return instRef.getObject().getDeclarationRef();
			}
			return null;
			
		}
		
	}
	
	// class_method_proto : "extern" class_method_qualifier_list class_method_prototype
	// class_method_prototype :(function_prototype | task_prototype);
	public static class Proto extends ClassMethodNode{
		public ReferenceItem<BaseFunction> createObjectSmall(ParseContext context) {
			TopASTNode externNode = this.getFirstASTChild();
			TopASTNode qualNode = externNode.getNextASTSibling();
			MethodProto protoNode = (MethodProto) qualNode.getNextASTSibling();
			
			ReferenceItem<FunctionDeclaration> decR = protoNode.createObject(context);
			this.handleFunctionAddition(context, decR);
			return null;	
		}
		
		protected ReferenceItem<FunctionDeclaration> getInstanceRef(ParseContext context) {
			TopASTNode externNode = this.getFirstASTChild();
			TopASTNode qualNode = externNode.getNextASTSibling();
			MethodProto protoNode = (MethodProto) qualNode.getNextASTSibling();
			return protoNode.createObject(context);
		}
		
	
	}
	
	public static class MethodProto extends TopASTNodeGeneric<ReferenceItem<FunctionDeclaration>> {
		
		 public void resolveContext(ParseContext context) {
			 TopASTNode taskNode = this.getFirstASTChild();
			 TopASTNode lifeNode = taskNode.getNextASTSibling();
			 FunctionHeadNode headNode = (FunctionHeadNode) lifeNode.getNextASTSibling();
				// Object Creation
			 ReferenceItem<FunctionDeclaration> decR = headNode.createObject(context);
			 if (decR != null) {
				 ModuleObjectBaseItem base = new ModuleObjectBaseItem(decR.getname());
				 ReferenceItem<FunctionHolder> ref = context.getRefHandler().findLocalObject(base, ReferenceUtilities.REF_MODULEOBJECT);
				 if (ref != null) {
					 FunctionHolder func = ref.getObject();
					 BaseFunction base1 = new BaseFunction(decR);
					 ReferenceItem baseR = func.findReference(base1.createReferenceItem());
					 ReferenceItem oldRef = context.getActiveReference();
					 context.setActiveReference(baseR);
					 context.getRefHandler().setLocalReference(oldRef);
				 }
				 
			 }
		 }
		
	}
	// function_prototype : "function" lifetime function_head_declaration
	
	
	
	public static class FunctionProto extends MethodProto {
		public ReferenceItem<FunctionDeclaration> createObjectSmall(ParseContext context) {
			// Node Handling
			TopASTNode funcNode = this.getFirstASTChild();
			TopASTNode lifeNode = funcNode.getNextASTSibling();
			FunctionHeadNode headNode = (FunctionHeadNode) lifeNode.getNextASTSibling();
			// Object Creation
			ReferenceItem<FunctionDeclaration> decR = headNode.createObject(context);
			
			return decR;
		}
	}
	
	// task_prototype : "task" lifetime task_head_declaration
	public static class TaskProto extends MethodProto {
		public ReferenceItem<FunctionDeclaration> createObjectSmall(ParseContext context) {
			// Node Handling
			TopASTNode taskNode = this.getFirstASTChild();
			TopASTNode lifeNode = taskNode.getNextASTSibling();
			FunctionHeadNode headNode = (FunctionHeadNode) lifeNode.getNextASTSibling();
			// Object Creation
			ReferenceItem<FunctionDeclaration> decR = headNode.createObject(context);
			return decR;
			//BaseFunction base = new BaseFunction(decR);

			//return base.createReferenceItemWithLocation(decR.getLocation());
		}
	}

	
	
	
	
}
