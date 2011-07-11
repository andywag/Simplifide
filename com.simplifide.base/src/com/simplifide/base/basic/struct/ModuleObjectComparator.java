/*
 * ModuleObjectComparator.java
 *
 * Created on March 13, 2007, 5:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

import java.util.Comparator;

/**
 *
 * @author Andy
 */
public interface ModuleObjectComparator<V extends ModuleObject> extends Comparator<V>{
    
    public static final int MATCH = 0;
    public static final int MATCH_NAME = 1;
    public static final int MATCH_NO = -1;
    
    public int comparePrefix(V o1, V o2);
}
