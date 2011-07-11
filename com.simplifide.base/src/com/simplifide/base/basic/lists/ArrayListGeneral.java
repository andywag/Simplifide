/*
 * ArrayListGeneral.java
 *
 * Created on March 7, 2007, 10:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.lists;

import com.simplifide.base.basic.struct.TopObjectBase;

/**
 *
 * @author awagner
 */
public class ArrayListGeneral<T> extends ArrayListWrap<T> {
    
    /** Creates a new instance of ArrayListGeneral */
    public ArrayListGeneral() {}
    
    
    public void addSelfList(TopObjectBase scale) {
        this.getArrayList().addAll(scale.getGenericSelfList());      
    }
    
    public int size() {
        return this.getArrayList().size();
    }
    
    public T getObject(int i){
        return this.getArrayList().get(i);
    }
    
    public void addObject(T object) {
        this.getArrayList().add(object);
    }
    
    /** Adds object at the given index */
    public void addObject(int index, T object) {
        this.getArrayList().add(index,object);
    }
    
    
    public void removeObject(int index) {
        this.getArrayList().remove(index);
    }
    public void removeObject(T object) {
        this.getArrayList().remove(object);
    }

    public void clearList() {
        this.getArrayList().clear();
    }
    
    /** Not Supported */
    public T findObject(T object) {
        for (T obj : this.getArrayList()) {
            if (obj.equals(object)) return obj;
        }
        return null;
    }

   
    
    
}
