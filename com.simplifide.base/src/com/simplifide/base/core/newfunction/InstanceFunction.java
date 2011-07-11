/*
 * InstanceFunction.java
 *
 * Created on February 8, 2007, 5:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.newfunction;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceItemNew;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author awagner
 * 
 * Not Exactly the correct implementation of this as it requires true understanding of 
 * type checking which is currently not functional as of yet
 */
public class InstanceFunction extends BaseFunction
{    
	private ReferenceItem enclosingObject;
    private ReferenceItem<BaseFunction> head;
    private ReferenceItem<BaseFunction> body;
    /** Creates a new instance of InstanceFunction */
    public InstanceFunction() {}
    public InstanceFunction(ReferenceItem<FunctionDeclaration> declaration) {
        super(declaration);
        
    }
    
   
    public HdlDoc getDoc() {
    	if (head != null && head.getObject().getDoc() != null) return head.getObject().getDoc();
    	if (body != null && body.getObject().getDoc() != null) return body.getObject().getDoc();
    	return super.getDoc();
    }
    
    
    public int getSearchType() {
        return ReferenceUtilities.REF_FUNCTION_INSTANCE;
    }
    
    public List<ReferenceItem> getRenameItemList(ReferenceItem item,ReferenceItem enclosingItem)
    {
        return this.getNavigatorList();
    }
    
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item)
    {
        return this.getNavigatorList();
    }
    
    public boolean hasNavigatorChildren() {
    	if (head != null || body != null) return true;
    	return false;
    }
    public List<ReferenceItem> getNavigatorList() {
        ArrayList<ReferenceItem> list = new ArrayList();
        if (head != null) list.add(head);
        if (body != null) list.add(body);
        return list;
    }

    
    
    public ReferenceItem<BaseFunction> getHead() {
        return head;
    }

    public void setHead(ReferenceItem<BaseFunction> head) {
    	if (head != null && head.getObject() != null) {
    		head.getObject().setInstanceRef(this.createReferenceItem());
    	}
        this.head = head;
    }

    public ReferenceItem<BaseFunction> getBody() {
        return body;
    }

    public void setBody(ReferenceItem<BaseFunction> body) {
    	if (body != null && body.getObject() != null) {
    		body.getObject().setInstanceRef(this.createReferenceItem());
    	}
        this.body = body;
    }
	public void setEnclosingObject(ReferenceItem enclosingObject) {
		this.enclosingObject = enclosingObject;
	}
	public ReferenceItem getEnclosingObject() {
		return enclosingObject;
	}

   

   
    
}
