/*
 * NodePosition.java
 *
 * Created on December 31, 2005, 2:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

/**
 *
 * @author awagner
 */
public class NodePosition implements Cloneable{
    
    public static NodePosition ZERO = new NodePosition(0,0,0,0);
    
    private int startPos;
    private int endPos;
    private int startLine;
    private int startCol;
    /** Creates a new instance of NodePosition */
    public NodePosition() {}
    public NodePosition(int pos)
    {
        this.startPos = pos;
        this.endPos = pos;
    }
    public NodePosition(int start, int end, int line, int col)
    {
        this.setStartPos(start);
        this.setEndPos(end);
        this.setStartLine(line);
        this.setStartCol(col);
    }
    
    public NodePosition copy()
    {
        return new NodePosition(startPos,endPos,startLine,startCol);
    }
    
    public String toString()
    {
        return "(" + getStartLine() + "," + getStartCol() + "," + getStartPos() + "," + getEndPos() + ")";
    }

    public int getLength()
    {
        return this.getEndPos() - this.getStartPos();
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }
    
    
}
