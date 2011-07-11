/*
 * TopObjectActionPerformer.java
 *
 * Created on June 27, 2006, 9:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.newaction;

/**
 *
 * @author awagner
 * 
 * This class will serve as the main action interface to be handle by objects
 * 
 */

public interface TopObjectActionPerformer 
{
    public void performAction(String command);
    public boolean actionEnabled(String command);
    
}
