/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.vars.signal;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;




/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 12:18:26 PM
 * To change this template use File | Settings | File Templates.
 */

public class VarListASTNode extends TopASTNode
{


    public VarListASTNode() {}
    public VarListASTNode(Token tok)
    {
        super(tok);
    }
    
    private TopObjectBase generateNonVariableList(ParseContext context) {
        ModuleObject ulist = new ModuleObject("Holder");
        TopASTNode node = this.getFirstASTChild();
        while (node != null)
        {
            ModuleObject ob = (ModuleObject) node.generateModule(context);    
            ulist.addObject(ob);
            node = node.getNextASTSibling();
            if (node != null) node = node.getNextASTSibling();
        }
        
        return ulist;
    }
    
    private TopObjectBase generateVariableList(ParseContext context) {
        NoSortList ulist = new NoSortList("VariableHolder");
        TopASTNode node = this.getFirstASTChild();
        while (node != null) {
            ReferenceLocation loc = context.createReferenceLocation(node);
            
            ModuleObjectNew obj = new ModuleObjectNew(node.getRealText());
            ulist.addObject(obj.createReferenceItemWithLocation(loc));
            node = node.getNextASTSibling();
            if (node != null) node = node.getNextASTSibling();
        }
        return ulist;
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        if (context.getDefinedMode() == ParseContext.ERROR_NOT_DEFINED_DISABLED) {
            return this.generateVariableList(context);
        }
        else {
            return this.generateNonVariableList(context);
        }
        
    }
 

    


}
