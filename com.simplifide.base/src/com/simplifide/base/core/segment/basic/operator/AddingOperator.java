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

public class AddingOperator extends MultiOperator
{
      
    /** Creates a new instance of RelationOperator */
    public AddingOperator() {}
    public AddingOperator(String name) {super(name);}
    
    public static class Plus extends MultiOperatorUnit
    {
        public Plus(ReferenceItem unit) {super(unit);}
        
        
    }
   
    public static class Minus extends MultiOperatorUnit
    {
        public Minus(ReferenceItem unit) {super(unit);}
         
    }
    
    public static class Ampersand extends MultiOperatorUnit
    {
        public Ampersand(ReferenceItem unit) {super(unit);}
       
    }
    
   
    
}
