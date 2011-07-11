/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.misc;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;




/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class LabelColonASTNode extends TopASTNode
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public LabelColonASTNode() {}
    public LabelColonASTNode(Token tok)
    {
        super(tok);
    }

    
     public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
         TopASTNode child = this.getFirstASTChild();
         ParseContext.Storage store = context.createStorage();
         context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
         TopObjectBase sc = child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT);
         context.restoreStorage(store);
         return sc;
    }
  

}
