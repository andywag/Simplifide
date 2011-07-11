/*
 * BasicIDEInterface.java
 *
 * Created on June 26, 2006, 11:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic;

import javax.swing.Action;

/**
 *
 * @author awagner
 */
public interface BasicIDEInterface 
{

    
    public String getDisplayName();
    public String getIconLocation();
    public Action[] getActions(boolean context);
    public Object[] getKeyObject();
    
    
    
    
}
