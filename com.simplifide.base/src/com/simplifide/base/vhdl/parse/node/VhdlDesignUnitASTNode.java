/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.SearchReferenceHolder;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */


// 

public class VhdlDesignUnitASTNode extends TopASTNode
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VhdlDesignUnitASTNode() {super();}
    public VhdlDesignUnitASTNode(Token tok)
    {
        super(tok);
        init();
    }

    private void init() {}

    /** @todo : Need to change search context functionallity */
    public void resolveContext(ParseContext context)
    {
         TopASTNode child = this.getFirstASTChild(); // Context Files
         ReferenceItem cont = (ReferenceItem) child.generateModule(context); 
         context.getRefHandler().setSearchReference(cont); 
    }
    
    private void addDoc(ModuleObject item, TopASTNode child) {
        
        TopASTToken tok = child.getFirstLeafNode().getToken();
        if (tok.getDoc() != null) {
            item.changeDoc(tok.getDoc());
        }
    }
    
    /** Normal Parsing */
    private TopObjectBase generateNormal(ParseContext context) {
        TopASTNode child = this.getFirstASTChild(); // Context Files
        ReferenceItem cont = (ReferenceItem) child.generateModule(context); 
        child = child.getNextASTSibling(); // Libary Unit (Entity, Architecture, Package Dec, Package Body)
        context.getRefHandler().setSearchReference(cont); 
        ModuleObject sc = (ModuleObject) child.generateModule(context);
        if (sc instanceof SearchReferenceHolder) {
        	SearchReferenceHolder search = (SearchReferenceHolder) sc;
        	search.setContext(cont);
        }
        this.addDoc( sc, child);
        return cont;
    }
    
    /** Context Build which only scans the file and generates the tree for compilation */
    private TopObjectBase generateFileContext(ParseContext context) {
         TopASTNode child = this.getFirstASTChild(); // Context Files
         TopObjectBase cont =  child.generateModule(context); 
         child = child.getNextASTSibling();
         child.generateModule(context);
         return cont;
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	TopObjectBase base;
        if (context.getPass() == BuildInterface.BUILD_FILE_CONTEXT) {
            base = this.generateFileContext(context);
        }
        else {
            base = this.generateNormal(context);
        }
        context.getRefHandler().setSearchReference(null);
        return base;
    }
    
    
}
