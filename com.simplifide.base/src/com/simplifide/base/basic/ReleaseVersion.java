/*
 * ReleaseVersion.java
 *
 * Created on September 27, 2006, 2:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic;

/**
 *
 * @author awagner
 */
public class ReleaseVersion {
    
    
    public static final int VERSION_DEBUG = 0;
    public static final int VERSION_RELEASE = 1;
    
    private static int release = VERSION_DEBUG;
    
    /** Creates a new instance of ReleaseVersion */
    public ReleaseVersion() {}

    public static int getRelease() {
        return release;
    }

    public static void setRelease(int aRelease) {
        release = aRelease;
    }
    
    
    
    
    
}
