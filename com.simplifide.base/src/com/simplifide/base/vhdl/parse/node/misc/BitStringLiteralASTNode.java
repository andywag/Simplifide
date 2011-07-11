/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.misc;

import antlr.Token;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class BitStringLiteralASTNode extends TopASTNode
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public BitStringLiteralASTNode() {}
    public BitStringLiteralASTNode(Token tok)
    {
        super(tok);
    }

    

  /*

    public ListObjectScalar createModuleSmall(SuperModule umod, TopModule topmod, ListObjectScalar scale, boolean initial)
    {
        return this.getFirstASTChild().createModule(umod,null, scale, initial);

    }

*/

    public TopASTNode getModNameNode()
    {
        return this.getFirstASTChild();
    }



}
