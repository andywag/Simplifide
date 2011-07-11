/*
 * BuildInterface.java
 *
 * Created on February 12, 2007, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.project;

/**
 *
 * @author awagner
 */
public interface BuildInterface {
    public static int BUILD_FILE_CONTEXT = 0;
    public static int BUILD_FILE_CLOSED = 1;    
    public static int BUILD_FILE_OPEN = 2;

    public static int SUITE_INITIAL_BUILD = 10;
   
    
    public static int BUILD_FIND_USAGES = 100;
    public static int BUILD_HDLDOC = 101;
    public static int BUILD_CLOSE_HDLDOC = 102;
    
}
