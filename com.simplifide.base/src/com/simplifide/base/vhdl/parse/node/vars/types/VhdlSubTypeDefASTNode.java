/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.SubTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
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

// subtype_declaration: SUBTYPE identifier IS subtype_indication SEMI

public class VhdlSubTypeDefASTNode extends TypeDefASTNode
{

    public VhdlSubTypeDefASTNode() {}
    public VhdlSubTypeDefASTNode(Token tok) {super(tok);}
    
    public boolean canFold() {return true;}
    
    // Only Used To Specify Context Types for Nodes
    public void resolveContext(ParseContext context) {
    	int pos = this.getChildPosition(context.getDocPosition());
    	//if (pos == 1) context.setSearchContext(new SearchContext.Signal());
    	if (pos == 3) context.setSearchContext(new SearchContext.Type());
   	
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // type
        child = child.getNextASTSibling();          // type name
        ReferenceLocation loc = context.createReferenceLocation(child);
        // Currently No Need to Search (May want to add later)
        //ModuleObject name = (ModuleObject) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.ALL);
        ModuleObject name = new ModuleObject(child.getRealText());
        
        child = child.getNextASTSibling();          // is
        child = child.getNextASTSibling();          // type definition
        ReferenceItem<? extends TypeVar> type = (ReferenceItem<? extends TypeVar>) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_TYPEVAR);
       
        SubTypeVar subType = new SubTypeVar(name.getname(),type);
        handleDocEndtoken(subType);
        return subType.createReferenceItemWithLocation(loc);
    }
    
    



}
