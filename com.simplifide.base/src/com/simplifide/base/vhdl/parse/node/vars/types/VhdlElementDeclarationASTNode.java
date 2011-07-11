/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.node.vars.signal.VhdlVarASTNode;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */
// element_declaration : identifier_list COLON element_subtype_definition SEMI

public class VhdlElementDeclarationASTNode extends VhdlVarASTNode
{

	private static final long serialVersionUID = 1L;







	public VhdlElementDeclarationASTNode() {}
    public VhdlElementDeclarationASTNode(Token tok) {super(tok);}
   

    public void resolveContext(ParseContext context) {
    	int pos = this.getChildPosition(context.getDocPosition());
    	//if (pos == 1) context.setSearchContext(new SearchContext.Signal());
    	if (pos == 2) context.setSearchContext(new SearchContext.Type());
   	
    }
    
    
    private void checkTokens(NoSortList<SystemVar> outList) {
    	int len = this.getNumberOfChildren();
    	TopASTNode node = this.getLastASTChild();
    	TopASTToken tok = node.getToken();
    	HdlDoc doc = tok.getDoc();
    	if (doc != null) {
    		SystemVar tvar = outList.getObject(0).getObject();
    		tvar.setDoc(doc);
    	}
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // varlist
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
        NoSortList sc1 = (NoSortList) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCELOCAL,ReferenceUtilities.REF_SYSTEMVAR);
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_ENABLED);
                
        child = child.getNextASTSibling(); // :
        child = child.getNextASTSibling(); // type indication
        ReferenceItem<? extends TypeVar> ref = (ReferenceItem<? extends TypeVar>) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_TYPEVAR);
        NoSortList<SystemVar> outvar = VarASTNode.createNewVar(sc1,ref, new OperatingTypeVar.SignalVar(),null,null);
        this.checkTokens(outvar);
        return outvar;  
    }
    
   
    
    

   

    public OperatingTypeVar createOperation() 
    {
        return new OperatingTypeVar.SignalVar();
    }

}
