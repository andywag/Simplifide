/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.function;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.newfunction.BaseFunction;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlSubProgramDeclarationASTNode extends SubProgramTopASTNode
{


	public VhdlSubProgramDeclarationASTNode() {super();}
    public VhdlSubProgramDeclarationASTNode(Token tok)
    {
        super(tok);     
    }

    public boolean canFold() {
    	return true;
    }
    
    public void setFunction(ReferenceItem<InstanceFunction> inst, ReferenceItem<FunctionDeclaration> base) {
        BaseFunction.Head head = new BaseFunction.Head(base);
        head.setDoc(base.getObject().getDoc());
        head.setInstanceRef(inst);
        //if (base.getDoc() != null) inst.setDoc(base.getDoc());
        inst.getObject().setHead(head.createReferenceItemWithLocation(base.getLocation()));
    }
    
    public void resolveContext(ParseContext context) {
    	TopASTNode headerNode = this.getFirstASTChild();
    	ReferenceItem<FunctionDeclaration> funcR = (ReferenceItem<FunctionDeclaration>) headerNode.generateModule(context);
    	ModuleObjectBaseItem baseFunc = new ModuleObjectBaseItem(funcR.getname());
    	ReferenceItem<FunctionHolder> holdRef = context.getRefHandler().findLocalObject(baseFunc,ReferenceUtilities.REF_FUNCTION_HOLDER);
        if (holdRef != null) {
        	BaseFunction base = new BaseFunction(funcR);
            ReferenceItem<InstanceFunction> instRef = holdRef.getObject().findReference(base.createReferenceItem());
            if (instRef != null) {
            	ReferenceItem basR =  instRef.getObject().getBody();
            	if (basR == null) basR = instRef.getObject().getHead();
            	if (basR != null) {
            		// Add the local reference to the search reference for internal types and other things
            		ReferenceItem localR = context.getRefHandler().getLocalReference();
            		ReferenceItem searchR = context.getRefHandler().getSearchReference();
            		//ModuleObjectCompositeHolder hol = ModuleObjectCompositeHolder.dualHolder("", localR, searchR);
            		//context.getRefHandler().setSearchReference(hol.createReferenceItem());
            		context.setActiveReference(basR);
            		context.getRefHandler().setLocalReference(basR);
            	}
            }

        }
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {	
        ReferenceItem<FunctionDeclaration> funcRef = (ReferenceItem) this.getFirstASTChild().generateModule(context);
        if (funcRef == null) return null;
        
        this.handleFunctionGeneration(context,funcRef);
        return null;
    }
    
}
