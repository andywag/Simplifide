/*
 * ReferenceUtilities.java
 *
 * Created on August 6, 2006, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;

import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.module.HardwareModule;

/**
 *
 * @author awagner
 */
public class ReferenceUtilities implements ReferenceUtilitiesInterface{
    
    private static int genCompare(int listType, int compType) {
        if (listType < compType) return -1;
        if (listType > compType) return 1;
        return 0;
    }
    /** Type Compare Function -- Based on Types being modulo 10 operations with different values per digit*/
    public static int checkType(int listType, int compType) {
        
        if (compType == 0) return 0;
        int compMod = compType / 1000;
        int listMod = listType / 1000;
        
        int fcomp = genCompare(listMod, compMod);
        if ( (fcomp != 0) || (compType % 1000 == 0)) return fcomp;
        
        compMod = (compType - 1000*compMod)/100;
        listMod = (listType - 1000*listMod)/100;
        
        return genCompare(listMod,compMod);
        
    }
    
    // Function Intentionally Not supported as it needs header and body similar to instance module
    
    
   
    
    
    public static String referenceString(int type) {
        String ustring = "Object";
        switch(type) {
            case(REF_MODULEOBJECT) : ustring = "Object"; break;
            case(REF_TYPEVAR) :
            case(REF_TYPE_STRUCT) :
            case(REF_TYPE_ARRAY) :
            case(REF_TYPE_ENUM) :
            case(REF_TYPE_ARRAY_UNCONSTRAINED) : ustring = "Type Declaration"; break;
            
            
            case(REF_SYSTEMVAR) :
            case(REF_CONSTANT) :
            case(REF_SIGNAL) :
            case(REF_VARIABLE) : ustring = "Variable Declaration"; break;
            
            case(REF_PROJECT_BASIC) : ustring = "Project"; break;
            case(REF_PROJECT_SUITE) : ustring = "Suite"; break;
            
            case(REF_TOP_MODULE)    : ustring = "Architecture"; break;
            case(REF_HARDWARE_MODULE) : ustring = "Architecture"; break;
            case(REF_ENTITY) : ustring = "Entity"; break;
            case(REF_PACKAGE_MODULE) : ustring = "Package Declaration"; break;
            case(REF_PACKAGE_MODULE_BODY) : ustring = "Package Body Declaration"; break;
            
            case(REF_PORT_TOP) : ustring = "Port Declaration"; break;
            case(REF_PORT_INPUT) : ustring = "Port Declaration"; break;
            case(REF_PORT_OUTPUT) : ustring = "Port Declaration"; break;
            
            case(REF_MODINSTANCE_TOP) : ustring = "Module Instantiation"; break;
            case(REF_MODINSTANCE_CONNECT) : ustring = "Module Instantiation"; break;
            
            case(REF_SUPERMODULE)      : ustring = "Module"; break;
            case(REF_CONNECTOR_MODULE)  : ustring = "Connection"; break;
            case(REF_FUNCTION_INSTANCE) : ustring = "Function"; break;
            case(REF_FUNCTION_HEAD) : ustring = "Function Declaration"; break;
            case(REF_FUNCTION_BODY) : ustring = "Function Body"; break;
            case(REF_COMPONENT) : ustring = "Component"; break;
            case(REF_ENUMVAR) : ustring = "Variable"; break;
            case(REF_PROCESS_STATEMENT) :ustring = "Process Statement"; break;
            case(REF_ASSIGN) :ustring = "Assignment"; break;
            
            default : ustring = "Object"; break;
        }
        return ustring;
    }
    
    public static String returnTypeString(int type) {
        String ustring = "Object";
        switch(type) {
            case(REF_MODULEOBJECT) : ustring = "Object"; break;
            case(REF_TYPEVAR) :
            case(REF_TYPE_STRUCT) :
            case(REF_TYPE_ARRAY) :
            case(REF_TYPE_ENUM) :
            case(REF_TYPE_ARRAY_UNCONSTRAINED) : ustring = "Type"; break;
            
            
            case(REF_SYSTEMVAR) :
            case(REF_CONSTANT) :
            case(REF_SIGNAL) :
            case(REF_VARIABLE) : ustring = "Variable"; break;
            
            case(REF_PROJECT_BASIC) : ustring = "Project"; break;
            case(REF_PROJECT_SUITE) : ustring = "Suite"; break;
            
            case(REF_TOP_MODULE)    : ustring = "Architecture"; break;
            case(REF_HARDWARE_MODULE) : ustring = "Architecture"; break;
            case(REF_ENTITY) : ustring = "Entity"; break;
            case(REF_PACKAGE_MODULE) : ustring = "Package"; break;
            case(REF_PACKAGE_MODULE_BODY) : ustring = "Package Body"; break;
            
            case(REF_PORT_TOP) : ustring = "Port"; break;
            case(REF_PORT_INPUT) : ustring = "Port"; break;
            case(REF_PORT_OUTPUT) : ustring = "Port"; break;
            
            case(REF_MODINSTANCE_TOP) : ustring = "Module Instantiation"; break;
            case(REF_MODINSTANCE_CONNECT) : ustring = "Module Instantiation"; break;
            
            case(REF_SUPERMODULE)      : ustring = "Module"; break;
            case(REF_CONNECTOR_MODULE)  : ustring = "Connection"; break;
            case(REF_FUNCTION_INSTANCE) : ustring = "Function"; break;
            case(REF_FUNCTION_HEAD) : ustring = "Function Declaration"; break;
            case(REF_FUNCTION_BODY) : ustring = "Function Body"; break;
            case(REF_COMPONENT) : ustring = "Component"; break;
            case(REF_ENUMVAR) : ustring = "Variable"; break;
            case(REF_PROCESS_STATEMENT) :ustring = "Process"; break;
            case(REF_ASSIGN) :ustring = "Assignment"; break;
            
            default : ustring = "Object"; break;
        }
        
        return ustring;
    }
    
    public static String goToString(ReferenceItem item) {
        if (item != null) {
            //if (item.getType() == FUNCTION) return item.getDisplayName();
        	if (item.getType() == REF_INSTANCE_MODULE) return "Entity/Architecture/Component/Instance";
            if (item.getType() == REF_FUNCTIONBODYHEAD) return "Body " + item.getDisplayName();
            if (item.getType() == REF_HARDWARE_MODULE) {
                HardwareModule umod = (HardwareModule) item.getObject();
                return "Architecture" + StringOps.addParens(umod.getArchName());
            } else return referenceString(item.getType());
        }
        return "None";
    }
    
}
