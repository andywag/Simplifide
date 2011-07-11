/*
 * StandardTypes.java
 *
 * Created on March 28, 2007, 9:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.project;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.EnumTypeVar;
import com.simplifide.base.core.var.types.SubTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.core.var.vhdl.types.VhdlUnconstrainedArrayTypeVar;

/**
 *
 * @author Andy
 */
public class VhdlStandardTypes {
    
    
    
    public static final Bit       TYPE_BIT        = new Bit();
    public static final ReferenceItem REF_TYPE_BIT        = TYPE_BIT.createReferenceItem();
    
    public static final BitVector TYPE_BIT_VECTOR = new BitVector();
    public static final ReferenceItem REF_TYPE_BIT_VECTOR = TYPE_BIT_VECTOR.createReferenceItem();
    
    public static final Minteger   TYPE_INTEGER    = new Minteger();
    public static final ReferenceItem REF_TYPE_INTEGER    = TYPE_INTEGER.createReferenceItem();
    
    public static final Natural   TYPE_NATURAL    = new Natural();
    public static final ReferenceItem REF_TYPE_NATURAL    = TYPE_NATURAL.createReferenceItem();
    
    public static final Positive  TYPE_POSITIVE   = new Positive();
    public static final ReferenceItem REF_TYPE_POSITIVE   = TYPE_POSITIVE.createReferenceItem();
    
    public static final Character TYPE_CHARACTER = new Character();
    public static final ReferenceItem REF_TYPE_CHARACTER = TYPE_CHARACTER.createReferenceItem();
    
    public static final Real      TYPE_REAL       = new Real();
    public static final ReferenceItem REF_TYPE_REAL       = TYPE_REAL.createReferenceItem();
    
    public static final Mboolean   TYPE_BOOLEAN    = new Mboolean();
    public static final ReferenceItem REF_TYPE_BOOLEAN    = TYPE_BOOLEAN.createReferenceItem();
    
    public static final Str TYPE_STRING = new Str();
    public static final ReferenceItem REF_TYPE_STRING = TYPE_STRING.createReferenceItem();
    
    public static final Time TYPE_TIME = new Time();
    public static final ReferenceItem REF_TYPE_TIME = TYPE_TIME.createReferenceItem();
    
    public static final Resolved TYPE_RESOLVED = new Resolved();
    public static final ReferenceItem REF_TYPE_RESOLVED = TYPE_RESOLVED.createReferenceItem();
    
    
    public static final SystemVar VAR_TRUE  = new SystemVar("true",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final SystemVar VAR_FALSE = new SystemVar("false",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    
    public static final SystemVar VAR_MESSAGE_NOTE  = new SystemVar("note",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final SystemVar VAR_MESSAGE_WARNING = new SystemVar("warning",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final SystemVar VAR_MESSAGE_ERROR  = new SystemVar("error",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final SystemVar VAR_MESSAGE_FAILURE = new SystemVar("failure",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    
    public static final SystemVar VAR_EVENT  = new SystemVar("event",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final SystemVar VAR_LEFT  = new SystemVar("left",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final SystemVar VAR_RIGHT  = new SystemVar("right",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    
    public static final SystemVar VAR_RANGE  = new SystemVar("range",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final SystemVar VAR_RANGE_REVERSE  = new SystemVar("reverse_range",REF_TYPE_BOOLEAN,new OperatingTypeVar.ConstantVar());
    public static final TypeVar VAR_TEXT  = new TypeVar("text");
    public static final TypeVar VAR_LINE  = new TypeVar("line");

    
    /** Creates a new instance of StandardTypes */
    public VhdlStandardTypes() {}
    
    public static class Resolved extends TypeVar {
    	private Resolved() {super("resolved");}
    }
    
    public static class Bit extends TypeVar {
        private Bit() {super("bit");}
    }
    public static class BitVector extends VhdlUnconstrainedArrayTypeVar {
        private BitVector() {super("bit_vector", REF_TYPE_BIT);}
    }
    public static class Minteger extends TypeVar {
        private Minteger() {super("integer");}
    }
    public static class Natural extends SubTypeVar {
        private Natural() {super("natural",REF_TYPE_INTEGER);}
    }
    public static class Positive extends SubTypeVar {
        private Positive() {super("positive",REF_TYPE_NATURAL);}
    }
    public static class Real extends TypeVar {
        private Real() {super("real");}
    }
    
    public static class Character extends TypeVar {
        private Character() {super("character");}
    }
    
    public static class Str extends VhdlUnconstrainedArrayTypeVar {
        private Str() {super("string",REF_TYPE_CHARACTER);}
    }
    
    public static class Time extends TypeVar {
        private Time() {super("time");}
    }
    
    /** @todo : Need to add the boolean elements */
    public static class Mboolean extends EnumTypeVar {
        private Mboolean() {super("boolean"); init();}
        private void init() {
            SystemVar tVar = new SystemVar("true",this.createReferenceItem(),OperatingTypeVar.TYPE_CONSTANT);
            SystemVar fVar = new SystemVar("false",this.createReferenceItem(),OperatingTypeVar.TYPE_CONSTANT);
            this.addReferenceItem(tVar.createReferenceItem());
            this.addReferenceItem(fVar.createReferenceItem());
        }
    }
    
    
}
