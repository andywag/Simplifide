/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.resources;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;

public class IconManager implements ReferenceUtilitiesInterface{

	public static ImageDescriptor getIcon(int type) {
        switch(type)
        {
            case(REF_TYPEVAR) : return BasicIconManager.getIcon(BasicIconManager.MAIN_TYPE);
            case(REF_TYPE_STRUCT) : return BasicIconManager.getIcon(BasicIconManager.MAIN_TYPE_RECORD);
            case(REF_TYPE_ARRAY) : return BasicIconManager.getIcon(BasicIconManager.MAIN_TYPE_ARRAY);
            case(REF_TYPE_ENUM) : return BasicIconManager.getIcon(BasicIconManager.MAIN_TYPE_ENUM);
            case(REF_TYPE_ARRAY_UNCONSTRAINED) :return BasicIconManager.getIcon(BasicIconManager.MAIN_TYPE_ARRAY_UNCONSTRAINED);
        
            case(REF_SYSTEMVAR) :return BasicIconManager.getIcon(BasicIconManager.MAIN_VARIABLE);
            case(REF_CONSTANT) :return BasicIconManager.getIcon(BasicIconManager.MAIN_CONSTANT);
            case(REF_SIGNAL) :return BasicIconManager.getIcon(BasicIconManager.MAIN_SIGNAL);
            case(REF_VARIABLE) :return BasicIconManager.getIcon(BasicIconManager.MAIN_VARIABLE);
        
        
            case(REF_PROJECT_BASIC) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PROJECT);
            case(REF_PROJECT_LIBRARY) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PROJECT);
            case(REF_PROJECT_SUITE) :return BasicIconManager.getIcon(BasicIconManager.MAIN_SUITE);
        
            case(REF_INSTANCE_MODULE): return BasicIconManager.getIcon(BasicIconManager.MAIN_MODULE);
            case(REF_TOP_MODULE) :return BasicIconManager.getIcon(BasicIconManager.MAIN_MODULE);
            case(REF_HARDWARE_MODULE) :return BasicIconManager.getIcon(BasicIconManager.MAIN_MODULE);
            case(REF_ENTITY) :return BasicIconManager.getIcon(BasicIconManager.MAIN_ENTITY);
            case(REF_COMPONENT) :return BasicIconManager.getIcon(BasicIconManager.MAIN_ENTITY);
            //case(REF_ENTITY_STATEMENT): return BasicIconManager.getIcon(BasicIconManager.MAIN_PORT);            
            
