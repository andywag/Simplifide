/*
 * RequestProcessorInt.java
 *
 * Created on January 22, 2007, 10:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.progress;

/**
 *
 * @author awagner
 */
public interface RequestProcessorInt {
    public void post(Runnable run, int delay, int priority);
    
}
