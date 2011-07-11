/*
 * ParseContextUsages.java
 *
 * Created on October 9, 2006, 10:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.parse;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;

/**
 *
 * @author Andy Wagner
 */
public class ParseContextUsages extends ParseContext
{
   
    private EditorFindItem editorFindItem;
    private List<EditorFindItem> findItem;
    private List<ReferenceUsage> findList = new ArrayList<ReferenceUsage>();
    
    /** Creates a new instance of ParseContextUsages */
    public ParseContextUsages(ParseDescriptor desc, int pass) 
    {
        super(desc,pass);
    }

    
    public void addUsage(ReferenceUsage usage) {
    	this.findList.add(usage);
    }
   
	public void setFindItem(List<EditorFindItem> findItem) {
		this.findItem = findItem;
	}
	public List<EditorFindItem> getFindItem() {
		return findItem;
	}
	public void setFindList(List<ReferenceUsage> findList) {
		this.findList = findList;
	}
	public List<ReferenceUsage> getFindList() {
		return findList;
	}
	public void setEditorFindItem(EditorFindItem editorFindItem) {
		this.editorFindItem = editorFindItem;
	}
	public EditorFindItem getEditorFindItem() {
		return editorFindItem;
	}
    
}
