/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import antlr.Token;

import com.simplifide.base.basic.struct.DocPosition;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Jun 14, 2004
 * Time: 3:54:09 PM
 * To change this template use File | Settings | File Templates.
 */

public class TextASTNode extends TopASTNode
{
    private DocPosition lastPosition;

    public TextASTNode() {}
    public TextASTNode(Token tok)
    {
        super(tok);
    }

    

   
   

    

    public DocPosition setLastFormat(DocPosition pos)
    {
        DocPosition pos2 = pos.copy();
        pos2.incrementCol(this.getText().length());
        this.setLastPosition(pos2);
        return pos2;
    }


    public DocPosition getEndPosition()
    {
        return this.getToken().getEndDocPosition();
    }

    public String getCreateText()
    {
        return this.getToken().getText();
    }

    public DocPosition getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(DocPosition lastPosition) {
        this.lastPosition = lastPosition;
    }

    public DocPosition getStartPosition()
    {
        return this.getToken().getStartDocPosition();
    //    return startPosition;
    }

    public void setStartPosition(DocPosition startPosition) {
    }




}
