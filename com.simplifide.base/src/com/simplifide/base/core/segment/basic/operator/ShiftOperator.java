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
  SLL
  | SRL
  | SLA
  | SRA
  | ROL
  | ROR
*/
public class ShiftOperator extends MultiOperator
{
      
    /** Creates a new instance of RelationOperator */
    public ShiftOperator() {}
    public ShiftOperator(String name) {super(name);}
    
    public static class SLL extends MultiOperatorUnit
    {
        public SLL(ReferenceItem unit) {super(unit);}
    }
   
    public static class SRL extends MultiOperatorUnit
    {
        public SRL(ReferenceItem unit) {super(unit);}
    }
    
    public static class SLA extends MultiOperatorUnit
    {
        public SLA(ReferenceItem unit) {super(unit);}
    }
    
    public static class SRA extends MultiOperatorUnit
    {
        public SRA(ReferenceItem unit) {super(unit);}
    }
    
    public static class ROL extends MultiOperatorUnit
    {
        public ROL(ReferenceItem unit) {super(unit);}
    }
    
    public static class ROR extends MultiOperatorUnit
    {
        public ROR(ReferenceItem unit) {super(unit);}
    }

    
}
