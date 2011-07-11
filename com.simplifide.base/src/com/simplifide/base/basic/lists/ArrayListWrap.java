/*
 * ArrayListWrap.java
 *
 * Created on March 7, 2007, 10:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.lists;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.TopObjectBase;

/**
 *
 * @author awagner
 */
public abstract class ArrayListWrap<T> {
    
    private ArrayList<T> arrayList = new ArrayList<T>();
    /** Creates a new instance of ArrayListWrap */
    public ArrayListWrap() {

    }

    /** Method which adds the self list of the input arguement to this */
    public abstract void addSelfList(TopObjectBase scale);
    /** Method which gives the size of the Array */
    public abstract int size();
    /** Method which returns the ith object in the list */
    public abstract T getObject(int i);
    /** Method which adds the object to the list */
    public abstract void addObject(T inval);
    /** Method whichremoves the Object at index i */
    public abstract void removeObject(int index);
    /** Method which removes this object */
    public abstract void removeObject(T object);
    /** Method which clears the list */
    public abstract void clearList();
    /** Checks to see if the input object exists in the list */
    public abstract T findObject(T object);
   
    
    
    
    public ArrayList<T> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<T> arrayList) {
        this.arrayList = arrayList;
    }
    
    
    
}
