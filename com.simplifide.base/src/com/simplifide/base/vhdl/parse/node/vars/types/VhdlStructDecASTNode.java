/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.StructTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.types.StructDecASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlStructDecASTNode extends StructDecASTNode
{

    // Struct Type
    // Packed Type ?
    // Struct List
    // Range Top
    
    private static final long serialVersionUID = 1L;

    public VhdlStructDecASTNode() {}
    public VhdlStructDecASTNode(Token tok) {super(tok);}
   
    public TopASTNode formatBase() {return this;}
    
    public void format(FormatPosition position) {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	child.addFormatIndent(position);
    }
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // record
        child = child.getNextASTSibling();          // record element
        ReferenceLocation loc = context.createReferenceLocation(child);
        
        StructTypeVar struct = new StructTypeVar(context.getActiveReference().getname());
        ReferenceItem<TypeVar> structRef = struct.createReferenceItemWithLocation(loc);
        
        NoSortList<SystemVar> varList = (NoSortList<SystemVar>) child.generateModule(context);;
        for (ReferenceItem<? extends SystemVar> varItem : varList.getGenericSelfList()) {
            structRef.addReferenceItem(varItem);
        }
        
        return structRef;
    }
    

}
