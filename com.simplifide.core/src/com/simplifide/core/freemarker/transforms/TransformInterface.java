/*
 * TransformInterface.java
 *
 * Created on April 15, 2007, 12:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.freemarker.transforms;

import java.util.Map;

/**
 *
 * @author Andy
 */
public interface TransformInterface {
    
    /**
     * Class which translates the string for the transform object
     * @param inval 
     * @param args 
     * @return 
     */
    public String transformString(String inval, Map args);
}
