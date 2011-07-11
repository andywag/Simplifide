/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.tok;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 10:26:45 AM
 * To change this template use File | Settings | File Templates.
 */


public class TopDocASTToken extends TopASTToken
{



    public TopDocASTToken() {super();}
    public TopDocASTToken(int type,String id)
    {
        super(type,id);
    }

    public boolean isWhiteSpace()
    {
       return true;
    }


}
