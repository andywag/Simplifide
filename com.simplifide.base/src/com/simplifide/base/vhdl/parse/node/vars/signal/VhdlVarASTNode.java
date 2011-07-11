/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.signal;

import antlr.Token;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.NotFoundTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */
 
// signal_declaration : SIGNAL identifier_list COLON subtype_indication signal_kind var_assign SEMI
// variable_declaration : ( SHARED )? VARIABLE identifier_list COLON subtype_indication var_assign SEMI
// constant_declaration : CONSTANT identifier_list COLON subtype_indication var_assign SEMI

public abstract class VhdlVarASTNode extends VarASTNode
{

    public VhdlVarASTNode() {}
    public VhdlVarASTNode(Token tok) {super(tok);}
    
    // Only Used To Specify Context Types for Nodes
    public void resolveContext(ParseContext context) {
    	int pos = this.getChildPosition(context.getDocPosition());
    	//if (pos == 1) context.setSearchContext(new SearchContext.Signal());
    	if (pos == 3) context.setSearchContext(new SearchContext.Type());
    	else if (pos >= 4) {
            context.setSearchContext(new SearchContext.Signal());
            NoSortList<SystemVar> vars = (NoSortList<SystemVar>) this.generateModule(context);
            if (vars != null) {
            	ReferenceItem<SystemVar> sysR = (ReferenceItem<SystemVar>) vars.getObject(0);
                if (sysR != null) {
                	TypeVar type = sysR.getObject().getType();
                	SearchContext cont = new SearchContext.SignalOfType(type);
                	context.setSearchContext(cont);
                }
            }
            
    	}
    	
    }
    
    
    private void checkTokens(NoSortList<SystemVar> outList) {
    	int len = this.getNumberOfChildren();
    	TopASTNode node = this.getLastASTChild();
    	TopASTToken tok = node.getToken();
    	HdlDoc doc = tok.getDoc();
    	if (doc != null) {
    		outList.getObject(0).getObject().setDoc(doc);
    	}
    }
    
    protected void setLastSignal(ParseContext context) {}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
    	if (context.getPass() == BuildInterface.BUILD_FILE_CONTEXT || 
    		context.getPass() == BuildInterface.BUILD_FILE_CLOSED 	) return null;
    	
    	
        TopASTNode child = this.getFirstASTChild(); // Type (Signal/Constant/Variable)
        if (child.getType() == VhdlTokenTypes.SHARED) {
        	child = child.getNextASTSibling();
        }
        TopASTNode varListNode = child.getNextASTSibling(); // Var List
        child = varListNode.getNextASTSibling(); // COLON
        TopASTNode typeNode = child.getNextASTSibling(); // subtype_indication
        TopASTNode assignNode = typeNode.getNextASTSibling(); // assign node
        if (assignNode.getText().equalsIgnoreCase("SignalKind")) {
        	assignNode = assignNode.getNextASTSibling();
        }
        
        int emode = context.getDefinedMode();
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
        NoSortList vlist = (NoSortList) varListNode.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCELOCAL,ReferenceUtilities.REF_MODULEOBJECT);
        context.setDefinedMode(emode);
        
        ReferenceItem<? extends TypeVar> ref = (ReferenceItem<? extends TypeVar>) typeNode.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_TYPEVAR);
        if (ref == null) {
        	String retStr = typeNode.getRealText();
        	NotFoundTypeVar ty = new NotFoundTypeVar(retStr);
        	ref = ty.createReferenceItem();
        }
        
        ReferenceItem assignRef = (ReferenceItem) assignNode.generateModule(context);
        NoSortList<SystemVar> outvar = VarASTNode.createNewVar(vlist,ref,this.createOperation(),assignRef,assignNode);
        
        // Attach the locaiton of this declaration for refactoring
        if (outvar.getGenericSelfList().size() == 1) { // Only Set the declaration if the varlist contains 1 var
        	SystemVar tvar = outvar.getObject(0).getObject();
        	tvar.setDeclarationLocation(context.createReferenceLocation(this));
        }
        
        
        this.checkTokens(outvar);
        this.setLastSignal(context);
        
        return outvar;
    }
    
   
    
   
   public void addValue(TopObjectBase var,TopObjectBase value) {} 
    
   

}
