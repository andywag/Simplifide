/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.parse;

import com.simplifide.base.core.reference.ReferenceItem;

public class EditorFindItem {
    private ReferenceItem enclosingItem;
    private ReferenceItem item;
    private int[] position;
    
    /** Creates a new instance of EditorFindItem */
    public EditorFindItem() {}
    public EditorFindItem( ReferenceItem item, ReferenceItem enclosingItem, int spos, int epos)
    {
        this.enclosingItem = enclosingItem;
        this.item = item;
        this.position = new int[2];
        this.position[0] = spos;
        this.position[1] = epos;
       
    }
    
    
    public ReferenceItem getEnclosingItem() {return this.enclosingItem;}
     public ReferenceItem getItem() {return this.item;}
    public int[] getPosition() {return this.position;}
    
    public void setEnclosingItem(ReferenceItem enclosingItem) {this.enclosingItem = enclosingItem;}
    public void setItem(ReferenceItem item) {this.item = item;}
    public void setPosition(int[] pos) {this.position = pos;}
}
