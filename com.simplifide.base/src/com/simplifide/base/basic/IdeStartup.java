/*
 * IdeStartup.java
 *
 * Created on February 13, 2007, 11:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic;


/**
 *
 * @author awagner
 */
public final class IdeStartup {
    
    private static IdeStartup instance;
    private static boolean loaded;
    /** Creates a new instance of IdeStartup */
    private IdeStartup() {
        IdeStartup.loadGeneralLookup();
    }
    
    public static IdeStartup getInstance() {
        if (instance == null) instance = new IdeStartup();
        return instance;
    }
    
    public static void loadGeneralLookup()
    {
        if (!loaded)
        {
            
            
        }
        loaded = true;
    }

   
    
}
