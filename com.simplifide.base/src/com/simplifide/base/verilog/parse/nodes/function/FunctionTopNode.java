/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.function;

import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceItemNew;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.vhdl.parse.node.function.FunctionInterfaceNode;


/** Node which encompasses both functions and tasks */
// function_declaration : "function" lifetime function_body_declaration
// task_declaration : "task" lifetime task_body_declaration 

public class FunctionTopNode extends TopASTNode implements FunctionInterfaceNode{


	public TopASTNode formatBase() {return this;}
	/*public void format(FormatPosition pos) {
		FormatPosition npos = pos.addNewPosition(this.getPosition().getStartPos(),
				this.getPosition().getEndPos());
	}*/
	
    public void resolveContext(ParseContext context) {
    	ReferenceItem<InstanceFunction> instR = this.returnInstanceFunction(context);
    	if (instR == null) return ;
    	
    	ReferenceItem baseR = instR.getObject().getBody();
    	ReferenceItem useR = instR;
    	if (baseR != null) useR = baseR;
    	
    	ReferenceItem oldR = context.getActiveReference();
    	
		context.getRefHandler().setActiveReference(useR);
    	context.getRefHandler().setLocalReference(oldR);

    	String classPrefix = instR.getObject().getClassPrefix();
    	if (classPrefix != null) {
        	ModuleObjectBaseItem classItem = new ModuleObjectBaseItem(classPrefix);
        	ReferenceItem<ClassInstanceModule> cmodR = context.getRefHandler().findContextObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
    		if (cmodR == null) cmodR = context.getRefHandler().findProjectObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
    		
    		if (cmodR != null && cmodR.getObject() != null) { // Set this module (mainly for completion)
    			context.getRefHandler().setModuleReference(cmodR.getObject().getArchitectureReference());
    		}
    		
    		ModuleObjectCompositeHolder hold = ModuleObjectCompositeHolder.dualHolder("", cmodR, useR);
        	context.getRefHandler().setLocalReference(cmodR);
        	context.getRefHandler().setActiveReference(useR);
    	}
    	ReferenceItem<HardwareModule> hardr = context.getRefHandler().getModuleReference();
    	if (hardr != null && hardr.getObject() instanceof ClassModule) {
    		ClassModule hmod = (ClassModule) hardr.getObject();
    		ModuleObjectCompositeHolder hold = ModuleObjectCompositeHolder.dualHolder("", hardr, useR);
        	context.getRefHandler().setLocalReference(hold.createReferenceItem());
        
        	
    	}
    

    
    }

	protected void handleClassFunctionAddition(ParseContext context, ReferenceItem<BaseFunction> baseR) {
		
		String className = baseR.getObject().getClassPrefix();
		String funcName = baseR.getObject().getname();
		
		//baseR.changeName(funcName);
		//baseR.getObject().getDeclarationRef().changeName(funcName);
		
		ModuleObjectBaseItem classItem = new ModuleObjectBaseItem(className);
		ReferenceItem<ClassInstanceModule> cmodR = context.getRefHandler().findContextObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
		if (cmodR == null) cmodR = context.getRefHandler().findProjectObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);

