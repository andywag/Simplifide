/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.instance;

import com.simplifide.base.sourcefile.antlr.node.port.PortASTNode;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class InstancePortDotASTNode extends PortASTNode
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InstancePortDotASTNode() {}
    

    


  
                                                


/*
     public ListObjectScalar createModuleSmall(SuperModule umod, TopModule topmod, ListObjectScalar obj, boolean initial)
    {
         TopASTNode ch = this.getFirstASTChild();

        ch = ch.getNextASTSibling(); // Lower Variable
        ListObjectScalar sc = ch.createModule(umod,null, obj, initial);
        ch = ch.getNextASTSibling(); // Open Par
        ch = ch.getNextASTSibling();
        SystemVar tvar = (SystemVar) ch.createModule(umod,null, obj, initial);

        PortTop port = new PortConnectDot(sc.getname(),tvar);
        return port;

       
    }

*/


}
