/*
 * ReferenceUtilitiesInterface.java
 *
 * Created on September 27, 2006, 9:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;


/**
 *
 * @author Andy Wagner
 */
public interface ReferenceUtilitiesInterface {
    

    public static final int REF_MODULEOBJECT = 0;
        
    public static final int REF_PROJECT_TOP   = 1000;
    public static final int REF_PROJECT_SUITE = 1100;
    public static final int REF_PROJECT_SUITE_VHDL = 1110;
    public static final int REF_PROJECT_SUITE_VHDL_ALTERA = 1120;
    
    public static final int REF_PROJECT_BASIC = 1200;
    public static final int REF_PROJECT_LIBRARY = 1210;
    
    public static final int REF_INSTANCE_MODULE_TOP = 2000;
    public static final int REF_INSTANCE_MODULE     = 2100;
    public static final int REF_INSTANCE_PACKAGE    = 2200;
    public static final int REF_INSTANCE_INTERFACE  = 2300;
    public static final int REF_INSTANCE_PROGRAM    = 2400;
    public static final int REF_INSTANCE_CLASS      = 2500;
    //public static final int REF_INSTANCE_PACKAGE_VERILOG = 2230;
    
    public static final int REF_ENTITY = 3000;
    public static final int REF_ENTITY_INTERFACE = 3100;
    
    public static final int REF_TOP_MODULE = 4000;
    public static final int REF_HARDWARE_MODULE = 4100; 
    public static final int REF_PACKAGE_MODULE = 4200;
    public static final int REF_PACKAGE_MODULE_BODY = 4300;
    
 
    public static final int REF_COMPONENT = 5000;

    public static final int REF_MODINSTANCE_TOP = 6000;
    public static final int REF_MODINSTANCE_CONNECT = 6100;
    public static final int REF_INTERFACE_CONNECT = 6200;

    
    public static final int REF_SUPERMODULE = 7000;
    public static final int REF_CONNECTOR_MODULE = 7100;
    public static final int REF_CONNECTOR_GENERATE = 7200;
    
    public static final int REF_PORT_TOP = 8000;
    public static final int REF_PORT_INPUT = 8100;
    public static final int REF_PORT_OUTPUT = 8200;
    public static final int REF_PORT_INOUT = 8300;
    
    public static final int REF_SYSTEMVAR = 9000;
    public static final int REF_CONSTANT  = 9100;
    public static final int REF_VARIABLE  = 9200;
    public static final int REF_SIGNAL    = 9300;
    public static final int REF_ENUMVAR   = 9400;
    
    public static final int REF_TYPEVAR = 10000;
    public static final int REF_TYPE_ARRAY = 10100;
    public static final int REF_TYPE_ARRAY_UNCONSTRAINED = 10200;
    public static final int REF_TYPE_STRUCT = 10300;
    public static final int REF_TYPE_ENUM   = 10400;
    public static final int REF_TYPE_SUB    = 10500;

   
    
    public static final int REF_CORE_PROJECT_HOLDER = 12000;
    public static final int REF_CORE_PROJECT_SUITE_PROJECT_LIST = 13000;
    public static final int REF_CORE_PROJECT_FOLDER = 14000;
    public static final int REF_SEGMENT = 15000;
    
    /** These don't work but have never worked, leave just as a reference for when this is fixed */
    public static final int REF_SUPER_FUNCTION = 16000;
    public static final int REF_FUNCTION_BASE = 16100;
    public static final int REF_FUNCTION_HOLDER = 16200;
    public static final int REF_FUNCTION_INSTANCE = 16300;
    public static final int REF_FUNCTION_HEAD = 16400;
    public static final int REF_FUNCTION_BODY = 16500;
    public static final int REF_FUNCTION = 16600;
    public static final int REF_FUNCTIONBODYHEAD = 16700;

    
    public static final int REF_CORE_PROJECT_HOLDER_ITEM = 17000;
    public static final int REF_PORT_LIST = 18000;
    public static final int REF_VAR_RANGE = 19000;
    public static final int REF_NO_SORT_LIST = 20000;
    
    public static int REF_FUNCTION_DECLARATION = 21000;
    
    public static int REF_SOURCE_OBJECT = 22000;
    public static int REF_SOURCE_FILE = 22100;
    public static int REF_DESIGN_FILE = 22200;
    public static int REF_VERILOG_FILE = 22210;
    public static int REF_VHDL_FILE = 22220;
    public static int REF_SOURCE_FOLDER = 22300;
    public static int REF_SPLIT_FILE = 22400;
  
    public static int REF_ENTITY_STATEMENT = 23000;
    public static int REF_ENUM_HOLDER = 24000;
    public static int REF_PROCESS_STATEMENT = 25000;
    public static int REF_ASSIGN = 26000;
    
    public static int REF_GENERATE_STATEMENT = 27000;
    
    public static int REF_LIBRARY_HOLDER = 28000;
    
    public static int REF_BASE_CLASS = 29000;
    
    public static final int REF_PROPERTY = 30000;
    public static final int REF_SEQUENCE = 31000;
    public static final int REF_CLOCKING = 32000;
    public static final int REF_ASSERTION = 33000;
    public static final int REF_CONTRAINT = 33000;
    public static final int REF_DEFINE_OBJECT = 34000;
    public static final int REF_DEFINE_MACRO = 34100;

    public static final int REF_MODPORT = 35000;

    

}
