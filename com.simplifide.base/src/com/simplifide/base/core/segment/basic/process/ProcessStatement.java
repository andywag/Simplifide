/*
 * Process.java
 *
 * Created on December 6, 2005, 5:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.process;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.segment.CodeSegment;

/**
 *
 * @author Andy Wagner
 */
public class ProcessStatement extends CodeSegment {
    
    private ReferenceItem<NoSortList> headRef;
    private ReferenceItem<ProcessBody> bodyRef;
    
    
    /** Creates a new instance of Process */
    public ProcessStatement() {}
    
   
    public ProcessStatement(String name,  ReferenceItem<NoSortList> obj) {
    	this(name,obj,null);
       
    }
    
    public ProcessStatement(String name, ReferenceItem<NoSortList> headRef,
    		ReferenceItem<ProcessBody> bodyRef) {
    	super(name);
    	this.headRef = headRef;
    	this.bodyRef = bodyRef;
    }
    
    
    public String getDisplayName() {
        //return this.getname() + this.getOutputListName();
    	return super.getDisplayName();
    }

    public String getOutputListName() {
    	String procName =  "(";
        ModuleObjectWithList<ModuleObject> listObj =  this.getOutputs().getObject();
        boolean first = true;
        for (ModuleObject item : listObj.getGenericSelfList()) {
            if (!first) procName += ",";
            procName += item.getDisplayName();
            first = false;
        }
        procName += ")";
        
        return procName;
    }
    
    
    public ReferenceItem<ModuleObjectWithList> getOutputs() {
        if (bodyRef != null) {
            return bodyRef.getOutputs();
        }
        return super.getOutputs();
    }
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        if (bodyRef != null) {
            return bodyRef.getDependants();
        }
        return super.getDependants();
    }

    
    public ArrayList<ModuleObject> checkDependencyList(ModuleObjectWithList<ModuleObject> deps) {
        ArrayList<ModuleObject> diffList = new ArrayList<ModuleObject>();
        if (this.headRef == null) return diffList;
        NoSortList<ModuleObject> head = this.headRef.getObject();
        
        boolean add;
        for (ModuleObject depItem :  deps.getGenericSelfList()) {
            add = true;
            for (ModuleObject headItem : head.getGenericSelfList()) {
                if (depItem.getname().equalsIgnoreCase(headItem.getname())) {
                    add = false;
                    break;
                }
            }
            if (add) {
                diffList.add(depItem);
            }
        }
        return diffList;
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_PROCESS_STATEMENT;
    }
    
    public ReferenceItem<NoSortList> getHeadRef() {
        return headRef;
    }
    
    public void setHeadRef(ReferenceItem<NoSortList> headRef) {
        this.headRef = headRef;
    }
    
    public ReferenceItem<ProcessBody> getBodyRef() {
        return bodyRef;
    }
    
    public void setBodyRef(ReferenceItem<ProcessBody> bodyRef) {
        this.bodyRef = bodyRef;
    }
    
   
    
    
}
