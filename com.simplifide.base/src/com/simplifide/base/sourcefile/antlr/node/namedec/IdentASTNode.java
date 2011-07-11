/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.namedec;

import antlr.Token;

import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class IdentASTNode extends IdentTopASTNode
{

    private static final long serialVersionUID = 1L;

    public IdentASTNode() {}
    public IdentASTNode(Token tok){
        super(tok);
    }

     public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        ModuleObjectFindItem item = this.createFindItem(context, -1);
        ReferenceItem out = context.getRefHandler().findProjectAndLibraryObject(item, context.getType());
        if (out == null) out = context.getRefHandler().findContextObject(item, context.getType());
        if (out == null) out = context.getRefHandler().findHardwareModuleObject(item, context.getType());
        return out;
    }
    
    public ModuleObjectFindItem createFindItem(ParseContext context, int pos) 
    {
        return new ModuleObjectBaseItem(this.getRealText());
    }

    


  

   


}
