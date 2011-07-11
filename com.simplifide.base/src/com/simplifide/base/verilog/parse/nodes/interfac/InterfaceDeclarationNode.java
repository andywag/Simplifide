package com.simplifide.base.verilog.parse.nodes.interfac;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.interfac.InterfaceEntity;
import com.simplifide.base.core.interfac.InterfaceInstanceModule;
import com.simplifide.base.core.interfac.InterfaceTopModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopBodyNode;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopDeclarationExternNode;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopDeclarationNormalNode;


//interface_declaration : interface_declaration_normal | interface_declaration_external
public class InterfaceDeclarationNode extends TopASTNode{

		 
    //interface_declaration_normal : interface_nonansi_header (options{greedy=true;}: timeunits_declaration )? 
    //		interface_body "endinterface" colon_identifierq
    public static class Normal extends ModuleTopDeclarationNormalNode {

		@Override
		public InstanceModule createInstanceModule(String name,
				ParseContext context) {
			return new InterfaceInstanceModule(name, context.getRefHandler().getProjectReference());
		}
    	
    }
    
    //interface_declaration_external :  "extern" interface_nonansi_header
    public static class Extern extends ModuleTopDeclarationExternNode {
    	
    }
    
    public static class Body extends ModuleTopBodyNode {

        protected void addNoSortItem(ReferenceItem uitem, ModuleObjectNew module) {
        	ModuleObject obj = uitem.getObject(); 
        	if (obj != null && obj instanceof InterfaceEntity) {
        		InterfaceEntity ent = (InterfaceEntity) obj;
        		module.addReferenceItem(ent.getInstanceModRef());
        	}
       	 	module.addReferenceItem(uitem);
       }
    	
    	protected void addItem(ModuleObjectNew module, ModuleObject item, TopASTNode node ) {
    		if (item instanceof ReferenceItem) {
    			ReferenceItem ref = (ReferenceItem) item;
    			if (ref.getObject() instanceof InterfaceEntity) {
    				InterfaceEntity ent = (InterfaceEntity) ref.getObject();
    				super.addItem(module, ent.getInstanceModRef(), node);
    				return;
    			}
    		}
    		super.addItem(module, item, node);
    		
    	}
    	
		@Override
		public TopModule createModule() {
			return new InterfaceTopModule();
		}
    	
    }
	
}
