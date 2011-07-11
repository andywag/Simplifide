/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.types.TypeDefASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

// type_declaration : TYPE identifier ( IS type_definition )? SEMI

public class VhdlTypeDefASTNode extends TypeDefASTNode
{

	private static final long serialVersionUID = 1L;

	public VhdlTypeDefASTNode() {}
    public VhdlTypeDefASTNode(Token tok) {super(tok);}
    
    public boolean canFold() {return true;}
    
    public TopASTNode formatBase() {return this;}
    public void format(FormatPosition position) {
    	FormatPosition pos = position.addNewPosition(this.getPosition().getStartPos(), this.getPosition().getEndPos());
    	pos.setIndent(position.getIndentOrZero());
    	super.format(pos);
    }
    
    public String getFoldName()
    {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	
        return this.getFirstASTChild().getRealText() + child.getRealText();
    }
    
    public void resolveContext(ParseContext context) {
    	
    	int pos = this.getChildPosition(context.getDocPosition());
    	if (pos == 3) context.setSearchContext(new SearchContext.Type());
    	
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        ReferenceItem item = context.getActiveReference().findReference(child.getRealText(), ReferenceUtilities.REF_TYPEVAR);
        ReferenceItem nitem = ModuleObjectCompositeHolder.dualHolder("", context.getActiveReference(), item).createReferenceItem();
        context.setActiveReference(nitem);
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // type
       
        child = child.getNextASTSibling();          // type name
        ReferenceLocation loc = context.createReferenceLocation(child);
        
        String typeName = child.getRealText();
        
        child = child.getNextASTSibling();          // is
        child = child.getNextASTSibling();          // type definition
        
        ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) child.generateModule(context);
        if (typeRef != null) {
        	typeRef.changeName(typeName);
            typeRef.setLocation(loc);
        }
        this.handleDocEndtoken(typeRef.getObject());
        return typeRef;
    }
    
   
    
   
    

   



}