            case(REF_INSTANCE_PACKAGE): return BasicIconManager.getIcon(BasicIconManager.MAIN_PACKAGE);
            case(REF_PACKAGE_MODULE) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PACKAGE);
            case(REF_PACKAGE_MODULE_BODY) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PACKAGE_BODY);
        
            case(REF_INSTANCE_INTERFACE): return BasicIconManager.getIcon(BasicIconManager.MAIN_INTERFACE);
            case(REF_INSTANCE_PROGRAM): return BasicIconManager.getIcon(BasicIconManager.MAIN_PROGRAM);
            case(REF_INSTANCE_CLASS): return BasicIconManager.getIcon(BasicIconManager.MAIN_CLASS);

            
            case(REF_PORT_TOP) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PORT_IN);
            case(REF_PORT_INPUT) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PORT_IN);
            case(REF_PORT_OUTPUT) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PORT_OUT);
            
        
            case(REF_MODINSTANCE_TOP) :return BasicIconManager.getIcon(BasicIconManager.MAIN_SUPERMODULE);
            case(REF_MODINSTANCE_CONNECT) :return BasicIconManager.getIcon(BasicIconManager.MAIN_CONNECTION);
            case(REF_INTERFACE_CONNECT) :return BasicIconManager.getIcon(BasicIconManager.MAIN_INTERFACE_CONNECTION);

            case(REF_SUPERMODULE)      :return BasicIconManager.getIcon(BasicIconManager.MAIN_SUPERMODULE);
            case(REF_CONNECTOR_GENERATE) : return BasicIconManager.getIcon(BasicIconManager.MAIN_ASSIGN);
            case(REF_CONNECTOR_MODULE) :return BasicIconManager.getIcon(BasicIconManager.MAIN_CONNECTION);
            case(REF_CORE_PROJECT_HOLDER) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PROJECT);
            //case(REF_CORE_PROJECT_SUITE_PROJECT_LIST) :return BasicIconManager.getIconWithBadge(BasicIconManager.MAIN_FOLDER,BasicIconManager.BADGE_MODULE,8,8);
            case(REF_CORE_PROJECT_FOLDER) :return BasicIconManager.getIcon(BasicIconManager.MAIN_FOLDER);
            case(REF_CORE_PROJECT_HOLDER_ITEM) :return BasicIconManager.getIcon(BasicIconManager.MAIN_PROJECT);
            
            case(REF_PORT_LIST) : return BasicIconManager.getIcon(BasicIconManager.MAIN_PORT_LIST);

            case(REF_FUNCTION_HOLDER) : return BasicIconManager.getIcon(BasicIconManager.MAIN_FUNCTION_HOLDER);
            case(REF_FUNCTION_INSTANCE) : return BasicIconManager.getIcon(BasicIconManager.MAIN_INSTANCE_FUNCTION);
            case(REF_FUNCTION_HEAD) : return BasicIconManager.getIcon(BasicIconManager.MAIN_FUNCTION_DECLARATION);
            case(REF_FUNCTION_BODY) : return BasicIconManager.getIcon(BasicIconManager.MAIN_FUNCTION_BODY);
            case(REF_NO_SORT_LIST) : return BasicIconManager.getIcon(BasicIconManager.MAIN_FOLDER);
            case(REF_ENUMVAR) : return BasicIconManager.getIcon(BasicIconManager.MAIN_ENUM);
            case(REF_PROCESS_STATEMENT) : return BasicIconManager.getIcon(BasicIconManager.MAIN_PROCESS);
            case(REF_ASSIGN) : return BasicIconManager.getIcon(BasicIconManager.MAIN_ASSIGN);
            
            case(REF_VERILOG_FILE) : return BasicIconManager.getIcon(BasicIconManager.MAIN_VHDL);
            case(REF_VHDL_FILE) : return BasicIconManager.getIcon(BasicIconManager.MAIN_VHDL);
            case(REF_BASE_CLASS) : return BasicIconManager.getIcon(BasicIconManager.MAIN_CLASS);
            case(REF_PROPERTY) : return BasicIconManager.getIcon(BasicIconManager.MAIN_PROPERTY);
            case(REF_SEQUENCE) : return BasicIconManager.getIcon(BasicIconManager.MAIN_SEQUENCE);
            case(REF_CLOCKING) : return BasicIconManager.getIcon(BasicIconManager.MAIN_CLOCKING);
            case(REF_ASSERTION) : return BasicIconManager.getIcon(BasicIconManager.MAIN_ASSERTION);
            case(REF_GENERATE_STATEMENT) : return BasicIconManager.getIcon(BasicIconManager.MAIN_GENERATE);
            case(REF_MODPORT) : return BasicIconManager.getIcon(BasicIconManager.MAIN_MODPORT);
            default : return BasicIconManager.getIcon(BasicIconManager.MAIN_TYPE);
        }
    }
	
	public static Image getImage(int type) {
        switch(type)
        {
            case(REF_TYPEVAR) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_TYPE);
            case(REF_TYPE_STRUCT) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_TYPE_RECORD);
            case(REF_TYPE_ARRAY) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_TYPE_ARRAY);
            case(REF_TYPE_ENUM) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_TYPE_ENUM);
            case(REF_TYPE_ARRAY_UNCONSTRAINED) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_TYPE_ARRAY_UNCONSTRAINED);
        
            case(REF_SYSTEMVAR) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_VARIABLE);
            case(REF_CONSTANT) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_CONSTANT);
            case(REF_SIGNAL) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_SIGNAL);
            case(REF_VARIABLE) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_VARIABLE);
        
        
            case(REF_PROJECT_BASIC) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROJECT);
            case(REF_PROJECT_LIBRARY) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROJECT);
            case(REF_PROJECT_SUITE) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_SUITE);
        
            case(REF_INSTANCE_MODULE): return BasicIconManager.getRealImage(BasicIconManager.MAIN_MODULE);
            case(REF_TOP_MODULE) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_MODULE);
            case(REF_HARDWARE_MODULE) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_MODULE);
            case(REF_ENTITY) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_ENTITY);
            case(REF_COMPONENT) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_ENTITY);
            //case(REF_ENTITY_STATEMENT): return BasicIconManager.getIcon(BasicIconManager.MAIN_PORT);            
            
            case(REF_INSTANCE_PACKAGE): return BasicIconManager.getRealImage(BasicIconManager.MAIN_PACKAGE);
            case(REF_PACKAGE_MODULE) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PACKAGE);
            case(REF_PACKAGE_MODULE_BODY) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PACKAGE_BODY);
        
            case(REF_INSTANCE_INTERFACE): return BasicIconManager.getRealImage(BasicIconManager.MAIN_INTERFACE);
            case(REF_INSTANCE_PROGRAM): return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROGRAM);
            case(REF_INSTANCE_CLASS): return BasicIconManager.getRealImage(BasicIconManager.MAIN_CLASS);

            
            case(REF_PORT_TOP) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PORT_IN);
            case(REF_PORT_INPUT) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PORT_IN);
            case(REF_PORT_OUTPUT) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PORT_OUT);
            
        
            case(REF_MODINSTANCE_TOP) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_SUPERMODULE);
            case(REF_MODINSTANCE_CONNECT) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_CONNECTION);
            case(REF_INTERFACE_CONNECT) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_INTERFACE_CONNECTION);

            case(REF_SUPERMODULE)      :return BasicIconManager.getRealImage(BasicIconManager.MAIN_SUPERMODULE);
            case(REF_CONNECTOR_GENERATE) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_ASSIGN);
            case(REF_CONNECTOR_MODULE) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_CONNECTION);
            case(REF_CORE_PROJECT_HOLDER) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROJECT);
            //case(REF_CORE_PROJECT_SUITE_PROJECT_LIST) :return BasicIconManager.getIconWithBadge(BasicIconManager.MAIN_FOLDER,BasicIconManager.BADGE_MODULE,8,8);
            case(REF_CORE_PROJECT_FOLDER) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_FOLDER);
            case(REF_CORE_PROJECT_HOLDER_ITEM) :return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROJECT);
            
            case(REF_PORT_LIST) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_PORT_LIST);

            case(REF_FUNCTION_HOLDER) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_FUNCTION_HOLDER);
            case(REF_FUNCTION_INSTANCE) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_INSTANCE_FUNCTION);
            case(REF_FUNCTION_HEAD) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_FUNCTION_DECLARATION);
            case(REF_FUNCTION_BODY) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_FUNCTION_BODY);
            case(REF_NO_SORT_LIST) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_FOLDER);
            case(REF_ENUMVAR) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_ENUM);
            case(REF_PROCESS_STATEMENT) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROCESS);
            case(REF_ASSIGN) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_ASSIGN);
            
            case(REF_VERILOG_FILE) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_VHDL);
            case(REF_VHDL_FILE) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_VHDL);
            case(REF_BASE_CLASS) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_CLASS);
            case(REF_PROPERTY) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROPERTY);
            case(REF_SEQUENCE) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_SEQUENCE);
            case(REF_CLOCKING) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_CLOCKING);
            case(REF_ASSERTION) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_ASSERTION);
            case(REF_GENERATE_STATEMENT) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_GENERATE);
            case(REF_MODPORT) : return BasicIconManager.getRealImage(BasicIconManager.MAIN_MODPORT);
            default : return BasicIconManager.getRealImage(BasicIconManager.MAIN_TYPE);
        }
    }
	
}
