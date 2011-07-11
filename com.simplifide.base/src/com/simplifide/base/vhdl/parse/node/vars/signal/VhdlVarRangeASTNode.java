/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.signal;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarRangeASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 12:23:57 PM
 * To change this template use File | Settings | File Templates.
 */

// [ top : bottom]
// [ top ]

/** @deprecated : This is probably no longer reachable */
public class VhdlVarRangeASTNode extends VarRangeASTNode
{

    private static final long serialVersionUID = 1L;

    public VhdlVarRangeASTNode() {}
    

    private int decodeDirection(String dir) {
        if (dir.equalsIgnoreCase("downto")) return VarRange.DOWN;
        else return VarRange.UP;
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // Top

        ReferenceItem top = (ReferenceItem) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT); 
        if (child.getNextASTSibling() == null) return top;
        
        ReferenceItem<ModuleObjectWithList> topDep = top.getObject().getDependants();
        this.generateUsedList(topDep.getObject());
        
        child = child.getNextASTSibling(); // Direction (Ignore)
        String dirText = child.getRealText();
        int dir = this.decodeDirection(dirText);
        child = child.getNextASTSibling(); // Bottom
        ReferenceItem bot = (ReferenceItem) (ReferenceItem) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT); 
        ReferenceItem<ModuleObjectWithList> botDep = bot.getObject().getDependants();
        this.generateUsedList(botDep.getObject());
        
        VarRange range = new VarRange(top,bot,dir);
        return range.createReferenceItem();
    }

/** Generates a Find Item which can be handled using the find interface. This needs
 *  support for expressions and other items */
    
  
    /** Returns a module object range based on the parsed segments */
    
   
    

}
