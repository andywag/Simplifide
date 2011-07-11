/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.struct;

/**
 * <p>Title: Code Generation</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

// Top Level Class which contains the standard list operation
// used for all objects in the design


import java.util.ArrayList;

import javax.swing.event.ChangeEvent;

import com.simplifide.base.basic.lists.ArrayListGeneral;
import com.simplifide.base.basic.lists.ArrayListWrap;
import com.simplifide.base.basic.struct.change.TopObjectChange;

public class TopObjectBase<T extends TopObjectBase> extends TopObject implements Cloneable {
    
    private ArrayListWrap<T> arrayList;
    
    public TopObjectBase() {
        super();
    }
    public TopObjectBase(String name1) {
        super(name1);
    }
    
    
    
    /* Method which can override the type of list used for this model */
    protected ArrayListWrap createNewArrayList() {
        return new ArrayListGeneral();
    }
    
    private void createSelfList() {
        if (this.getArrayList() == null) {
            this.setArrayList(this.createNewArrayList());
        }
    }
    
    
    /** This method adds the list from the input to this */
    public void addSelfList(TopObjectBase scale) {
        this.createSelfList();
        this.getArrayList().addSelfList(scale);
    }
    
    /** @deprecated : Should not have any effect on operations */
    public void sortSelfList() {
        //Collections.sort(this.getselflist());
    }
    
    /** Gets the number of elements in the listobject */
    public int getnumber() {
        if (this.getArrayList() == null) return 0;
        return this.getArrayList().size();
    }
    
    /** Returns the object at the index i */
    public T getObject(int i) {
        if (this.getArrayList() == null) return null;
        if (this.getArrayList().size() > i) return  this.getArrayList().getObject(i);
        else return null;
    }
    
    /** Used by ModuleObjectFindItemList to Bypass it's parent */
    protected void addObjectBypass(T inval) {
        if (inval != null) {
            if (this.getArrayList() == null) this.createSelfList();
            this.getArrayList().addObject(inval);
            this.addUpdate(inval,this.getArrayList().size());
        }
    }
    
    /** Generic addition to the list of values */
    public void addObject(T inval) {
        if (inval != null) {
            if (this.getArrayList() == null) this.createSelfList();
            this.getArrayList().addObject(inval);
            this.addUpdate(inval,this.getArrayList().size());
        }
    }
    
    private void addUpdate(T inval,int ind) {   }
    
    
    /** Removes the object at the ith index */
    public void removeObject(int i) {
        if (this.getArrayList() == null) return;
        TopObjectBase uval = this.getObject(i);
        //uval.removeListener(this);
        this.getArrayList().removeObject(i);
        
        //ListObjectChange change1 = new ListObjectChange(this, uval,
        //        ListObjectChange.REMOVE, i);
        //this.valueChanged(change1);
    }
    
    
    /** Removes this object from the list if it exists */
    public void removeObject(T inval) {
        if (this.getArrayList() == null) return;
        this.getArrayList().removeObject(inval);
        //this.fireChange();
    }
    
    
    /** Deletes this object and all of its corresponding children */
    public void deleteObjectwithChildren() {
        
        if (this.getArrayList() == null) return;
        for (int i=this.getnumber()-1;i>=0;i--) {
            this.getObject(i).deleteObjectwithChildren();
        }
        this.deleteObject();
    }
    
    /** @todo : This noticably has clearing the list out of it's operations which most
     *          certainly needs to be done */
    public void deleteObject() {
        
        //TopObjectChange change2 = new TopObjectChange(this,this,TopObjectChange.DELETE);
        //this.valueChanged(change2);
        this.clearListeners();
    }
    
    
    public void clearList() {
        if (this.getArrayList() != null) this.getArrayList().clearList();
    }
    
    public String toString() {return this.getDisplayName();}
    
    
    public void debugListObject() {
        smallDebug("");
    }
    public void smallDebug(String space) {
        debugString(space);
        for (int i=0;i<this.getnumber();i++)
            this.getObject(i).smallDebug(space + "   ");
    }
    protected void debugString(String space) {
        //com.simplifide.core.codegen.basic.util.IDEout.printFinder(space + this.getname());
    }
    
  
    
    
    
    public void stateChanged(ChangeEvent e) {
        if (e instanceof TopObjectChange) {
            TopObjectChange change = (TopObjectChange) e;
            if (change.getType() == TopObjectChange.DELETE) {
                this.removeObject((T)change.getBotValue());
                
            }
        }
    }
    
    /** @deprecated : Old Method which doesn't serve much purpose */
    public void addUnObject(T obj) {
        this.addObject(obj);
    }
    
    
    public ArrayList<T> getList() {
        return this.getGenericSelfList();
    }
    
    public final ArrayList<T> getGenericSelfList() {
        if (this.getArrayList() == null) return new ArrayList<T>();
        return this.getArrayList().getArrayList();
    }
    
    public ArrayListWrap<T> getArrayList() {
        if (arrayList == null) this.arrayList = this.createNewArrayList();
        return arrayList;
    }
    
    public void setArrayList(ArrayListWrap<T> arrayList) {
        this.arrayList = arrayList;
    }
    
    
    
    
    
    
}
