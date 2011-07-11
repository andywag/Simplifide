/*
 * OperatingTypeVar.java
 *
 * Created on June 13, 2006, 10:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.var.realvars;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.segment.CodeSegmentInt;

/**
 *
 * @author awagner
 */

/** This class defines the operating type of the variable */
public class OperatingTypeVar extends ModuleObjectNew{
    
    public static final ConstantVar TYPE_CONSTANT = new ConstantVar();
    public static final SignalVar TYPE_SIGNAL = new SignalVar();
    public static final VariableVar TYPE_VARIABLE = new VariableVar();
    
    public static final IOVar TYPE_INPUT = new IOVar(IOVar.INPUT);
    public static final IOVar TYPE_OUTPUT = new IOVar(IOVar.OUTPUT);
    public static final IOVar TYPE_INOUT = new IOVar(IOVar.INOUT);
    public static final IOVar TYPE_REF = new IOVar(IOVar.REF);
    
    public static IOVar createIOVar(int type) {
    	if (type == IOVar.INPUT) return TYPE_INPUT;
    	if (type == IOVar.OUTPUT) return TYPE_OUTPUT;
    	return TYPE_INOUT;
    }
    
    /** Creates a new instance of OperatingTypeVar */
    public OperatingTypeVar() {}
    public OperatingTypeVar(String name) {super(name);}
    
    /** Returns the variable type using the system var number system. 
     *  This is kind of a kludge but is being used for refactoring. 
     **/
    public int getVariableType() {
    	return SystemVar.SIGNAL;
    }
    
    public static class IOVar extends OperatingTypeVar
    {
        public static final int NONE = 0;
        public static final int INPUT = 1;
        public static final int INOUT = 2;
        public static final int OUTPUT = 3;
        public static final int BUFFER = 4;
        public static final int PARAMETER = 10;
        public static final int REF = 11;
        
              
        private int type;
        public IOVar(int type) 
        {
            super("Port");
            this.type = type;
            
        }
        
        public boolean isInput() {
        	if (type == INPUT) return true;
        	return false;
        }
        
        public boolean isOutput() {
        	if (type == OUTPUT) return true;
        	return false;
        }
        
        /** Returns the variable type using the system var number system */
        public int getVariableType() {
        	if      (type == INPUT) return SystemVar.INPUT;
        	else if (type == INOUT) return SystemVar.INOUT;
        	else if (type == OUTPUT) return SystemVar.OUTPUT;
        	else if (type == BUFFER) return SystemVar.BUFFER;
        	else if (type == PARAMETER) return SystemVar.PARAMETER;
        	return SystemVar.CONSTANT;
        }
        
        
        public Object getVhdlTypeName() {
            if (this.type == INPUT) return "in";
            else if (this.type == INOUT) return "inout";
            else if (this.type == OUTPUT) return "out";
            else if (this.type == BUFFER) return "buffer";
            else return "none";
        }
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

       

       
    }
    
    public static class File extends OperatingTypeVar {
        public File() {super("File");}
    }
    
    public static class SignalVar extends OperatingTypeVar
    {
        public SignalVar() {super("Signal");}
        public int getVariableType() {
        	return SystemVar.SIGNAL;
        }
    }
    
    public static class VariableVar extends OperatingTypeVar
    {
        public VariableVar() {super("Variable");}
        public int getVariableType() {
        	return SystemVar.VARIABLE;
        }
    }
    
    public static class ConstantVar extends OperatingTypeVar
    {
        private CodeSegmentInt value;
        public ConstantVar() {super("Constant");}
        public int getVariableType() {
        	return SystemVar.VARIABLE;
        }

        public CodeSegmentInt getValue() {
            return value;
        }

        public void setValue(CodeSegmentInt value) {
            this.value = value;
        }
        
        
    }
    
}
