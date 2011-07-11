/*
 * VerilogBaseTypes.java
 *
 * Created on April 23, 2007, 1:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.core.types;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author Andy
 */
public class VerilogBaseTypes extends TypeVar{
    
    private static final VarRange RANGE_BYTE = new VarRange(7,0);
    private static final VarRange RANGE_SHORT = new VarRange(15,0);
    private static final VarRange RANGE_INT   = new VarRange(31,0);
    private static final VarRange RANGE_LONG = new VarRange(63,0);
    private static final VarRange RANGE_REAL = new VarRange(-1,0);
    
     
    public static final TypeVar REG = new Reg();
    public static final TypeVar WIRE = new Wire();
    public static final TypeVar LOGIC = new Logic();
    public static final TypeVar BIT = new Bit();
    
    public static final TypeVar BYTE = new Hbyte();
    public static final TypeVar SHORT = new Hshort();
    public static final TypeVar INT  = new Hint();
    public static final TypeVar LONG = new Hlong();
    public static final TypeVar INTEGER = new Hinteger();
    public static final TypeVar REAL = new Real();
    
    /** Creates a new instance of VerilogBaseTypes */
    public VerilogBaseTypes() {}
    
    public VerilogBaseTypes(String name) {
        super(name);
        
    }
    
   
    public static class Vector extends VerilogBaseTypes {
       
        public Vector(String name) {
            super(name);
            
        }
        
        public void setname(String name) {
        }
       
    }
    public static class Wire extends Vector {public Wire() {
    	super("wire"); }
    	public String getVerilogDisplayName() {return "wire";}
    }
    public static class Reg extends Vector {public Reg() {
    	super("reg"); }
		public String getVerilogDisplayName() {return "reg";}
    }
    
   
    public static class Logic extends Vector {
       
        public Logic() {super("logic");}
    }    
    
    public static class Bit extends Vector {
       
        public Bit() {super("bit");}
        
       
    }
    
    public static class FixedWidth extends VerilogArrayType {
        public FixedWidth(String name, ReferenceItem<VarRange> range, ReferenceItem<TypeVar> type) {
            super(name,range,type);
        }
        
        public void setname(String name) {
        }
    }
    
    public static class Hbyte extends FixedWidth {
        public Hbyte() {super("byte",RANGE_BYTE.createReferenceItem(),BIT.createReferenceItem()); }
    }
        
    public static class Hshort extends FixedWidth {
        public Hshort() {super("short",RANGE_SHORT.createReferenceItem(),BIT.createReferenceItem());}
    }
      
    public static class Hinteger extends FixedWidth {
        public Hinteger() {super("integer",RANGE_INT.createReferenceItem(),BIT.createReferenceItem());}
    }
    
    public static class Hint extends FixedWidth {
        public Hint() {super("int",RANGE_INT.createReferenceItem(),BIT.createReferenceItem());}
    }
        
    public static class Hlong extends FixedWidth {
        public Hlong() {super("long",RANGE_LONG.createReferenceItem(),BIT.createReferenceItem());}
    }
    
    public static class Real extends FixedWidth {
        public Real() {super("real",RANGE_REAL.createReferenceItem(),BIT.createReferenceItem());}
    }
     
    
}
