/*
 * StringObject.java
 *
 * Created on April 12, 2007, 3:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.general;

import com.simplifide.base.basic.struct.ModuleObjectNew;

/**
 *
 * @author Andy
 */
public class StringObject extends ModuleObjectNew{
    
    public StringObject() {}
    /** Creates a new instance of StringObject 
     * @param value 
     */
    public StringObject(String value) {
        super(value);
    }
    
    public String getname() {
        return  super.getname();
    }
    
}
