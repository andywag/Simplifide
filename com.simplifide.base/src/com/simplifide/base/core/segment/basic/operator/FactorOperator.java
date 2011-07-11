/*
 * RelationOperator.java
 *
 * Created on December 12, 2005, 8:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.operator;

import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy Wagner
 */

public class FactorOperator extends MultiOperator
{
      
    /** Creates a new instance of RelationOperator */
    public FactorOperator() {}
    public FactorOperator(String name) {super(name);}
    
    public static class DStar extends MultiOperatorUnit
    {
        public DStar(ReferenceItem unit) {super(unit);} 
    }
   
    public class Abs extends MultiOperatorUnit
    {
        public Abs(ReferenceItem unit) {super(unit);}
    }
    
    public class Not extends MultiOperatorUnit
    {
        public Not(ReferenceItem unit) {super(unit);}
    }
    
   
    
}
