/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.reference;

import java.util.HashMap;




/** Kludge of a class to mirror SourceLocation Handler */
public class SourceNameFactory {

    private static SourceNameFactory instance;
    private HashMap<Integer,String> locationMap = new HashMap();
    
    public static SourceNameFactory getDefault() {
        if (instance == null) instance = new SourceNameFactory();
        return instance;
    }
    
    public void addEntry(Integer index, String name) {
        this.locationMap.put(index, name);
    }
    
    public String getEntry(int index) {
        return this.locationMap.get(Integer.valueOf(index));
    }
    
}
