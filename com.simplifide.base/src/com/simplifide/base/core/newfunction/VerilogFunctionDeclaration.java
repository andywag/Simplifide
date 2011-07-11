package com.simplifide.base.core.newfunction;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.TypeVar;

public class VerilogFunctionDeclaration extends FunctionDeclaration{

	 public VerilogFunctionDeclaration() {}
	    
	 
	    public VerilogFunctionDeclaration(String name, ReferenceItem<TypeVar> typeRef, ReferenceItem<FunctionPortList> portsR) {
	        super(name,typeRef,portsR);
	        
	    }
	
		public String getTemplatePattern() {
			ReferenceItem portsR = this.getPortsR();
			if (portsR == null || 
				portsR.getObject() == null ||
				portsR.getObject().getnumber() == 0) {
				return this.getname() + "()${cursor}";
			}
			return super.getTemplatePattern();
		}
	    
}
