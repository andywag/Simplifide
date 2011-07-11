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
import com.simplifide.base.core.var.types.TypeVar;

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
public class RelationOperator extends MultiOperator
{
      
    /** Creates a new instance of RelationOperator */
    public RelationOperator() {}
    public RelationOperator(String name) {super(name);}

    public TypeVar getType()
    {
        return null;
    }
    public static class Eq extends MultiOperatorUnit
    {
        public Eq(ReferenceItem unit) {super(unit);}
    }
   
    public static class Neq extends MultiOperatorUnit
    {
        public Neq(ReferenceItem unit) {super(unit);}
    }
    
    public static class Less extends MultiOperatorUnit
    {
        public Less(ReferenceItem unit) {super(unit);}
    }
    
    public static class LessEq extends MultiOperatorUnit
    {
        public LessEq(ReferenceItem unit) {super(unit);}
    }
    
    public static class Greater extends MultiOperatorUnit
    {
        public Greater(ReferenceItem unit) {super(unit);}
    }
    
    public static class GreaterEq extends MultiOperatorUnit
    {
        public GreaterEq(ReferenceItem unit) {super(unit);}
    }

    
}
