/*
 * GeneralLookup.java
 *
 * Created on January 4, 2007, 8:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api;

import java.util.HashMap;



/**
 *
 * @author awagner
 * 
 * Simplified implementation of the lookup provided by netbeans. This was done to avoid
 * figuring out how the netbeans interface works, and additionally to stop ...
 */
public final class GeneralLookup {
    
    private static GeneralLookup instance;
    private HashMap map = new HashMap();
    
    /** Creates a new instance of GeneralLookup */
    private GeneralLookup() {

    }
    
    public static GeneralLookup getLookup()
    {   
        if (instance == null) instance = new GeneralLookup();
         return instance;
    }
    
    /** Generics should be used to simplify the casting of this operation */
    public <T> T simpleLookup(Class<T> clazz)
    {
        return (T) this.map.get(clazz);
    }
    
    public void registerClass(Class uclass, Object item)
    {
        this.map.put(uclass, item);
    }
    
    public void unregisterClass(Class aclass)
    {
        
    }
    
}
