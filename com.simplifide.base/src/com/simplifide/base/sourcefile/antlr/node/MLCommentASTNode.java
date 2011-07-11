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

public class MLCommentASTNode extends TextASTNode
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lines;
    private int lineAfter;

    public MLCommentASTNode() {}
    public MLCommentASTNode(Token tok)
    {
        super(tok);
    }

    public MLCommentASTNode(String[] str)
    {
        super(new TopASTToken(""));
        this.setToken(str);
    }



  

    public DocPosition setLastFormat(DocPosition pos)
    {
        DocPosition pos2 = new DocPosition(pos.getRow()+lines+1,0,pos.getDocp()+lines+
                this.getToken().getText().length());
        this.setLastPosition(pos2);
        return pos2;
    }


    public DocPosition getEndPosition()
    {
        TopASTToken tok = this.getToken();
        return new DocPosition(tok.getLine()+lines+1,0,tok.getPosition()+lines+tok.getText().length());
    }

     public String getCreateText()
    {
        return this.getToken().getText();
    }




    public void setToken(String[] alpha)
    {
        String tot = "/*";
        for (int i=0;i<alpha.length;i++)
        {
            tot +=  alpha[i];
            if (i != (alpha.length-1)) tot += "\n";
        }
        tot += "*/\n";

        TopASTToken tok = new TopASTToken(tot);

        this.setToken(tok);
        this.lines = alpha.length + 1;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getLineAfter() {
        return lineAfter;
    }

    public void setLineAfter(int lineAfter) {
        this.lineAfter = lineAfter;
    }

}
