/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.namedec;

import antlr.Token;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class NameDotASTNode extends NameTopASTNode
{


    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameDotASTNode() {}
    public NameDotASTNode(Token tok)
    {
        super(tok);
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        if (child == null) return null;
        if (child.getRealText().equalsIgnoreCase("all")) return null;
        //if (child instanceof VhdlAllLiteralASTNode) 
        //    return null;
        else 
            return new ModuleObjectBaseItem(child.getRealText());
    }
	@Override
	public NodePosition getNodeRange() {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		return child.getPosition();
	}

   
}
