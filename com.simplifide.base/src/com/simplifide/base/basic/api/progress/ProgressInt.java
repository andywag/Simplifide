/*
 * ProgressInt.java
 *
 * Created on August 17, 2006, 11:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.progress;

/**
 *
 * @author awagner
 */
public interface ProgressInt 
{
    
    public ProgressInt newProgressInt(String name);
    public void start(int unit);
    public void process(int element);
    public void process(int element, String str);
    public void finish();
    
    public void message(String mess);
}
