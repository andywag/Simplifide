/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import antlr.Token;

import com.simplifide.base.basic.struct.DocPosition;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Jun 8, 2004
 * Time: 3:46:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class SLCommentASTNode extends TextASTNode
{

    private int lineAfter;

    public SLCommentASTNode() {}
    public SLCommentASTNode(Token tok)
    {
        super(tok);
    }

    public SLCommentASTNode(String text)
    {
        super(new TopASTToken("// " + text));
    }

    public SLCommentASTNode(String text,int la)
    {
        super(new TopASTToken("// " + text));
        this.lineAfter = la;
    }



    public DocPosition setLastFormat(DocPosition pos)
    {
        DocPosition pos2 = new DocPosition(pos.getRow() + 1 + this.lineAfter,0,pos.getDocp()+this.getToken().getText().length()+1);
        this.setLastPosition(pos2);
        return pos2;
    }



     public String getCreateText()
    {
        return this.getToken().getText();
    }

    public void setToken(String alpha)
    {
        String tot = "// " + alpha;

        TopASTToken tok = new TopASTToken(tot);
        this.setToken(tok);

    }

    public int getLineAfter() {
        return lineAfter;
    }

    public void setLineAfter(int lineAfter) {
        this.lineAfter = lineAfter;
    }

}
