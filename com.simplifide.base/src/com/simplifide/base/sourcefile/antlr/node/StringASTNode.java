/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import antlr.Token;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class StringASTNode extends TopASTNode
{



    public StringASTNode() {}
    public StringASTNode(Token tok)
    {
        super(tok);
    }

   




    

    public String getValue()
    {

        String text = this.getText();
        return text.substring(1,text.length()-1);
        
    }


}
