/*
 * ParseDescriptorInt.java
 *
 * Created on August 3, 2006, 4:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct.reference;

import javax.swing.event.ChangeListener;

import com.simplifide.base.basic.struct.TopObjectBase;

/**
 *
 * @author awagner
 */
public interface ParseDescriptorInt extends ChangeListener
{
    public TopObjectBase getModules();
    public TopObjectBase getPackages();
    
}
