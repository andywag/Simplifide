/*
 * TopObjectComparator.java
 *
 * Created on December 15, 2005, 1:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

import java.util.Comparator;

/**
 *
 * @author awagner
 */
public class TopObjectComparator<T extends TopObject> implements Comparator<T>{
    
    /** Creates a new instance of TopObjectComparator */
    
    public static TopObjectComparator Comp = new TopObjectComparator();
    
    public TopObjectComparator() {}

    

    public int compare(T o1, T o2) {
        return o1.getname().compareTo(o2.getname());
    }
    
}
