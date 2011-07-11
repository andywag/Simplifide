/*
 * SuperModuleLocator.java
 *
 * Created on August 18, 2006, 5:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;

import java.util.HashMap;

/**
 *
 * @author Andy Wagner
 * 
 * This class will serve as a locator for the hardware sources to be used for location
 * This is not the most elegant solution but the easiest for the time being. Attempting
 * to open and reseach the modules added unwanted complexity
 * 
 */
public class ObjectLocator<T> {
    


    private HashMap<Integer,T> map = new HashMap<Integer,T>();
    private int index = 0;
    /** Creates a new instance of SuperModuleLocator */
    public ObjectLocator()
    {
    }

    public void clearMap()
    {
        this.map.clear();
    }
    
    public T getObject(int index)
    {
        Integer uint = Integer.valueOf(index);
        return map.get(uint);
    }
    
    public int addObject(T smod)
    {
        Integer uint = Integer.valueOf(index);
        map.put(uint,smod);
        index = index + 1;
        return index-1;
    }
    
    public void removeObject(int uind)
    {
        Integer uint = Integer.valueOf(uind);
        map.remove(uint);
    }
    

    public HashMap getMap() {
        return map;
    }

    public void setMap(HashMap map) {
        this.map = map;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
