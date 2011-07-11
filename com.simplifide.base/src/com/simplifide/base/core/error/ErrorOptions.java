/*
 * ErrorOptions.java
 *
 * Created on March 28, 2007, 9:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.error;

import java.util.prefs.Preferences;

/**
 *
 * @author Andy
 */
public class ErrorOptions {
    
    public static final String ERROR_SYNTAX = "Syntax";
    public static final String ERROR_TYPE   = "Type";
    public static final String ERROR_NODEF  = "NotDefined";
    
    public static final boolean DEFAULT_SYNTAX = true;
    public static final boolean DEFAULT_TYPE   = true;
    public static final boolean DEFAULT_NODEF  = true;
    
    public static Preferences pref = Preferences.userNodeForPackage(ErrorOptions.class);
    
    /** Creates a new instance of ErrorOptions */
    public ErrorOptions() {}
    
    public static boolean syntaxEnabled() {return pref.getBoolean(ERROR_SYNTAX,DEFAULT_SYNTAX);}
    public static boolean typeEnabled() {return pref.getBoolean(ERROR_TYPE,DEFAULT_TYPE);}
    public static boolean noDefEnabled() {return pref.getBoolean(ERROR_NODEF,DEFAULT_NODEF);}
    
}
