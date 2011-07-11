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
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

/* subprogram_body
: subprogram_specification IS
  subprogram_declarative_part
  BEGIN
  subprogram_statement_part
  END ( subprogram_kind )? ( designator )? SEMI */

public class VhdlSubProgramBodyASTNode extends SubProgramTopASTNode {

	
	public TopASTNode formatBase() {return this;}
	
	public void format(FormatPosition pos) {
		FormatPosition npos = pos.addNewPosition(this.getPosition().getStartPos(),
				this.getPosition().getEndPos());
		npos.setIndent(pos.getIndentOrZero());
		
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		TopASTNode dec = child.getNextASTSibling();
		child = dec.getNextASTSibling();
		child = child.getNextASTSibling();
		
		dec.addFormatIndent(npos);
		child.addFormatIndent(npos);
	}
	 
	
	public VhdlSubProgramBodyASTNode() {super();}
    public VhdlSubProgramBodyASTNode(Token tok)
    {
        super(tok);
    }

    public boolean canFold() {
    	return true;
    }
    
    /** Method used to find the context of the node, used for completion and navigation operations */
    /*public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        this.resolveContext(context);
        TopASTNode node = this.getFirstASTChild();
        NodePosition npos = node.getPosition();
        if (pos > npos.getStartPos() && pos < npos.getEndPos()) return context.getActiveReference();
        return null;
    }*/
    
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
    	
    	TopASTNode headerNode = this.getFirstASTChild();
    	TopASTNode isNode = headerNode.getNextASTSibling();
    	TopASTNode decNode = isNode.getNextASTSibling();
    	TopASTNode beginNode = decNode.getNextASTSibling();
    	TopASTNode stateNode = beginNode.getNextASTSibling();
    	
        ReferenceItem<FunctionDeclaration> funcRef = (ReferenceItem) headerNode.generateModule(context);
        ReferenceItem<BaseFunction> baseR = this.handleFunctionGeneration(context, funcRef);
        
        context.getRefHandler().setActiveReference(baseR);
        decNode.generateModule(context);
        
        if (context.getPass() == ParseContext.BUILD_FIND_USAGES) {
        	stateNode.generateModule(context);
        	// Disabled because it finds multiple functions of the same name
        	// It is desired to only find the correct one
        	/*
        	TopASTNode next = stateNode.getNextASTSibling();
        	if (next != null) next = next.getNextASTSibling();
        	if (next != null) next = next.getNextASTSibling();
        	if (next != null) next.generateModule(context);
        	*/
        }
        
        
        
        
        return null;
    } 
    

    public void setFunction(ReferenceItem<InstanceFunction> inst, ReferenceItem<FunctionDeclaration> base) {
        BaseFunction.Body head = new BaseFunction.Body(base);
        head.setDoc(base.getObject().getDoc());
        inst.getObject().setBody(head.createReferenceItemWithLocation(base.getLocation()));
        
    }



}
