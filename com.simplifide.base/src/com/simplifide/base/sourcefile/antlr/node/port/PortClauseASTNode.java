/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.port;

import antlr.Token;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

/* Need to Split Up Instances from Module Ports ASAP */

// Convenience Holder Node
public class PortClauseASTNode extends TopASTNode
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PortClauseASTNode() {}
    public PortClauseASTNode(Token tok)
    {
        super(tok);
    }

   

   /*

    public ListObjectScalar createModuleSmall(SuperModule umod,TopModule topmod, ListObjectScalar sc, boolean initial)
    {

        TopASTNode node = this.getChildNumber(1);
        ModInstanceTop inst = (ModInstanceTop) sc;
        PortTop port = (PortTop) node.createModule(umod,null, sc, initial);
        inst.setPort(port);
        return port;
    }
    */
   

}
