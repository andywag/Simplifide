/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

public class StructListASTNode extends TopASTNode
{

    // LCurly
    // Sys_var_dec * 
    // RCurly

    public StructListASTNode() {}
    public StructListASTNode(Token tok) {super(tok);}
   
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild();
        NoSortList<SystemVar> outList = new NoSortList<SystemVar>();
        while (child != null)
        {
            NoSortList<SystemVar> varList = (NoSortList<SystemVar>) child.generateModule(context);
            for (ReferenceItem<? extends SystemVar> varItem : varList.getGenericSelfList()) {
                outList.addObject(varItem);
            }
            child = child.getNextASTSibling();
        }
        return outList;
    }
    
  
    
   
    


}
