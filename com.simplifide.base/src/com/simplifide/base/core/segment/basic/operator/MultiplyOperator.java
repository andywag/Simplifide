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
public class MultiplyOperator extends MultiOperator
{
      
    /** Creates a new instance of RelationOperator */
    public MultiplyOperator() {}
    public MultiplyOperator(String name) {super(name);}
    
    public static class Times extends MultiOperatorUnit
    {
        public Times(ReferenceItem unit) {super(unit);}

    }
   
    public static class Divide extends MultiOperatorUnit
    {
        public Divide(ReferenceItem unit) {super(unit);}   
    }
    
    public static class Mod extends MultiOperatorUnit
    {
        public Mod(ReferenceItem unit) {super(unit);}
    }
    
    public static class Rem extends MultiOperatorUnit
    {
        public Rem(ReferenceItem unit) {super(unit);}
    }
    
    
   
    
}
