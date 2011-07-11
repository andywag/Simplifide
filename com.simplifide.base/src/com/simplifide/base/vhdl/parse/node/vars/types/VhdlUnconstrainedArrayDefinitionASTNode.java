/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.core.var.vhdl.types.VhdlUnconstrainedArrayTypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

// unconstrained_array_definition : ARRAY LPAREN index_subtype_definition ( COMMA index_subtype_definition )* RPAREN OF subtype_indication


public class VhdlUnconstrainedArrayDefinitionASTNode extends TopASTNode
{


	private static final long serialVersionUID = 1L;

	public VhdlUnconstrainedArrayDefinitionASTNode() {}
    public VhdlUnconstrainedArrayDefinitionASTNode(Token tok) {super(tok);}

    
    // Only Used To Specify Context Types for Nodes
    public void resolveContext(ParseContext context) {
    	int pos = this.getChildPosition(context.getDocPosition());
    	//if (pos == 1) context.setSearchContext(new SearchContext.Signal());
    	if (pos > 4) context.setSearchContext(new SearchContext.Type());
   	
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child1 = this.getFirstASTChild(); // array
        TopASTNode child = child1.getNextASTSibling();          // (
        child = child.getNextASTSibling();          // index constraint
        child = child.getNextASTSibling();          // )
        //ArrPointer point = (ArrPointer) child.createModule(umod,topmod, obj, initial);
        child = child.getNextASTSibling();          // of
        child = child.getNextASTSibling();          // subtype indication
        ReferenceItem<TypeVar> utype = (ReferenceItem<TypeVar>) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_TYPEVAR); 

        VhdlUnconstrainedArrayTypeVar atype  = new VhdlUnconstrainedArrayTypeVar(context.getActiveReference().getname(),utype);
        //context.getActiveReference().addModuleObject(atype,context.createReferenceLocation(child1.getPosition().getStartPos()));
        
        return atype.createReferenceItem();
    }
    

   





}