		if (cmodR != null && cmodR.getObject() != null) { // Only Generate the Body if the class exists
			ClassInstanceModule cmod = cmodR.getObject();
			if (cmod.getArchitecture() == null) return;
			ReferenceItem<FunctionHolder> hfuncR = cmod.getArchitecture().findBaseReference(funcName, ReferenceUtilities.REF_FUNCTION_HOLDER);
			if (hfuncR != null && hfuncR.getObject() != null) {
				hfuncR.getObject().setClassPrefix(className);
				ReferenceItem<InstanceFunction> instR = hfuncR.getObject().findBaseReference(baseR);
				if (instR != null && instR.getObject() != null) {
					instR.getObject().setClassPrefix(className);
					instR.getObject().setBody(baseR);
				}
			}
			// Always add the function to the module
			if (hfuncR != null) {
				SuperModule smod = context.getRefHandler().getSuperModuleReference().getObject();
				ReferenceItem ref = new ReferenceItemNew(cmodR.getname(),ReferenceUtilities.REF_MODULEOBJECT,null);
				
				ReferenceItem<ClassInstanceModule> cmodRR = smod.findBaseReference(ref);
				//if (cmodRR == null) {
					ReferenceItem<ClassModule> chmodR = cmodR.getObject().getArchitectureReference();
					// Add a copy of the reference item to the supermodule to avoid cleaning
					ReferenceItem del = new ReferenceItemNew(chmodR.getname(),ReferenceUtilitiesInterface.REF_BASE_CLASS,null);
					del.setObject(chmodR.getObject());
					del.setLocation(chmodR.getLocation());
					smod.addReferenceItem(del);
				//}
			}
		}
		else {
			/* 
			FunctionHolder holder = new FunctionHolder(baseR.getname());
			holder.addReferenceItem(baseR);
			context.getRefHandler().getSuperModuleReference().getObject().addReferenceItem(holder.createReferenceItem());
			 	*/
		}
	
	}
	
	protected void handleFunctionAddition(ParseContext context, ReferenceItem<FunctionDeclaration> funcR) {
		// Calculate a function holder which is a local object holding functions
		ModuleObjectBaseItem base = new ModuleObjectBaseItem(funcR.getname());
		ReferenceItem<FunctionHolder> holderR = null;
		if (context.getActiveReference().getObject() instanceof ClassModule) {
			ClassModule cmod = (ClassModule) context.getActiveReference().getObject();
			holderR = cmod.getFunctionHolder(base.getname());
		}
		else {
			 holderR = context.getRefHandler().findLocalObject(base,ReferenceUtilities.REF_FUNCTION_HOLDER);
		}
        //BaseFunction base;
        if (holderR == null) {
            FunctionHolder holder = new FunctionHolder(funcR.getname());
            holderR = holder.createReferenceItem();
            context.getActiveReference().addReferenceItem(holderR);
            // Always add to the supermodule for outline view
            if (context.getRefHandler().getModuleReference() == null) // Add to supermodule if out of context
            	context.getRefHandler().getSuperModuleReference().getObject().addReferenceItem(holderR);

        }
        // Check to see if there is an existing instance function 
        BaseFunction baseFunc = new BaseFunction.Body(funcR);
        ReferenceItem<BaseFunction> baseFuncR = baseFunc.createReferenceItemWithLocation(funcR.getLocation());
        
        ReferenceItem<InstanceFunction> instRef = holderR.getObject().findReference(baseFuncR);
        if (instRef == null) { // Create an Instance Function if it doesn't exist
            InstanceFunction inst = new InstanceFunction(funcR);    
            instRef = inst.createReferenceItemWithLocation(funcR.getLocation());
            holderR.addReferenceItem(instRef);
        }
        instRef.getObject().setBody(baseFuncR);
        
        if (instRef.getObject().getDoc() == null) {
        	 this.handleDoc(baseFunc);
             this.handleDoc(instRef.getObject());
        }
       
        
	}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		// Node Handling
		TopASTNode child = this.getFirstASTChild(); // function
		child  = child.getNextASTSibling(); // name_of_function
		FunctionBodyNode body = (FunctionBodyNode) child.getNextASTSibling();
		// Object Creation
		ReferenceItem<BaseFunction> baseR = body.createObject(context);
		ReferenceItem<FunctionDeclaration> decR = baseR.getObject().getDeclarationRef();
		
		if (decR.getObject().isClassMethod()) {
			this.handleClassFunctionAddition(context, baseR);
		}
		else {
			this.handleFunctionAddition(context, decR);
		}
		return null;
		
		
	}
	
	private ReferenceItem<InstanceFunction> findNormalInstanceFunction(ParseContext context,
									ReferenceItem<BaseFunction> funcR) {
		ModuleObjectBaseItem base = new ModuleObjectBaseItem(funcR.getname());
        ReferenceItem<FunctionHolder> holderR = context.getRefHandler().findLocalObject(base,ReferenceUtilities.REF_FUNCTION_HOLDER);
        if (holderR == null) holderR = context.getRefHandler().findGlobalObject(base,ReferenceUtilities.REF_FUNCTION_HOLDER);

        if (holderR != null && holderR.getObject() != null) {
        	  // Check to see if there is an existing instance function 
            ReferenceItem<InstanceFunction> instRef = holderR.getObject().findReference(funcR);
            if (instRef == null) instRef = (ReferenceItem<InstanceFunction>) holderR.getObject().getObject(0);
            return instRef;
        }
        return null;
	}

	
	private ReferenceItem<InstanceFunction> findClassInstanceFunction(ParseContext context, 
			ReferenceItem<BaseFunction> baseR) {
		
		
		BaseFunction base = baseR.getObject();
		ModuleObjectBaseItem classItem = new ModuleObjectBaseItem(base.getClassPrefix());
		ModuleObjectBaseItem funcItem  = new ModuleObjectBaseItem(base.getname());
		ReferenceItem<ClassInstanceModule> cmodR = context.getRefHandler().findContextObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
		if (cmodR == null) cmodR = context.getRefHandler().findProjectObject(classItem, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
		if (cmodR == null) return null;
		ClassInstanceModule cmod = cmodR.getObject();
		context.getRefHandler().setModuleReference(cmod.getArchitectureReference());
		ReferenceItem<FunctionHolder> hfuncR = cmod.getArchitecture().findBaseReference(base.getname(), ReferenceUtilities.REF_FUNCTION_HOLDER);
		if (hfuncR != null && hfuncR.getObject() != null) {
			ReferenceItem<InstanceFunction> instR = hfuncR.getObject().findReference(baseR);
			if (instR == null) instR = hfuncR.getObject(0);
			return instR;
		}
		return null;
	}
	
	private ReferenceItem<InstanceFunction> findInstanceFunction(ParseContext context, 
			ReferenceItem<BaseFunction> funcR) {
		if (funcR.getObject().isClassMethod()) { // Class Function
			return findClassInstanceFunction(context, funcR);
		}
		else { // Normal Function
			return this.findNormalInstanceFunction(context, funcR);
		}
	}

	 
	public ReferenceItem<InstanceFunction> returnInstanceFunction(
			ParseContext context) {
		TopASTNode child = this.getFirstASTChild(); // function
		child  = child.getNextASTSibling(); // lifetime
		
		FunctionBodyNode body = (FunctionBodyNode) child.getNextASTSibling();
		
		ReferenceItem<BaseFunction> baseR =  body.createObject(context);
		return this.findInstanceFunction(context, baseR);
	}
	
}
