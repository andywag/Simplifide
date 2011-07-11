/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.signal;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.NewNameASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarDecASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.vhdl.parse.node.vars.types.SubtypeFirstNameNode;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 *
 */

public class VhdlVarDecASTNode extends VarDecASTNode
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public VhdlVarDecASTNode() {}
    public VhdlVarDecASTNode(Token tok) {super(tok);}
   
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        SubtypeFirstNameNode firstNameNode = (SubtypeFirstNameNode) this.getFirstASTChild();
        TopASTNode secondNode = firstNameNode.getNextASTSibling();
        TopASTNode useNode = firstNameNode;
        if (secondNode instanceof NewNameASTNode) {
           useNode = secondNode;
        }
        ReferenceItem ref = (ReferenceItem) useNode.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_TYPEVAR);
        if (ref == null) {
        	TypeVar utype = new TypeVar.NotDefined(useNode.getRealText());
        	ref = utype.createReferenceItemWithLocation(context.createReferenceLocation(useNode));
        }
        return ref;
        
    }
    
 
     


}
