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
/*
 : EQ
  | NEQ
  | LOWERTHAN
  | LE
  | GREATERTHAN
  | GE
*/
public class AddingOperatorTerm extends UniOperator
{
      
    /** Creates a new instance of RelationOperator */
    public AddingOperatorTerm() {}
    public AddingOperatorTerm(String name) {super(name);}
    
    public static class Plus extends UniOperator
    {
        public Plus(ReferenceItem unit) {super("Plus",unit);}   
    }
   
    public static class Minus extends UniOperator{
        public Minus(ReferenceItem unit) {super("Minus",unit);}
    }
    
    public static class Not extends UniOperator {
        public Not(ReferenceItem unit) {super("Not",unit);}
    }
   
   public static class Abs extends UniOperator{
        public Abs(ReferenceItem unit) {super("Abs",unit);}
       
    }
    
}
