/*
 * TopOperator.java
 *
 * Created on December 12, 2005, 7:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.operator;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.NoSortList;

/**
 *
 * @author Andy Wagner
 */
public class TopOperator<T extends ModuleObjectNew> extends NoSortList<T>
{
    
    /** Creates a new instance of TopOperator */
    public TopOperator() {}
    public TopOperator(String name) {super(name);}
    
}
