/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.instance;

import antlr.Token;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class InstancePortListASTNode extends TopASTNode
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InstancePortListASTNode() {}
    public InstancePortListASTNode(Token tok)
    {
        super(tok);
    }

   

 


/*
     public ListObjectScalar createModuleSmall(SuperModule umod, TopModule topmod, ListObjectScalar obj, boolean initial)
    {
       TopASTNode ch = this.getFirstASTChild(); // (
        PortTop top = new PortTop("Port List","Instance List");
        ch = ch.getNextASTSibling(); // First Port
        while (ch != null)
        {
            PortTop port = (PortTop) ch.createModule(umod,null, obj, initial);

            if (port != null) top.addObject(port);
            ch = ch.getNextASTSibling(); // Port
            if (ch != null) ch = ch.getNextASTSibling(); // ,
        }
        return top;
    }
*/



}
