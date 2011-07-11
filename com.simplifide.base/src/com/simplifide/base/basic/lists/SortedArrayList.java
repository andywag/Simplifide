/*
 * SortedArrayList.java
 *
 * Created on March 7, 2007, 11:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectComparator;

/**
 *
 * @author awagner
 */
public class SortedArrayList<T extends ModuleObject> extends ArrayListGeneral<T>{
    
    private ModuleObjectComparator compare;
    /** Creates a new instance of SortedArrayList */
    public SortedArrayList() {}
    public SortedArrayList(ModuleObjectComparator compare) {
        this.compare = compare;
        
    }
    
    /** Searches the list of the object */
    protected int search(T object)  {
        return Collections.binarySearch(this.getArrayList(),object,compare);
    }
    /** Searches the list and returns the object matching the input object */
    public T findObject(T inputobject) {
        int position = this.search(inputobject);
        if (position >= 0) return this.getObject(position);
        else return null;
    }
    /** Finds a list of ReferenceItem Objects with the same prefix */
    public List<T> findPrefixItemList(T item) {
        ArrayList<T> list = new ArrayList();
        int position = this.search(item);
        if (position < 0) position = -position-1;
        
        for (int i=position;i<this.getArrayList().size();i++) {
            T obj = this.getObject(i);
            int match = compare.comparePrefix(obj,item);
            if (match == ModuleObjectComparator.MATCH) {
                list.add(obj);
            } 
        }
        return list;
    }
    
    /** Adds the object in the appropriate ordered place and correspondingly removes
     *  an object if it is an exact match. Not sure if this is appropriate */
    
    public void addObject(T object) {
        int spos = this.search(object);
        if (spos < 0) {
            this.getArrayList().add(-spos-1,object);
        } else {
            this.getArrayList().remove(spos);
            this.getArrayList().add(spos,object);
        }
    }
    
    public void removeObject(T object) {
        int spos = this.search(object);
        if (spos >= 0) {
            this.getArrayList().remove(spos);
        }
    }
    
    public ModuleObjectComparator getCompare() {
        return compare;
    }
    
    public void setCompare(ModuleObjectComparator compare) {
        this.compare = compare;
    }
    
}
