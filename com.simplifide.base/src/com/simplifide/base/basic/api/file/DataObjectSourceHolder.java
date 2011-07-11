/*
 * SourceObjectHolder.java
 *
 * Created on May 18, 2006, 12:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.file;

import com.simplifide.base.basic.struct.reference.HardwareSourceInt;

/**
 *
 * @author awagner
 */
public interface DataObjectSourceHolder extends DataObjectHolder
{
    public HardwareSourceInt getHardwareSource();
}
