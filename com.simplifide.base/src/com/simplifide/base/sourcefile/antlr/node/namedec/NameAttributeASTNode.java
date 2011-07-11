/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.namedec;

import antlr.Token;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectAttributeItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 *
 * 
 */

public class NameAttributeASTNode extends NameTopASTNode
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameAttributeASTNode() {}
    public NameAttributeASTNode(Token tok)
    {
        super(tok);
    }

    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        child.generateModule(context);
        return new ModuleObjectAttributeItem(child.getRealText());

    }
	@Override
	public NodePosition getNodeRange() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
