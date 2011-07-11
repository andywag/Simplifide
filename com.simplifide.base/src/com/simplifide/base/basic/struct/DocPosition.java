/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.struct;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Jun 14, 2004
 * Time: 7:33:28 AM
 * To change this template use File | Settings | File Templates.
 */

public class DocPosition
{

    public static DocPosition ZEROPOS = new DocPosition(0,0,0);
    public static final int INDENT = 4;

    private int row;
    private int col;
    private int docp;
    private int indentPosition;



    public DocPosition() {}
    public DocPosition(int row,int col, int docp)
    {
        this(row,col,docp,0);
    }

    public DocPosition(int row,int col,int docp,int indentPosition)
    {
        this.row = row;
        this.col = col;
        this.docp = docp;
        this.indentPosition = indentPosition;
    }

    public DocPosition copy()
    {
        return new DocPosition(row,col,docp,indentPosition);
    }

    public String toString()
    {
       // if (this.indentPosition != 0)
          return "<" + row + "," + col + "," + docp + "," + indentPosition +  ">";
       // else
       //   return "<" + row + "," + col + "," + docp + ">";
    }

    public void incrementRow(int alpha)
    {
        if (alpha == 0) return;
        this.row = this.row + alpha;
        this.col = 0;
        this.docp += alpha;
    }

    public void incrementCol(int alpha)
    {
        this.col += alpha;
        this.docp += alpha;
    }

    public void incrementPosition(int alpha)
    {
        this.docp += alpha;
    }

    public void incrementIndent(int ind)
    {
      this.indentPosition += ind;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getDocp() {
        return docp;
    }

    public void setDocp(int docp) {
        this.docp = docp;
    }

    public int getIndentPosition() {
        return indentPosition;
    }

    public void setIndentPosition(int indentPosition) {
        this.indentPosition = indentPosition;
    }

}
