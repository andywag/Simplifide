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
import com.simplifide.base.core.var.vhdl.types.VhdlArrayTypeVar;
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

// constrained_array_definition : ARRAY index_constraint OF subtype_indication


public class VhdlConstrainedArrayDefinitionASTNode extends TopASTNode
{

  
	private static final long serialVersionUID = 1L;

	public VhdlConstrainedArrayDefinitionASTNode() {}
    public VhdlConstrainedArrayDefinitionASTNode(Token tok) {super(tok);}

    // Only Used To Specify Context Types for Nodes
    public void resolveContext(ParseContext context) {
    	int pos = this.getChildPosition(context.getDocPosition());
    	//if (pos == 1) context.setSearchContext(new SearchContext.Signal());
    	if (pos == 3) context.setSearchContext(new SearchContext.Type());
   	
    }
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child1 = this.getFirstASTChild(); // array
        TopASTNode child = child1.getNextASTSibling();          // index constraint
        ReferenceItem pointhold = ( ReferenceItem) child.generateModule(context);
       // ArrPointer point = (ArrPointer) pointhold.getObject(0);
        child = child.getNextASTSibling();          // of
        child = child.getNextASTSibling();          // subtype indication
        ReferenceItem<TypeVar> utype = (ReferenceItem<TypeVar>) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_TYPEVAR); 

        VhdlArrayTypeVar atype  = new VhdlArrayTypeVar("array",pointhold,utype);
        
        return atype.createReferenceItem();
    }
    

   





}
