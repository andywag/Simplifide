/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.namedec;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectRange;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class NameRangeASTNode extends TopASTNode
{

    
	private static final long serialVersionUID = 1L;

	public NameRangeASTNode() {}
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // (
        child = child.getNextASTSibling();
        ReferenceItem<VarRange> rangeRef = (ReferenceItem) child.generateModule(context);
        ModuleObjectRange range = new ModuleObjectRange(rangeRef);
        return range;
    }
    
  
}
