/*
 * SubProgramTopASTNode.java
 *
 * Created on March 19, 2007, 9:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.function;

import antlr.Token;

import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;

/**
 *
 * @author Andy
 */
public abstract class SubProgramTopASTNode extends TopASTNode implements FunctionInterfaceNode{
    
    /** Creates a new instance of SubProgramTopASTNode */
    public SubProgramTopASTNode() {}
    public SubProgramTopASTNode(Token tok) {super(tok);}
    
    public abstract void setFunction(ReferenceItem<InstanceFunction> inst, ReferenceItem<FunctionDeclaration> base);
    
    public ReferenceItem<InstanceFunction> returnInstanceFunction(ParseContext context) {
    	TopASTNode headerNode = this.getFirstASTChild();
    	ReferenceItem<FunctionDeclaration> funcR = (ReferenceItem<FunctionDeclaration>) headerNode.generateModule(context);
    	ModuleObjectBaseItem baseFunc = new ModuleObjectBaseItem(funcR.getname());
    	ReferenceItem<FunctionHolder> holdRef = context.getRefHandler().findLocalObject(baseFunc,ReferenceUtilities.REF_FUNCTION_HOLDER);
        if (holdRef != null) {
        	BaseFunction base = new BaseFunction(funcR);
            ReferenceItem<InstanceFunction> instRef = holdRef.getObject().findReference(base.createReferenceItem());
            return instRef;

        }
        return null;
    }
    
    protected ReferenceItem<BaseFunction> handleFunctionGeneration(ParseContext context, ReferenceItem<FunctionDeclaration> funcRef) {
        ModuleObjectBaseItem baseFunc = new ModuleObjectBaseItem(funcRef.getname());
        
        /** Check for the existance of a function holder (If it doesn't exist create it, and add it to the active reference */
        ReferenceItem<FunctionHolder> holdRef = context.getRefHandler().findLocalObject(baseFunc,ReferenceUtilities.REF_FUNCTION_HOLDER);
        if (holdRef == null) {
            FunctionHolder holder = new FunctionHolder(baseFunc.getname());
            holdRef = holder.createReferenceItem();
            context.getActiveReference().addReferenceItem(holdRef);
        }
        // Check to see if there is an existing instance function 
        BaseFunction baseRef = new BaseFunction(funcRef);
    	TopASTToken firstTok = this.getFirstLeafNode().getToken();
        if (firstTok.getDoc() != null) {
            baseRef.changeDoc(firstTok.getDoc());
            
        } 
        ReferenceItem baseR = baseRef.createReferenceItemWithLocation(funcRef.getLocation());
        
        ReferenceItem<InstanceFunction> instRef = holdRef.getObject().findReference(baseR);
        
        if (instRef == null) {
            InstanceFunction inst = new InstanceFunction(funcRef);
            
            
            instRef = inst.createReferenceItemWithLocation(funcRef.getLocation());
            holdRef.addReferenceItem(instRef);
        }
        instRef.getObject().setEnclosingObject(context.getActiveReference()); // Attach the enclosing object for refactoring
        //instRef.getObject().setDoc(baseRef.getDoc());
        this.setFunction(instRef,funcRef);
        baseRef.setInstanceRef(instRef);
        
        baseR =  instRef.getObject().getBody();
        if (instRef.getObject().getBody() != null) baseR.updateHdlDoc(baseR.getObject());
        return baseR;
    }
    
  
    
}
