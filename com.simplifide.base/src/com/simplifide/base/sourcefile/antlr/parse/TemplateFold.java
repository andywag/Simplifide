/*
 * TemplateFold.java
 *
 * Created on July 25, 2006, 8:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.parse;

/**
 *
 * @author awagner
 */
public class TemplateFold {
    
    private int position;
    private int length;
    private String foldText;
    /** Creates a new instance of TemplateFold */
    public TemplateFold() {}
    public TemplateFold(int pos, int length, String text)
    {
        this.position = pos;
        this.length = length;
        this.foldText = text;
    }

    public String toString() 
    {
        return this.foldText + "(" + this.position + "," + this.length + ")";
    }
    
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFoldText() {
        return foldText;
    }

    public void setFoldText(String foldText) {
        this.foldText = foldText;
    }
    
}
