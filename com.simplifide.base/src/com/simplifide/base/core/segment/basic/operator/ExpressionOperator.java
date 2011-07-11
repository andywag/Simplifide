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
public class ExpressionOperator extends MultiOperator
{
      
    /** Creates a new instance of RelationOperator */
    public ExpressionOperator() {}
    public ExpressionOperator(String name) {super(name);}
    
    public static class And extends MultiOperatorUnit
    {
        public And(ReferenceItem unit) {super(unit);}
    }
   
    public static class Or extends MultiOperatorUnit
    {
        public Or(ReferenceItem unit) {super(unit);}
    }
    
    public static class Nand extends MultiOperatorUnit
    {
        public Nand(ReferenceItem unit) {super(unit);}
    }
    
    public static class Nor extends MultiOperatorUnit
    {
        public Nor(ReferenceItem unit) {super(unit);}
    }
    
    public static class Xor extends MultiOperatorUnit
    {
        public Xor(ReferenceItem unit) {super(unit);}
    }
    
    public static class Xnor extends MultiOperatorUnit
    {
        public Xnor(ReferenceItem unit) {super(unit);}
    }

    
}
